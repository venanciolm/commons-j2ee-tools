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

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.farmafene.commons.j2ee.tools.jca.common.StringPrintStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:com/farmafene/commons/j2ee/tools/jca/spi/testLocalapplicationContext.xml" })
public class JCA01UTest implements InitializingBean {

	private static final Logger logger = LoggerFactory
			.getLogger(JCA01UTest.class);
	@Autowired
	private ConfigurableApplicationContext applicationContext;
	private static ConfigurableApplicationContext APPCTX;

	@BeforeClass
	public static void beforeClass() throws UnknownHostException, IOException {
		logger.info("Starting ...");
	}

	@AfterClass
	public static void afterClass() throws IOException {
		APPCTX.close();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		APPCTX = this.applicationContext;
	}

	@Test
	public void ask01() throws IOException {
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*-------------------------------------------------------+|");
			ps.println("||                                                       ||");
			ps.println("|| ASK01                                                 ||");
			ps.println("||                                                       ||");
			ps.println("|+-------------------------------------------------------*/");
			logger.info("{}", ps);
		}
		Assert.assertNotNull(this.applicationContext);
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*-------------------------------------------------------+|");
			ps.println("||                                                       ||");
			ps.println("||  Buscando el TransactionManger                        ||");
			ps.println("||                                                       ||");
			ps.println("|+-------------------------------------------------------*/");
			logger.info("{}", ps);
		}
		final Object oCf = this.applicationContext
				.getBean("connectionFactory1");
		Assert.assertNotNull(oCf);
		logger.info("Hemos obtenido: {}", oCf);
		final IConnectionFactoryDummy cf = (IConnectionFactoryDummy) oCf;
		logger.info("Probando conexión");
		IManagedDriverDummy con = cf.getConnection();
		AtomicInteger test = con.getAtomicInteger();
		logger.info("Hemos obtenido: {}", con);
		Assert.assertEquals("No está equilibrado", 0, test.intValue());
		con.close();
		logger.info("cerrado");
		con = cf.getConnection();
		logger.info("Hemos obtenido: {}", con);
		Assert.assertEquals("No está equilibrado", 0, test.intValue());
		con.close();
		logger.info("cerrado");
		con = cf.getConnection();
		logger.info("Hemos obtenido: {}", con);
		Assert.assertEquals("No está equilibrado", 0, test.intValue());
		con.close();
		logger.info("cerrado");
		Assert.assertEquals("No está equilibrado", 0, test.intValue());
	}

	@Test
	public void ask02() throws IOException, NotSupportedException,
			SystemException, IllegalStateException, SecurityException,
			HeuristicMixedException, HeuristicRollbackException,
			RollbackException {
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*-------------------------------------------------------+|");
			ps.println("||                                                       ||");
			ps.println("|| ASK02                                                 ||");
			ps.println("||                                                       ||");
			ps.println("|+-------------------------------------------------------*/");
			logger.info("{}", ps);
		}
		Assert.assertNotNull(this.applicationContext);
		doTest();
		doTest();
		doTest();
	}

	public void doTest() throws IOException, NotSupportedException,
			SystemException, IllegalStateException, SecurityException,
			HeuristicMixedException, HeuristicRollbackException,
			RollbackException {

		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*-------------------------------------------------------+|");
			ps.println("||                                                       ||");
			ps.println("||  Buscando el TransactionManger                        ||");
			ps.println("||                                                       ||");
			ps.println("|+-------------------------------------------------------*/");
			logger.info("{}", ps);
		}
		final TransactionManager txm = this.applicationContext
				.getBean(TransactionManager.class);
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*-------------------------------------------------------+|");
			ps.println("||                                                       ||");
			ps.println("||  Before begin                                         ||");
			ps.println("||                                                       ||");
			ps.println("|+-------------------------------------------------------*/");
			logger.info("{}", ps);
		}
		txm.begin();
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*-------------------------------------------------------+|");
			ps.println("||                                                       ||");
			ps.println("||  After begin                                          ||");
			ps.println("||                                                       ||");
			ps.println("|+-------------------------------------------------------*/");
			logger.info("{}", ps);
		}
		logger.info("Probando conexión");
		final IManagedDriverDummy con1 = this.applicationContext.getBean(
				"connectionFactory1", IConnectionFactoryDummy.class)
				.getConnection();
		AtomicInteger test = con1.getAtomicInteger();
		logger.info("Hemos obtenido 01: {}", con1);
		Assert.assertEquals("No está equilibrado", 1, test.intValue());
		final IManagedDriverDummy con2 = this.applicationContext.getBean(
				"connectionFactory2", IConnectionFactoryDummy.class)
				.getConnection();
		logger.info("Hemos obtenido 02: {}", con2);
		Assert.assertEquals("No está equilibrado", 2, test.intValue());
		final IManagedDriverDummy con3 = this.applicationContext.getBean(
				"connectionFactory3", IConnectionFactoryDummy.class)
				.getConnection();
		logger.info("Hemos obtenido 03: {}", con3);
		Assert.assertEquals("No está equilibrado", 3, test.intValue());
		con3.close();
		logger.info("cerrado 3");
		con2.close();
		logger.info("cerrado 2");
		con1.close();
		logger.info("cerrado 1");
		Assert.assertEquals("No está equilibrado", 3, test.intValue());
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*-------------------------------------------------------+|");
			ps.println("||                                                       ||");
			ps.println("||  Before commit                                        ||");
			ps.println("||                                                       ||");
			ps.println("|+-------------------------------------------------------*/");
			logger.info("{}", ps);
		}
		txm.commit();
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*-------------------------------------------------------+|");
			ps.println("||                                                       ||");
			ps.println("||  After commit                                         ||");
			ps.println("||                                                       ||");
			ps.println("|+-------------------------------------------------------*/");
			logger.info("{}", ps);
		}
		Assert.assertEquals("No está equilibrado", 0, test.intValue());
	}
}
