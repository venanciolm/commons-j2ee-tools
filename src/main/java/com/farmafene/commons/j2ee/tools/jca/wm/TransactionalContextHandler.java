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
package com.farmafene.commons.j2ee.tools.jca.wm;

import javax.resource.spi.work.TransactionContext;
import javax.resource.spi.work.WorkCompletedException;
import javax.resource.spi.work.WorkContext;
import javax.resource.spi.work.WorkException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vlopez@farmafene.com
 *
 */
public class TransactionalContextHandler implements WorkContextHandler<TransactionContext> {

	private static final Logger logger = LoggerFactory.getLogger(TransactionalContextHandler.class);
	private IXAWorkAdapter iXAWorkAdapter;

	public TransactionalContextHandler() {

	}

	public TransactionalContextHandler(final IXAWorkAdapter iXAWorkAdapter) {
		this.iXAWorkAdapter = iXAWorkAdapter;
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
		sb.append("iXAWorkAdapter=").append(this.iXAWorkAdapter);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.commons.j2ee.tools.jca.wm.WorkContextHandler#supports(javax.resource.spi.work.WorkContext)
	 */
	@Override
	public boolean supports(final WorkContext workContexts) {
		return TransactionContext.class.isAssignableFrom(workContexts.getClass());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.commons.j2ee.tools.jca.wm.WorkContextHandler#before()
	 */
	@Override
	public void before(final TransactionContext workContext) throws WorkException {
		if (workContext.getXid() != null) {
			try {
				final long transactionTimeout = workContext.getTransactionTimeout();
				// translate -1 value to 0 to indicate default transaction
				// timeout.
				this.iXAWorkAdapter.begin(workContext.getXid(), transactionTimeout < 0 ? 0 : transactionTimeout);
			} catch (final XAException e) {
				final WorkException we = (WorkException) new WorkCompletedException("Transaction import failed for xid " + workContext.getXid(),
								WorkException.TX_RECREATE_FAILED).initCause(e);
				logger.error("Transaction import failed for xid", we);
				throw we;
			} catch (final InvalidTransactionException e) {
				final WorkException we = (WorkException) new WorkCompletedException("Transaction import failed for xid " + workContext.getXid(),
								WorkException.TX_RECREATE_FAILED).initCause(e);
				logger.error("Transaction import failed for xid", we);
				throw we;
			} catch (final SystemException e) {
				final WorkException we = (WorkCompletedException) new WorkCompletedException("Transaction import failed for xid "
						+ workContext.getXid(), WorkException.TX_RECREATE_FAILED).initCause(e);
				logger.error("Transaction import failed for xid", we);
				throw we;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.commons.j2ee.tools.jca.wm.WorkContextHandler#after()
	 */
	@Override
	public void after(final TransactionContext workContext) throws WorkException {
		if (workContext.getXid() != null) {
			try {
				this.iXAWorkAdapter.end(workContext.getXid());
			} catch (final XAException e) {
				final WorkException we = (WorkException) new WorkCompletedException("Transaction end failed for xid " + workContext.getXid(),
								WorkException.TX_RECREATE_FAILED).initCause(e);
				logger.error("Transaction end failed for xid", we);
				throw we;
			} catch (final SystemException e) {
				final WorkException we = (WorkException) new WorkCompletedException("Transaction end failed for xid " + workContext.getXid(),
								WorkException.TX_RECREATE_FAILED).initCause(e);
				logger.error("Transaction end failed for xid", we);
				throw we;
			}
		}
	}
}
