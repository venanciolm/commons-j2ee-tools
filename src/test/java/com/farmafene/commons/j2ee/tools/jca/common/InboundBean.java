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

import java.io.IOException;

import javax.resource.spi.XATerminator;
import javax.resource.spi.work.WorkManager;
import javax.transaction.TransactionManager;
import javax.transaction.TransactionSynchronizationRegistry;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import com.farmafene.commons.j2ee.tools.jca.ActivationSpecLog;
import com.farmafene.commons.j2ee.tools.jca.spi.IConnectionFactoryDummy;
import com.farmafene.commons.j2ee.tools.jca.spi.IManagedDriverDummy;

public class InboundBean {

	private static final Logger logger = LoggerFactory
			.getLogger(InboundBean.class);

	private ConfigurableApplicationContext ctx;

	public InboundBean() {

	}

	public void initTest() {
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| Comenzamos a validar la BeanFactory        ||");
			ps.print("|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		logger.info("WorkManager:                        {}",
				this.ctx.getBean(WorkManager.class));
		logger.info("TransactionManager:                 {}",
				this.ctx.getBean(TransactionManager.class));
		logger.info("TransactionSynchronizationRegistry: {}",
				this.ctx.getBean(TransactionSynchronizationRegistry.class));
		logger.info("XATerminator:                       {}",
				this.ctx.getBean(XATerminator.class));
		Assert.assertNotNull(this.ctx.getBean(WorkManager.class));
		Assert.assertNotNull(this.ctx.getBean(TransactionManager.class));
		Assert.assertNotNull(this.ctx
				.getBean(TransactionSynchronizationRegistry.class));
		Assert.assertNotNull(this.ctx.getBean(XATerminator.class));
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| Existe el AtivationSpec                    ||");
			ps.print("|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		final ActivationSpecLog ac = this.ctx.getBean(ActivationSpecLog.class);
		Assert.assertNotNull(ac);
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| Existe el conector para inbound            ||");
			ps.print("|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		final IConnectionFactoryDummy dfi = this.ctx.getBean(
				"connectionFactory0", IConnectionFactoryDummy.class);
		Assert.assertNotNull(dfi);
	}

	public void inBoundTest() throws Exception {
		doTest1();
		doTest2();
		doTest3();
		doTest4();
	}

	void doTest1() throws IOException {
		if (logger.isInfoEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| test 1                                     ||");
			ps.print("|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		final IConnectionFactoryDummy dfi = this.ctx.getBean(
				"connectionFactory0", IConnectionFactoryDummy.class);

		IManagedDriverDummy con = dfi.getConnection();
		Assert.assertEquals("Mal inicializado", 0, con.getAtomicInteger()
				.intValue());
		String msg = "hola";
		String aux = con.echo(msg);
		con.close();
		Assert.assertEquals("Error en retorno", aux, msg);
	}

	void doTest2() throws Exception {
		if (logger.isInfoEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| test 2                                     ||");
			ps.print("|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		this.ctx.getBean(TransactionManager.class).begin();
		final IConnectionFactoryDummy dfi = this.ctx.getBean(
				"connectionFactory0", IConnectionFactoryDummy.class);
		IManagedDriverDummy con = dfi.getConnection();
		Assert.assertEquals("Mal inicializado", 1, con.getAtomicInteger()
				.intValue());
		String msg = "hola";
		String aux = con.echo(msg);
		Assert.assertEquals("Error en retorno", aux, msg);
		Assert.assertEquals("Error en tipo", 1, con.getAtomicInteger()
				.intValue());
		con.close();
		this.ctx.getBean(TransactionManager.class).commit();
		Assert.assertEquals("Error en tipo", 0, con.getAtomicInteger()
				.intValue());
	}

	void doTest3() throws Exception {
		this.ctx.getBean(TransactionManager.class).begin();
		final IConnectionFactoryDummy dfi = this.ctx.getBean(
				"connectionFactory0", IConnectionFactoryDummy.class);
		IManagedDriverDummy con = dfi.getConnection();
		Assert.assertEquals("Mal inicializado", 1, con.getAtomicInteger()
				.intValue());
		String msg = "hola";
		String aux = con.echo(msg);
		Assert.assertEquals("Error en retorno", aux, msg);
		Assert.assertEquals("Error en tipo", 1, con.getAtomicInteger()
				.intValue());
		con.close();
		this.ctx.getBean(TransactionManager.class).rollback();
		Assert.assertEquals("Error en tipo", 0, con.getAtomicInteger()
				.intValue());
	}

	void doTest4() throws Exception {
		this.ctx.getBean(TransactionManager.class).begin();
		final IConnectionFactoryDummy dfi = this.ctx.getBean(
				"connectionFactory0", IConnectionFactoryDummy.class);
		IManagedDriverDummy con = dfi.getConnection();
		Assert.assertEquals("Mal inicializado", 1, con.getAtomicInteger()
				.intValue());
		String msg = "hola";
		String aux = con.echo(msg);
		Assert.assertEquals("Error en retorno", aux, msg);
		Assert.assertEquals("Error en tipo", 1, con.getAtomicInteger()
				.intValue());
		con.close();
		con = dfi.getConnection();
		Assert.assertEquals("Mal inicializado", 1, con.getAtomicInteger()
				.intValue());
		msg = "hola";
		aux = con.echo(msg);
		Assert.assertEquals("Error en retorno", aux, msg);
		Assert.assertEquals("Error en tipo", 1, con.getAtomicInteger()
				.intValue());
		con.close();
		this.ctx.getBean(TransactionManager.class).commit();
		Assert.assertEquals("Error en tipo", 0, con.getAtomicInteger()
				.intValue());
	}

	/**
	 * @return the ctx
	 */
	public ConfigurableApplicationContext getCtx() {
		return this.ctx;
	}

	/**
	 * @param ctx
	 *            the ctx to set
	 */
	public void setCtx(final ConfigurableApplicationContext ctx) {
		this.ctx = ctx;
	}

}
