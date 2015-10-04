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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.resource.ResourceException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageInvocationHandler implements InvocationHandler,
		MessageEndpoint {

	private static Method BEFORE_DELIVERY;
	private static Method AFTER_DELIVERY;
	private static Method RELEASE;
	private static final Logger logger = LoggerFactory
			.getLogger(MessageInvocationHandler.class);

	static {
		try {
			BEFORE_DELIVERY = MessageEndpoint.class.getDeclaredMethod(
					"beforeDelivery", Method.class);
			AFTER_DELIVERY = MessageEndpoint.class.getDeclaredMethod(
					"afterDelivery", (Class[]) null);
			RELEASE = MessageEndpoint.class.getDeclaredMethod("release",
					(Class[]) null);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		}
	}
	private XAResource xAResource;
	private Object inner;

	public MessageInvocationHandler() {

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
	 * @see javax.resource.spi.endpoint.MessageEndpoint#beforeDelivery(java.lang.reflect.Method)
	 */
	@Override
	public void beforeDelivery(Method method) throws NoSuchMethodException,
			ResourceException {
		if (logger.isDebugEnabled()) {
			logger.debug("beforeDelivery(" + method + ")");
		}
		throw new ResourceException(new UnsupportedOperationException(
				"MessageEndpoint#beforeDelivery(java.lang.reflect.Method)"));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.endpoint.MessageEndpoint#afterDelivery()
	 */
	@Override
	public void afterDelivery() throws ResourceException {
		if (logger.isDebugEnabled()) {
			logger.debug("afterDelivery()");
		}
		throw new ResourceException(new UnsupportedOperationException(
				"MessageEndpoint#afterDelivery()"));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.endpoint.MessageEndpoint#release()
	 */
	@Override
	public void release() {
		if (logger.isDebugEnabled()) {
			logger.debug("release()");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object invoke = null;
		if (BEFORE_DELIVERY.equals(method)) {
			beforeDelivery((Method) args[0]);
		} else if (AFTER_DELIVERY.equals(method)) {
			afterDelivery();
		} else if (RELEASE.equals(method)) {
			release();
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("invoke({})", method);
			}
			try {
				invoke = method.invoke(inner, args);
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		}
		return invoke;
	}

	/**
	 * @return the xAResource
	 */
	public XAResource getXAResource() {
		return xAResource;
	}

	/**
	 * @param xAResource
	 *            the xAResource to set
	 */
	public void setXAResource(XAResource xAResource) {
		this.xAResource = xAResource;
	}

	/**
	 * @return the xAResource
	 */
	public XAResource getxAResource() {
		return xAResource;
	}

	/**
	 * @param xAResource
	 *            the xAResource to set
	 */
	public void setxAResource(XAResource xAResource) {
		this.xAResource = xAResource;
	}

	/**
	 * @return the inner
	 */
	public Object getInner() {
		return inner;
	}

	/**
	 * @param inner
	 *            the inner to set
	 */
	public void setInner(Object inner) {
		this.inner = inner;
	}
}
