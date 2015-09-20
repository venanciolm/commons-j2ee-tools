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
package com.farmafene.commons.j2ee.tools.jca.btm;

import java.util.concurrent.CountDownLatch;

import javax.resource.spi.UnavailableException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.Work;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.farmafene.commons.j2ee.tools.jca.MessageEPIntf;

public class InboundWork implements Work {

	private static final Logger logger = LoggerFactory
			.getLogger(InboundWork.class);
	private final MessageEndpointFactory mepf;
	private final CountDownLatch latch = new CountDownLatch(1);
	private final XAResource xa;

	public InboundWork(final MessageEndpointFactory mepf, final XAResource xa) {
		this.mepf = mepf;
		this.xa = xa;
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
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		logger.info("run");
		try {
			final MessageEPIntf a = (MessageEPIntf) this.mepf
					.createEndpoint(this.xa);
			a.echo();
		} catch (final UnavailableException e) {
			logger.error("Error en EndPoint", e);
		}
		this.latch.countDown();
		logger.info("done! {}", this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.work.Work#release()
	 */
	@Override
	public void release() {
		logger.info("Ejecutado release y liberando latch");
	}

	/**
	 * @return the latch
	 */
	public CountDownLatch getLatch() {
		return this.latch;
	}
}
