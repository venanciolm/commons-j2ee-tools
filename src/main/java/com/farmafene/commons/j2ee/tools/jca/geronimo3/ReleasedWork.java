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
import java.util.List;
import java.util.concurrent.Executor;

import javax.resource.NotSupportedException;
import javax.resource.spi.work.ExecutionContext;
import javax.resource.spi.work.TransactionContext;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkContext;
import javax.resource.spi.work.WorkContextProvider;

@SuppressWarnings("serial")
class ReleasedWork implements Work, WorkContextProvider {

	private Work work;
	private Executor executor;
	private ExecutionContext execContext;

	public ReleasedWork(Work work, ExecutionContext execContext,
			Executor releaseExecutor) {
		this.work = work;
		this.executor = releaseExecutor;
		this.execContext = execContext;
	}

	@Override
	public void run() {
		work.run();
	}

	@Override
	public List<WorkContext> getWorkContexts() {
		List<WorkContext> workContexts = new ArrayList<WorkContext>();
		if (work instanceof WorkContextProvider) {
			workContexts.addAll(((WorkContextProvider) work).getWorkContexts());
		} else if (null != execContext) {

			TransactionContext tc = new TransactionContext();
			tc.setXid(execContext.getXid());
			try {
				tc.setTransactionTimeout(execContext.getTransactionTimeout());
			} catch (NotSupportedException e) {
			}
			workContexts.add(tc);
		}
		workContexts.add(new ReleaseContext(work, executor));
		return workContexts;
	}

	@Override
	public void release() {
		work.release();
	}

}
