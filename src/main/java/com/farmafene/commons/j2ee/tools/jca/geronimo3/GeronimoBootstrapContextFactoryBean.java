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

import org.apache.geronimo.connector.GeronimoBootstrapContext;
import org.apache.geronimo.connector.work.GeronimoWorkManager;
import org.apache.geronimo.transaction.manager.GeronimoTransactionManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class GeronimoBootstrapContextFactoryBean implements InitializingBean, FactoryBean<GeronimoBootstrapContext> {

	private GeronimoTransactionManager transactionManager;
	private GeronimoWorkManager workManager;

	public GeronimoBootstrapContextFactoryBean() {

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
	public GeronimoBootstrapContext getObject() throws Exception {
		return new GeronimoBootstrapContext(this.workManager, this.transactionManager, this.transactionManager);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return GeronimoBootstrapContext.class;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	@Override
	public boolean isSingleton() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.workManager, "Debe Establecerse el WorkManager");
		Assert.notNull(this.transactionManager, "Debe Establecerse el transactionManager");
	}

	/**
	 * @return the transactionManager
	 */
	public GeronimoTransactionManager getTransactionManager() {
		return this.transactionManager;
	}

	/**
	 * @param transactionManager the transactionManager to set
	 */
	public void setTransactionManager(final GeronimoTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	/**
	 * @return the workManager
	 */
	public GeronimoWorkManager getWorkManager() {
		return this.workManager;
	}

	/**
	 * @param workManager the workManager to set
	 */
	public void setWorkManager(final GeronimoWorkManager workManager) {
		this.workManager = workManager;
	}
}
