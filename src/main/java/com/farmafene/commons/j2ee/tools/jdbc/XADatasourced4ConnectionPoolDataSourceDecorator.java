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

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class XADatasourced4ConnectionPoolDataSourceDecorator implements XADataSource, InitializingBean {

	private ConnectionPoolDataSource connectionPoolDataSource;

	/**
	 * {@inheritDoc}
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.connectionPoolDataSource, "connectionPoolDataSource is not set!");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.XADataSource#getXAConnection()
	 */
	@Override
	public XAConnection getXAConnection() throws SQLException {
		final PooledConnection pconn = this.connectionPoolDataSource.getPooledConnection();
		final XAConnection xaConn = new XAConnection4PooledConnection(pconn);
		return xaConn;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.XADataSource#getXAConnection(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public XAConnection getXAConnection(final String user, final String password) throws SQLException {
		final PooledConnection pconn = this.connectionPoolDataSource.getPooledConnection(user, password);
		final XAConnection xaConn = new XAConnection4PooledConnection(pconn);
		return xaConn;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.CommonDataSource#getParentLogger()
	 */
	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException("Not supproted");
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see javax.sql.CommonDataSource#getLogWriter()
	 */
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return this.connectionPoolDataSource.getLogWriter();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
	 */
	@Override
	public void setLogWriter(final PrintWriter out) throws SQLException {
		this.connectionPoolDataSource.setLogWriter(out);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.CommonDataSource#setLoginTimeout(int)
	 */
	@Override
	public void setLoginTimeout(final int seconds) throws SQLException {
		this.connectionPoolDataSource.setLoginTimeout(seconds);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.CommonDataSource#getLoginTimeout()
	 */
	@Override
	public int getLoginTimeout() throws SQLException {
		return this.connectionPoolDataSource.getLoginTimeout();
	}

	/**
	 * Devuelve el valor de la propiedad 'connectionPoolDataSource'
	 * @return Propiedad connectionPoolDataSource
	 */
	public ConnectionPoolDataSource getConnectionPoolDataSource() {
		return this.connectionPoolDataSource;
	}

	/**
	 * Asigna el valor de la propiedad 'connectionPoolDataSource'
	 * @param connectionPoolDataSource valor que se le quiere dar a la propiedad
	 *            'connectionPoolDataSource'
	 */
	public void setConnectionPoolDataSource(final ConnectionPoolDataSource connectionPoolDataSource) {
		this.connectionPoolDataSource = connectionPoolDataSource;
	}
}
