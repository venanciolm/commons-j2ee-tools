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

import java.util.Collection;
import java.util.concurrent.Executor;

import javax.resource.spi.work.ExecutionContext;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkListener;
import javax.resource.spi.work.WorkManager;

import org.apache.geronimo.connector.work.GeronimoWorkManager;
import org.apache.geronimo.connector.work.WorkContextHandler;

class ReleaseProviderWorkManager extends GeronimoWorkManager implements
		WorkManager {

	private Executor releaseExecutor;

	public ReleaseProviderWorkManager(
			Executor sync,
			Executor start,
			Executor sched,
			@SuppressWarnings("rawtypes") Collection<WorkContextHandler> workContextHandlers,
			Executor releaseExecutor) {
		super(sync, start, sched, workContextHandlers);
		this.releaseExecutor = releaseExecutor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.work.WorkManager#doWork(javax.resource.spi.work.Work)
	 */
	@Override
	public void doWork(Work work) throws WorkException {
		Work newWork = workFactory(work);
		super.doWork(newWork);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.work.WorkManager#doWork(javax.resource.spi.work.Work,
	 *      long, javax.resource.spi.work.ExecutionContext,
	 *      javax.resource.spi.work.WorkListener)
	 */
	@Override
	public void doWork(Work work, long startTimeout,
			ExecutionContext execContext, WorkListener workListener)
			throws WorkException {
		Work newWork = workFactory(work, execContext);
		super.doWork(newWork, startTimeout, null, workListener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.work.WorkManager#startWork(javax.resource.spi.work.Work)
	 */
	@Override
	public long startWork(Work work) throws WorkException {
		Work newWork = workFactory(work);
		return super.startWork(newWork);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.work.WorkManager#startWork(javax.resource.spi.work.Work,
	 *      long, javax.resource.spi.work.ExecutionContext,
	 *      javax.resource.spi.work.WorkListener)
	 */
	@Override
	public long startWork(Work work, long startTimeout,
			ExecutionContext execContext, WorkListener workListener)
			throws WorkException {
		Work newWork = workFactory(work, execContext);
		return super.startWork(newWork, startTimeout, null, workListener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.work.WorkManager#scheduleWork(javax.resource.spi.work.Work)
	 */
	@Override
	public void scheduleWork(Work work) throws WorkException {
		Work newWork = workFactory(work);
		super.scheduleWork(newWork);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.work.WorkManager#scheduleWork(javax.resource.spi.work.Work,
	 *      long, javax.resource.spi.work.ExecutionContext,
	 *      javax.resource.spi.work.WorkListener)
	 */
	@Override
	public void scheduleWork(Work work, long startTimeout,
			ExecutionContext execContext, WorkListener workListener)
			throws WorkException {
		Work newWork = workFactory(work, execContext);
		super.scheduleWork(newWork, startTimeout, null, workListener);
	}

	private Work workFactory(Work work) {
		return new ReleasedWork(work,null, releaseExecutor);
	}
	private Work workFactory(Work work,ExecutionContext execContext) {
		return new ReleasedWork(work, execContext,releaseExecutor);
	}
}
