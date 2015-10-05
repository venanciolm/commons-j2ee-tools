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

public class XADatasourced4ConnectionPoolDataSource implements XADataSource {

	private ConnectionPoolDataSource inner;

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.XADataSource#getXAConnection()
	 */
	@Override
	public XAConnection getXAConnection() throws SQLException {
		final PooledConnection pconn = this.inner.getPooledConnection();
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
		final PooledConnection pconn = this.inner.getPooledConnection(user, password);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#getLogWriter()
	 */
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
	 */
	@Override
	public void setLogWriter(final PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#setLoginTimeout(int)
	 */
	@Override
	public void setLoginTimeout(final int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.CommonDataSource#getLoginTimeout()
	 */
	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
}
