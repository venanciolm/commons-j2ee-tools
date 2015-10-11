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

import javax.resource.spi.XATerminator;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkManager;
import javax.transaction.TransactionManager;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;

import org.apache.geronimo.transaction.manager.GeronimoTransactionManager;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import com.farmafene.commons.j2ee.tools.jca.XAResourceLog;

public class InboundBean {

	private static final Logger logger = LoggerFactory
			.getLogger(InboundBean.class);

	private ConfigurableApplicationContext ctx;

	public InboundBean() {

	}

	public void aa_cretateFactory() {
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
	}

	public void cretateFactory() throws WorkException, InterruptedException,
			XAException {
		final XATerminator xATerminator = this.ctx.getBean(XATerminator.class);
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| Existe el AtivationSpec                    ||");
			ps.print("|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		final InboundAtivationSpec ac = this.ctx
				.getBean(InboundAtivationSpec.class);
		Assert.assertNotNull(ac);

		XAResource xa = new XAResourceLog();
		InboundWork w = null;
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| Enviamos un trabajo                        ||");
			ps.print(  "|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		w = ac.process(null);
		logger.info("Esperando el Release el trabajo {}", w);
		w.getLatch().await();
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| Enviamos un trabajo XA                     ||");
			ps.print(  "|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		w = ac.process(xa);
		logger.info("Esperando el Release el trabajo {}", w);
		w.getLatch().await();
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| Procesado un trabajo XA                    ||");
			ps.print(  "|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		InboundWork tx = null;
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| Enviamos un trabajo no XA                  ||");
			ps.print(  "|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		tx = ac.processTx(null);
		logger.info("Esperando el Release el trabajo {}", tx);
		tx.getLatch().await();
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| Procesado un trabajo no XA                 ||");
			ps.print(  "|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		tx = ac.processTx(xa);
		logger.info("Esperando el Release el trabajo {}", tx);
		tx.getLatch().await();

		InboundTransactionalWork wtx = null;

		wtx = ac.processInbound(null);
		logger.info("Esperando el Release el trabajo {}", wtx);
		wtx.getLatch().await();
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| Reproducimos un commit sin XAResource      ||");
			ps.print(  "|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		if (xATerminator instanceof GeronimoTransactionManager) {
			logger.info("Total Commits: {}",
					((GeronimoTransactionManager) xATerminator)
							.getTotalCommits());
		}
		logger.info("{}", xATerminator);
		xATerminator.commit(wtx.getXid(), true);
		if (xATerminator instanceof GeronimoTransactionManager) {
			logger.info("Total Commits: {}",
					((GeronimoTransactionManager) xATerminator)
							.getTotalCommits());
		}
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| Final del commit                           ||");
			ps.print(  "|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| Reproducimos un commit con XAResource      ||");
			ps.print(  "|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		wtx = ac.processInbound(xa);
		logger.info("Esperando el Release el trabajo {}", wtx);
		wtx.getLatch().await();
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| Commit con XAResource                      ||");
			ps.print(  "|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		logger.info("{}", xATerminator);
		if (xATerminator instanceof GeronimoTransactionManager) {
			logger.info("Total Commits: {}",
					((GeronimoTransactionManager) xATerminator)
							.getTotalCommits());
		}
		xATerminator.commit(wtx.getXid(), true);
		logger.info("{}", xATerminator);
		if (xATerminator instanceof GeronimoTransactionManager) {
			logger.info("Total Commits: {}",
					((GeronimoTransactionManager) xATerminator)
							.getTotalCommits());
		}
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| Final del commit con XAResource            ||");
			ps.print(  "|+--------------------------------------------*/");
			logger.info("{}", ps);
		}
		logger.info("Liberado latch!!!");
		xa = null;
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
