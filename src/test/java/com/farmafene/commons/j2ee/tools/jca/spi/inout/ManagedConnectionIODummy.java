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
package com.farmafene.commons.j2ee.tools.jca.spi.inout;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.resource.spi.XATerminator;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

import org.apache.geronimo.transaction.manager.XidFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.farmafene.commons.j2ee.tools.jca.ActivationSpecLog;
import com.farmafene.commons.j2ee.tools.jca.MessageRALog;
import com.farmafene.commons.j2ee.tools.jca.common.StringPrintStream;
import com.farmafene.commons.j2ee.tools.jca.spi.ConnectionRequestInfoDummy;
import com.farmafene.commons.j2ee.tools.jca.spi.IManagedDriverDummy;
import com.farmafene.commons.j2ee.tools.jca.spi.ManagedConnectionMetaDataSPI;
import com.farmafene.commons.j2ee.tools.jca.spi.ManagedConnectionSPI;

public class ManagedConnectionIODummy
		extends
		ManagedConnectionSPI<IManagedDriverDummy, ConnectionRequestInfoDummy, ManagedConnectionMetaDataSPI>
		implements IManagedDriverDummy {

	private static final Logger logger = LoggerFactory
			.getLogger(ManagedConnectionIODummy.class);
	private static final AtomicInteger count = new AtomicInteger(0);
	private XATerminator xATerminator;
	private ActivationSpecLog activationSpec;
	private XidFactoryImpl xidF = new XidFactoryImpl();
	private Xid external;

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.commons.j2ee.tools.jca.spi.ManagedConnectionSPI#close()
	 */
	@Override
	public void close() throws IOException {
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| close:                                           ||");
			ps.print("|+--------------------------------------------------*/");
			logger.info("{}", ps);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.commons.j2ee.tools.jca.spi.IManagedDriverDummy#echo(java.lang.String)
	 */
	@Override
	public String echo(final String test) {

		MessageRALog m = new MessageRALog();
		String echo = null;
		m.setInput(test);
		if (null != external) {
			m.setTransaction(external);
		}
		try {
			activationSpec.getIn().put(m);
			echo = activationSpec.getOut().take();
		} catch (InterruptedException e) {
			logger.warn("Se ha interrumpido", e);
		}
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.print("|| In:");
			ps.println(test);
			ps.print("|| Out:");
			ps.println(echo);
			ps.print("|+--------------------------------------------------*/");
			logger.info("{}", ps);
		}
		return test;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.commons.j2ee.tools.jca.spi.ManagedConnectionSPI#open()
	 */
	@Override
	public void open() throws IOException {
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| Open  :                                          ||");
			ps.print("|+--------------------------------------------------*/");
			logger.info("{}", ps);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.commons.j2ee.tools.jca.spi.ManagedConnectionSPI#doComit(javax.transaction.xa.Xid,
	 *      boolean)
	 */
	@Override
	protected void doComit(final Xid id, final boolean onePhase)
			throws IOException {
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| Commit:                                          ||");
			ps.print("|| OnePhase: ");
			ps.println(onePhase);
			ps.print("|| Xid: ");
			ps.println(id);
			ps.print("|+--------------------------------------------------*/");
			logger.info("{}", ps);
		}
		try {
			if (null != external) {
				xATerminator.commit(external, onePhase);
				external = null;
			}
		} catch (XAException e) {
			throw new IOException(e);
		}
		int item = count.decrementAndGet();
		logger.info("El valor de enlistados es: {}", item);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.commons.j2ee.tools.jca.spi.ManagedConnectionSPI#doPrepare(javax.transaction.xa.Xid)
	 */
	@Override
	protected void doPrepare(final Xid id) throws IOException {
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| Prepare:                                         ||");
			ps.print("|| Xid: ");
			ps.println(id);
			ps.print("|+--------------------------------------------------*/");
			logger.info("{}", ps);
		}
		try {
			if (null != external) {
				xATerminator.prepare(external);
				external = null;
			}
		} catch (XAException e) {
			throw new IOException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.commons.j2ee.tools.jca.spi.ManagedConnectionSPI#doRollback(javax.transaction.xa.Xid)
	 */
	@Override
	protected void doRollback(final Xid id) throws IOException {
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| Rollback:                                        ||");
			ps.print("|| Xid: ");
			ps.println(id);
			ps.print("|+--------------------------------------------------*/");
			logger.info("{}", ps);
		}
		try {
			if (null != external) {
				xATerminator.rollback(external);
				external = null;
			}
		} catch (XAException e) {
			throw new IOException(e);
		}
		int item = count.decrementAndGet();
		logger.info("El valor de enlistados es: {}", item);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.commons.j2ee.tools.jca.spi.ManagedConnectionSPI#doForget()
	 */
	@Override
	protected void doForget(final Xid id) throws IOException {
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| forget:                                          ||");
			ps.print("|| Xid: ");
			ps.println(id);
			ps.print("|+--------------------------------------------------*/");
			logger.info("{}", ps);
		}
		try {
			if (null != external) {
				xATerminator.forget(external);
				external = null;
			}
		} catch (XAException e) {
			throw new IOException(e);
		}
		int item = count.decrementAndGet();
		logger.info("El valor de enlistados es: {}", item);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.commons.j2ee.tools.jca.spi.ManagedConnectionSPI#doStart(javax.transaction.xa.Xid,
	 *      int)
	 */
	@Override
	protected void doStart(final Xid xid, final int flag) throws IOException {
		if (null == external) {
			external = xidF.createXid();
		}
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| doStart:                                         ||");
			ps.print("|| flags: ");
			ps.println(flag);
			ps.print("|| Xid: ");
			ps.println(xid);
			ps.print("|| Xid: ");
			ps.println(external);
			ps.print("|+--------------------------------------------------*/");
			logger.info("{}", ps);
		}
		int item = count.incrementAndGet();
		logger.info("El valor de enlistados es: {}", item);
	}

	/**
	 * To make test
	 */
	@Override
	public AtomicInteger getAtomicInteger() {
		return count;
	}

	public XATerminator getXATerminator() {
		return xATerminator;
	}

	public void setXATerminator(XATerminator xATerminator) {
		this.xATerminator = xATerminator;
	}

	/**
	 * @return the activationSpec
	 */
	public ActivationSpecLog getActivationSpec() {
		return activationSpec;
	}

	/**
	 * @param activationSpec
	 *            the activationSpec to set
	 */
	public void setActivationSpec(ActivationSpecLog activationSpec) {
		this.activationSpec = activationSpec;
	}
}
