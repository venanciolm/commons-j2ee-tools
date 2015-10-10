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
import java.util.Set;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.security.auth.Subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.farmafene.commons.j2ee.tools.jca.IManagedDriver;

@SuppressWarnings("serial")
public abstract class ManagedConnectionFactorySPI<Driver extends IManagedDriver, CRI extends ConnectionRequestInfo, MCMD extends ManagedConnectionMetaDataSPI>
		implements ManagedConnectionFactory {

	private static final Logger logger = LoggerFactory
			.getLogger(ManagedConnectionFactorySPI.class);
	private PrintWriter logWriter;
	private boolean supportedXATransaction;
	private boolean supportedLocalTransaction;
	private boolean readOnly;

	public ManagedConnectionFactorySPI() {
		setSupportedLocalTransaction(true);
		setSupportedXATransaction(true);
	}

	public abstract ConnectionFactorySPI<Driver> createInstaceOfConnectionFactorySPI();

	public abstract CRI getConnectionRequestInfo();

	public abstract MCMD getManagedConnectionMetaData();

	public abstract ManagedConnectionSPI<Driver, CRI, MCMD> createInstanceOfManagedConnectionSPI();

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("=[");
		sb.append("]");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnectionFactory#createConnectionFactory(javax.resource.spi.ConnectionManager)
	 */
	@Override
	public Object createConnectionFactory(ConnectionManager cxManager) {
		ConnectionFactorySPI<Driver> acf = createInstaceOfConnectionFactorySPI();
		acf.setConnectionManager(cxManager);
		acf.setManagedConnectionFactory(this);
		acf.setConnectionRequestInfo(getConnectionRequestInfo());
		return acf;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnectionFactory#createConnectionFactory()
	 */
	@Override
	public Object createConnectionFactory() throws ResourceException {
		throw new NotSupportedException();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnectionFactory#createManagedConnection(javax.security.auth.Subject,
	 *      javax.resource.spi.ConnectionRequestInfo)
	 */
	@Override
	public ManagedConnection createManagedConnection(Subject subject,
			ConnectionRequestInfo cxRequestInfo) throws ResourceException {
		ManagedConnectionSPI<Driver, CRI, MCMD> mc = createInstanceOfManagedConnectionSPI();
		mc.setConnectionRequestInfo(getConnectionRequestInfo());
		mc.setManagedConnectionMetaData(getManagedConnectionMetaData());
		mc.setSupportedLocalTransactions(isSupportedLocalTransaction());
		if (isSupportedLocalTransaction()) {
			LocalTransactionImpl<Driver> localTx = new LocalTransactionImpl<Driver>();
			localTx.setManagedConnection(mc);
			mc.setLocalTx(localTx);
		}
		mc.setSupportedXATransactions(isSupportedXATransaction());
		if (isSupportedXATransaction()) {
			XAResourceImpl<Driver> xaRes = new XAResourceImpl<Driver>();
			xaRes.setManagedConnection(mc);
			mc.setXAResource(xaRes);
		}
		mc.setReadOnly(isReadOnly());
		try {
			mc.open();
		} catch (IOException e) {
			ResourceException re = new ResourceException(e);
			logger.error("Error al abrir la conexión", re);
			throw re;
		}
		return mc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnectionFactory#matchManagedConnections(java.util.Set,
	 *      javax.security.auth.Subject,
	 *      javax.resource.spi.ConnectionRequestInfo)
	 */
	@Override
	public ManagedConnection matchManagedConnections(
			@SuppressWarnings("rawtypes") Set pool, Subject subject,
			ConnectionRequestInfo cxRequestInfo) throws ResourceException {
		ManagedConnectionSPI<Driver, CRI, MCMD> salida = null;
		if (pool != null && cxRequestInfo instanceof ConnectionRequestInfoSPI) {
			@SuppressWarnings("unchecked")
			Set<ManagedConnectionSPI<Driver, CRI, MCMD>> smc = pool;
			for (ManagedConnectionSPI<Driver, CRI, MCMD> candidate : smc) {
				cxRequestInfo.equals(candidate.getConnectionRequestInfo());
				salida = candidate;
				break;
			}
		}
		return salida;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnectionFactory#setLogWriter(java.io.PrintWriter)
	 */
	@Override
	public void setLogWriter(PrintWriter out) throws ResourceException {
		this.logWriter = out;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnectionFactory#getLogWriter()
	 */
	@Override
	public PrintWriter getLogWriter() throws ResourceException {
		return logWriter;
	}

	/**
	 * @return the supportedXATransaction
	 */
	public boolean isSupportedXATransaction() {
		return supportedXATransaction;
	}

	/**
	 * @param supportedXATransaction
	 *            the supportedXATransaction to set
	 */
	public void setSupportedXATransaction(boolean supportedXATransaction) {
		this.supportedXATransaction = supportedXATransaction;
	}

	/**
	 * @return the supportedLocalTransaction
	 */
	public boolean isSupportedLocalTransaction() {
		return supportedLocalTransaction;
	}

	/**
	 * @param supportedLocalTransaction
	 *            the supportedLocalTransaction to set
	 */
	public void setSupportedLocalTransaction(boolean supportedLocalTransaction) {
		this.supportedLocalTransaction = supportedLocalTransaction;
	}

	/**
	 * @return the readOnly
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * @param readOnly
	 *            the readOnly to set
	 */
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
}
