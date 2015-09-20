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

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.ejb.MessageDrivenBean;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.UnavailableException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class DefaultMessageEndPointFactory implements MessageEndpointFactory,
		InitializingBean {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultMessageEndPointFactory.class);

	private Class<?> messageDrivenBeanClass;
	private Class<?> messageDrivenBeanInterface;
	private ResourceAdapter resourceAdapter;
	private TransactionManager transactionManager;
	private IEnlistXAResource iEnlistXAResource;

	public DefaultMessageEndPointFactory() {
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
		sb.append("messageDrivenBeanInterface=").append(
				messageDrivenBeanInterface);
		sb.append(", messageDrivenBeanClass=").append(messageDrivenBeanClass);
		sb.append("}");
		return sb.toString();
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
		Assert.notNull(messageDrivenBeanClass, "MDB Class, not set!");
		Assert.notNull(messageDrivenBeanInterface, "MDB Interface, not set!");
		if (null == iEnlistXAResource) {
			Assert.notNull(transactionManager, "TransactionManager, not set!");
			DefaultEnlistXAResource der = new DefaultEnlistXAResource();
			der.setTransactionManager(transactionManager);
			this.iEnlistXAResource = der;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.endpoint.MessageEndpointFactory#createEndpoint(javax.transaction.xa.XAResource)
	 */
	@Override
	public MessageEndpoint createEndpoint(XAResource xaResource)
			throws UnavailableException {
		logger.info("createEndpoint(" + xaResource + ")");
		return createEndpoint(xaResource, Long.MAX_VALUE);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.endpoint.MessageEndpointFactory#createEndpoint(javax.transaction.xa.XAResource,
	 *      long)
	 */
	@Override
	public MessageEndpoint createEndpoint(XAResource xaResource, long timeout)
			throws UnavailableException {
		logger.info("createEndpoint(" + xaResource + "," + timeout + ")");
		Object a = null;
		Class<?>[] interfaces = null;
		MessageEndpoint proxy = null;
		try {
			a = messageDrivenBeanClass.newInstance();
			if (MessageDrivenBean.class
					.isAssignableFrom(messageDrivenBeanClass)) {
				interfaces = new Class<?>[] { MessageEndpoint.class,
						MessageDrivenBean.class, messageDrivenBeanInterface };
				MessageDrivenBean b = (MessageDrivenBean) a;
				b.setMessageDrivenContext(new DefaultMessageDrivenContext());
			} else {
				interfaces = new Class<?>[] { MessageEndpoint.class,
						messageDrivenBeanInterface };
			}
			MessageInvocationHandler h = new MessageInvocationHandler();
			h.setXAResource(xaResource);
			h.setInner(a);
			proxy = (MessageEndpoint) Proxy.newProxyInstance(resourceAdapter
					.getClass().getClassLoader(), interfaces, h);
		} catch (InstantiationException e) {
			logger.error("Error en la generación del MessageEndPoint", e);
		} catch (IllegalAccessException e) {
			logger.error("Error en la generación del MessageEndPoint", e);
		}
		if (null != xaResource) {
			try {
				if (iEnlistXAResource.isInTransaction()) {
					iEnlistXAResource.enlist(xaResource);
				} else {
					logger.warn("No existe transacción o esta no es activa.");
				}
			} catch (IllegalStateException e) {
				throw new UnavailableException(e);
			} catch (RollbackException e) {
				throw new UnavailableException(e);
			} catch (SystemException e) {
				throw new UnavailableException(e);
			}
		}
		return proxy;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.endpoint.MessageEndpointFactory#isDeliveryTransacted(java.lang.reflect.Method)
	 */
	@Override
	public boolean isDeliveryTransacted(Method method)
			throws NoSuchMethodException {
		logger.info("isDeliveryTransacted(" + method + ")");
		return null != transactionManager;
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

	/**
	 * @return the iEnlistXAResource
	 */
	public IEnlistXAResource getIEnlistXAResource() {
		return iEnlistXAResource;
	}

	/**
	 * @param iEnlistXAResource
	 *            the iEnlistXAResource to set
	 */
	public void setIEnlistXAResource(IEnlistXAResource iEnlistXAResource) {
		this.iEnlistXAResource = iEnlistXAResource;
	}
}
