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

import javax.resource.spi.UnavailableException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageEndPointFactoryLog implements MessageEndpointFactory {

	private static final Logger logger = LoggerFactory.getLogger(MessageEndPointFactoryLog.class);

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

	@Override
	public MessageEndpoint createEndpoint(final XAResource xaResource) throws UnavailableException {
		logger.info("createEndpoint(" + xaResource + ")");
		return null;
	}

	@Override
	public MessageEndpoint createEndpoint(final XAResource xaResource, final long timeout) throws UnavailableException {
		logger.info("createEndpoint(" + xaResource + "," + timeout + ")");
		return null;
	}

	@Override
	public boolean isDeliveryTransacted(final Method method) throws NoSuchMethodException {
		logger.info("isDeliveryTransacted((" + method + ")");
		return false;
	}

}