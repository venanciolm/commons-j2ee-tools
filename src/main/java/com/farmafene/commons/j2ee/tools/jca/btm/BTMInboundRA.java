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

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.resource.spi.ConnectionManager;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bitronix.tm.internal.XAResourceHolderState;
import bitronix.tm.recovery.RecoveryException;
import bitronix.tm.resource.ResourceRegistrar;
import bitronix.tm.resource.common.RecoveryXAResourceHolder;
import bitronix.tm.resource.common.ResourceBean;
import bitronix.tm.resource.common.TransactionContextHelper;
import bitronix.tm.resource.common.XAResourceHolder;
import bitronix.tm.resource.common.XAResourceProducer;
import bitronix.tm.resource.common.XAStatefulHolder;

import com.farmafene.commons.j2ee.tools.jca.IEnlistXAResource;
import com.farmafene.commons.j2ee.tools.jca.common.StringPrintStream;

/**
 * @author vlopez
 */
@SuppressWarnings("serial")
public class BTMInboundRA extends ResourceBean implements XAResourceProducer,
		IEnlistXAResource {

	private static final Logger logger = LoggerFactory
			.getLogger(BTMInboundRA.class);
	private boolean failed;
	private XAStatefulHolder revoveryXAStatefulHolder;
	private RecoveryXAResourceHolder recoveryXAResourceHolder;
	private final Map<XAResource, XAResourceHolder> enlisted;

	/**
	 *
	 */
	public BTMInboundRA() {
		this.enlisted = new ConcurrentHashMap<XAResource, XAResourceHolder>();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.commons.jca.IEnlistXAResource#enlist(javax.transaction.xa.XAResource)
	 */
	@Override
	public void enlist(final XAResource xaResource) throws SystemException,
			RollbackException {
		XAResourceHolder item = findXAResourceHolder(xaResource);
		if (null == item) {
			final InboundXAResourceHolder holder = new InboundXAResourceHolder(
					xaResource, this);
			item = holder;
			this.enlisted.put(xaResource, item);
			TransactionContextHelper.enlistInCurrentTransaction(holder);
		} else {
			logger.debug("Container not found!");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Enlisted: {} in {}", xaResource,
					BTMLocator.getBitronixTransactionManager());
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.naming.Referenceable#getReference()
	 */
	@Override
	public Reference getReference() throws NamingException {
		return new Reference(ConnectionManager.class.getName(),
				new StringRefAddr("uniqueName", getUniqueName()),
				this.getClassName(), null);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceProducer#startRecovery()
	 */
	@Override
	public XAResourceHolderState startRecovery() throws RecoveryException {
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+\\");
			ps.println("|| startRecovery                                    ||");
			ps.print("\\+--------------------------------------------------*/");
			logger.debug("{}", ps);
		}
		try {
			/*
			 * En realidad, no hace nada!
			 */
			this.revoveryXAStatefulHolder = new InboundXAResourceHolder(
					new XAResourceRecovery(), this);
			this.revoveryXAStatefulHolder
					.setState(XAStatefulHolder.STATE_NOT_ACCESSIBLE);
			this.recoveryXAResourceHolder = new RecoveryXAResourceHolder(
					this.revoveryXAStatefulHolder.getXAResourceHolders().get(0));
			return new XAResourceHolderState(this.recoveryXAResourceHolder,
					this);
		} catch (final Exception e) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+\\");
			ps.println("|| startRecovery                                    ||");
			ps.println("|| Error:                                           ||");
			e.printStackTrace(ps);
			ps.print("\\+--------------------------------------------------*/");
			logger.error("{}", ps);
			throw new RecoveryException("startRecovery()", e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceProducer#endRecovery()
	 */
	@Override
	public void endRecovery() throws RecoveryException {
		if (logger.isDebugEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+\\");
			ps.println("|| endRecovery                                      ||");
			ps.print("\\+--------------------------------------------------*/");
			logger.debug("{}", ps);
		}
		try {
			this.recoveryXAResourceHolder.close();
			this.revoveryXAStatefulHolder.close();
		} catch (final Exception e) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------------+\\");
			ps.println("|| endRecovery                                      ||");
			ps.println("|| Error:                                           ||");
			e.printStackTrace(ps);
			ps.print("\\+--------------------------------------------------*/");
			logger.error("{}", ps);
			throw new RecoveryException("endRecovery()", e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceProducer#setFailed(boolean)
	 */
	@Override
	public void setFailed(final boolean failed) {
		this.failed = failed;

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceProducer#init()
	 */
	@Override
	public void init() {
		try {
			ResourceRegistrar.register(this);
		} catch (final Exception e) {
			setFailed(true);
			logger.error("Error en la ceación de la factoría!", e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceProducer#close()
	 */
	@Override
	public void close() {
		ResourceRegistrar.unregister(this);

	}

	/**
	 * @return the failed
	 */
	public boolean isFailed() {
		return this.failed;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceProducer#findXAResourceHolder(javax.transaction.xa.XAResource)
	 */
	@Override
	public XAResourceHolder findXAResourceHolder(final XAResource xaResource) {
		return this.enlisted.get(xaResource);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceProducer#createPooledConnection(java.lang.Object,
	 *      bitronix.tm.resource.common.ResourceBean)
	 */
	@Override
	public XAStatefulHolder createPooledConnection(final Object xaFactory,
			final ResourceBean bean) throws Exception {
		throw new UnsupportedOperationException("Not supported!");
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
			final boolean hayTx = TransactionContextHelper.currentTransaction() != null;
			isInTransaction = hayTx
					&& Status.STATUS_ACTIVE == TransactionContextHelper
							.currentTransaction().getStatus();
		} catch (final SystemException e) {
			// do nothing
		}
		return isInTransaction;
	}
}
