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

import javax.transaction.Synchronization;
import javax.transaction.TransactionSynchronizationRegistry;

abstract class BTMTransactionSynchronizationRegistry extends BTMXAWorkAdapter
		implements TransactionSynchronizationRegistry {

	public BTMTransactionSynchronizationRegistry() {

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.TransactionSynchronizationRegistry#getResource(java.lang.Object)
	 */
	@Override
	public Object getResource(final Object key) {
		return BTMLocator.getTransactionSynchronizationRegistry().getResource(
				key);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.TransactionSynchronizationRegistry#getRollbackOnly()
	 */
	@Override
	public boolean getRollbackOnly() {
		return BTMLocator.getTransactionSynchronizationRegistry()
				.getRollbackOnly();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.TransactionSynchronizationRegistry#getTransactionKey()
	 */
	@Override
	public Object getTransactionKey() {
		return BTMLocator.getTransactionSynchronizationRegistry()
				.getTransactionKey();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.TransactionSynchronizationRegistry#getTransactionStatus()
	 */
	@Override
	public int getTransactionStatus() {
		return BTMLocator.getTransactionSynchronizationRegistry()
				.getTransactionStatus();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.TransactionSynchronizationRegistry#putResource(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public void putResource(final Object key, final Object value) {
		BTMLocator.getTransactionSynchronizationRegistry().putResource(key,
				value);

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.TransactionSynchronizationRegistry#registerInterposedSynchronization(javax.transaction.Synchronization)
	 */
	@Override
	public void registerInterposedSynchronization(final Synchronization sync) {
		BTMLocator.getTransactionSynchronizationRegistry()
				.registerInterposedSynchronization(sync);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.TransactionSynchronizationRegistry#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly() {
		BTMLocator.getTransactionSynchronizationRegistry().setRollbackOnly();
	}
}
