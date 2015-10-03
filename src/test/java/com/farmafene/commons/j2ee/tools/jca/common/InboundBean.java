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

	private static final Logger logger = LoggerFactory.getLogger(InboundBean.class);

	private ConfigurableApplicationContext ctx;

	public InboundBean() {

	}

	public void aa_cretateFactory() {
		logger.info("================================================");
		logger.info("= Comenzamos a validar la BeanFactory");
		logger.info("================================================");
		Assert.assertNotNull(this.ctx);
		logger.info("WorkManager:                        {}", this.ctx.getBean(WorkManager.class));
		logger.info("TransactionManager:                 {}", this.ctx.getBean(TransactionManager.class));
		logger.info("TransactionSynchronizationRegistry: {}", this.ctx.getBean(TransactionSynchronizationRegistry.class));
		logger.info("XATerminator:                       {}", this.ctx.getBean(XATerminator.class));
		Assert.assertNotNull(this.ctx.getBean(WorkManager.class));
		Assert.assertNotNull(this.ctx.getBean(TransactionManager.class));
		Assert.assertNotNull(this.ctx.getBean(TransactionSynchronizationRegistry.class));
		Assert.assertNotNull(this.ctx.getBean(XATerminator.class));
	}

	public void cretateFactory() throws WorkException, InterruptedException, XAException {
		logger.info("================================================");
		logger.info("= Comenzamos a validar la BeanFactory");
		logger.info("================================================");
		Assert.assertNotNull(this.ctx);
		final WorkManager wm = this.ctx.getBean(WorkManager.class);
		Assert.assertNotNull(wm);
		logger.info("================================================");
		logger.info("= Existe el AtivationSpec");
		logger.info("================================================");
		final InboundAtivationSpec ac = this.ctx.getBean(InboundAtivationSpec.class);
		Assert.assertNotNull(ac);
		logger.info("================================================");
		logger.info("= Existe el XATerminator");
		logger.info("================================================");
		final XATerminator xATerminator = this.ctx.getBean(XATerminator.class);
		Assert.assertNotNull(xATerminator);
		XAResource xa = new XAResourceLog();

		InboundWork w = null;
		logger.info("================================================");
		logger.info("= Enviamos un trabajo");
		logger.info("================================================");
		w = ac.process(null);
		logger.info("Esperando el Release el trabajo {}", w);
		w.getLatch().await();
		logger.info("================================================");
		logger.info("= Enviamos un trabajo XA");
		logger.info("================================================");
		w = ac.process(xa);
		logger.info("Esperando el Release el trabajo {}", w);
		w.getLatch().await();
		logger.info("================================================");
		logger.info("= Procesado Trabajo XA");
		logger.info("================================================");

		InboundWork tx = null;
		logger.info("================================================");
		logger.info("= Enviamos un trabajo");
		logger.info("================================================");
		tx = ac.processTx(null);
		logger.info("Esperando el Release el trabajo {}", tx);
		tx.getLatch().await();
		logger.info("________________________________________________");

		logger.info("================================================");
		logger.info("= Enviamos un trabajo XA");
		logger.info("================================================");
		tx = ac.processTx(xa);
		logger.info("Esperando el Release el trabajo {}", tx);
		tx.getLatch().await();
		logger.info("________________________________________________");

		InboundTransactionalWork wtx = null;

		wtx = ac.processInbound(null);
		logger.info("Esperando el Release el trabajo {}", wtx);
		wtx.getLatch().await();
		logger.info("================================================");
		logger.info("= Reproducimos un  commit sin XAResource");
		logger.info("================================================");
		if (xATerminator instanceof GeronimoTransactionManager) {
			logger.info("Total Commits: {}", ((GeronimoTransactionManager) xATerminator).getTotalCommits());
		}
		xATerminator.commit(wtx.getXid(), true);
		if (xATerminator instanceof GeronimoTransactionManager) {
			logger.info("Total Commits: {}", ((GeronimoTransactionManager) xATerminator).getTotalCommits());
		}
		logger.info("================================================");
		logger.info("= Final del  commit");
		logger.info("================================================");
		logger.info("================================================");
		logger.info("= Enviamos un trabajo XA con XAResource");
		logger.info("================================================");
		wtx = ac.processInbound(xa);
		logger.info("Esperando el Release el trabajo {}", wtx);
		wtx.getLatch().await();
		logger.info("================================================");
		logger.info("= Reproducimos un  commit");
		logger.info("================================================");
		if (xATerminator instanceof GeronimoTransactionManager) {
			logger.info("Total Commits: {}", ((GeronimoTransactionManager) xATerminator).getTotalCommits());
		}
		xATerminator.commit(wtx.getXid(), true);
		if (xATerminator instanceof GeronimoTransactionManager) {
			logger.info("Total Commits: {}", ((GeronimoTransactionManager) xATerminator).getTotalCommits());
		}
		logger.info("================================================");
		logger.info("= Final del  commit");
		logger.info("================================================");
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
	 * @param ctx the ctx to set
	 */
	public void setCtx(final ConfigurableApplicationContext ctx) {
		this.ctx = ctx;
	}

}
