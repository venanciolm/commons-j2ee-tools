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
package com.farmafene.commons.j2ee.tools.jca.spi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

public class ResourceAdapterSPI implements ResourceAdapter {

	@SuppressWarnings("unused")
	private BootstrapContext ctx;
	private Map<ActivationSpec, MessageEndpointFactory> activationSpecs;

	public ResourceAdapterSPI() {
		activationSpecs = new ConcurrentHashMap<ActivationSpec, MessageEndpointFactory>();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("=[");
		sb.append("thread=").append(Thread.currentThread().getName());
		sb.append("]");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activationSpecs == null) ? 0 : activationSpecs.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourceAdapterSPI other = (ResourceAdapterSPI) obj;
		if (activationSpecs == null) {
			if (other.activationSpecs != null)
				return false;
		} else if (!activationSpecs.equals(other.activationSpecs))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ResourceAdapter#start(javax.resource.spi.BootstrapContext)
	 */
	@Override
	public void start(BootstrapContext ctx)
			throws ResourceAdapterInternalException {
		this.ctx = ctx;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ResourceAdapter#stop()
	 */
	@Override
	public void stop() {
		this.ctx = null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ResourceAdapter#endpointActivation(javax.resource.spi.endpoint.MessageEndpointFactory,
	 *      javax.resource.spi.ActivationSpec)
	 */
	@Override
	public void endpointActivation(MessageEndpointFactory endpointFactory,
			ActivationSpec spec) throws ResourceException {
		activationSpecs.put(spec, endpointFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ResourceAdapter#endpointDeactivation(javax.resource.spi.endpoint.MessageEndpointFactory,
	 *      javax.resource.spi.ActivationSpec)
	 */
	@Override
	public void endpointDeactivation(MessageEndpointFactory endpointFactory,
			ActivationSpec spec) {
		activationSpecs.remove(spec);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ResourceAdapter#getXAResources(javax.resource.spi.ActivationSpec[])
	 */
	@Override
	public XAResource[] getXAResources(ActivationSpec[] specs)
			throws ResourceException {
		return new XAResource[0];
	}
}
