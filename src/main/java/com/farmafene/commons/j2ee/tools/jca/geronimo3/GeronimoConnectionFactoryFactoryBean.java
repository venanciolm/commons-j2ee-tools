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
package com.farmafene.commons.j2ee.tools.jca.geronimo3;

import javax.resource.spi.ManagedConnectionFactory;

import org.apache.geronimo.connector.outbound.GenericConnectionManager;
import org.apache.geronimo.connector.outbound.SubjectSource;
import org.apache.geronimo.connector.outbound.connectionmanagerconfig.LocalTransactions;
import org.apache.geronimo.connector.outbound.connectionmanagerconfig.NoPool;
import org.apache.geronimo.connector.outbound.connectionmanagerconfig.NoTransactions;
import org.apache.geronimo.connector.outbound.connectionmanagerconfig.PartitionedPool;
import org.apache.geronimo.connector.outbound.connectionmanagerconfig.PoolingSupport;
import org.apache.geronimo.connector.outbound.connectionmanagerconfig.SinglePool;
import org.apache.geronimo.connector.outbound.connectionmanagerconfig.TransactionSupport;
import org.apache.geronimo.connector.outbound.connectionmanagerconfig.XATransactions;
import org.apache.geronimo.connector.outbound.connectiontracking.ConnectionTracker;
import org.apache.geronimo.transaction.manager.RecoverableTransactionManager;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class GeronimoConnectionFactoryFactoryBean implements FactoryBean<Object>, InitializingBean, DisposableBean {

	private Class<?> factoryClass = Object.class;
	
	private RecoverableTransactionManager transactionManager;
	// : none, local, xa
	private String transaction;
	// : none, by-subject ,by-connector-properties
	private String partitionStrategy;
	private boolean pooling = true;
	private int poolMaxSize = 10;
	private int poolMinSize = 0;
	private boolean allConnectionsEqual = true;
	private int connectionMaxWaitMilliseconds = 5000;
	private int connectionMaxIdleMinutes = 15;

	private GenericConnectionManager connectionManager;
	private ManagedConnectionFactory managedConnectionFactory;
	private Object connectionFactory;
	private TransactionSupport transactionSupport;
	private SubjectSource subjectSource;
	private ConnectionTracker connectionTracker;
	private PoolingSupport poolingSupport;

	public GeronimoConnectionFactoryFactoryBean() {

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
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public Object getObject() throws Exception {
		if (this.connectionFactory == null) {
			if (this.connectionManager == null) {
				if (this.transactionManager == null) {
					throw new NullPointerException("transactionManager is null");
				}

				// Instanciate the Geronimo Connection Manager
				this.connectionManager = new GenericConnectionManager(this.transactionSupport, this.poolingSupport, this.subjectSource,
						this.connectionTracker, this.transactionManager, this.managedConnectionFactory, getClass().getName(), getClass()
						.getClassLoader());

				this.connectionManager.doStart();
			}
			this.connectionFactory = this.connectionManager.createConnectionFactory();
		}
		return this.connectionFactory;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		if (this.connectionManager != null) {
			this.connectionManager.doStop();
			this.connectionManager = null;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return factoryClass;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}

	public PoolingSupport getPoolingSupport() {
		return this.poolingSupport;
	}

	/**
	 * Set the pooling support for the Geronimo Connection Manager. Geronimo
	 * provides two kinds of pool: single and partitioned.
	 *
	 * @see org.apache.geronimo.connector.outbound.connectionmanagerconfig.SinglePool
	 * @see org.apache.geronimo.connector.outbound.connectionmanagerconfig.PartitionedPool
	 */
	public void setPoolingSupport(final PoolingSupport support) {
		this.poolingSupport = support;
	}

	public RecoverableTransactionManager getTransactionManager() {
		return this.transactionManager;
	}

	/**
	 * Set the transaction manager for the Geronimo Connection Manager.
	 */
	public void setTransactionManager(final RecoverableTransactionManager manager) {
		this.transactionManager = manager;
	}

	public String getTransaction() {
		return this.transaction;
	}

	public void setTransaction(final String transaction) {
		this.transaction = transaction;
	}

	public TransactionSupport getTransactionSupport() {
		return this.transactionSupport;
	}

	/**
	 * Set the transaction support for the Geronimo Connection Manager. Geronimo
	 * provides in this case three kinds of support like the JCA specification:
	 * no transaction, local transactions, XA transactions.
	 *
	 * @see NoTransactions
	 * @see org.apache.geronimo.connector.outbound.connectionmanagerconfig.LocalTransactions
	 * @see org.apache.geronimo.connector.outbound.connectionmanagerconfig.XATransactions
	 */
	public void setTransactionSupport(final TransactionSupport support) {
		this.transactionSupport = support;
	}

	public ConnectionTracker getConnectionTracker() {
		return this.connectionTracker;
	}

	/**
	 * Set the connection tracker for the Geronimo Connection Manager.
	 */
	public void setConnectionTracker(final ConnectionTracker tracker) {
		this.connectionTracker = tracker;
	}

	/**
	 * Enables/disables container managed security
	 */
	public void setContainerManagedSecurity(final boolean containerManagedSecurity) {
		// TODO: warn for deprecated method
	}

	public SubjectSource getSubjectSource() {
		return this.subjectSource;
	}

	public void setSubjectSource(final SubjectSource subjectSource) {
		this.subjectSource = subjectSource;
	}

	public boolean isPooling() {
		return this.pooling;
	}

	public void setPooling(final boolean pooling) {
		this.pooling = pooling;
	}

	public String getPartitionStrategy() {
		return this.partitionStrategy;
	}

	public void setPartitionStrategy(final String partitionStrategy) {
		this.partitionStrategy = partitionStrategy;
	}

	public int getPoolMaxSize() {
		return this.poolMaxSize;
	}

	public void setPoolMaxSize(final int poolMaxSize) {
		this.poolMaxSize = poolMaxSize;
	}

	public int getPoolMinSize() {
		return this.poolMinSize;
	}

	public void setPoolMinSize(final int poolMinSize) {
		this.poolMinSize = poolMinSize;
	}

	public boolean isAllConnectionsEqual() {
		return this.allConnectionsEqual;
	}

	public void setAllConnectionsEqual(final boolean allConnectionsEqual) {
		this.allConnectionsEqual = allConnectionsEqual;
	}

	public int getConnectionMaxWaitMilliseconds() {
		return this.connectionMaxWaitMilliseconds;
	}

	public void setConnectionMaxWaitMilliseconds(final int connectionMaxWaitMilliseconds) {
		this.connectionMaxWaitMilliseconds = connectionMaxWaitMilliseconds;
	}

	public int getConnectionMaxIdleMinutes() {
		return this.connectionMaxIdleMinutes;
	}

	public void setConnectionMaxIdleMinutes(final int connectionMaxIdleMinutes) {
		this.connectionMaxIdleMinutes = connectionMaxIdleMinutes;
	}

	/**
	 * This method checks all the needed parameters to construct the Geronimo
	 * connection manager which is implemented by the GenericConnectionManager
	 * class. If the transaction support property is not set, the method
	 * configures the connection manager with the no transaction value. If the
	 * pooling support property is not set, the method configures the connection
	 * manager with the no pool value. If the realm bridge is not set, the
	 * method configure the connection manager with an identity realm bridge.
	 *
	 * @see GenericConnectionManager
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// Apply the default value for property if necessary
		if (this.transactionSupport == null) {
			// No transaction
			this.transactionSupport = createTransactionSupport(this.transaction);
		}
		if (this.poolingSupport == null) {
			// No pool
			if (!this.pooling) {
				this.poolingSupport = new NoPool();
			} else {
				if (this.partitionStrategy == null || "none".equalsIgnoreCase(this.partitionStrategy)) {

					// unpartitioned pool
					this.poolingSupport = new SinglePool(this.poolMaxSize, this.poolMinSize, this.connectionMaxWaitMilliseconds,
							this.connectionMaxIdleMinutes, this.allConnectionsEqual, !this.allConnectionsEqual, false);

				} else if ("by-connector-properties".equalsIgnoreCase(this.partitionStrategy)) {

					// partition by contector properties such as username and
					// password on a jdbc connection
					this.poolingSupport = new PartitionedPool(this.poolMaxSize, this.poolMinSize, this.connectionMaxWaitMilliseconds,
							this.connectionMaxIdleMinutes, this.allConnectionsEqual, !this.allConnectionsEqual, false, true, false);
				} else if ("by-subject".equalsIgnoreCase(this.partitionStrategy)) {

					// partition by caller subject
					this.poolingSupport = new PartitionedPool(this.poolMaxSize, this.poolMinSize, this.connectionMaxWaitMilliseconds,
							this.connectionMaxIdleMinutes, this.allConnectionsEqual, !this.allConnectionsEqual, false, false, true);
				} else {
					throw new FatalBeanException("Unknown partition strategy " + this.partitionStrategy);
				}
			}
		}
	}

	private TransactionSupport createTransactionSupport(final String transaction) {
		if (transaction == null || "local".equalsIgnoreCase(transaction)) {
			return LocalTransactions.INSTANCE;
		} else if ("none".equalsIgnoreCase(transaction)) {
			return NoTransactions.INSTANCE;
		} else if ("xa".equalsIgnoreCase(transaction)) {
			return new XATransactions(true, false);
		} else {
			throw new FatalBeanException("Unknown transaction type " + transaction);
		}
	}

	/**
	 * @return the managedConnectionFactory
	 */
	public ManagedConnectionFactory getManagedConnectionFactory() {
		return this.managedConnectionFactory;
	}

	/**
	 * @param managedConnectionFactory the managedConnectionFactory to set
	 */
	public void setManagedConnectionFactory(final ManagedConnectionFactory managedConnectionFactory) {
		this.managedConnectionFactory = managedConnectionFactory;
	}

	/**
	 * @return the factoryClass
	 */
	public Class<?> getFactoryClass() {
		return factoryClass;
	}

	/**
	 * @param factoryClass the factoryClass to set
	 */
	public void setFactoryClass(Class<?> factoryClass) {
		this.factoryClass = factoryClass;
	}
}
