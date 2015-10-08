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

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author vlopez
 *
 */
public class Connection4ConnectionPooled implements Connection {

	private PooledConnectionSubject subject;
	private Connection connection;

	private Connection4ConnectionPooled() {

	}

	/**
	 * Constructor
	 *
	 * @param connection
	 * @param subject
	 */
	public Connection4ConnectionPooled(final Connection connection, final PooledConnectionSubject subject) {
		this();
		this.connection = connection;
		this.subject = subject;
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
		if (this.subject != null) {
			sb.append("subject=");
			sb.append(this.subject);
			sb.append(", ");
		}
		if (this.connection != null) {
			sb.append("connection=");
			sb.append(this.connection);
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException {
		return this.connection.unwrap(iface);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	@Override
	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		return this.connection.isWrapperFor(iface);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#createStatement()
	 */
	@Override
	public Statement createStatement() throws SQLException {
		SQLException sqle = null;
		try {
			final Statement stmt = this.connection.createStatement();
			final Statement wrapper = new Statement4ConnectionPooled(stmt, this.subject);
			return wrapper;
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#prepareStatement(java.lang.String)
	 */
	@Override
	public PreparedStatement prepareStatement(final String sql) throws SQLException {
		SQLException sqle = null;
		try {
			final PreparedStatement pstmt = this.connection.prepareStatement(sql);
			final PreparedStatement wrapper = new PreparedStatement4ConnectionPooled(pstmt, this.subject);
			this.subject.addStatement(pstmt);
			return wrapper;
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#prepareCall(java.lang.String)
	 */
	@Override
	public CallableStatement prepareCall(final String sql) throws SQLException {
		SQLException sqle = null;
		try {
			final CallableStatement cstmt = this.connection.prepareCall(sql);
			final CallableStatement wrapper = new CallableStatement4ConnectionPooled(cstmt, this.subject);
			this.subject.addStatement(cstmt);
			return wrapper;
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#nativeSQL(java.lang.String)
	 */
	@Override
	public String nativeSQL(final String sql) throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.nativeSQL(sql);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#setAutoCommit(boolean)
	 */
	@Override
	public void setAutoCommit(final boolean autoCommit) throws SQLException {
		SQLException sqle = null;
		try {
			this.connection.setAutoCommit(autoCommit);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#getAutoCommit()
	 */
	@Override
	public boolean getAutoCommit() throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.getAutoCommit();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#commit()
	 */
	@Override
	public void commit() throws SQLException {
		SQLException sqle = null;
		try {
			this.connection.commit();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#rollback()
	 */
	@Override
	public void rollback() throws SQLException {
		SQLException sqle = null;
		try {
			this.connection.rollback();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#close()
	 */
	@Override
	public void close() throws SQLException {
		this.subject.connectionClosed(null);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#isClosed()
	 */
	@Override
	public boolean isClosed() throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.isClosed();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#getMetaData()
	 */
	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.getMetaData();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(final boolean readOnly) throws SQLException {
		SQLException sqle = null;
		try {
			this.connection.setReadOnly(readOnly);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.isReadOnly();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#setCatalog(java.lang.String)
	 */
	@Override
	public void setCatalog(final String catalog) throws SQLException {
		SQLException sqle = null;
		try {
			this.connection.setCatalog(catalog);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#getCatalog()
	 */
	@Override
	public String getCatalog() throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.getCatalog();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#setTransactionIsolation(int)
	 */
	@Override
	public void setTransactionIsolation(final int level) throws SQLException {
		SQLException sqle = null;
		try {
			this.connection.setTransactionIsolation(level);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#getTransactionIsolation()
	 */
	@Override
	public int getTransactionIsolation() throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.getTransactionIsolation();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#getWarnings()
	 */
	@Override
	public SQLWarning getWarnings() throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.getWarnings();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#clearWarnings()
	 */
	@Override
	public void clearWarnings() throws SQLException {
		SQLException sqle = null;
		try {
			this.connection.clearWarnings();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#createStatement(int, int)
	 */
	@Override
	public Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {
		SQLException sqle = null;
		try {
			final Statement stmt = this.connection.createStatement(resultSetType, resultSetConcurrency);
			final Statement wrapper = new Statement4ConnectionPooled(stmt, this.subject);
			return wrapper;
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int, int)
	 */
	@Override
	public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
		SQLException sqle = null;
		try {
			final PreparedStatement pstmt = this.connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
			final PreparedStatement wrapper = new PreparedStatement4ConnectionPooled(pstmt, this.subject);
			this.subject.addStatement(pstmt);
			return wrapper;
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#prepareCall(java.lang.String, int, int)
	 */
	@Override
	public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
		SQLException sqle = null;
		try {
			final CallableStatement cstmt = this.connection.prepareCall(sql, resultSetType, resultSetConcurrency);
			final CallableStatement wrapper = new CallableStatement4ConnectionPooled(cstmt, this.subject);
			this.subject.addStatement(cstmt);
			return wrapper;
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#getTypeMap()
	 */
	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.getTypeMap();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#setTypeMap(java.util.Map)
	 */
	@Override
	public void setTypeMap(final Map<String, Class<?>> map) throws SQLException {
		SQLException sqle = null;
		try {
			this.connection.setTypeMap(map);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#setHoldability(int)
	 */
	@Override
	public void setHoldability(final int holdability) throws SQLException {
		SQLException sqle = null;
		try {
			this.connection.setHoldability(holdability);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#getHoldability()
	 */
	@Override
	public int getHoldability() throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.getHoldability();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#setSavepoint()
	 */
	@Override
	public Savepoint setSavepoint() throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.setSavepoint();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#setSavepoint(java.lang.String)
	 */
	@Override
	public Savepoint setSavepoint(final String name) throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.setSavepoint(name);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#rollback(java.sql.Savepoint)
	 */
	@Override
	public void rollback(final Savepoint savepoint) throws SQLException {
		SQLException sqle = null;
		try {
			this.connection.rollback(savepoint);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
	 */
	@Override
	public void releaseSavepoint(final Savepoint savepoint) throws SQLException {
		SQLException sqle = null;
		try {
			this.connection.releaseSavepoint(savepoint);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#createStatement(int, int, int)
	 */
	@Override
	public Statement createStatement(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
		SQLException sqle = null;
		try {
			final Statement stmt = this.connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
			final Statement wrapper = new Statement4ConnectionPooled(stmt, this.subject);
			return wrapper;
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int, int,
	 *      int)
	 */
	@Override
	public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency,
			final int resultSetHoldability) throws SQLException {
		SQLException sqle = null;
		try {
			final PreparedStatement pstmt = this.connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
			final PreparedStatement wrapper = new PreparedStatement4ConnectionPooled(pstmt, this.subject);
			this.subject.addStatement(pstmt);
			return wrapper;
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#prepareCall(java.lang.String, int, int, int)
	 */
	@Override
	public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability)
			throws SQLException {
		SQLException sqle = null;
		try {
			final CallableStatement cstmt = this.connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
			final CallableStatement wrapper = new CallableStatement4ConnectionPooled(cstmt, this.subject);
			this.subject.addStatement(cstmt);
			return wrapper;
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int)
	 */
	@Override
	public PreparedStatement prepareStatement(final String sql, final int autoGeneratedKeys) throws SQLException {
		SQLException sqle = null;
		try {
			final PreparedStatement pstmt = this.connection.prepareStatement(sql, autoGeneratedKeys);
			final PreparedStatement wrapper = new PreparedStatement4ConnectionPooled(pstmt, this.subject);
			this.subject.addStatement(pstmt);
			return wrapper;
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int[])
	 */
	@Override
	public PreparedStatement prepareStatement(final String sql, final int[] columnIndexes) throws SQLException {
		SQLException sqle = null;
		try {
			final PreparedStatement pstmt = this.connection.prepareStatement(sql, columnIndexes);
			final PreparedStatement wrapper = new PreparedStatement4ConnectionPooled(pstmt, this.subject);
			this.subject.addStatement(pstmt);
			return wrapper;
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#prepareStatement(java.lang.String,
	 *      java.lang.String[])
	 */
	@Override
	public PreparedStatement prepareStatement(final String sql, final String[] columnNames) throws SQLException {
		SQLException sqle = null;
		try {
			final PreparedStatement pstmt = this.connection.prepareStatement(sql, columnNames);
			final PreparedStatement wrapper = new PreparedStatement4ConnectionPooled(pstmt, this.subject);
			this.subject.addStatement(pstmt);
			return wrapper;
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#createClob()
	 */
	@Override
	public Clob createClob() throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.createClob();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#createBlob()
	 */
	@Override
	public Blob createBlob() throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.createBlob();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#createNClob()
	 */
	@Override
	public NClob createNClob() throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.createNClob();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#createSQLXML()
	 */
	@Override
	public SQLXML createSQLXML() throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.createSQLXML();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#isValid(int)
	 */
	@Override
	public boolean isValid(final int timeout) throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.isValid(timeout);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#setClientInfo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void setClientInfo(final String name, final String value) throws SQLClientInfoException {
		SQLClientInfoException sqle = null;
		try {
			this.connection.setClientInfo(name, value);
		} catch (final SQLClientInfoException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLClientInfoException();
			sqle.initCause(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#setClientInfo(java.util.Properties)
	 */
	@Override
	public void setClientInfo(final Properties properties) throws SQLClientInfoException {
		SQLClientInfoException sqle = null;
		try {
			this.connection.setClientInfo(properties);
		} catch (final SQLClientInfoException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLClientInfoException();
			sqle.initCause(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#getClientInfo(java.lang.String)
	 */
	@Override
	public String getClientInfo(final String name) throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.getClientInfo(name);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#getClientInfo()
	 */
	@Override
	public Properties getClientInfo() throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.getClientInfo();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#createArrayOf(java.lang.String,
	 *      java.lang.Object[])
	 */
	@Override
	public Array createArrayOf(final String typeName, final Object[] elements) throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.createArrayOf(typeName, elements);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#createStruct(java.lang.String,
	 *      java.lang.Object[])
	 */
	@Override
	public Struct createStruct(final String typeName, final Object[] attributes) throws SQLException {
		SQLException sqle = null;
		try {
			return this.connection.createStruct(typeName, attributes);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				this.subject.connectionErrorOccurred(sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#closeOnCompletion()
	 */
	public void closeOnCompletion() throws SQLException {
		throw new SQLFeatureNotSupportedException("Not Supported");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#isCloseOnCompletion()
	 */
	public boolean isCloseOnCompletion() throws SQLException {
		throw new SQLFeatureNotSupportedException("Not Supported");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#setSchema(java.lang.String)
	 */
	@Override
	public void setSchema(final String schema) throws SQLException {
		throw new SQLFeatureNotSupportedException("Not supported");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#getSchema()
	 */
	@Override
	public String getSchema() throws SQLException {
		throw new SQLFeatureNotSupportedException("Not supported");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#abort(java.util.concurrent.Executor)
	 */
	@Override
	public void abort(final Executor executor) throws SQLException {
		throw new SQLFeatureNotSupportedException("Not supported");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#setNetworkTimeout(java.util.concurrent.Executor,
	 *      int)
	 */
	@Override
	public void setNetworkTimeout(final Executor executor, final int milliseconds) throws SQLException {
		throw new SQLFeatureNotSupportedException("Not supported");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Connection#getNetworkTimeout()
	 */
	@Override
	public int getNetworkTimeout() throws SQLException {
		throw new SQLFeatureNotSupportedException("Not supported");
	}
}
