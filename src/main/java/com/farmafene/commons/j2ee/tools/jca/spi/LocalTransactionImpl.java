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

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.farmafene.commons.j2ee.tools.jca.IManagedDriver;

public class LocalTransactionImpl<Driver extends IManagedDriver> implements
		LocalTransaction {
	private static final Logger logger = LoggerFactory
			.getLogger(LocalTransactionImpl.class);
	private ManagedConnectionSPI<Driver, ? extends ConnectionRequestInfo, ? extends ManagedConnectionMetaData> managedConnection;

	LocalTransactionImpl() {

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
	 * @see javax.resource.spi.LocalTransaction#begin()
	 */
	@Override
	public void begin() throws ResourceException {
		try {
			managedConnection.started(getXid(), XAResource.TMONEPHASE);
		} catch (XAException xaexp) {
			throw new ResourceException(xaexp);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.LocalTransaction#commit()
	 */
	@Override
	public void commit() throws ResourceException {
		try {
			managedConnection.commit(getXid(), true);
		} catch (XAException e) {
			ResourceException re = new ResourceException(e);
			logger.error("Error en commit", re);
			throw re;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.LocalTransaction#rollback()
	 */
	@Override
	public void rollback() throws ResourceException {
		try {
			managedConnection.rollback(getXid());
		} catch (XAException e) {
			ResourceException re = new ResourceException(e);
			logger.error("Error en rollback", re);
			throw re;
		}
	}

	private Xid getXid() {
		return null;
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
