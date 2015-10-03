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
package com.farmafene.commons.j2ee.tools.jca;

import java.util.Timer;

import javax.resource.spi.BootstrapContext;
import javax.resource.spi.UnavailableException;
import javax.resource.spi.XATerminator;
import javax.resource.spi.work.WorkContext;
import javax.resource.spi.work.WorkManager;
import javax.transaction.TransactionSynchronizationRegistry;

import org.apache.geronimo.connector.work.GeronimoWorkManager;

public class BootstrapContextImpl implements BootstrapContext {

	private WorkManager workManager;
	private XATerminator xATerminator;
	private TransactionSynchronizationRegistry transactionSynchronizationRegistry;
	private ITimerFactory iTimerFactory;
	private IContextSupportedProvided iContextSupportedProvided;

	public BootstrapContextImpl() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("={");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.BootstrapContext#getWorkManager()
	 */
	@Override
	public WorkManager getWorkManager() {
		return workManager;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.BootstrapContext#getXATerminator()
	 */
	@Override
	public XATerminator getXATerminator() {
		return xATerminator;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.BootstrapContext#createTimer()
	 */
	@Override
	public Timer createTimer() throws UnavailableException {
		if (iTimerFactory == null) {
			iTimerFactory = new DefaultITimeFactory();
		}
		return iTimerFactory.createTimer();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.resource.spi.BootstrapContext#getTransactionSynchronizationRegistry()
	 */
	@Override
	public TransactionSynchronizationRegistry getTransactionSynchronizationRegistry() {
		return transactionSynchronizationRegistry;
	}

	/**
	 * @return the xATerminator
	 */
	public XATerminator getxATerminator() {
		return xATerminator;
	}

	/**
	 * @param xATerminator
	 *            the xATerminator to set
	 */
	public void setxATerminator(XATerminator xATerminator) {
		this.xATerminator = xATerminator;
	}

	/**
	 * @param workManager
	 *            the workManager to set
	 */
	public void setWorkManager(WorkManager workManager) {
		this.workManager = workManager;
	}

	/**
	 * @param transactionSynchronizationRegistry
	 *            the transactionSynchronizationRegistry to set
	 */
	public void setTransactionSynchronizationRegistry(
			TransactionSynchronizationRegistry transactionSynchronizationRegistry) {
		this.transactionSynchronizationRegistry = transactionSynchronizationRegistry;
	}

	public ITimerFactory getITimerFactory() {
		return iTimerFactory;
	}

	public void setITimerFactory(ITimerFactory iTimerFactory) {
		this.iTimerFactory = iTimerFactory;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.BootstrapContext#isContextSupported(java.lang.Class)
	 */
	@Override
	public boolean isContextSupported(
			Class<? extends WorkContext> workContextClass) {
		boolean isContextSupported = false;
		if (workManager instanceof IContextSupportedProvided) {
			isContextSupported = ((IContextSupportedProvided) workManager)
					.isContextSupported(workContextClass);
		} else {
			boolean isGeronimo = false;

			try {
				isGeronimo = workManager instanceof GeronimoWorkManager;
			} catch (Exception e) {
			}
			if (isGeronimo) {
				isContextSupported = ((GeronimoWorkManager) workManager)
						.isContextSupported(workContextClass);

			} else if (null != iContextSupportedProvided) {
				isContextSupported = iContextSupportedProvided
						.isContextSupported(workContextClass);
			}
		}
		return isContextSupported;
	}

	/**
	 * @return the contextSupportedProvided
	 */
	public IContextSupportedProvided getIContextSupportedProvided() {
		return iContextSupportedProvided;
	}

	/**
	 * @param contextSupportedProvided
	 *            the contextSupportedProvided to set
	 */
	public void setIContextSupportedProvided(
			IContextSupportedProvided contextSupportedProvided) {
		this.iContextSupportedProvided = contextSupportedProvided;
	}
}
