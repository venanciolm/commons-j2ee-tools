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

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.farmafene.commons.j2ee.tools.jca.common.StringPrintStream;

public class XAResourceLog implements XAResource {

	private static final Logger logger = LoggerFactory
			.getLogger(XAResourceLog.class);
	private Xid xId;
	private int transactionTimeout = 120;
	private ActivationSpecLog activationSpec;

	public XAResourceLog(Xid xId, ActivationSpecLog activationSpec) {
		this.xId = xId;
		this.activationSpec = activationSpec;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.xId == null) ? 0 : this.xId.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final XAResourceLog other = (XAResourceLog) obj;
		if (this.xId == null) {
			if (other.xId != null) {
				return false;
			}
		} else if (!this.xId.equals(other.xId)) {
			return false;
		}
		return true;
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
		sb.append(this.xId);
		sb.append(", transactionTimeout=").append(this.transactionTimeout);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#commit(javax.transaction.xa.Xid,
	 *      boolean)
	 */
	@Override
	public void commit(final Xid xid, final boolean onePhase)
			throws XAException {
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| Commit:                                          ||");
			ps.print("|| OnePhase: ");
			ps.println(onePhase);
			ps.print("|| Xid: ");
			ps.println(xid);
			ps.print("|| Xid: ");
			ps.println(xId);
			ps.println("|+--------------------------------------------------*/");
			logger.info("{}", ps);
		}
		this.activationSpec.getXAResources().remove(xId);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#end(javax.transaction.xa.Xid, int)
	 */
	@Override
	public void end(final Xid xid, final int flags) throws XAException {
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| End:                                             ||");
			ps.print("|| Flags: ");
			ps.println(flags);
			ps.print("|| Xid: ");
			ps.println(xid);
			ps.print("|| Xid: ");
			ps.println(xId);
			ps.println("|+--------------------------------------------------*/");
			logger.info("{}", ps);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#forget(javax.transaction.xa.Xid)
	 */
	@Override
	public void forget(final Xid xid) throws XAException {
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| Forget:                                          ||");
			ps.print("|| Xid: ");
			ps.println(xid);
			ps.print("|| Xid: ");
			ps.println(xId);
			ps.println("|+--------------------------------------------------*/");
			logger.info("{}", ps);
		}
		this.activationSpec.getXAResources().remove(xId);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#getTransactionTimeout()
	 */
	@Override
	public int getTransactionTimeout() throws XAException {
		logger.info("getTransactionTimeout()");
		return this.transactionTimeout;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#isSameRM(javax.transaction.xa.XAResource)
	 */
	@Override
	public boolean isSameRM(final XAResource xaResource) throws XAException {
		logger.info("isSameRM(" + xaResource + ")");
		return this == xaResource;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#prepare(javax.transaction.xa.Xid)
	 */
	@Override
	public int prepare(final Xid xid) throws XAException {
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| Prepare:                                         ||");
			ps.print("|| Xid: ");
			ps.println(xid);
			ps.print("|| Xid: ");
			ps.println(xId);
			ps.println("|+--------------------------------------------------*/");
			logger.info("{}", ps);
		}
		return XA_OK;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#recover(int)
	 */
	@Override
	public Xid[] recover(final int flag) throws XAException {
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| Recover:                                         ||");
			ps.print("|| Flag: ");
			ps.println(flag);
			ps.println("|+--------------------------------------------------*/");
			logger.info("{}", ps);
		}
		return new Xid[] {};
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#rollback(javax.transaction.xa.Xid)
	 */
	@Override
	public void rollback(final Xid xid) throws XAException {
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| Rollback:                                        ||");
			ps.print("|| Xid: ");
			ps.println(xid);
			ps.print("|| Xid: ");
			ps.println(xId);
			ps.println("|+--------------------------------------------------*/");
			logger.info("{}", ps);
		}
		this.activationSpec.getXAResources().remove(xId);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#setTransactionTimeout(int)
	 */
	@Override
	public boolean setTransactionTimeout(final int seconds) throws XAException {
		logger.info("setTransactionTimeout(" + seconds + ")");
		this.transactionTimeout = seconds;
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.transaction.xa.XAResource#start(javax.transaction.xa.Xid, int)
	 */
	@Override
	public void start(final Xid xid, final int flags) throws XAException {
		if (logger.isInfoEnabled()) {
			final StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+|");
			ps.println("|| Start:                                           ||");
			ps.print("|| Flags: ");
			ps.println(flags);
			ps.print("|| Xid: ");
			ps.println(xid);
			ps.print("|| Xid: ");
			ps.println(xId);
			ps.println("|+--------------------------------------------------*/");
			logger.info("{}", ps);
		}
		this.activationSpec.getXAResources().remove(xId);
	}
}
