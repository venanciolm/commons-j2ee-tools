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
package com.farmafene.commons.j2ee.tools.jca.btm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bitronix.tm.internal.ThreadContext;

import com.farmafene.commons.j2ee.tools.jca.wm.IXAWorkAdapter;

abstract class BTMXAWorkAdapter implements IXAWorkAdapter {

	private static final Logger logger = LoggerFactory
			.getLogger(BTMXAWorkAdapter.class);

	private final Map<Xid, ThreadContext> importedTransactions;

	public BTMXAWorkAdapter() {
		this.importedTransactions = new ConcurrentHashMap<Xid, ThreadContext>();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.commons.jca.wm.IXAWorkAdapter#begin(javax.transaction.xa
	 *      .Xid, long)
	 */
	@Override
	public void begin(final Xid xid, final long txTimeout) throws XAException,
			SystemException, InvalidTransactionException {
		if (null == xid) {
			throw new InvalidTransactionException("Xid is null!");
		}
		final ThreadContext ctx = unbound();
		if (null != ctx) {
			final Xid existente = (Xid) BTMLocator
					.getTransactionSynchronizationRegistry()
					.getTransactionKey();
			this.importedTransactions.put(existente, ctx);
		}
		ThreadContext tc = null;
		try {
			tc = this.importedTransactions.get(xid);
		} catch (final Throwable e) {
			final RuntimeException rtm = new RuntimeException(
					"Se ha producido un error al encontrar el XID " + xid, e);
			logger.error("Exception: " + e);
			throw rtm;
		}
		if (tc == null) {
			tc = createThreadContext(xid);
			this.importedTransactions.put(xid, tc);
		}
		tc.setTimeout((int) txTimeout);
		bind(xid);
	}

	protected ThreadContext createThreadContext(final Xid xid)
			throws SystemException {
		try {
			BTMLocator.getBitronixTransactionManager().begin();
			final Transaction t = BTMLocator.getBitronixTransactionManager()
					.getCurrentTransaction();
			t.registerSynchronization(new Synchronization() {

				/**
				 * {@inheritDoc}
				 *
				 * @see javax.transaction.Synchronization#beforeCompletion()
				 */
				@Override
				public void beforeCompletion() {
					// do nothing
				}

				/**
				 * {@inheritDoc}
				 *
				 * @see javax.transaction.Synchronization#afterCompletion(int)
				 */
				@Override
				public void afterCompletion(final int status) {
					BTMXAWorkAdapter.this.importedTransactions.remove(xid);
				}
			});
			return unbound();
		} catch (final NotSupportedException e) {
			final SystemException se = new SystemException();
			se.initCause(e);
			throw se;
		} catch (final IllegalStateException e) {
			final SystemException se = new SystemException();
			se.initCause(e);
			throw se;
		} catch (final RollbackException e) {
			final SystemException se = new SystemException();
			se.initCause(e);
			throw se;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.commons.jca.wm.IXAWorkAdapter#end(javax.transaction.xa.Xid)
	 */
	@Override
	public void end(final Xid xid) throws XAException, SystemException {
		unbound();
	}

	/**
	 * Realiza el bind en BTM del thread
	 *
	 * @param xid
	 * @throws XAException
	 */
	public ThreadContext bind(final Xid xid) throws XAException {
		final ThreadContext item = this.importedTransactions.get(xid);
		if (null == item) {
			throw new XAException(XAException.XAER_INVAL);
		}
		BTMLocator.getContexts().put(Thread.currentThread(), item);
		return item;
	}

	public ThreadContext unbound() {
		final ThreadContext tc = BTMLocator.getContexts().remove(
				Thread.currentThread());
		return tc;
	}
}
