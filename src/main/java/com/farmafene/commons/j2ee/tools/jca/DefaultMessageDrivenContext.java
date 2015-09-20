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

import java.security.Principal;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TimerService;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class DefaultMessageDrivenContext implements MessageDrivenContext {

	private UserTransaction userTransaction;
	private Context context;
	private Map<String, Object> contextData;

	public DefaultMessageDrivenContext() {

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
	 * @see javax.ejb.EJBContext#getEJBHome()
	 */
	@Override
	public EJBHome getEJBHome() {
		throw new UnsupportedOperationException(this + ", Not supported!");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ejb.EJBContext#getEJBLocalHome()
	 */
	@Override
	public EJBLocalHome getEJBLocalHome() {
		throw new UnsupportedOperationException(this + ", Not supported!");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ejb.EJBContext#getEnvironment()
	 */
	@Override
	public Properties getEnvironment() {
		throw new UnsupportedOperationException(this + ", Not supported!");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ejb.EJBContext#getCallerIdentity()
	 */
	@Deprecated
	@Override
	public java.security.Identity getCallerIdentity() {
		throw new UnsupportedOperationException(this + ", Not supported!");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ejb.EJBContext#getCallerPrincipal()
	 */
	@Override
	public Principal getCallerPrincipal() {
		throw new UnsupportedOperationException(this + ", Not supported!");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ejb.EJBContext#isCallerInRole(java.security.Identity)
	 */
	@Override
	@Deprecated
	public boolean isCallerInRole(java.security.Identity role) {
		throw new UnsupportedOperationException(this + ", Not supported!");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ejb.EJBContext#isCallerInRole(java.lang.String)
	 */
	@Override
	public boolean isCallerInRole(String roleName) {
		throw new UnsupportedOperationException(this + ", Not supported!");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ejb.EJBContext#getUserTransaction()
	 */
	@Override
	public UserTransaction getUserTransaction() throws IllegalStateException {
		return userTransaction;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ejb.EJBContext#setRollbackOnly()
	 */
	@Override
	public void setRollbackOnly() throws IllegalStateException {
		try {
			getUserTransaction().setRollbackOnly();
		} catch (SystemException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ejb.EJBContext#getRollbackOnly()
	 */
	@Override
	public boolean getRollbackOnly() throws IllegalStateException {
		try {
			return Status.STATUS_MARKED_ROLLBACK == getUserTransaction()
					.getStatus();
		} catch (SystemException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ejb.EJBContext#getTimerService()
	 */
	@Override
	public TimerService getTimerService() throws IllegalStateException {
		throw new UnsupportedOperationException(this + ", Not supported!");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ejb.EJBContext#lookup(java.lang.String)
	 */
	@Override
	public Object lookup(String name) {
		try {
			return context.lookup(name);
		} catch (NamingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.ejb.EJBContext#getContextData()
	 */
	@Override
	public Map<String, Object> getContextData() {
		return contextData;
	}
}
