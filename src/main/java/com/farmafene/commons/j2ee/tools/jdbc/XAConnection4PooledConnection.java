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
package com.farmafene.commons.j2ee.tools.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;
import javax.sql.StatementEventListener;
import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;

public class XAConnection4PooledConnection implements XAConnection {

	private PooledConnection connection;
	private XAResource xaResource;

	private XAConnection4PooledConnection() {

	}

	public XAConnection4PooledConnection(final PooledConnection pconn) throws SQLException {
		this();
		this.connection = pconn;
		this.xaResource = new XAResourceImpl(getConnection());

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.PooledConnection#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return this.connection.getConnection();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.PooledConnection#close()
	 */
	@Override
	public void close() throws SQLException {
		this.connection.close();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.PooledConnection#addConnectionEventListener(javax.sql.
	 *      ConnectionEventListener)
	 */
	@Override
	public void addConnectionEventListener(final ConnectionEventListener listener) {
		this.connection.addConnectionEventListener(listener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.PooledConnection#removeConnectionEventListener(javax.sql.
	 *      ConnectionEventListener)
	 */
	@Override
	public void removeConnectionEventListener(final ConnectionEventListener listener) {
		this.connection.removeConnectionEventListener(listener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.PooledConnection#addStatementEventListener(javax.sql.
	 *      StatementEventListener)
	 */
	@Override
	public void addStatementEventListener(final StatementEventListener listener) {
		this.connection.addStatementEventListener(listener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.PooledConnection#removeStatementEventListener(javax.sql.
	 *      StatementEventListener)
	 */
	@Override
	public void removeStatementEventListener(final StatementEventListener listener) {
		this.connection.removeStatementEventListener(listener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.XAConnection#getXAResource()
	 */
	@Override
	public XAResource getXAResource() throws SQLException {
		return this.xaResource;
	}
}
