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

import javax.resource.spi.ManagedConnectionFactory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class BTMConectionFactoryFactoryBean implements FactoryBean<Object>, InitializingBean, DisposableBean {

	private BTMConnectionManager connectionManager;
	private ManagedConnectionFactory managedConnectionFactory;
	private int minPoolSize = 10;
	private int maxPoolSize = 10;
	private String name;

	public BTMConectionFactoryFactoryBean() {
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
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		this.connectionManager.close();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		this.connectionManager = new BTMConnectionManager();
		this.connectionManager.setClassName(this.managedConnectionFactory.getClass().getCanonicalName());
		this.connectionManager.setMinPoolSize(this.minPoolSize);
		this.connectionManager.setMaxPoolSize(this.maxPoolSize);
		this.connectionManager.setManagedConnectionFactory(this.managedConnectionFactory);
		this.connectionManager.setUniqueName(this.name);
		this.connectionManager.setAllowLocalTransactions(true);
		this.connectionManager.setApplyTransactionTimeout(true);
		this.connectionManager.setShareTransactionConnections(true);
		this.connectionManager.setIgnoreRecoveryFailures(true);
		this.connectionManager.init();
		// private volatile boolean automaticEnlistingEnabled = true;
		// private volatile boolean useTmJoin = true;
		// private volatile boolean deferConnectionRelease = true;
		// private volatile int acquisitionInterval = 1;
		// private volatile boolean allowLocalTransactions = false;
		// private volatile boolean applyTransactionTimeout = false;
		// private volatile boolean shareTransactionConnections = false;
		// private volatile boolean ignoreRecoveryFailures = false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public Object getObject() throws Exception {
		return this.connectionManager.getConnectionFactory();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return Object.class;
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

	/**
	 * @param managedConnectionFactory the managedConnectionFactory to set
	 */
	public void setManagedConnectionFactory(final ManagedConnectionFactory managedConnectionFactory) {
		this.managedConnectionFactory = managedConnectionFactory;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the connectionManager
	 */
	public BTMConnectionManager getConnectionManager() {
		return this.connectionManager;
	}

	/**
	 * @return the minPoolSize
	 */
	public int getMinPoolSize() {
		return this.minPoolSize;
	}

	/**
	 * @param minPoolSize the minPoolSize to set
	 */
	public void setMinPoolSize(final int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	/**
	 * @return the maxPoolSize
	 */
	public int getMaxPoolSize() {
		return this.maxPoolSize;
	}

	/**
	 * @param maxPoolSize the maxPoolSize to set
	 */
	public void setMaxPoolSize(final int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
}
