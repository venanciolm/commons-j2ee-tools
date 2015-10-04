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
package com.farmafene.commons.j2ee.tools.jca.btm;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ManagedConnection;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bitronix.tm.internal.BitronixSystemException;
import bitronix.tm.resource.common.AbstractXAResourceHolder;
import bitronix.tm.resource.common.ResourceBean;
import bitronix.tm.resource.common.StateChangeListener;
import bitronix.tm.resource.common.TransactionContextHelper;
import bitronix.tm.resource.common.XAResourceHolder;
import bitronix.tm.resource.common.XAStatefulHolder;
import bitronix.tm.utils.Decoder;
import bitronix.tm.utils.MonotonicClock;

import com.farmafene.commons.j2ee.tools.jca.common.StringPrintStream;

public class XAStatefulHolderWrapper extends AbstractXAResourceHolder implements
		ConnectionEventListener, StateChangeListener {

	private static final Logger logger = LoggerFactory
			.getLogger(XAStatefulHolderWrapper.class);
	private ManagedConnection managedConnection;
	private BTMConnectionManager connectionManager;
	private final List<XAResourceHolder> holders;
	private int usageCount;
	private Date lastReleaseDate;
	private Date acquisitionDate;
	private boolean fail = false;

	public XAStatefulHolderWrapper() {
		this.holders = new LinkedList<XAResourceHolder>();
		this.holders.add(this);
		this.addStateChangeEventListener(this);
	}

	public XAStatefulHolderWrapper(final ManagedConnection mc,
			final BTMConnectionManager connectionManager) {
		this();
		this.managedConnection = mc;
		this.connectionManager = connectionManager;
		this.managedConnection.addConnectionEventListener(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("={");
		sb.append("state=").append(
				Decoder.decodeXAStatefulHolderState(getState()));
		sb.append("}");
		return sb.toString();
	}

	private String getEvent(final int event) {
		String salida = null;
		switch (event) {
		case ConnectionEvent.CONNECTION_CLOSED: // 1
			salida = "CONNECTION_CLOSED";
			break;
		case ConnectionEvent.LOCAL_TRANSACTION_STARTED: // 2
			salida = "LOCAL_TRANSACTION_STARTED";
			break;
		case ConnectionEvent.LOCAL_TRANSACTION_COMMITTED: // 3
			salida = "LOCAL_TRANSACTION_COMMITTED";
			break;
		case ConnectionEvent.CONNECTION_ERROR_OCCURRED:// 4
			salida = "CONNECTION_ERROR_OCCURRED";
			break;
		case ConnectionEvent.LOCAL_TRANSACTION_ROLLEDBACK:// 5
			salida = "LOCAL_TRANSACTION_ROLLEDBACK";
			break;
		default:
			salida = "UNKNOW";
		}
		return salida;

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceHolder#getXAResource()
	 */
	@Override
	public XAResource getXAResource() {
		try {
			return this.managedConnection.getXAResource();
		} catch (final ResourceException e) {
			logger.error("Error al obtener el XAResource!!", e);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceHolder#getResourceBean()
	 */
	@Override
	public ResourceBean getResourceBean() {
		return this.connectionManager;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAStatefulHolder#getXAResourceHolders()
	 */
	@Override
	public List<XAResourceHolder> getXAResourceHolders() {
		return this.holders;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAStatefulHolder#close()
	 */
	@Override
	public void close() throws Exception {
		setState(STATE_CLOSED);
		this.managedConnection.destroy();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAStatefulHolder#getLastReleaseDate()
	 */
	@Override
	public Date getLastReleaseDate() {
		return this.lastReleaseDate;
	}

	/**
	 * @see bitronix.tm.resource.common.AbstractXAStatefulHolder#setState(int)
	 */
	@Override
	public void setState(final int state) {
		super.setState(state);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.ConnectionEventListener#connectionClosed(javax.resource
	 *      .spi.ConnectionEvent)
	 */
	@Override
	public void connectionClosed(final ConnectionEvent event) {
		this.usageCount--;
		log("connectionClosed", event);
		try {
			TransactionContextHelper.requeue(this, this.connectionManager);
		} catch (final BitronixSystemException e) {
			logger.error("Error en el cierre de conexión(Requeue)", e);
		}
		if (this.fail) {
			logger.warn("Fail in connection close(): {}", event);
			try {
				this.connectionManager.getXaPool().shrink();
			} catch (final Exception e) {
				logger.error("Error en el shrink!", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.ConnectionEventListener#localTransactionStarted(javax.resource.spi.ConnectionEvent)
	 */
	@Override
	public void localTransactionStarted(final ConnectionEvent event) {
		log("localTransactionStarted", event);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.ConnectionEventListener#localTransactionCommitted(javax.resource.spi.ConnectionEvent)
	 */
	@Override
	public void localTransactionCommitted(final ConnectionEvent event) {
		log("localTransactionCommitted", event);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.ConnectionEventListener#localTransactionRolledback(javax.resource.spi.ConnectionEvent)
	 */
	@Override
	public void localTransactionRolledback(final ConnectionEvent event) {
		log("localTransactionRolledback", event);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.ConnectionEventListener#connectionErrorOccurred(javax.resource.spi.ConnectionEvent)
	 */
	@Override
	public void connectionErrorOccurred(final ConnectionEvent event) {
		log("connectionErrorOccurred", event);
		this.fail = true;
	}

	private void log(final String method, final ConnectionEvent event) {
		if (logger.isDebugEnabled()) {
			StringPrintStream pf = new StringPrintStream();
			pf.println();
			pf.println("/*========================================================+|");
			pf.print("|| Method:           ");
			pf.println(method);
			pf.print("|| Id:               ");
			pf.println(getEvent(event.getId()));
			pf.print("|| Source:           ");
			pf.println(event.getSource());
			pf.print("|| ConnectionHandle: ");
			pf.println(event.getConnectionHandle());
			pf.print("|+=========================================================*/");
			logger.debug("{}", pf);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAStatefulHolder#getConnectionHandle()
	 */
	@Override
	public Object getConnectionHandle() throws Exception {
		final int oldState = getState();

		// Increment the usage count
		this.usageCount++;
		/*
		 * Only transition to STATE_ACCESSIBLE on the first usage. If we're not
		 * sharing connections (default behavior) usageCount is always 1 here,
		 * so this transition will always occur (current behavior unchanged). If
		 * we _are_ sharing connections, and this is _not_ the first usage, it
		 * is valid for the state to already be STATE_ACCESSIBLE. Calling
		 * setState() with STATE_ACCESSIBLE when the state is already
		 * STATE_ACCESSIBLE fails the sanity check in AbstractXAStatefulHolder.
		 * Even if the connection is shared (usageCount > 1), if the state was
		 * STATE_NOT_ACCESSIBLE we transition back to STATE_ACCESSIBLE.
		 */
		if (this.usageCount == 1 || oldState == STATE_NOT_ACCESSIBLE) {
			setState(STATE_ACCESSIBLE);
		}
		//
		final Object connObject = this.managedConnection.getConnection(null,
				this.connectionManager.getConnectionRequestInfo());
		return connObject;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.StateChangeListener#stateChanged(bitronix.tm.resource.common.XAStatefulHolder,
	 *      int, int)
	 */
	@Override
	public void stateChanged(final XAStatefulHolder source, final int oldState,
			final int newState) {
		if (newState == STATE_IN_POOL) {
			if (!this.fail) {
				this.lastReleaseDate = new Date(
						MonotonicClock.currentTimeMillis());
			} else {
				this.lastReleaseDate = new Date();
				this.lastReleaseDate.setTime(0);
			}
		}
		if (oldState == STATE_IN_POOL && newState == STATE_ACCESSIBLE) {
			this.acquisitionDate = new Date(MonotonicClock.currentTimeMillis());
			try {
				TransactionContextHelper.enlistInCurrentTransaction(this);
			} catch (final SystemException e) {
				logger.error("Error al enlistar!", e);
			} catch (final RollbackException e) {
				logger.error("Error al enlistar!", e);
			}
		}
		if (oldState == STATE_NOT_ACCESSIBLE && newState == STATE_ACCESSIBLE) {
			TransactionContextHelper.recycle(this);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.StateChangeListener#stateChanging(bitronix.tm.resource.common.XAStatefulHolder,
	 *      int, int)
	 */
	@Override
	public void stateChanging(final XAStatefulHolder source,
			final int currentState, final int futureState) {
		if (futureState == STATE_IN_POOL) {
			if (this.usageCount > 0) {
				logger.warn("usage count too high (" + this.usageCount
						+ ") on connection returned to pool " + source);
			}
		}
	}

	/**
	 * @return the acquisitionDate
	 */
	public Date getAcquisitionDate() {
		return this.acquisitionDate;
	}
}
