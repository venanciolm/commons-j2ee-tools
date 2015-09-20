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

import java.util.LinkedList;
import java.util.List;

import javax.resource.NotSupportedException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.HintsContext;
import javax.resource.spi.work.TransactionContext;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkContext;
import javax.resource.spi.work.WorkContextProvider;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.apache.geronimo.transaction.manager.XidFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class InboundTransactionalWork extends InboundWork implements Work, WorkContextProvider {

	private static final Logger logger = LoggerFactory.getLogger(InboundTransactionalWork.class);
	private Xid xid = null;

	public InboundTransactionalWork(final MessageEndpointFactory mepf, final XAResource xaResource) {
		super(mepf, xaResource);
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
		sb.append("xid=").append(this.xid);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.work.WorkContextProvider#getWorkContexts()
	 */
	@Override
	public List<WorkContext> getWorkContexts() {
		final List<WorkContext> ctxs = new LinkedList<WorkContext>();
		final TransactionContext tc = new TransactionContext();
		ctxs.add(new HintsContext());
		ctxs.add(tc);
		try {
			tc.setTransactionTimeout(120);
		} catch (final NotSupportedException e) {
			logger.error("Error en el txTimeout", e);
		}
		this.xid = new XidFactoryImpl().createXid();
		tc.setXid(this.xid);
		return ctxs;
	}

	/**
	 * @return the xid
	 */
	public Xid getXid() {
		return this.xid;
	}

}
