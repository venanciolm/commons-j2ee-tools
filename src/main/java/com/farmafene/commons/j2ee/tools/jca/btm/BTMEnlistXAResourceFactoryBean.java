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

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.farmafene.commons.j2ee.tools.jca.IEnlistXAResource;

public class BTMEnlistXAResourceFactoryBean implements FactoryBean<IEnlistXAResource>, InitializingBean, DisposableBean {

	private BTMInboundRA inboundRA;
	private Class<?> messageEndpointFactory;
	private String name;

	public BTMEnlistXAResourceFactoryBean() {
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
		this.inboundRA.close();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.messageEndpointFactory, "MessageEndpointFactory Not set!");
		this.inboundRA = new BTMInboundRA();
		this.inboundRA.setClassName(this.messageEndpointFactory.getCanonicalName());
		this.inboundRA.setUniqueName(this.name);
		this.inboundRA.setAllowLocalTransactions(true);
		this.inboundRA.setApplyTransactionTimeout(true);
		this.inboundRA.setShareTransactionConnections(true);
		this.inboundRA.setIgnoreRecoveryFailures(true);
		this.inboundRA.init();
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
	public BTMInboundRA getObject() throws Exception {
		return this.inboundRA;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return IEnlistXAResource.class;
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
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @param messageEndpointFactory the messageEndpointFactory to set
	 */
	public void setMessageEndpointFactory(final Class<?> messageEndpointFactory) {
		this.messageEndpointFactory = messageEndpointFactory;
	}
}
