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

import javax.resource.spi.ActivationSpec;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.TransactionManager;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class DefaultActivationSpecContainer implements InitializingBean,
		DisposableBean {
	private ResourceAdapter resourceAdapter;
	private ActivationSpec activationSpec;
	private MessageEndpointFactory messageEndpointFactory;
	private Class<?> messageDrivenBeanClass;
	private Class<?> messageDrivenBeanInterface;
	private TransactionManager transactionManager;

	public DefaultActivationSpecContainer() {
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
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		resourceAdapter.endpointDeactivation(messageEndpointFactory,
				activationSpec);
	}

	public void start() throws Exception {
		activationSpec.setResourceAdapter(resourceAdapter);
		resourceAdapter.endpointActivation(messageEndpointFactory,
				activationSpec);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(resourceAdapter,
				ResourceAdapter.class.getCanonicalName() + ", not set!");
		Assert.notNull(activationSpec, ActivationSpec.class.getCanonicalName()
				+ ", not set!");
		activationSpec.validate();
		if (null == messageEndpointFactory) {
			Assert.notNull(transactionManager,
					TransactionManager.class.getCanonicalName() + ", not set!");
			Assert.notNull(messageDrivenBeanClass, "MDB Class, not set!");
			Assert.notNull(messageDrivenBeanInterface,
					"MDB Interface, not set!");
			DefaultMessageEndPointFactory epf = new DefaultMessageEndPointFactory();
			epf.setMessageDrivenBeanClass(messageDrivenBeanClass);
			epf.setMessageDrivenBeanInterface(messageDrivenBeanInterface);
			epf.setResourceAdapter(resourceAdapter);
			epf.setTransactionManager(transactionManager);
			messageEndpointFactory = epf;
		}
		start();
	}

	/**
	 * @return the resourceAdapter
	 */
	public ResourceAdapter getResourceAdapter() {
		return resourceAdapter;
	}

	/**
	 * @param resourceAdapter
	 *            the resourceAdapter to set
	 */
	public void setResourceAdapter(ResourceAdapter resourceAdapter) {
		this.resourceAdapter = resourceAdapter;
	}

	/**
	 * @return the activationSpec
	 */
	public ActivationSpec getActivationSpec() {
		return activationSpec;
	}

	/**
	 * @param activationSpec
	 *            the activationSpec to set
	 */
	public void setActivationSpec(ActivationSpec activationSpec) {
		this.activationSpec = activationSpec;
	}

	/**
	 * @return the messageEndpointFactory
	 */
	public MessageEndpointFactory getMessageEndpointFactory() {
		return messageEndpointFactory;
	}

	/**
	 * @param messageEndpointFactory
	 *            the messageEndpointFactory to set
	 */
	public void setMessageEndpointFactory(
			MessageEndpointFactory messageEndpointFactory) {
		this.messageEndpointFactory = messageEndpointFactory;
	}

	/**
	 * @return the messageDrivenBeanClass
	 */
	public Class<?> getMessageDrivenBeanClass() {
		return messageDrivenBeanClass;
	}

	/**
	 * @param messageDrivenBeanClass
	 *            the messageDrivenBeanClass to set
	 */
	public void setMessageDrivenBeanClass(Class<?> messageDrivenBeanClass) {
		this.messageDrivenBeanClass = messageDrivenBeanClass;
	}

	/**
	 * @return the messageDrivenBeanInterface
	 */
	public Class<?> getMessageDrivenBeanInterface() {
		return messageDrivenBeanInterface;
	}

	/**
	 * @param messageDrivenBeanInterface
	 *            the messageDrivenBeanInterface to set
	 */
	public void setMessageDrivenBeanInterface(
			Class<?> messageDrivenBeanInterface) {
		this.messageDrivenBeanInterface = messageDrivenBeanInterface;
	}

	/**
	 * @return the transactionManager
	 */
	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	/**
	 * @param transactionManager
	 *            the transactionManager to set
	 */
	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

}
