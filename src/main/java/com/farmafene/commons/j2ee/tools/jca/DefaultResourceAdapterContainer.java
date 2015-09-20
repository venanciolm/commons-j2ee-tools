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
import javax.resource.spi.ResourceAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class DefaultResourceAdapterContainer implements InitializingBean,
		DisposableBean {
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultResourceAdapterContainer.class);
	private BootstrapContext bootstrapContext;
	private ResourceAdapter resourceAdapter;

	public DefaultResourceAdapterContainer() {

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
		if (resourceAdapter != null) {
			resourceAdapter.stop();
		}
	}

	public void start() throws Exception {
		resourceAdapter.start(bootstrapContext);
		String version = null;
		Package aPackage = Package.getPackage("com.farmafene.commons.jca");
		if (aPackage != null) {
			version = aPackage.getImplementationVersion();
		}

		logger.info("farmafene.com JCA Container (http://www.farmafene.com/) has started running version: "
				+ version);
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
		Assert.notNull(bootstrapContext,
				BootstrapContext.class.getCanonicalName() + ", not set!");
		start();
	}

	/**
	 * @return the bootstrapContext
	 */
	public BootstrapContext getBootstrapContext() {
		return bootstrapContext;
	}

	/**
	 * @param bootstrapContext the bootstrapContext to set
	 */
	public void setBootstrapContext(BootstrapContext bootstrapContext) {
		this.bootstrapContext = bootstrapContext;
	}

	/**
	 * @return the resourceAdapter
	 */
	public ResourceAdapter getResourceAdapter() {
		return resourceAdapter;
	}

	/**
	 * @param resourceAdapter the resourceAdapter to set
	 */
	public void setResourceAdapter(ResourceAdapter resourceAdapter) {
		this.resourceAdapter = resourceAdapter;
	}

}
