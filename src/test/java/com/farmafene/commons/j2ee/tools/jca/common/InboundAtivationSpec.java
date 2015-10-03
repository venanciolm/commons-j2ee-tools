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
package com.farmafene.commons.j2ee.tools.jca.common;

import javax.resource.spi.BootstrapContext;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.farmafene.commons.j2ee.tools.jca.ActivationSpecLog;
import com.farmafene.commons.j2ee.tools.jca.ResourceAdapterLog;

public class InboundAtivationSpec extends ActivationSpecLog implements Work {

	private static final Logger logger = LoggerFactory
			.getLogger(InboundAtivationSpec.class);
	private BootstrapContext bootstrapContex;
	private MessageEndpointFactory messageEndpointFactory;

	@Autowired
	private ApplicationContext ctx;

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("={");
		sb.append("bootstrapContex=").append(this.bootstrapContex);
		sb.append(", messageEndpointFactory=").append(
				this.messageEndpointFactory);
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
		this.bootstrapContex = ((ResourceAdapterLog) getResourceAdapter())
				.getBootstrapContext();
		this.messageEndpointFactory = ((ResourceAdapterLog) getResourceAdapter())
				.getMessageEndpointFactory(this);
		logger.info("run(): " + this);
	}

	public InboundWork process(final XAResource ra) throws WorkException {
		final InboundWork w = new InboundWork(this.messageEndpointFactory, ra);
		this.bootstrapContex.getWorkManager().doWork(w);
		return w;

	}

	public InboundWork processTx(final XAResource ra) throws WorkException {
		final InboundWorkIntanciate w = new InboundWorkIntanciate(
				this.messageEndpointFactory, ra);
		w.setApplicationContext(ctx);
		this.bootstrapContex.getWorkManager().doWork(w);
		return w;

	}

	public InboundTransactionalWork processInbound(final XAResource ra)
			throws WorkException {
		final InboundTransactionalWork w = new InboundTransactionalWork(
				this.messageEndpointFactory, ra);
		this.bootstrapContex.getWorkManager().doWork(w);
		return w;

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.work.Work#release()
	 */
	@Override
	public void release() {
		logger.info("release(): " + this);
		this.bootstrapContex = null;
		this.messageEndpointFactory = null;
	}

}
