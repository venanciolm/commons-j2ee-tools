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

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bitronix.tm.BitronixXid;
import bitronix.tm.internal.XAResourceHolderState;
import bitronix.tm.resource.common.ResourceBean;
import bitronix.tm.resource.common.StateChangeListener;
import bitronix.tm.resource.common.XAResourceHolder;
import bitronix.tm.resource.common.XAStatefulHolder;
import bitronix.tm.utils.Uid;

public class InboundXAResourceHolder implements XAResourceHolder {

	private static final Logger logger = LoggerFactory.getLogger(InboundXAResourceHolder.class);
	private final XAResource xAResource;
	private final ResourceBean resourceBean;

	public InboundXAResourceHolder(final XAResource xAResource, final ResourceBean resourceBean) {
		this.xAResource = xAResource;
		this.resourceBean = resourceBean;
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
	 * @see bitronix.tm.resource.common.XAStatefulHolder#addStateChangeEventListener
	 *      (bitronix.tm.resource.common.StateChangeListener)
	 */
	@Override
	public void addStateChangeEventListener(final StateChangeListener statechangelistener) {
		logger.debug("XAStatefulHolder.addStateChangeEventListener()");
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 * @see bitronix.tm.resource.common.XAStatefulHolder#removeStateChangeEventListener
	 *      (bitronix.tm.resource.common.StateChangeListener)
	 */
	@Override
	public void removeStateChangeEventListener(final StateChangeListener statechangelistener) {
		logger.debug("XAStatefulHolder.removeStateChangeEventListener()");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAStatefulHolder#close()
	 */
	@Override
	public void close() throws Exception {
		logger.debug("XAStatefulHolder.close()");
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 * @see bitronix.tm.resource.common.XAStatefulHolder#getConnectionHandle()
	 */
	@Override
	public Object getConnectionHandle() throws Exception {
		logger.debug("XAStatefulHolder.getConnectionHandle()");
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 * @see bitronix.tm.resource.common.XAStatefulHolder#getLastReleaseDate()
	 */
	@Override
	public Date getLastReleaseDate() {
		logger.debug("XAStatefulHolder.getLastReleaseDate()");
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 * @see bitronix.tm.resource.common.XAStatefulHolder#getState()
	 */
	@Override
	public int getState() {
		logger.debug("XAStatefulHolder.getState()");
		return XAStatefulHolder.STATE_ACCESSIBLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 *
	 * @see bitronix.tm.resource.common.XAStatefulHolder#getXAResourceHolders()
	 */
	@Override
	public List<XAResourceHolder> getXAResourceHolders() {
		logger.debug("XAStatefulHolder.getXAResourceHolders()");
		return Arrays.asList(new XAResourceHolder[] { this });
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAStatefulHolder#setState(int)
	 */
	@Override
	public void setState(final int i) {
		logger.debug("XAStatefulHolder.setState()");

	}

	/*----------------------------------------- RESOURCE HOLDER ------------------------*/
	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceHolder#getResourceBean()
	 */
	@Override
	public ResourceBean getResourceBean() {
		return this.resourceBean;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceHolder#getXAResource()
	 */
	@Override
	public XAResource getXAResource() {
		return this.xAResource;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceHolder#
	 *      getXAResourceHolderStatesForGtrid(bitronix.tm.utils.Uid)
	 */
	@Override
	public Map<Uid, XAResourceHolderState> getXAResourceHolderStatesForGtrid(final Uid uid) {
		logger.debug("XAResourceHolder.getXAResourceHolderStatesForGtrid()");
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceHolder#hasStateForXAResource(bitronix
	 *      .tm.resource.common.XAResourceHolder)
	 */
	@Override
	public boolean hasStateForXAResource(final XAResourceHolder xaresourceholder) {
		logger.debug("XAResourceHolder.hasStateForXAResource()");
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceHolder#putXAResourceHolderState
	 *      (bitronix.tm.BitronixXid,
	 *      bitronix.tm.internal.XAResourceHolderState)
	 */
	@Override
	public void putXAResourceHolderState(final BitronixXid bitronixxid, final XAResourceHolderState xaresourceholderstate) {
		logger.debug("XAResourceHolder.putXAResourceHolderState()");

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see bitronix.tm.resource.common.XAResourceHolder#removeXAResourceHolderState
	 *      (bitronix.tm.BitronixXid)
	 */
	@Override
	public void removeXAResourceHolderState(final BitronixXid bitronixxid) {
		logger.debug("XAResourceHolder.removeXAResourceHolderState()");

	}

}
