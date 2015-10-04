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

import org.apache.geronimo.transaction.manager.GeronimoTransactionManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class GeronimoTransactionManagerFactory implements InitializingBean,
		DisposableBean, FactoryBean<GeronimoTransactionManager> {

	private GeronimoTransactionManager transactionManager;
	private int defaultTransactionTimeoutSeconds = 120;

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("={");
		sb.append("transactionManager=").append(this.transactionManager);
		sb.append(", defaultTransactionTimeoutSeconds=").append(
				this.defaultTransactionTimeoutSeconds);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public GeronimoTransactionManager getObject() throws Exception {
		return this.transactionManager;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		final Class<?> clazz = GeronimoTransactionManager.class;
		return clazz;
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
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		assert 0 < this.defaultTransactionTimeoutSeconds : "Invalid transaction timeout!";
		this.transactionManager = new GeronimoTransactionManager(
				this.defaultTransactionTimeoutSeconds);
	}

	/**
	 * @return the defaultTransactionTimeoutSeconds
	 */
	public int getDefaultTransactionTimeoutSeconds() {
		return this.defaultTransactionTimeoutSeconds;
	}

	/**
	 * @param defaultTransactionTimeoutSeconds
	 *            the defaultTransactionTimeoutSeconds to set
	 */
	public void setDefaultTransactionTimeoutSeconds(
			final int defaultTransactionTimeoutSeconds) {
		this.defaultTransactionTimeoutSeconds = defaultTransactionTimeoutSeconds;
	}
}
