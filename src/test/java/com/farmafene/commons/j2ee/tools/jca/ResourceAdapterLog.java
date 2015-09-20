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

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.Work;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceAdapterLog implements ResourceAdapter {

	private static final Logger logger = LoggerFactory.getLogger(ResourceAdapterLog.class);

	private final Map<ActivationSpec, MessageEndpointFactory> mepf;
	private BootstrapContext bootstrapContext;

	public ResourceAdapterLog() {
		this.mepf = new ConcurrentHashMap<ActivationSpec, MessageEndpointFactory>();
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
	 * (non-Javadoc)
	 *
	 * @see javax.resource.spi.ResourceAdapter#start(javax.resource.spi.BootstrapContext)
	 */
	@Override
	public void start(final BootstrapContext ctx) throws ResourceAdapterInternalException {
		logger.info("start(" + ctx + ")! on " + this);
		this.bootstrapContext = ctx;

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.ResourceAdapter#stop()
	 */
	@Override
	public void stop() {
		logger.info("stop()! on " + this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.ResourceAdapter#endpointActivation(javax.resource.
	 *      spi.endpoint.MessageEndpointFactory,
	 *      javax.resource.spi.ActivationSpec)
	 */
	@Override
	public void endpointActivation(final MessageEndpointFactory endpointFactory, final ActivationSpec spec) throws ResourceException {
		logger.info("endpointActivation(" + endpointFactory + ", " + spec + ")");
		this.mepf.put(spec, endpointFactory);
		if (Work.class.isAssignableFrom(spec.getClass())) {
			((Work) spec).run();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.ResourceAdapter#endpointDeactivation(javax.resource
	 *      .spi.endpoint.MessageEndpointFactory,
	 *      javax.resource.spi.ActivationSpec)
	 */
	@Override
	public void endpointDeactivation(final MessageEndpointFactory endpointFactory, final ActivationSpec spec) {
		logger.info("endpointDeactivation(" + endpointFactory + ", " + spec + ")");
		if (Work.class.isAssignableFrom(spec.getClass())) {
			((Work) spec).release();
		}
		this.mepf.remove(spec);

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.ResourceAdapter#getXAResources(javax.resource.spi.
	 *      ActivationSpec[])
	 */
	@Override
	public XAResource[] getXAResources(final ActivationSpec[] specs) throws ResourceException {
		logger.info("getXAResources(" + Arrays.toString(specs) + ")");
		return new XAResource[] {};
	}

	/**
	 * @return the bootstrapContext
	 */
	public BootstrapContext getBootstrapContext() {
		return this.bootstrapContext;
	}

	public MessageEndpointFactory getMessageEndpointFactory(final ActivationSpec spec) {
		return this.mepf.get(spec);
	}
}
