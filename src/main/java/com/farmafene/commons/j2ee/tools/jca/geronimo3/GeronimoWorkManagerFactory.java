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
package com.farmafene.commons.j2ee.tools.jca.geronimo3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.resource.spi.work.WorkManager;

import org.apache.geronimo.connector.work.GeronimoWorkManager;
import org.apache.geronimo.connector.work.HintsContextHandler;
import org.apache.geronimo.connector.work.TransactionContextHandler;
import org.apache.geronimo.connector.work.WorkContextHandler;
import org.apache.geronimo.transaction.manager.XAWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class GeronimoWorkManagerFactory implements InitializingBean, DisposableBean, FactoryBean<WorkManager> {

	private static final Logger logger = LoggerFactory.getLogger(GeronimoWorkManagerFactory.class);
	private GeronimoWorkManager workManager;
	private int poolSize = 50;
	private XAWork xaWork = new GeronimoXAWorkNotSupported();
	private ThreadPoolExecutor threadPool;

	public GeronimoWorkManagerFactory() {

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
		sb.append("workManager=").append(this.workManager);
		sb.append(", xaWork=").append(this.xaWork);
		sb.append(", poolSize=").append(this.poolSize);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		this.workManager.doStop();
		this.threadPool.shutdown();
		if (logger.isDebugEnabled()) {
			logger.debug("destroy()");
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.xaWork, "XAWork is null!");
		assert 1 < this.poolSize : "invalid  poolSize value";
		final BlockingQueue<Runnable> bq = new SynchronousQueue<Runnable>();
		this.threadPool = new ThreadPoolExecutor(this.poolSize, this.poolSize, 1L, TimeUnit.MINUTES, bq);
		@SuppressWarnings("rawtypes")
		final Collection<WorkContextHandler> workContextHandlers = new ArrayList<WorkContextHandler>();
		workContextHandlers.add(new HintsContextHandler());
		workContextHandlers.add(new TransactionContextHandler(this.xaWork));
		// workContextHandlers.add(securityContextHandler);

		this.workManager = new GeronimoWorkManager(this.threadPool, this.threadPool, this.threadPool, workContextHandlers);
		this.workManager.doStart();
		if (logger.isDebugEnabled()) {
			logger.debug("afterPropertiesSet()." + this);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.ObjectFactory#getObject()
	 */
	@Override
	public WorkManager getObject() throws BeansException {
		if (logger.isDebugEnabled()) {
			logger.debug("getObject() " + this.workManager);
		}
		return this.workManager;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return WorkManager.class;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}

	/**
	 * @return the poolSize
	 */
	public int getPoolSize() {
		return this.poolSize;
	}

	/**
	 * @param poolSize the poolSize to set
	 */
	public void setPoolSize(final int poolSize) {
		this.poolSize = poolSize;
	}

	/**
	 * @return the xaWork
	 */
	public XAWork getXaWork() {
		return this.xaWork;
	}

	/**
	 * @param xaWork the xaWork to set
	 */
	public void setXaWork(final XAWork xaWork) {
		this.xaWork = xaWork;
	}
}
