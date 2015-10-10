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

import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import com.farmafene.commons.j2ee.tools.jca.IManagedDriver;

public class XAResourceImpl<Driver extends IManagedDriver> implements
		XAResource {
	private ManagedConnectionSPI<Driver, ? extends ConnectionRequestInfo, ? extends ManagedConnectionMetaData> managedConnection;

	private int transactionTimeout;

	XAResourceImpl() {

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
		sb.append(managedConnection);
		sb.append("]");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (this.managedConnection == null) ? 0 : this.managedConnection
				.hashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj instanceof XAResourceImpl) {
			equals = this.hashCode() == obj.hashCode();
		}
		return equals;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.xa.XAResource#commit(javax.transaction.xa.Xid,
	 *      boolean)
	 */
	@Override
	public void commit(Xid xId, boolean onePhase) throws XAException {
		managedConnection.commit(xId, onePhase);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.xa.XAResource#end(javax.transaction.xa.Xid, int)
	 */
	@Override
	public void end(Xid xId, int flag) throws XAException {
		managedConnection.end(xId, flag);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.xa.XAResource#forget(javax.transaction.xa.Xid)
	 */
	@Override
	public void forget(Xid xId) throws XAException {
		managedConnection.forget(xId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.xa.XAResource#getTransactionTimeout()
	 */
	@Override
	public int getTransactionTimeout() throws XAException {
		return this.transactionTimeout;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.xa.XAResource#isSameRM(javax.transaction.xa.XAResource)
	 */
	@Override
	public boolean isSameRM(XAResource xAResource) throws XAException {
		return equals(xAResource);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.xa.XAResource#prepare(javax.transaction.xa.Xid)
	 */
	@Override
	public int prepare(Xid xId) throws XAException {
		return managedConnection.prepare(xId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.xa.XAResource#recover(int)
	 */
	@Override
	public Xid[] recover(int flag) throws XAException {
		return managedConnection.recover(flag);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.xa.XAResource#rollback(javax.transaction.xa.Xid)
	 */
	@Override
	public void rollback(Xid xId) throws XAException {
		managedConnection.rollback(xId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.xa.XAResource#setTransactionTimeout(int)
	 */
	@Override
	public boolean setTransactionTimeout(int transactionTimeout)
			throws XAException {
		this.transactionTimeout = transactionTimeout;
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.xa.XAResource#start(javax.transaction.xa.Xid, int)
	 */
	@Override
	public void start(Xid xId, int flag) throws XAException {
		if (TMRESUME != flag) {
			managedConnection.started(xId, flag);
		}
	}

	/**
	 * @return the managedConnection
	 */
	public ManagedConnectionSPI<Driver, ? extends ConnectionRequestInfo, ? extends ManagedConnectionMetaData> getManagedConnection() {
		return managedConnection;
	}

	/**
	 * @param managedConnection
	 *            the managedConnection to set
	 */
	public void setManagedConnection(
			ManagedConnectionSPI<Driver, ? extends ConnectionRequestInfo, ? extends ManagedConnectionMetaData> managedConnection) {
		this.managedConnection = managedConnection;
	}
}
