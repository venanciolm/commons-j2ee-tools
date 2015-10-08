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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * @author vlopez
 *
 */
public class PreparedStatement4ConnectionPooled extends Statement4ConnectionPooled implements PreparedStatement {

	private final PreparedStatement preparedStatement;

	public PreparedStatement4ConnectionPooled(final PreparedStatement preparedStatement, final PooledConnectionSubject subject) {
		super(preparedStatement, subject);
		this.preparedStatement = preparedStatement;
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
		if (this.preparedStatement != null) {
			sb.append("preparedStatement=");
			sb.append(this.preparedStatement);
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.PreparedStatement#executeQuery()
	 */
	@Override
	public ResultSet executeQuery() throws SQLException {
		SQLException sqle = null;
		try {
			return this.preparedStatement.executeQuery();
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
	 * @see java.sql.PreparedStatement#executeUpdate()
	 */
	@Override
	public int executeUpdate() throws SQLException {
		SQLException sqle = null;
		try {
			return this.preparedStatement.executeUpdate();
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
	 * @see java.sql.PreparedStatement#setNull(int, int)
	 */
	@Override
	public void setNull(final int parameterIndex, final int sqlType) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setNull(parameterIndex, sqlType);
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
	 * @see java.sql.PreparedStatement#setBoolean(int, boolean)
	 */
	@Override
	public void setBoolean(final int parameterIndex, final boolean x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setBoolean(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setByte(int, byte)
	 */
	@Override
	public void setByte(final int parameterIndex, final byte x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setByte(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setShort(int, short)
	 */
	@Override
	public void setShort(final int parameterIndex, final short x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setShort(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setInt(int, int)
	 */
	@Override
	public void setInt(final int parameterIndex, final int x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setInt(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setLong(int, long)
	 */
	@Override
	public void setLong(final int parameterIndex, final long x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setLong(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setFloat(int, float)
	 */
	@Override
	public void setFloat(final int parameterIndex, final float x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setFloat(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setDouble(int, double)
	 */
	@Override
	public void setDouble(final int parameterIndex, final double x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setDouble(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setBigDecimal(int, java.math.BigDecimal)
	 */
	@Override
	public void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setBigDecimal(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setString(int, java.lang.String)
	 */
	@Override
	public void setString(final int parameterIndex, final String x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setString(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setBytes(int, byte[])
	 */
	@Override
	public void setBytes(final int parameterIndex, final byte[] x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setBytes(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setDate(int, java.sql.Date)
	 */
	@Override
	public void setDate(final int parameterIndex, final Date x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setDate(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setTime(int, java.sql.Time)
	 */
	@Override
	public void setTime(final int parameterIndex, final Time x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setTime(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setTimestamp(int, java.sql.Timestamp)
	 */
	@Override
	public void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setTimestamp(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream,
	 *      int)
	 */
	@Override
	public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setAsciiStream(parameterIndex, x, length);
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
	 * @see java.sql.PreparedStatement#setUnicodeStream(int,
	 *      java.io.InputStream, int)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setUnicodeStream(parameterIndex, x, length);
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
	 * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream,
	 *      int)
	 */
	@Override
	public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setBinaryStream(parameterIndex, x, length);
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
	 * @see java.sql.PreparedStatement#clearParameters()
	 */
	@Override
	public void clearParameters() throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.clearParameters();
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
	 * @see java.sql.PreparedStatement#setObject(int, java.lang.Object, int)
	 */
	@Override
	public void setObject(final int parameterIndex, final Object x, final int targetSqlType) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setObject(parameterIndex, x, targetSqlType);
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
	 * @see java.sql.PreparedStatement#setObject(int, java.lang.Object)
	 */
	@Override
	public void setObject(final int parameterIndex, final Object x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setObject(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#execute()
	 */
	@Override
	public boolean execute() throws SQLException {
		SQLException sqle = null;
		try {
			return this.preparedStatement.execute();
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
	 * @see java.sql.PreparedStatement#addBatch()
	 */
	@Override
	public void addBatch() throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.addBatch();
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
	 * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader,
	 *      int)
	 */
	@Override
	public void setCharacterStream(final int parameterIndex, final Reader reader, final int length) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setCharacterStream(parameterIndex, reader, length);
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
	 * @see java.sql.PreparedStatement#setRef(int, java.sql.Ref)
	 */
	@Override
	public void setRef(final int parameterIndex, final Ref x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setRef(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setBlob(int, java.sql.Blob)
	 */
	@Override
	public void setBlob(final int parameterIndex, final Blob x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setBlob(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setClob(int, java.sql.Clob)
	 */
	@Override
	public void setClob(final int parameterIndex, final Clob x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setClob(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setArray(int, java.sql.Array)
	 */
	@Override
	public void setArray(final int parameterIndex, final Array x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setArray(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#getMetaData()
	 */
	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		SQLException sqle = null;
		try {
			return this.preparedStatement.getMetaData();
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
	 * @see java.sql.PreparedStatement#setDate(int, java.sql.Date,
	 *      java.util.Calendar)
	 */
	@Override
	public void setDate(final int parameterIndex, final Date x, final Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setDate(parameterIndex, x, cal);
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
	 * @see java.sql.PreparedStatement#setTime(int, java.sql.Time,
	 *      java.util.Calendar)
	 */
	@Override
	public void setTime(final int parameterIndex, final Time x, final Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setTime(parameterIndex, x, cal);
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
	 * @see java.sql.PreparedStatement#setTimestamp(int, java.sql.Timestamp,
	 *      java.util.Calendar)
	 */
	@Override
	public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setTimestamp(parameterIndex, x, cal);
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
	 * @see java.sql.PreparedStatement#setNull(int, int, java.lang.String)
	 */
	@Override
	public void setNull(final int parameterIndex, final int sqlType, final String typeName) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setNull(parameterIndex, sqlType, typeName);
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
	 * @see java.sql.PreparedStatement#setURL(int, java.net.URL)
	 */
	@Override
	public void setURL(final int parameterIndex, final URL x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setURL(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#getParameterMetaData()
	 */
	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		SQLException sqle = null;
		try {
			return this.preparedStatement.getParameterMetaData();
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
	 * @see java.sql.PreparedStatement#setRowId(int, java.sql.RowId)
	 */
	@Override
	public void setRowId(final int parameterIndex, final RowId x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setRowId(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setNString(int, java.lang.String)
	 */
	@Override
	public void setNString(final int parameterIndex, final String value) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setNString(parameterIndex, value);
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
	 * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader,
	 *      long)
	 */
	@Override
	public void setNCharacterStream(final int parameterIndex, final Reader value, final long length) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setNCharacterStream(parameterIndex, value, length);
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
	 * @see java.sql.PreparedStatement#setNClob(int, java.sql.NClob)
	 */
	@Override
	public void setNClob(final int parameterIndex, final NClob value) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setNClob(parameterIndex, value);
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
	 * @see java.sql.PreparedStatement#setClob(int, java.io.Reader, long)
	 */
	@Override
	public void setClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setClob(parameterIndex, reader, length);
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
	 * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream, long)
	 */
	@Override
	public void setBlob(final int parameterIndex, final InputStream inputStream, final long length) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setBlob(parameterIndex, inputStream, length);
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
	 * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader, long)
	 */
	@Override
	public void setNClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setNClob(parameterIndex, reader, length);
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
	 * @see java.sql.PreparedStatement#setSQLXML(int, java.sql.SQLXML)
	 */
	@Override
	public void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setSQLXML(parameterIndex, xmlObject);
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
	 * @see java.sql.PreparedStatement#setObject(int, java.lang.Object, int,
	 *      int)
	 */
	@Override
	public void setObject(final int parameterIndex, final Object x, final int targetSqlType, final int scaleOrLength) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
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
	 * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream,
	 *      long)
	 */
	@Override
	public void setAsciiStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setAsciiStream(parameterIndex, x, length);
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
	 * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream,
	 *      long)
	 */
	@Override
	public void setBinaryStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setBinaryStream(parameterIndex, x, length);
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
	 * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader,
	 *      long)
	 */
	@Override
	public void setCharacterStream(final int parameterIndex, final Reader reader, final long length) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setCharacterStream(parameterIndex, reader, length);
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
	 * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream)
	 */
	@Override
	public void setAsciiStream(final int parameterIndex, final InputStream x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setAsciiStream(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream)
	 */
	@Override
	public void setBinaryStream(final int parameterIndex, final InputStream x) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setBinaryStream(parameterIndex, x);
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
	 * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader)
	 */
	@Override
	public void setCharacterStream(final int parameterIndex, final Reader reader) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setCharacterStream(parameterIndex, reader);
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
	 * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader)
	 */
	@Override
	public void setNCharacterStream(final int parameterIndex, final Reader value) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setNCharacterStream(parameterIndex, value);
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
	 * @see java.sql.PreparedStatement#setClob(int, java.io.Reader)
	 */
	@Override
	public void setClob(final int parameterIndex, final Reader reader) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setClob(parameterIndex, reader);
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
	 * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream)
	 */
	@Override
	public void setBlob(final int parameterIndex, final InputStream inputStream) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setBlob(parameterIndex, inputStream);
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
	 * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader)
	 */
	@Override
	public void setNClob(final int parameterIndex, final Reader reader) throws SQLException {
		SQLException sqle = null;
		try {
			this.preparedStatement.setNClob(parameterIndex, reader);
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
}
