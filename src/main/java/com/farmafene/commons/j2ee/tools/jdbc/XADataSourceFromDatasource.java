/*
 * Copyright (c) 2009-2021 farmafene.com
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

import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

/**
 * Implementación de un XADataSource para no-XA JDBC Driver.
 */
public class XADataSourceFromDatasource implements XADataSource {

	private DataSource datasource;

	/**
	 * Constructor de la clase
	 */
	public XADataSourceFromDatasource() {
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append("={");
		sb.append("datasource=").append(this.datasource);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XADataSource#getLogWriter()
	 */
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XADataSource#setLogWriter(PrintWriter)
	 */
	@Override
	public void setLogWriter(final PrintWriter out) throws SQLException {
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XADataSource#getXAConnection()
	 */
	@Override
	public XAConnection getXAConnection() throws SQLException {
		return new Connection2XAConnection(this.datasource.getConnection());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XADataSource#getXAConnection(String, String)
	 */
	@Override
	public XAConnection getXAConnection(final String user, final String password) throws SQLException {
		return new Connection2XAConnection(datasource.getConnection(user, password));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.CommonDataSource#getParentLogger()
	 */
	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException("Not supported!");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XADataSource#getLoginTimeout()
	 */
	@Override
	public int getLoginTimeout() throws SQLException {
		return this.datasource.getLoginTimeout();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see XADataSource#setLoginTimeout(int)
	 */
	@Override
	public void setLoginTimeout(final int seconds) throws SQLException {
		this.datasource.setLoginTimeout(seconds);
	}

	/**
	 * @return the datasource
	 */
	public DataSource getDatasource() {
		return datasource;
	}

	/**
	 * @param datasource the datasource to set
	 */
	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
}
