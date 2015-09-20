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

import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivationSpecLog implements ActivationSpec {

	private ResourceAdapter resourceAdapter;
	private static final Logger logger = LoggerFactory.getLogger(ActivationSpecLog.class);

	public ActivationSpecLog() {

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
	 * @see javax.resource.spi.ResourceAdapterAssociation#getResourceAdapter()
	 */
	@Override
	public ResourceAdapter getResourceAdapter() {
		logger.info("getResourceAdapter()");
		return this.resourceAdapter;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.ResourceAdapterAssociation#setResourceAdapter(javax.resource.spi.ResourceAdapter)
	 */
	@Override
	public void setResourceAdapter(final ResourceAdapter ra) throws ResourceException {
		this.resourceAdapter = ra;
		logger.info("setResourceAdapter(" + ra + ")");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.ActivationSpec#validate()
	 */
	@Override
	public void validate() throws InvalidPropertyException {
		logger.info("validate()");
	}
}
