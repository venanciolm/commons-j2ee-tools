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
package com.farmafene.commons.j2ee.tools.jca.spi;

import java.io.IOException;
import java.io.Serializable;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnectionMetaData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.farmafene.commons.j2ee.tools.jca.IManagedDriver;

@SuppressWarnings("serial")
public class ConnectionFactorySPI<Driver extends IManagedDriver> implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionFactorySPI.class);
	private ManagedConnectionFactorySPI<Driver, ? extends ConnectionRequestInfo, ? extends ManagedConnectionMetaData> managedConnectionFactory;
	private ConnectionManager connectionManager;
	private ConnectionRequestInfo connectionRequestInfo;

	public ConnectionFactorySPI() {

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("=[");
		sb.append("]");
		return sb.toString();
	}

	public Driver getConnection() throws IOException {
		try {
			@SuppressWarnings("unchecked")
			final Driver con = (Driver) this.connectionManager.allocateConnection(this.managedConnectionFactory, getConnectionRequestInfo());
			return con;
		} catch (final ResourceException e) {
			logger.error("Se ha producido un ResourceException ", e);
			throw new IOException("getConnection()", e);
		}
	}

	/**
	 * @return the managedConnectionFactory
	 */
	public ManagedConnectionFactorySPI<Driver, ? extends ConnectionRequestInfo, ? extends ManagedConnectionMetaData> getManagedConnectionFactory() {
		return this.managedConnectionFactory;
	}

	/**
	 * @param managedConnectionFactory the managedConnectionFactory to set
	 */
	public void setManagedConnectionFactory(
			final ManagedConnectionFactorySPI<Driver, ? extends ConnectionRequestInfo, ? extends ManagedConnectionMetaData> managedConnectionFactory) {
		this.managedConnectionFactory = managedConnectionFactory;
	}

	/**
	 * @return the connectionManager
	 */
	public ConnectionManager getConnectionManager() {
		return this.connectionManager;
	}

	/**
	 * @param connectionManager the connectionManager to set
	 */
	public void setConnectionManager(final ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	/**
	 * @return the connectionRequestInfo
	 */
	public ConnectionRequestInfo getConnectionRequestInfo() {
		return this.connectionRequestInfo;
	}

	/**
	 * @param connectionRequestInfo the connectionRequestInfo to set
	 */
	public void setConnectionRequestInfo(final ConnectionRequestInfo connectionRequestInfo) {
		this.connectionRequestInfo = connectionRequestInfo;

	}

}
