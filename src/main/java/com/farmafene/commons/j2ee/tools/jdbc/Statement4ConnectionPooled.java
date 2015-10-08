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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.Statement;

import javax.sql.PooledConnection;

/**
 * @author vlopez
 *
 */
public class Statement4ConnectionPooled implements Statement {

	private Statement statement;
	private PooledConnectionSubject subject;

	private Statement4ConnectionPooled() {

	}

	/**
	 * Constructor
	 *
	 * @param statement
	 * @param subject
	 */
	public Statement4ConnectionPooled(final Statement statement, final PooledConnectionSubject subject) {
		this();
		this.statement = statement;
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
		if (getSubject() != null) {
			sb.append("subject=");
			sb.append(getSubject());
			sb.append(", ");
		}
		if (this.statement != null) {
			sb.append("statement=");
			sb.append(this.statement);
		}
		sb.append("}");
		return sb.toString();
	}

	protected PooledConnectionSubject getSubject() {
		return this.subject;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException {
		return this.statement.unwrap(iface);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	@Override
	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		return this.statement.isWrapperFor(iface);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#executeQuery(java.lang.String)
	 */
	@Override
	public ResultSet executeQuery(final String sql) throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.executeQuery(sql);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#executeUpdate(java.lang.String)
	 */
	@Override
	public int executeUpdate(final String sql) throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.executeUpdate(sql);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#close()
	 */
	@Override
	public void close() throws SQLException {
		statementClosed(this.statement, null);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#getMaxFieldSize()
	 */
	@Override
	public int getMaxFieldSize() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.getMaxFieldSize();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#setMaxFieldSize(int)
	 */
	@Override
	public void setMaxFieldSize(final int max) throws SQLException {
		SQLException sqle = null;
		try {
			this.statement.setMaxFieldSize(max);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#getMaxRows()
	 */
	@Override
	public int getMaxRows() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.getMaxRows();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#setMaxRows(int)
	 */
	@Override
	public void setMaxRows(final int max) throws SQLException {
		SQLException sqle = null;
		try {
			this.statement.setMaxRows(max);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#setEscapeProcessing(boolean)
	 */
	@Override
	public void setEscapeProcessing(final boolean enable) throws SQLException {
		SQLException sqle = null;
		try {
			this.statement.setEscapeProcessing(enable);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#getQueryTimeout()
	 */
	@Override
	public int getQueryTimeout() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.getQueryTimeout();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#setQueryTimeout(int)
	 */
	@Override
	public void setQueryTimeout(final int seconds) throws SQLException {
		SQLException sqle = null;
		try {
			this.statement.setQueryTimeout(seconds);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#cancel()
	 */
	@Override
	public void cancel() throws SQLException {
		SQLException sqle = null;
		try {
			this.statement.cancel();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#getWarnings()
	 */
	@Override
	public SQLWarning getWarnings() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.getWarnings();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#clearWarnings()
	 */
	@Override
	public void clearWarnings() throws SQLException {
		SQLException sqle = null;
		try {
			this.statement.clearWarnings();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#setCursorName(java.lang.String)
	 */
	@Override
	public void setCursorName(final String name) throws SQLException {
		SQLException sqle = null;
		try {
			this.statement.setCursorName(name);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#execute(java.lang.String)
	 */
	@Override
	public boolean execute(final String sql) throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.execute(sql);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#getResultSet()
	 */
	@Override
	public ResultSet getResultSet() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.getResultSet();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#getUpdateCount()
	 */
	@Override
	public int getUpdateCount() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.getUpdateCount();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#getMoreResults()
	 */
	@Override
	public boolean getMoreResults() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.getMoreResults();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#setFetchDirection(int)
	 */
	@Override
	public void setFetchDirection(final int direction) throws SQLException {
		SQLException sqle = null;
		try {
			this.statement.setFetchDirection(direction);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#getFetchDirection()
	 */
	@Override
	public int getFetchDirection() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.getFetchDirection();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#setFetchSize(int)
	 */
	@Override
	public void setFetchSize(final int rows) throws SQLException {
		SQLException sqle = null;
		try {
			this.statement.setFetchSize(rows);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#getFetchSize()
	 */
	@Override
	public int getFetchSize() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.getFetchSize();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#getResultSetConcurrency()
	 */
	@Override
	public int getResultSetConcurrency() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.getResultSetConcurrency();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#getResultSetType()
	 */
	@Override
	public int getResultSetType() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.getResultSetType();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#addBatch(java.lang.String)
	 */
	@Override
	public void addBatch(final String sql) throws SQLException {
		SQLException sqle = null;
		try {
			this.statement.addBatch(sql);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#clearBatch()
	 */
	@Override
	public void clearBatch() throws SQLException {
		SQLException sqle = null;
		try {
			this.statement.clearBatch();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#executeBatch()
	 */
	@Override
	public int[] executeBatch() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.executeBatch();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return ((PooledConnection) this.subject).getConnection();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#getMoreResults(int)
	 */
	@Override
	public boolean getMoreResults(final int current) throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.getMoreResults(current);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#getGeneratedKeys()
	 */
	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.getGeneratedKeys();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#executeUpdate(java.lang.String, int)
	 */
	@Override
	public int executeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.executeUpdate(sql, autoGeneratedKeys);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#executeUpdate(java.lang.String, int[])
	 */
	@Override
	public int executeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.executeUpdate(sql, columnIndexes);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#executeUpdate(java.lang.String,
	 *      java.lang.String[])
	 */
	@Override
	public int executeUpdate(final String sql, final String[] columnNames) throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.executeUpdate(sql, columnNames);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#execute(java.lang.String, int)
	 */
	@Override
	public boolean execute(final String sql, final int autoGeneratedKeys) throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.execute(sql, autoGeneratedKeys);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#execute(java.lang.String, int[])
	 */
	@Override
	public boolean execute(final String sql, final int[] columnIndexes) throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.execute(sql, columnIndexes);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#execute(java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean execute(final String sql, final String[] columnNames) throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.execute(sql, columnNames);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#getResultSetHoldability()
	 */
	@Override
	public int getResultSetHoldability() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.getResultSetHoldability();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#isClosed()
	 */
	@Override
	public boolean isClosed() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.isClosed();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#setPoolable(boolean)
	 */
	@Override
	public void setPoolable(final boolean poolable) throws SQLException {
		SQLException sqle = null;
		try {
			this.statement.setPoolable(poolable);
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#isPoolable()
	 */
	@Override
	public boolean isPoolable() throws SQLException {
		SQLException sqle = null;
		try {
			return this.statement.isPoolable();
		} catch (final SQLException e) {
			sqle = e;
			throw sqle;
		} catch (final Throwable th) {
			sqle = new SQLException(th);
			throw sqle;
		} finally {
			if (null != sqle) {
				statementErrorOccurred(this, sqle);
			}
		}
	}

	private void statementClosed(final Statement stmt, final SQLException e) throws SQLException {
		if (stmt instanceof PreparedStatement) {
			this.subject.statementClosed((PreparedStatement) stmt, e);
		} else {
			SQLException sqle = null;
			try {
				stmt.close();
			} catch (final SQLException ex) {
				sqle = ex;
				throw sqle;
			} catch (final Throwable th) {
				sqle = new SQLException(th);
				throw sqle;
			}
		}
	}

	protected void statementErrorOccurred(final Statement stmt, final SQLException e) {
		if (stmt instanceof PreparedStatement) {
			this.subject.statementErrorOccurred((PreparedStatement) stmt, e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#closeOnCompletion()
	 */
	@Override
	public void closeOnCompletion() throws SQLException {
		throw new SQLFeatureNotSupportedException("Not Supported");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.Statement#isCloseOnCompletion()
	 */
	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		throw new SQLFeatureNotSupportedException("Not Supported");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.CallableStatement#getObject(int, java.lang.Class)
	 */
	public <T> T getObject(final int parameterIndex, final Class<T> type) throws SQLException {
		throw new SQLFeatureNotSupportedException("Not Supported");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.CallableStatement#getObject(java.lang.String,
	 *      java.lang.Class)
	 */
	public <T> T getObject(final String parameterName, final Class<T> type) throws SQLException {
		throw new SQLFeatureNotSupportedException("Not Supported");
	}
}
