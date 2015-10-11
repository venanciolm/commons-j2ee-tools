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

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.resource.spi.XATerminator;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import bitronix.tm.internal.ThreadContext;

public class BTMXATerminator extends BTMTransactionSynchronizationRegistry
		implements XATerminator, TransactionManager, UserTransaction {

	private final Set<Xid> prepared;

	public BTMXATerminator() {
		this.prepared = new CopyOnWriteArraySet<Xid>();
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
		sb.append(BTMLocator.getBitronixTransactionManager());
		sb.append(", current=").append(BTMLocator.getBitronixTransactionManager().getCurrentTransaction());
		sb.append("}");
		return sb.toString();
	}

	public void shutdown() {
		BTMLocator.getBitronixTransactionManager().shutdown();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.XATerminator#commit(javax.transaction.xa.Xid,
	 *      boolean)
	 */
	@Override
	public void commit(final Xid xid, final boolean onePhase)
			throws XAException {
		try {
			final ThreadContext tc = bind(xid);
			tc.getTransaction().commit();
		} catch (final SecurityException e) {
			final XAException xa = new XAException(XAException.XAER_INVAL);
			xa.initCause(e);
			throw xa;
		} catch (final RollbackException e) {
			final XAException xa = new XAException(XAException.XAER_INVAL);
			xa.initCause(e);
			throw xa;
		} catch (final HeuristicMixedException e) {
			final XAException xa = new XAException(XAException.XAER_INVAL);
			xa.initCause(e);
			throw xa;
		} catch (final HeuristicRollbackException e) {
			final XAException xa = new XAException(XAException.XAER_INVAL);
			xa.initCause(e);
			throw xa;
		} catch (final SystemException e) {
			final XAException xa = new XAException(XAException.XAER_INVAL);
			xa.initCause(e);
			throw xa;
		} finally {
			unbound();
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.XATerminator#forget(javax.transaction.xa.Xid)
	 */
	@Override
	public void forget(final Xid xid) throws XAException {
		rollback(xid);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.XATerminator#prepare(javax.transaction.xa.Xid)
	 */
	@Override
	public int prepare(final Xid xid) throws XAException {
		try {
			bind(xid);
			registerInterposedSynchronization(new Synchronization() {
				/**
				 * {@inheritDoc}
				 *
				 * @see javax.transaction.Synchronization#beforeCompletion()
				 */
				@Override
				public void beforeCompletion() {
				}

				/**
				 * {@inheritDoc}
				 *
				 * @see javax.transaction.Synchronization#afterCompletion(int)
				 */
				@Override
				public void afterCompletion(final int status) {
					synchronized (BTMXATerminator.this.prepared) {
						BTMXATerminator.this.prepared.remove(xid);
					}
				}
			});
			if (getRollbackOnly()) {
				throw new XAException(XAException.XAER_INVAL);
			}
			synchronized (this.prepared) {
				this.prepared.add(xid);
			}
			return XAResource.XA_OK;
		} finally {
			unbound();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.XATerminator#recover(int)
	 */
	@Override
	public Xid[] recover(final int flag) throws XAException {
		switch (flag) {
		case XAResource.TMSTARTRSCAN:
		case XAResource.TMENDRSCAN:
		case XAResource.TMNOFLAGS:
			break;
		default:
			final XAException ex = new XAException(XAException.XAER_INVAL);
			throw ex;
		}
		try {
			synchronized (this.prepared) {
				return this.prepared.toArray(new Xid[0]);
			}
		} catch (final Throwable th) {
			final XAException ex = new XAException(XAException.XAER_INVAL);
			ex.initCause(th);
			throw ex;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.XATerminator#rollback(javax.transaction.xa.Xid)
	 */
	@Override
	public void rollback(final Xid xid) throws XAException {
		try {
			final ThreadContext tc = bind(xid);
			tc.getTransaction().rollback();
		} catch (final IllegalStateException e) {
			final XAException xa = new XAException(XAException.XAER_INVAL);
			xa.initCause(e);
			throw xa;
		} catch (final SystemException e) {
			final XAException xa = new XAException(XAException.XAER_INVAL);
			xa.initCause(e);
			throw xa;
		} finally {
			unbound();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.TransactionManager#begin()
	 */
	@Override
	public void begin() throws NotSupportedException, SystemException {
		BTMLocator.getBitronixTransactionManager().begin();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.TransactionManager#commit()
	 */
	@Override
	public void commit() throws HeuristicMixedException,
			HeuristicRollbackException, IllegalStateException,
			RollbackException, SecurityException, SystemException {
		BTMLocator.getBitronixTransactionManager().commit();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.TransactionManager#getStatus()
	 */
	@Override
	public int getStatus() throws SystemException {
		return BTMLocator.getBitronixTransactionManager().getStatus();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.TransactionManager#getTransaction()
	 */
	@Override
	public Transaction getTransaction() throws SystemException {
		return BTMLocator.getBitronixTransactionManager().getTransaction();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.TransactionManager#resume(javax.transaction.Transaction)
	 */
	@Override
	public void resume(Transaction tobj) throws IllegalStateException,
			InvalidTransactionException, SystemException {
		BTMLocator.getBitronixTransactionManager().resume(tobj);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.TransactionManager#rollback()
	 */
	@Override
	public void rollback() throws IllegalStateException, SecurityException,
			SystemException {
		BTMLocator.getBitronixTransactionManager().rollback();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.TransactionManager#setTransactionTimeout(int)
	 */
	@Override
	public void setTransactionTimeout(int seconds) throws SystemException {
		BTMLocator.getBitronixTransactionManager().setTransactionTimeout(
				seconds);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.transaction.TransactionManager#suspend()
	 */
	@Override
	public Transaction suspend() throws SystemException {
		return BTMLocator.getBitronixTransactionManager().suspend();
	}

}
