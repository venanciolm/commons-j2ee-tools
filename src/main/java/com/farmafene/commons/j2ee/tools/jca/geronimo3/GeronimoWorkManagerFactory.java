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
import java.util.concurrent.LinkedBlockingQueue;
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
	private int maxWorks = 75;
	private int poolSize = 50;
	private int releasePoolSize = 5;
	private XAWork xaWork = new GeronimoXAWorkNotSupported();
	private ThreadPoolExecutor threadPool;
	private boolean enableRelease = true;
	private ThreadPoolExecutor releaseThreadPool;

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
		sb.append("workManager=").append(this.workManager.getClass().getSimpleName());
		sb.append(", maxWorks=").append(this.maxWorks);
		sb.append(", poolSize=").append(this.poolSize);
		if (this.enableRelease) {
			sb.append(", releasePoolSize=").append(this.releasePoolSize);
		}
		sb.append(", xaWork=").append(this.xaWork);
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
		this.threadPool.awaitTermination(60, TimeUnit.SECONDS);
		if (null != this.releaseThreadPool) {
			this.releaseThreadPool.shutdown();
			this.releaseThreadPool.awaitTermination(60, TimeUnit.SECONDS);
		}
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
		assert 1 <= this.poolSize : "invalid  poolSize value";
		BlockingQueue<Runnable> bq = null;
		if ((this.maxWorks - this.poolSize) > 0) {
			bq = new LinkedBlockingQueue<Runnable>(this.maxWorks - this.poolSize);
		} else {
			bq = new SynchronousQueue<Runnable>(true);
			this.maxWorks = this.poolSize;
		}
		final NamedThreadFactory tpf = new NamedThreadFactory();
		this.threadPool = new ThreadPoolExecutor(this.poolSize, this.poolSize, 60L, TimeUnit.SECONDS, bq, tpf);
		if (this.enableRelease) {
			final BlockingQueue<Runnable> bqRelease = new LinkedBlockingQueue<Runnable>();
			final NamedThreadFactory rtpf = new NamedThreadFactory();
			rtpf.setType("R");
			final int releasePoolSize = (this.releasePoolSize > this.poolSize) ? this.poolSize : ((this.releasePoolSize < 2) ? 1
					: this.releasePoolSize);
			this.releaseThreadPool = new ThreadPoolExecutor(releasePoolSize, releasePoolSize, 10L, TimeUnit.SECONDS, bqRelease, rtpf);
			this.releasePoolSize = releasePoolSize;
		}
		@SuppressWarnings("rawtypes")
		final Collection<WorkContextHandler> workContextHandlers = new ArrayList<WorkContextHandler>();
		workContextHandlers.add(new HintsContextHandler());
		workContextHandlers.add(new TransactionContextHandler(this.xaWork));
		if (this.enableRelease) {
			workContextHandlers.add(new ReleaseContextHandler());
			final ReleaseProviderWorkManager rwm = new ReleaseProviderWorkManager(this.threadPool, this.threadPool, this.threadPool,
					workContextHandlers, this.releaseThreadPool);
			this.workManager = rwm;
		} else {
			this.workManager = new GeronimoWorkManager(this.threadPool, this.threadPool, this.threadPool, workContextHandlers);
		}
		this.workManager.doStart();
		if (logger.isDebugEnabled()) {
			logger.debug("afterPropertiesSet() - {}", this);
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
			logger.debug("getObject() {}", this.workManager);
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

	/**
	 * @return the enableRelease
	 */
	public boolean isEnableRelease() {
		return this.enableRelease;
	}

	/**
	 * @param enableRelease the enableRelease to set
	 */
	public void setEnableRelease(final boolean enableRelease) {
		this.enableRelease = enableRelease;
	}

	/**
	 * Devuelve el valor de la propiedad 'maxWorks'
	 * @return Propiedad maxWorks
	 */
	public int getMaxWorks() {
		return this.maxWorks;
	}

	/**
	 * Devuelve el valor de la propiedad 'releasePoolSize'
	 * @return Propiedad releasePoolSize
	 */
	public int getReleasePoolSize() {
		return this.releasePoolSize;
	}

	/**
	 * Asigna el valor de la propiedad 'releasePoolSize'
	 * @param releasePoolSize valor que se le quiere dar a la propiedad
	 *            'releasePoolSize'
	 */
	public void setReleasePoolSize(final int releasePoolSize) {
		this.releasePoolSize = releasePoolSize;
	}

	/**
	 * Devuelve el valor de la propiedad 'releaseThreadPool'
	 * @return Propiedad releaseThreadPool
	 */
	public ThreadPoolExecutor getReleaseThreadPool() {
		return this.releaseThreadPool;
	}

	/**
	 * Asigna el valor de la propiedad 'releaseThreadPool'
	 * @param releaseThreadPool valor que se le quiere dar a la propiedad
	 *            'releaseThreadPool'
	 */
	public void setReleaseThreadPool(final ThreadPoolExecutor releaseThreadPool) {
		this.releaseThreadPool = releaseThreadPool;
	}

	/**
	 * Asigna el valor de la propiedad 'maxWorks'
	 * @param maxWorks valor que se le quiere dar a la propiedad 'maxWorks'
	 */
	public void setMaxWorks(final int maxWorks) {
		this.maxWorks = maxWorks;
	}
}
