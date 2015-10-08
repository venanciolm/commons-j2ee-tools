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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

public class ExecutorServiceFactoryBean implements InitializingBean, DisposableBean, FactoryBean<ThreadPoolExecutor> {

	private int maxRunnables = 50;
	private int poolSize = 5;
	private ThreadPoolExecutor threadPool = null;
	private String name;
	private String type;

	public ExecutorServiceFactoryBean() {

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		BlockingQueue<Runnable> bq = null;
		assert 1 <= this.poolSize : "invalid  poolSize value";
		if ((this.maxRunnables - this.poolSize) > 0) {
			bq = new LinkedBlockingQueue<Runnable>(this.maxRunnables - this.poolSize);
		} else if ((this.maxRunnables - this.poolSize) < 0) {
			bq = new LinkedBlockingQueue<Runnable>();
		} else {
			bq = new SynchronousQueue<Runnable>(true);
		}
		if (null != this.name && StringUtils.hasText(this.name)) {
			final String threadName = this.name.trim();
			String groupName = this.name.trim();
			if (null != this.type && StringUtils.hasText(this.type)) {
				groupName = this.type.trim();
			} else {
				groupName = "W";
				final CommonThreadFactory cth = new CommonThreadFactory();
				cth.setName(threadName);
				cth.setType(groupName);
				this.threadPool = new ThreadPoolExecutor(this.poolSize, this.poolSize, 60L, TimeUnit.SECONDS, bq, cth);
			}
		} else {
			this.threadPool = new ThreadPoolExecutor(this.poolSize, this.poolSize, 60L, TimeUnit.SECONDS, bq);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		if (null != this.threadPool && !this.threadPool.isShutdown()) {
			this.threadPool.shutdown();
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public ThreadPoolExecutor getObject() throws Exception {
		return this.threadPool;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return ThreadPoolExecutor.class;
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
	 * Devuelve el valor de la propiedad 'maxRunnables'
	 * @return Propiedad maxRunnables
	 */
	public int getMaxRunnables() {
		return this.maxRunnables;
	}

	/**
	 * Asigna el valor de la propiedad 'maxRunnables'
	 * @param maxRunnables valor que se le quiere dar a la propiedad
	 *            'maxRunnables'
	 */
	public void setMaxRunnables(final int maxRunnables) {
		this.maxRunnables = maxRunnables;
	}

	/**
	 * Devuelve el valor de la propiedad 'poolSize'
	 * @return Propiedad poolSize
	 */
	public int getPoolSize() {
		return this.poolSize;
	}

	/**
	 * Asigna el valor de la propiedad 'poolSize'
	 * @param poolSize valor que se le quiere dar a la propiedad 'poolSize'
	 */
	public void setPoolSize(final int poolSize) {
		this.poolSize = poolSize;
	}

	/**
	 * Devuelve el valor de la propiedad 'name'
	 * @return Propiedad name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Asigna el valor de la propiedad 'name'
	 * @param name valor que se le quiere dar a la propiedad 'name'
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Devuelve el valor de la propiedad 'type'
	 * @return Propiedad type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Asigna el valor de la propiedad 'type'
	 * @param type valor que se le quiere dar a la propiedad 'type'
	 */
	public void setType(final String type) {
		this.type = type;
	}
}
