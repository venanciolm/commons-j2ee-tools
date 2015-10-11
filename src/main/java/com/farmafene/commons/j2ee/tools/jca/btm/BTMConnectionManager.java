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

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.farmafene.commons.j2ee.tools.jca.common.StringPrintStream;

import bitronix.tm.internal.XAResourceHolderState;
import bitronix.tm.recovery.RecoveryException;
import bitronix.tm.resource.ResourceRegistrar;
import bitronix.tm.resource.common.RecoveryXAResourceHolder;
import bitronix.tm.resource.common.ResourceBean;
import bitronix.tm.resource.common.XAPool;
import bitronix.tm.resource.common.XAResourceHolder;
import bitronix.tm.resource.common.XAResourceProducer;
import bitronix.tm.resource.common.XAStatefulHolder;

/**
 * @author vlopez
 */
@SuppressWarnings("serial")
public class BTMConnectionManager extends ResourceBean implements
		ConnectionManager, XAResourceProducer {

	private static final Logger logger = LoggerFactory
			.getLogger(BTMConnectionManager.class);
	private ManagedConnectionFactory managedConnectionFactory;
	private boolean failed;
	private XAStatefulHolder revoveryXAStatefulHolder;
	private RecoveryXAResourceHolder recoveryXAResourceHolder;
	private Object connectionFactory;
	private XAPool xaPool;
	private ConnectionRequestInfo connectionRequestInfo;

	public BTMConnectionManager() {
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
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.ConnectionManager#allocateConnection(javax.resource
	 *      .spi.ManagedConnectionFactory,
	 *      javax.resource.spi.ConnectionRequestInfo)
	 */
	@Override
	public Object allocateConnection(final ManagedConnectionFactory mcf,
			final ConnectionRequestInfo cxRequestInfo) throws ResourceException {
		Object connectionHandle = null;
		try {
			if (this.xaPool == null) {
				this.connectionRequestInfo = cxRequestInfo;
				this.xaPool = new XAPool(this, this);
				ResourceRegistrar.register(this);
			}
			connectionHandle = this.xaPool.getConnectionHandle();
		} catch (final Exception e) {
			final ResourceException re = new ResourceException(e);
			logger.error("Excepción en la creación del pool", e);
			throw re;
		}
		return connectionHandle;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.naming.Referenceable#getReference()
	 */
	@Override
	public Reference getReference() throws NamingException {
		return new Reference(ConnectionManager.class.getName(),
				new StringRefAddr("uniqueName", getUniqueName()),
				this.getClassName(), null);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceProducer#startRecovery()
	 */
	@Override
	public XAResourceHolderState startRecovery() throws RecoveryException {
		try {
			this.revoveryXAStatefulHolder = createPooledConnection(null, this);
			this.revoveryXAStatefulHolder
					.setState(XAStatefulHolder.STATE_NOT_ACCESSIBLE);
			this.recoveryXAResourceHolder = new RecoveryXAResourceHolder(
					this.revoveryXAStatefulHolder.getXAResourceHolders().get(0));
			return new XAResourceHolderState(this.recoveryXAResourceHolder,
					this);
		} catch (final Exception e) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| startRecovery                                    ||");
			ps.println("|| Error:                                           ||");
			e.printStackTrace(ps);
			ps.print("|+--------------------------------------------------*/");
			logger.error("{}", ps);
			throw new RecoveryException("startRecovery()", e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceProducer#endRecovery()
	 */
	@Override
	public void endRecovery() throws RecoveryException {
		try {
			this.recoveryXAResourceHolder.close();
			this.revoveryXAStatefulHolder.close();
		} catch (final Exception e) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| endRecovery                                      ||");
			ps.println("|| Error:                                           ||");
			e.printStackTrace(ps);
			ps.print("|+--------------------------------------------------*/");
			logger.error("{}", ps);
			throw new RecoveryException("endRecovery()", e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceProducer#setFailed(boolean)
	 */
	@Override
	public void setFailed(final boolean failed) {
		this.failed = failed;

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceProducer#init()
	 */
	@Override
	public void init() {
		try {
			this.connectionFactory = this.managedConnectionFactory
					.createConnectionFactory(this);
		} catch (final Exception e) {
			setFailed(true);
			logger.error("Error en la ceación de la factoría!", e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceProducer#close()
	 */
	@Override
	public void close() {
		if (this.xaPool == null) {
			return;
		}
		this.xaPool.close();
		this.xaPool = null;
		ResourceRegistrar.unregister(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceProducer#createPooledConnection
	 *      (java.lang.Object, bitronix.tm.resource.common.ResourceBean)
	 */
	@Override
	public XAStatefulHolder createPooledConnection(final Object xaFactory,
			final ResourceBean bean) {
		XAStatefulHolderWrapper w = null;
		try {
			final ManagedConnection mc = this.managedConnectionFactory
					.createManagedConnection(null, getConnectionRequestInfo());
			w = new XAStatefulHolderWrapper(mc, this);
		} catch (final ResourceException e) {
			logger.error("Error in create pooled connection", e);
		}
		return w;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceProducer#findXAResourceHolder(javax
	 *      .transaction.xa.XAResource)
	 */
	@Override
	public XAResourceHolder findXAResourceHolder(final XAResource xaResource) {
		return this.xaPool.findXAResourceHolder(xaResource);
	}

	/**
	 * @return the managedConnectionFactory
	 */
	public ManagedConnectionFactory getManagedConnectionFactory() {
		return this.managedConnectionFactory;
	}

	/**
	 * @param managedConnectionFactory
	 *            the managedConnectionFactory to set
	 */
	public void setManagedConnectionFactory(
			final ManagedConnectionFactory managedConnectionFactory) {
		this.managedConnectionFactory = managedConnectionFactory;
	}

	/**
	 * @return the failed
	 */
	public boolean isFailed() {
		return this.failed;
	}

	/**
	 * @return the connectionFactory
	 */
	public Object getConnectionFactory() {
		return this.connectionFactory;
	}

	ConnectionRequestInfo getConnectionRequestInfo() {
		return this.connectionRequestInfo;
	}

	/**
	 * @return the xaPool
	 */
	public XAPool getXaPool() {
		return this.xaPool;
	}
}
