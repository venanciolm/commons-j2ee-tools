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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;

import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkManager;
import javax.sql.DataSource;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import bitronix.tm.utils.Decoder;

public class OutBoundBean {
	private static final Logger logger = LoggerFactory
			.getLogger(OutBoundBean.class);

	private ConfigurableApplicationContext ctx;

	public void afterPropertiesSet() throws Exception {
		logger.info("Estamos entrando en afterPropertiesSet()");
		logger.info("Creando la primera tabla");
		DataSource ds = (DataSource) this.ctx.getBean("Datasource1");
		Assert.assertNotNull(ds);
		Connection con = null;
		Statement s = null;
		con = ds.getConnection();
		s = con.createStatement();
		s.execute("CREATE TABLE ITEM (UNO INT, DOS INT)");
		s.close();
		con.close();
		logger.info("Creando la segunda tabla");
		Assert.assertNotNull(ds);
		ds = (DataSource) this.ctx.getBean("Datasource2");
		con = ds.getConnection();
		s = con.createStatement();
		s.execute("CREATE TABLE ITEM (UNO INT, DOS INT)");
		s.close();
		con.close();
	}

	public void InitTest() {
		Assert.assertNotNull(this.ctx.getBean(TransactionManager.class));
		Assert.assertNotNull(this.ctx.getBean("Datasource1"));
		logger.info("{}", this.ctx.getBean("Datasource1"));
		Assert.assertNotNull(this.ctx.getBean("Datasource2"));
		logger.info("{}", this.ctx.getBean("Datasource2"));
	}

	public void Init3Test() throws WorkException, InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);
		final WorkManager gwm = this.ctx.getBean(WorkManager.class);
		logger.info("WorkManager " + gwm);
		final WorkManager wm = gwm;
		logger.info("doWork");
		wm.doWork(new Work() {
			/**
			 * {@inheritDoc}
			 *
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				for (int i = 0; i < 2; i++) {
					try {
						if (logger.isDebugEnabled()) {
							StringPrintStream out = new StringPrintStream();
							out.println();
							out.println("=============================");
							out.println("= Comenzando la transacción =");
							out.print("=============================");
							out.flush();
							logger.info("Valor: {}", out);
						}
						UserTransaction utx = null;
						try {
							utx = ctx.getBean(UserTransaction.class);
						} catch (Exception e) {
							Assert.assertNull(e);
						}
						utx.begin();
						ctx.getBean(TransactionManager.class).getTransaction()
								.registerSynchronization(new Synchronization() {

									@Override
									public void beforeCompletion() {
										logger.info("beforeCompletion()");
									}

									@Override
									public void afterCompletion(int status) {
										logger.info("afterCompletion({}): {}",
												status,
												Decoder.decodeStatus(status));
									}
								});
						Assert.assertEquals(Status.STATUS_ACTIVE,
								utx.getStatus());
						logger.info("- {} [{}] ({})", i,
								Decoder.decodeStatus(utx.getStatus()), utx);
						DataSource ds = null;
						Connection con = null;
						Statement stmt = null;
						ResultSet rs = null;

						ds = (DataSource) ctx.getBean("Datasource1");
						logger.info("-- ds:" + ds);
						con = ds.getConnection();
						logger.info("-- c2:" + con);
						stmt = con.createStatement();
						rs = stmt.executeQuery("SELECT 1 FROM ITEM");
						rs.close();
						stmt.close();
						con.close();

						ds = (DataSource) ctx.getBean("Datasource2");
						logger.info("-- ds2:" + ds);
						con = ds.getConnection();
						logger.info("-- c2:" + con);
						stmt = con.createStatement();
						rs = stmt.executeQuery("SELECT 1 FROM ITEM");
						rs.close();
						stmt.close();
						con.close();
						Assert.assertEquals(Status.STATUS_ACTIVE,
								utx.getStatus());
						if (logger.isDebugEnabled()) {
							StringPrintStream out = new StringPrintStream();
							out.println();
							out.println("=============================");
							out.println(" La transacción es           ");
							out.println(utx);
							out.print("=============================");
							logger.info("{}", out);
						}
						if (i % 2 == 0) {
							utx.commit();
							if (logger.isDebugEnabled()) {
								StringPrintStream out = new StringPrintStream();
								out.println();
								out.println("=============================");
								out.println("= Realizado commit          =");
								out.print("=============================");
								logger.info("{}", out);
							}
						} else {
							utx.rollback();
							if (logger.isDebugEnabled()) {
								StringPrintStream out = new StringPrintStream();
								out.println();
								out.println("=============================");
								out.println("= Realizado rollback        =");
								out.print("=============================");
								logger.info("{}", out);
							}
						}
						if (logger.isDebugEnabled()) {
							StringPrintStream out = new StringPrintStream();
							out.println();
							out.println("=============================");
							out.println("= terminada la  transacción =");
							out.print("=============================");
							logger.info("{}", out);
						}
						logger.info("- {} [{}] ({})", i,
								Decoder.decodeStatus(utx.getStatus()), utx);
					} catch (final NotSupportedException e) {
						logger.error("", e);
						Assert.assertNull(e);
					} catch (final SystemException e) {
						logger.error("", e);
						Assert.assertNull(e);
					} catch (final IllegalStateException e) {
						logger.error("", e);
						Assert.assertNull(e);
					} catch (final SecurityException e) {
						logger.error("", e);
						Assert.assertNull(e);
					} catch (final HeuristicMixedException e) {
						logger.error("", e);
						Assert.assertNull(e);
					} catch (final HeuristicRollbackException e) {
						logger.error("", e);
						Assert.assertNull(e);
					} catch (final RollbackException e) {
						logger.error("", e);
						Assert.assertNull(e);
					} catch (final SQLException e) {
						logger.error("", e);
						Assert.assertNull(e);
					}
				}
			}

			/**
			 *
			 * @see javax.resource.spi.work.Work#release()
			 */
			@Override
			public void release() {
				logger.info("Release");
				latch.countDown();
			}
		});
		logger.info("Esperando al Release");
		latch.await();
	}

	/**
	 * @return the ctx
	 */
	public ConfigurableApplicationContext getCtx() {
		return ctx;
	}

	/**
	 * @param ctx
	 *            the ctx to set
	 */
	public void setCtx(ConfigurableApplicationContext ctx) {
		this.ctx = ctx;
	}
}
