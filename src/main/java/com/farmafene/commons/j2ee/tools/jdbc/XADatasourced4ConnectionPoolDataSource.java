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
