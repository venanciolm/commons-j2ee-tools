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

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bitronix.tm.TransactionManagerServices;

class XAResourceRecovery implements XAResource {
	private static final Logger logger = LoggerFactory.getLogger(XAResourceRecovery.class);

	public XAResourceRecovery() {

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
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#start(javax.transaction.xa.Xid, int)
	 */
	@Override
	public void start(final Xid xid, final int flags) throws XAException {
		if (logger.isDebugEnabled()) {
			logger.debug(this + ".start(Xid=" + xid + ", flags=" + flags + ")");
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#setTransactionTimeout(int)
	 */
	@Override
	public boolean setTransactionTimeout(final int seconds) throws XAException {
		if (logger.isDebugEnabled()) {
			logger.debug(this + ".setTransactionTimeout(seconds=" + seconds + ")");
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#rollback(javax.transaction.xa.Xid)
	 */
	@Override
	public void rollback(final Xid xid) throws XAException {
		if (logger.isDebugEnabled()) {
			logger.debug(this + ".rollback(Xid=" + xid + ")");
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#recover(int)
	 */
	@Override
	public Xid[] recover(final int flag) throws XAException {
		if (logger.isDebugEnabled()) {
			logger.debug(this + ".recover(flag=" + flag + ")");
		}
		return new Xid[] {};
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#prepare(javax.transaction.xa.Xid)
	 */
	@Override
	public int prepare(final Xid xid) throws XAException {
		if (logger.isDebugEnabled()) {
			logger.debug(this + ".prepare(Xid=" + xid + ")");
		}
		return XA_OK;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#isSameRM(javax.transaction.xa.XAResource)
	 */
	@Override
	public boolean isSameRM(final XAResource xares) throws XAException {
		if (logger.isDebugEnabled()) {
			logger.debug(this + ".isSameRM(XAResource=" + xares + ")");
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#getTransactionTimeout()
	 */
	@Override
	public int getTransactionTimeout() throws XAException {
		if (logger.isDebugEnabled()) {
			logger.debug(this + ".getTransactionTimeout()");
		}
		return TransactionManagerServices.getConfiguration().getDefaultTransactionTimeout();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#forget(javax.transaction.xa.Xid)
	 */
	@Override
	public void forget(final Xid xid) throws XAException {
		if (logger.isDebugEnabled()) {
			logger.debug(this + ".forget(Xid=" + xid + ")");
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#end(javax.transaction.xa.Xid, int)
	 */
	@Override
	public void end(final Xid xid, final int flags) throws XAException {
		if (logger.isDebugEnabled()) {
			logger.debug(this + ".end(Xid=" + xid + ", flags=" + flags + ")");
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#commit(javax.transaction.xa.Xid,
	 *      boolean)
	 */
	@Override
	public void commit(final Xid xid, final boolean onePhase) throws XAException {
		if (logger.isDebugEnabled()) {
			logger.debug(this + ".commit(Xid=" + xid + ", onePhase=" + onePhase + ")");
		}
	}
}