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

import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;

public class DefaultEnlistXAResource implements IEnlistXAResource {

	private TransactionManager transactionManager;

	public DefaultEnlistXAResource() {
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
	 * (non-Javadoc)
	 * 
	 * @see com.farmafene.commons.jca.IEnlistXAResource#enlist(javax.transaction.xa.XAResource)
	 */
	@Override
	public void enlist(XAResource xaResource) throws IllegalStateException,
			RollbackException, SystemException {
		transactionManager.getTransaction().enlistResource(xaResource);
	}

	/**
	 * Establece el TransactionManager
	 * 
	 * @param transactionManager
	 *            transactionManager a establecer
	 */
	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.farmafene.commons.jca.IEnlistXAResource#isInTransaction()
	 */
	@Override
	public boolean isInTransaction() {
		boolean isInTransaction = false;
		try {
			return Status.STATUS_ACTIVE == transactionManager.getStatus();
		} catch (SystemException e) {
			// do nothing
		}
		return isInTransaction;
	}
}
