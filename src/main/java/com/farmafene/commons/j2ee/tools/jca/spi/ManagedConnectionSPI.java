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
package com.farmafene.commons.j2ee.tools.jca.spi;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.security.auth.Subject;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.farmafene.commons.j2ee.tools.jca.IManagedDriver;

public abstract class ManagedConnectionSPI<Driver extends IManagedDriver, CRI extends ConnectionRequestInfo, MCMD extends ManagedConnectionMetaDataSPI>
		implements ManagedConnection, ICloseable {

	private static final Logger logger = LoggerFactory.getLogger(ManagedConnectionSPI.class);
	private PrintWriter logWriter;
	private CRI connectionRequestInfo;
	private Set<Driver> connectionHandlers;
	private UUID uUID;
	private List<ConnectionEventListener> connectionEventListeners;
	private MCMD managedConnectionMetaData;

	private LocalTransactionImpl<Driver> localTx;
	private ProxyFactory pf;

	private XAResourceImpl<Driver> xaResource;
	private boolean supportedXATransactions;
	private boolean supportedLocalTransactions;
	private boolean readOnly;

	public ManagedConnectionSPI() {
		this.uUID = UUID.randomUUID();
		this.connectionEventListeners = new LinkedList<ConnectionEventListener>();
		this.connectionHandlers = new HashSet<Driver>();
		this.supportedXATransactions = true;
		this.supportedLocalTransactions = true;
		pf = new ProxyFactory();
		pf.setInterfaces(getClass().getInterfaces());
	}

	/**
	 * Destroy the connection
	 * 
	 * @throws IOException
	 */
	public abstract void close() throws IOException;

	/**
	 * Open de connection
	 * 
	 * @throws IOException
	 */
	public abstract void open() throws IOException;

	/**
	 * Realiza el Start
	 * 
	 * @param xid
	 * @param flag
	 * @throws IOException Communication failure
	 * @throws XAException An error has occurred. Possible exceptions are XA_RB*,
	 *                     XAER_RMERR, XAER_RMFAIL, XAER_DUPID, XAER_OUTSIDE,
	 *                     XAER_NOTA, XAER_INVAL, or XAER_PROTO.
	 */
	protected abstract void doStart(Xid xid, int flag) throws IOException, XAException;

	/**
	 * Realiza el commit
	 * 
	 * @param id
	 * @param onePhase
	 * @throws IOException Communication failure
	 * @throws XAException - An error has occurred. Possible XAException values are
	 *                     XAER_RMERR, XAER_RMFAIL, XAER_NOTA, XAER_INVAL,
	 *                     XAER_PROTO, or XA_RB*.
	 */
	protected abstract void doComit(Xid id, boolean onePhase) throws IOException, XAException;

	/**
	 * Realiza el Forget
	 * 
	 * @param xId
	 * 
	 * @throws IOException Communication failure
	 * @throws XAException - An error has occurred. Possible exception values are
	 *                     XAER_RMERR, XAER_RMFAIL, XAER_NOTA, XAER_INVAL, or
	 *                     XAER_PROTO.
	 */
	protected abstract void doForget(Xid xId) throws IOException, XAException;

	/**
	 * 
	 * @param id
	 * @throws IOException Communication failure
	 * @throws XAException - An error has occurred. Possible exception values are:
	 *                     XA_RB*, XAER_RMERR, XAER_RMFAIL, XAER_NOTA, XAER_INVAL,
	 *                     or XAER_PROTO.
	 */
	protected abstract void doPrepare(Xid id) throws IOException, XAException;

	/**
	 * Realiza el rollback
	 * 
	 * @param id
	 * @throws IOException Communication failure
	 * @throws XAException - An error has occurred. Possible XAExceptions are
	 *                     XA_HEURHAZ, XA_HEURCOM, XA_HEURRB, XA_HEURMIX,
	 *                     XAER_RMERR, XAER_RMFAIL, XAER_NOTA, XAER_INVAL, or
	 *                     XAER_PROTO. If the transaction branch is already marked
	 *                     rollback-only the resource manager may throw one of the
	 *                     XA_RB* exceptions. Upon return, the resource manager has
	 *                     rolled back the branch's work and has released all held
	 *                     resources.
	 */
	protected abstract void doRollback(Xid id) throws IOException, XAException;

	/**
	 * 
	 * @param flag - One of TMSTARTRSCAN, TMENDRSCAN, TMNOFLAGS. TMNOFLAGS must be
	 *             used when no other flags are set in the parameter. These
	 *             constants are defined in javax.transaction.xa.XAResource
	 *             interface.
	 * @return The resource manager returns zero or more XIDs of the transaction
	 *         branches that are currently in a prepared or heuristically completed
	 *         state. If an error occurs during the operation, the resource manager
	 *         should throw the appropriate XAException.
	 * @throws IOException Communication failure
	 * @throws XAException - An error has occurred. Possible values are XAER_RMERR,
	 *                     XAER_RMFAIL, XAER_INVAL, and XAER_PROTO.
	 */
	protected Xid[] doRecover(int flag) throws IOException, XAException {
		List<Xid> aux = new ArrayList<Xid>();
		readFromRecovery(aux);
		if (aux.size() > 0) {
			// TODO recover(int arg0) throws XAException
		}
		return aux.toArray(new Xid[0]);
	}

	/**
	 * 
	 * @param xid
	 * @param flags - One of TMSUCCESS, TMFAIL, or TMSUSPEND.
	 * 
	 * @throws IOException Communication failure
	 * @throws XAException - An error has occurred. Possible XAException values are
	 *                     XAER_RMERR, XAER_RMFAIL, XAER_NOTA, XAER_INVAL,
	 *                     XAER_PROTO, or XA_RB*.
	 */
	protected void doEnd(Xid xid, int flags) throws IOException, XAException {
		switch (flags) {
		case XAResource.TMSUCCESS:
			break;
		case XAResource.TMFAIL:
			break;
		case XAResource.TMSUSPEND:
			break;
		}

	}

	/*
	 * Propios del objeto
	 */

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connectionRequestInfo == null) ? 0 : connectionRequestInfo.hashCode());
		result = prime * result + ((uUID == null) ? 0 : uUID.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		ManagedConnectionSPI<Driver, CRI, MCMD> other = (ManagedConnectionSPI<Driver, CRI, MCMD>) obj;
		if (connectionRequestInfo == null) {
			if (other.connectionRequestInfo != null)
				return false;
		} else if (!connectionRequestInfo.equals(other.connectionRequestInfo))
			return false;
		if (uUID == null) {
			if (other.uUID != null)
				return false;
		} else if (!uUID.equals(other.uUID))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("=[");
		sb.append("UUID=").append(this.uUID);
		sb.append(", with=").append(connectionHandlers.size()).append(" active handlers");
		sb.append(", ").append(this.connectionRequestInfo);
		sb.append("]");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnection#getConnection(javax.security.auth.Subject,
	 *      javax.resource.spi.ConnectionRequestInfo)
	 */
	@Override
	public Object getConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
		ConnectionSPI<Driver> con = new ConnectionSPI<Driver>();
		con.setManagedConnection(this);
		Driver driver = pf.newInstance(con);
		con.setDriver(driver);
		connectionHandlers.add(driver);
		return driver;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnection#destroy()
	 */
	@Override
	public void destroy() throws ResourceException {
		try {
			this.close();
		} catch (IOException e) {
			ResourceException re = new ResourceException(e);
			logger.error("Error en el cierre", re);
			throw re;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnection#cleanup()
	 */
	@Override
	public void cleanup() throws ResourceException {
		for (Driver con : connectionHandlers) {
			try {
				con.close();
			} catch (IOException e) {
				logger.warn("Error al cerrar la conexión!!", e);
			}
		}
		this.connectionHandlers.clear();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnection#associateConnection(java.lang.Object)
	 */
	@Override
	public void associateConnection(Object connection) throws ResourceException {
		@SuppressWarnings("unchecked")
		ConnectionSPI<Driver> con = (ConnectionSPI<Driver>) Proxy.getInvocationHandler(connection);
		XAResourceImpl<Driver> xaRes = con.getManagedConnection().xaResource;
		LocalTransactionImpl<Driver> localRes = con.getManagedConnection().localTx;
		con.getManagedConnection().connectionHandlers.remove((Object)con);
		localRes.setManagedConnection(this);
		xaRes.setManagedConnection(this);
		con.setManagedConnection(this);
		this.connectionHandlers.add(pf.newInstance(con));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnection#addConnectionEventListener(javax.resource.spi.ConnectionEventListener)
	 */
	@Override
	public void addConnectionEventListener(ConnectionEventListener listener) {
		connectionEventListeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnection#removeConnectionEventListener(javax.resource.spi.ConnectionEventListener)
	 */
	@Override
	public void removeConnectionEventListener(ConnectionEventListener listener) {
		connectionEventListeners.remove(listener);
	}

	private void sendEvent(int type, Driver connection) {
		ConnectionEvent event = new ConnectionEvent(this, type);
		for (ConnectionEventListener listener : connectionEventListeners) {
			switch (type) {
			case ConnectionEvent.CONNECTION_CLOSED:
				event.setConnectionHandle(connection);
				listener.connectionClosed(event);
				connectionHandlers.remove(connection);
				break;
			case ConnectionEvent.CONNECTION_ERROR_OCCURRED:
				listener.connectionErrorOccurred(event);
				break;
			case ConnectionEvent.LOCAL_TRANSACTION_COMMITTED:
				listener.localTransactionCommitted(event);
				break;
			case ConnectionEvent.LOCAL_TRANSACTION_ROLLEDBACK:
				listener.localTransactionRolledback(event);
				break;
			case ConnectionEvent.LOCAL_TRANSACTION_STARTED:
				listener.localTransactionStarted(event);
				break;
			}
		}
	}

	void close(Driver con) {
		connectionHandlers.remove(con);
		sendEvent(ConnectionEvent.CONNECTION_CLOSED, con);
	}

	private void committed() {
		sendEvent(ConnectionEvent.LOCAL_TRANSACTION_COMMITTED, null);
	}

	void error() {
		sendEvent(ConnectionEvent.CONNECTION_ERROR_OCCURRED, null);
	}

	private void rolledback() {
		sendEvent(ConnectionEvent.LOCAL_TRANSACTION_ROLLEDBACK, null);
	}

	private void started() {
		sendEvent(ConnectionEvent.LOCAL_TRANSACTION_STARTED, null);
	}

	protected void started(Xid xid, int flag) throws XAException {
		if (XAResource.TMRESUME != flag) {
			try {
				doStart(xid, flag);
				started();
			} catch (IOException ioe) {
				error();
				XAException e = new XAException("Error en el start");
				e.errorCode = XAException.XA_RBCOMMFAIL;
				e.initCause(ioe);
				throw e;
			} catch (XAException exp) {
				throw exp;
			} catch (Exception exp) {
				XAException e = new XAException("Error en el start");
				e.errorCode = XAException.XAER_RMERR;
				e.initCause(exp);
				throw e;
			}
		}
	}

	protected void end(Xid xid, int flags) throws XAException {
		try {
			doEnd(xid, flags);
		} catch (IOException exp) {
			error();
			XAException e = new XAException("Error en el end");
			e.errorCode = XAException.XA_RBCOMMFAIL;
			e.initCause(exp);
			throw e;
		} catch (XAException e) {
			throw e;
		} catch (Exception ioe) {
			XAException e = new XAException("Error en el end");
			e.errorCode = XAException.XAER_RMERR;
			e.initCause(ioe);
			throw e;
		}
	}

	protected void commit(Xid id, boolean onePhase) throws XAException {
		try {
			doComit(id, onePhase);
			removeFromRecovery(id);
			committed();
		} catch (IOException exp) {
			error();
			XAException e = new XAException("Error en el commit");
			e.errorCode = XAException.XA_RBCOMMFAIL;
			e.initCause(exp);
			throw e;
		} catch (XAException exp) {
			throw exp;
		} catch (Exception ioe) {
			XAException e = new XAException("Error en el commit");
			e.errorCode = XAException.XAER_RMERR;
			e.initCause(ioe);
			throw e;
		}
	}

	protected void forget(Xid xId) throws XAException {
		try {
			doForget(xId);
			rolledback();
		} catch (IOException ioe) {
			error();
			XAException e = new XAException("Error en el forget");
			e.errorCode = XAException.XAER_RMERR;
			e.initCause(ioe);
			throw e;
		} catch (XAException exp) {
			throw exp;
		} catch (Exception ioe) {
			XAException e = new XAException("Error en el forget");
			e.errorCode = XAException.XAER_RMERR;
			e.initCause(ioe);
			throw e;
		}
	}

	protected int prepare(Xid id) throws XAException {
		try {
			doPrepare(id);
			addToRecovery(id);
			return readOnly ? XAResource.XA_RDONLY : XAResource.XA_OK;
		} catch (IOException ioe) {
			error();
			XAException e = new XAException("Error en el prepare");
			e.errorCode = XAException.XA_RBCOMMFAIL;
			e.initCause(ioe);
			throw e;
		} catch (XAException exp) {
			throw exp;
		} catch (Exception ioe) {
			XAException e = new XAException("Error en el prepare");
			e.errorCode = XAException.XAER_RMERR;
			e.initCause(ioe);
			throw e;
		}
	}

	void rollback(Xid id) throws XAException {
		try {
			doRollback(id);
			removeFromRecovery(id);
			rolledback();
		} catch (IOException ioe) {
			error();
			XAException e = new XAException("Error en RollBack");
			e.errorCode = XAException.XAER_RMERR;
			e.initCause(ioe);
			throw e;
		} catch (XAException exp) {
			throw exp;
		} catch (Exception ioe) {
			XAException e = new XAException("Error en RollBack");
			e.errorCode = XAException.XAER_RMERR;
			e.initCause(ioe);
			throw e;
		}
	}

	Xid[] recover(int flag) throws XAException {
		try {
			return doRecover(flag);
		} catch (IOException ioe) {
			error();
			XAException e = new XAException("Error en recover");
			e.errorCode = XAException.XAER_RMERR;
			e.initCause(ioe);
			throw e;
		} catch (XAException exp) {
			throw exp;
		} catch (Exception ioe) {
			XAException e = new XAException("Error en Recover");
			e.errorCode = XAException.XAER_RMERR;
			e.initCause(ioe);
			throw e;
		}
	}

	private void addToRecovery(Xid id) {
		// TODO Auto-generated method stub

	}

	private void removeFromRecovery(Xid id) {
		// TODO Auto-generated method stub

	}

	private void readFromRecovery(List<Xid> aux) {
		// TODO Auto-generated method stub
	}

	/*
	 * Setters y Getters
	 */
	void setLocalTx(LocalTransactionImpl<Driver> localTx) {
		this.localTx = localTx;
	}

	void setXAResource(XAResourceImpl<Driver> xaResource) {
		this.xaResource = xaResource;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnection#getXAResource()
	 */
	@Override
	public XAResource getXAResource() throws ResourceException {
		if (!isSupportedXATransactions()) {
			ResourceException e = new NotSupportedException("XA Transactions not Supported!");
			throw e;
		}
		return xaResource;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnection#getLocalTransaction()
	 */
	@Override
	public LocalTransaction getLocalTransaction() throws ResourceException {
		if (!isSupportedLocalTransactions()) {
			ResourceException e = new NotSupportedException("Local Transactions not Supported!");
			throw e;
		}
		return localTx;
	}

	/**
	 * @return the uUID
	 */
	public UUID getUUID() {
		return uUID;
	}

	/**
	 * @return the connectionRequestInfo
	 */
	public CRI getConnectionRequestInfo() {
		return connectionRequestInfo;
	}

	/**
	 * @param connectionRequestInfo the connectionRequestInfo to set
	 */
	public void setConnectionRequestInfo(CRI connectionRequestInfo) {
		this.connectionRequestInfo = connectionRequestInfo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnection#getMetaData()
	 */
	@Override
	public ManagedConnectionMetaData getMetaData() throws ResourceException {
		return managedConnectionMetaData;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnection#setLogWriter(java.io.PrintWriter)
	 */
	@Override
	public void setLogWriter(PrintWriter out) throws ResourceException {
		this.logWriter = out;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnection#getLogWriter()
	 */
	@Override
	public PrintWriter getLogWriter() throws ResourceException {
		return logWriter;
	}

	/**
	 * @return the supportedXATransactions
	 */
	public boolean isSupportedXATransactions() {
		return supportedXATransactions;
	}

	/**
	 * @param supportedXATransactions the supportedXATransactions to set
	 */
	public void setSupportedXATransactions(boolean supportedXATransactions) {
		this.supportedXATransactions = supportedXATransactions;
	}

	/**
	 * @return the supportedLocalTransactions
	 */
	public boolean isSupportedLocalTransactions() {
		return supportedLocalTransactions;
	}

	/**
	 * @param supportedLocalTransactions the supportedLocalTransactions to set
	 */
	public void setSupportedLocalTransactions(boolean supportedLocalTransactions) {
		this.supportedLocalTransactions = supportedLocalTransactions;
	}

	public void setManagedConnectionMetaData(MCMD managedConnectionMetaData) {
		this.managedConnectionMetaData = managedConnectionMetaData;
	}

	/**
	 * @return the managedConnectionMetaData
	 */
	public MCMD getManagedConnectionMetaData() {
		return managedConnectionMetaData;
	}

	/**
	 * @return the readOnly
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * @param readOnly the readOnly to set
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
}
