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

import javax.transaction.InvalidTransactionException;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

import org.apache.geronimo.transaction.manager.ImportedTransactionActiveException;
import org.apache.geronimo.transaction.manager.XAWork;

import com.farmafene.commons.j2ee.tools.jca.wm.IXAWorkAdapter;
import com.farmafene.commons.j2ee.tools.jca.wm.XAWorkAdapterNotSupported;

public class GeronimoXAWorkNotSupported implements XAWork {

	private final IXAWorkAdapter wa;

	public GeronimoXAWorkNotSupported() {
		this.wa = new XAWorkAdapterNotSupported();
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
	 * @see org.apache.geronimo.transaction.manager.XAWork#begin(javax.transaction.xa.Xid,
	 *      long)
	 */
	@Override
	public void begin(final Xid xid, final long txTimeout) throws XAException, InvalidTransactionException, SystemException,
			ImportedTransactionActiveException {
		this.wa.begin(xid, txTimeout);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.apache.geronimo.transaction.manager.XAWork#end(javax.transaction.xa.Xid)
	 */
	@Override
	public void end(final Xid xid) throws XAException, SystemException {
		this.wa.end(xid);
	}
}
