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
package com.farmafene.commons.j2ee.tools.jca;

import javax.resource.spi.BootstrapContext;
import javax.resource.spi.XATerminator;
import javax.resource.spi.work.WorkManager;
import javax.transaction.TransactionSynchronizationRegistry;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class BootstrapContextFactoryBean implements InitializingBean,
		FactoryBean<BootstrapContext> {

	private XATerminator xATerminator;
	private WorkManager workManager;
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	private IWorkContextValidator iWorkContextValidator;

	public BootstrapContextFactoryBean() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
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
	public BootstrapContext getObject() throws Exception {
		BootstrapContextImpl ctx = new BootstrapContextImpl();
		ctx.setWorkManager(workManager);
		ctx.setxATerminator(xATerminator);
		ctx.setTransactionSynchronizationRegistry(transactionSynchronizationRegistry);
		ctx.setIWorkContextValidator(iWorkContextValidator);
		return ctx;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return BootstrapContext.class;
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
		Assert.notNull(workManager, "Debe Establecerse el WorkManager");
		Assert.notNull(xATerminator, "Debe Establecerse el transactionManager");
		Assert.notNull(transactionSynchronizationRegistry,
				"Debe establecerse el TransactionSynchronizationRegistry");
		Assert.notNull(iWorkContextValidator,
				"Debe establecerse el IWorkContextValidator");
	}

	/**
	 * @return the xaTerminator
	 */
	public XATerminator getXATerminator() {
		return xATerminator;
	}

	/**
	 * @param xaTerminator
	 *            the xaTerminator to set
	 */
	public void setXATerminator(XATerminator xaTerminator) {
		this.xATerminator = xaTerminator;
	}

	/**
	 * @return the workManager
	 */
	public WorkManager getWorkManager() {
		return workManager;
	}

	/**
	 * @param workManager
	 *            the workManager to set
	 */
	public void setWorkManager(WorkManager workManager) {
		this.workManager = workManager;
	}

	/**
	 * @return the transactionSynchronizationRegistry
	 */
	public TransactionSynchronizationRegistry getTransactionSynchronizationRegistry() {
		return transactionSynchronizationRegistry;
	}

	/**
	 * @param transactionSynchronizationRegistry
	 *            the transactionSynchronizationRegistry to set
	 */
	public void setTransactionSynchronizationRegistry(
			TransactionSynchronizationRegistry transactionSynchronizationRegistry) {
		this.transactionSynchronizationRegistry = transactionSynchronizationRegistry;
	}

	/**
	 * @return the iWorkContextValidator
	 */
	public IWorkContextValidator getIWorkContextValidator() {
		return iWorkContextValidator;
	}

	/**
	 * @param iWorkContextValidator
	 *            the iWorkContextValidator to set
	 */
	public void setIWorkContextValidator(
			IWorkContextValidator iWorkContextValidator) {
		this.iWorkContextValidator = iWorkContextValidator;
	}
}
