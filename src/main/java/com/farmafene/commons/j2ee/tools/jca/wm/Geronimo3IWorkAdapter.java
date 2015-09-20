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

import javax.transaction.InvalidTransactionException;
import javax.transaction.SystemException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

import org.apache.geronimo.transaction.manager.GeronimoTransactionManager;
import org.apache.geronimo.transaction.manager.ImportedTransactionActiveException;

public class Geronimo3IWorkAdapter implements IXAWorkAdapter {

	private GeronimoTransactionManager geronimo3;

	public Geronimo3IWorkAdapter() {

	}

	public Geronimo3IWorkAdapter(final GeronimoTransactionManager tx) {
		this();
		this.geronimo3 = tx;
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
	 * @see com.farmafene.commons.j2ee.tools.jca.wm.IXAWorkAdapter#begin(javax.transaction.xa.Xid,
	 *      long)
	 */
	@Override
	public void begin(final Xid xid, final long txTimeout) throws XAException, SystemException, InvalidTransactionException {
		try {
			this.geronimo3.begin(xid, txTimeout);
		} catch (final ImportedTransactionActiveException e) {
			final SystemException se = new SystemException("Error");
			se.initCause(e);
			throw se;
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.commons.j2ee.tools.jca.wm.IXAWorkAdapter#end(javax.transaction.xa.Xid)
	 */
	@Override
	public void end(final Xid xid) throws XAException, SystemException {
		this.geronimo3.end(xid);

	}

	/**
	 * @return the geronimo3
	 */
	public GeronimoTransactionManager getGeronimo3() {
		return this.geronimo3;
	}

	/**
	 * @param geronimo3 the geronimo3 to set
	 */
	public void setGeronimo3(final GeronimoTransactionManager geronimo3) {
		this.geronimo3 = geronimo3;
	}
}
