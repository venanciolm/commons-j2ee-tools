/*
 * Copyright (c) 2009-2015 farmafene.com
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.farmafene.commons.j2ee.tools.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

/**
 * Implementación de XAResource para no-XA JDBC connection emulando XA
 * <p>
 * El flujo del protocolo XA implementa esta máquina de estados:
 * </p>
 *
 * <pre>
 * NO_TX
 *   |
 *   | start(TMNOFLAGS)
 *   |
 *   |       end(TMFAIL)
 * STARTED -------------- NO_TX
 *   |
 *   | end(TMSUCCESS)
 *   |
 *   |    start(TMJOIN)
 * ENDED ---------------- STARTED
 *   |\
 *   | \  commit (one phase)
 *   |  ----------------- NO_TX
 *   |
 *   | prepare()
 *   |
 *   |       commit() or
 *   |       rollback()
 * PREPARED ------------- NO_TX
 * </pre>
 */
public class XAResourceImpl implements XAResource {

	public static final int NO_TX = 0;
	public static final int STARTED = 1;
	public static final int ENDED = 2;
	public static final int PREPARED = 3;
	private final Connection connection;
	private Xid xid;
	private boolean autocommitActiveBeforeStart;
	private int state = NO_TX;
	private int txTimeout = 0;

	/**
	 * Constructor
	 *
	 * @param connection conexión a controlar
	 */
	public XAResourceImpl(final Connection connection) {
		this.connection = connection;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XAResource#forget(Xid)
	 */
	@Override
	public void forget(final Xid xid) throws XAException {
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XAResource#recover(int)
	 */
	@Override
	public Xid[] recover(final int flags) throws XAException {
		return new Xid[0];
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XAResource#isSameRM(XAResource)
	 */
	@Override
	public boolean isSameRM(final XAResource xaResource) throws XAException {
		return xaResource == this;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XAResource#start(Xid, int)
	 */
	@Override
	public void start(final Xid xid, final int flag) throws XAException {
		if (xid == null) {
			final XAErrorsException e = new XAErrorsException("XID no puede ser nulo", XAException.XAER_INVAL);
			throw e;
		}

		if (this.state == NO_TX) {
			if (this.xid != null) {
				final XAErrorsException e = new XAErrorsException("Recurso ya iniciado con XID " + this.xid, XAException.XAER_PROTO);
				throw e;
			}
			if (flag == XAResource.TMJOIN) {
				final XAErrorsException e = new XAErrorsException("Recurso aún no arrancado", XAException.XAER_PROTO);
				throw e;
			}
			this.xid = xid;
		} else if (this.state == STARTED) {
			final XAErrorsException e = new XAErrorsException("Recurso ya arrancado con XID " + this.xid, XAException.XAER_PROTO);
			throw e;
		} else if (this.state == ENDED) {
			if (flag == XAResource.TMNOFLAGS) {
				final XAErrorsException e = new XAErrorsException("Recurso ya registrado con XID " + this.xid, XAException.XAER_DUPID);
				throw e;
			}
			if (!xid.equals(this.xid)) {
				final XAErrorsException e = new XAErrorsException("Recurso ya arrancado con XID " + this.xid
						+ " - no se pueden arrancar más de un XID al mismo tiempo", XAException.XAER_RMERR);
				throw e;
			}
		}
		try {
			this.autocommitActiveBeforeStart = this.connection.getAutoCommit();
			if (this.autocommitActiveBeforeStart) {
				this.connection.setAutoCommit(false);
			}
			this.state = STARTED;
		} catch (final SQLException ex) {
			final XAErrorsException e = new XAErrorsException("No se puede deshabilitar autocommit en connection no-XA ", XAException.XAER_RMERR);
			throw e;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XAResource#end(Xid, int)
	 */
	@Override
	public void end(final Xid xid, final int flag) throws XAException {
		if (xid == null) {
			final XAErrorsException e = new XAErrorsException("XID no puede ser nulo", XAException.XAER_INVAL);
			throw e;

		}
		if (flag == XAResource.TMFAIL) {
			try {
				this.connection.rollback();
				this.state = NO_TX;
				this.xid = null;
				return;
			} catch (final SQLException ex) {
				final XAErrorsException e = new XAErrorsException("Error en rollback del recurso al terminar", XAException.XAER_RMERR, ex);
				throw e;
			}
		}
		this.state = ENDED;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XAResource#prepare(Xid)
	 */
	@Override
	public int prepare(final Xid xid) throws XAException {
		if (xid == null) {
			final XAErrorsException e = new XAErrorsException("XID no puede ser nulo", XAException.XAER_INVAL);
			throw e;
		}

		this.state = PREPARED;
		return XAResource.XA_OK;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XAResource#commit(Xid, boolean)
	 */
	@Override
	public void commit(final Xid xid, final boolean onePhase) throws XAException {
		if (xid == null) {
			final XAErrorsException e = new XAErrorsException("XID no puede ser nulo", XAException.XAER_INVAL);
			throw e;
		}
		try {
			this.connection.commit();
		} catch (final SQLException ex) {
			final XAErrorsException e = new XAErrorsException("Error en el commit", XAException.XAER_RMERR, ex);
			throw e;
		}

		this.state = NO_TX;
		this.xid = null;

		try {
			if (this.autocommitActiveBeforeStart) {
				this.connection.setAutoCommit(true);
			}
		} catch (final SQLException ex) {
			final XAErrorsException e = new XAErrorsException("no se puede establecer autocommit en la connection no-XA", XAException.XAER_RMERR);
			throw e;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XAResource#rollback(Xid)
	 */
	@Override
	public void rollback(final Xid xid) throws XAException {
		if (xid == null) {
			final XAErrorsException e = new XAErrorsException("XID no puede ser nulo", XAException.XAER_INVAL);
			throw e;
		}

		try {
			this.connection.rollback();
			this.state = NO_TX;
			this.xid = null;
		} catch (final SQLException ex) {
			final XAErrorsException e = new XAErrorsException("Error en el prepare del recurso  no-XA", XAException.XAER_RMERR, ex);
			throw e;
		}

		try {
			if (this.autocommitActiveBeforeStart) {
				this.connection.setAutoCommit(true);
			}
		} catch (final SQLException ex) {
			final XAErrorsException e = new XAErrorsException("no se puede establecer autocommit en la connection no-XA", XAException.XAER_RMERR);
			throw e;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("={");
		sb.append("state=").append(pintaEstado());
		sb.append(", txTimeout=").append(this.txTimeout);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Obtiene el descriptivo de un flag de XAResource
	 *
	 * @param flag el flag
	 * @return descriptivo del flag
	 */
	protected String decodeXAResourceFlag(final int flag) {
		switch (flag) {
		case XAResource.TMENDRSCAN:
			return "ENDRSCAN";
		case XAResource.TMFAIL:
			return "FAIL";
		case XAResource.TMJOIN:
			return "JOIN";
		case XAResource.TMNOFLAGS:
			return "NOFLAGS";
		case XAResource.TMONEPHASE:
			return "ONEPHASE";
		case XAResource.TMRESUME:
			return "RESUME";
		case XAResource.TMSTARTRSCAN:
			return "STARTRSCAN";
		case XAResource.TMSUCCESS:
			return "SUCCESS";
		case XAResource.TMSUSPEND:
			return "SUSPEND";
		default:
			return "¡flag invalido(" + flag + ")!";
		}
	}

	/**
	 * Obtiene el string con el estado del Recurso
	 *
	 * @return descriptivo del estado del recurso
	 */
	private String pintaEstado() {
		switch (this.state) {
		case NO_TX:
			return "NO_TX";
		case STARTED:
			return "STARTED";
		case ENDED:
			return "ENDED";
		case PREPARED:
			return "PREPARED";
		default:
			return "!estado invalido (" + this.state + ")!";
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XAResource#getTransactionTimeout()
	 */
	@Override
	public int getTransactionTimeout() throws XAException {
		return this.txTimeout;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XAResource#setTransactionTimeout(int)
	 */
	@Override
	public boolean setTransactionTimeout(final int seconds) throws XAException {
		this.txTimeout = seconds;
		return true;
	}
}
