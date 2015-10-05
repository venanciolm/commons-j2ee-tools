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
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * @author vlopez
 *
 */
public class CallableStatement4ConnectionPooled extends PreparedStatement4ConnectionPooled implements CallableStatement {

	private final CallableStatement callableStatement;

	public CallableStatement4ConnectionPooled(final CallableStatement callableStatement, final PooledConnectionSubject subject) {
		super(callableStatement, subject);
		this.callableStatement = callableStatement;
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
		if (this.callableStatement != null) {
			sb.append("callableStatement=");
			sb.append(this.callableStatement);
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.CallableStatement#registerOutParameter(int, int)
	 */
	@Override
	public void registerOutParameter(final int parameterIndex, final int sqlType) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.registerOutParameter(parameterIndex, sqlType);
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
	 * @see java.sql.CallableStatement#registerOutParameter(int, int, int)
	 */
	@Override
	public void registerOutParameter(final int parameterIndex, final int sqlType, final int scale) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.registerOutParameter(parameterIndex, sqlType, scale);
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
	 * @see java.sql.CallableStatement#wasNull()
	 */
	@Override
	public boolean wasNull() throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.wasNull();
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
	 * @see java.sql.CallableStatement#getString(int)
	 */
	@Override
	public String getString(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getString(parameterIndex);
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
	 * @see java.sql.CallableStatement#getBoolean(int)
	 */
	@Override
	public boolean getBoolean(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getBoolean(parameterIndex);
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
	 * @see java.sql.CallableStatement#getByte(int)
	 */
	@Override
	public byte getByte(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getByte(parameterIndex);
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
	 * @see java.sql.CallableStatement#getShort(int)
	 */
	@Override
	public short getShort(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getShort(parameterIndex);
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
	 * @see java.sql.CallableStatement#getInt(int)
	 */
	@Override
	public int getInt(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getInt(parameterIndex);
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
	 * @see java.sql.CallableStatement#getLong(int)
	 */
	@Override
	public long getLong(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getLong(parameterIndex);
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
	 * @see java.sql.CallableStatement#getFloat(int)
	 */
	@Override
	public float getFloat(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getFloat(parameterIndex);
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
	 * @see java.sql.CallableStatement#getDouble(int)
	 */
	@Override
	public double getDouble(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getDouble(parameterIndex);
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
	 * @see java.sql.CallableStatement#getBigDecimal(int, int)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public BigDecimal getBigDecimal(final int parameterIndex, final int scale) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getBigDecimal(parameterIndex, scale);
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
	 * @see java.sql.CallableStatement#getBytes(int)
	 */
	@Override
	public byte[] getBytes(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getBytes(parameterIndex);
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
	 * @see java.sql.CallableStatement#getDate(int)
	 */
	@Override
	public Date getDate(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getDate(parameterIndex);
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
	 * @see java.sql.CallableStatement#getTime(int)
	 */
	@Override
	public Time getTime(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getTime(parameterIndex);
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
	 * @see java.sql.CallableStatement#getTimestamp(int)
	 */
	@Override
	public Timestamp getTimestamp(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getTimestamp(parameterIndex);
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
	 * @see java.sql.CallableStatement#getObject(int)
	 */
	@Override
	public Object getObject(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getObject(parameterIndex);
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
	 * @see java.sql.CallableStatement#getBigDecimal(int)
	 */
	@Override
	public BigDecimal getBigDecimal(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getBigDecimal(parameterIndex);
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
	 * @see java.sql.CallableStatement#getObject(int, java.util.Map)
	 */
	@Override
	public Object getObject(final int parameterIndex, final Map<String, Class<?>> map) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getObject(parameterIndex, map);
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
	 * @see java.sql.CallableStatement#getRef(int)
	 */
	@Override
	public Ref getRef(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getRef(parameterIndex);
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
	 * @see java.sql.CallableStatement#getBlob(int)
	 */
	@Override
	public Blob getBlob(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getBlob(parameterIndex);
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
	 * @see java.sql.CallableStatement#getClob(int)
	 */
	@Override
	public Clob getClob(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getClob(parameterIndex);
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
	 * @see java.sql.CallableStatement#getArray(int)
	 */
	@Override
	public Array getArray(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getArray(parameterIndex);
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
	 * @see java.sql.CallableStatement#getDate(int, java.util.Calendar)
	 */
	@Override
	public Date getDate(final int parameterIndex, final Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getDate(parameterIndex);
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
	 * @see java.sql.CallableStatement#getTime(int, java.util.Calendar)
	 */
	@Override
	public Time getTime(final int parameterIndex, final Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getTime(parameterIndex, cal);
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
	 * @see java.sql.CallableStatement#getTimestamp(int, java.util.Calendar)
	 */
	@Override
	public Timestamp getTimestamp(final int parameterIndex, final Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getTimestamp(parameterIndex, cal);
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
	 * @see java.sql.CallableStatement#registerOutParameter(int, int,
	 *      java.lang.String)
	 */
	@Override
	public void registerOutParameter(final int parameterIndex, final int sqlType, final String typeName) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.registerOutParameter(parameterIndex, sqlType, typeName);
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
	 * @see java.sql.CallableStatement#registerOutParameter(java.lang.String,
	 *      int)
	 */
	@Override
	public void registerOutParameter(final String parameterName, final int sqlType) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.registerOutParameter(parameterName, sqlType);
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
	 * @see java.sql.CallableStatement#registerOutParameter(java.lang.String,
	 *      int, int)
	 */
	@Override
	public void registerOutParameter(final String parameterName, final int sqlType, final int scale) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.registerOutParameter(parameterName, sqlType, scale);
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
	 * @see java.sql.CallableStatement#registerOutParameter(java.lang.String,
	 *      int, java.lang.String)
	 */
	@Override
	public void registerOutParameter(final String parameterName, final int sqlType, final String typeName) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.registerOutParameter(parameterName, sqlType, typeName);
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
	 * @see java.sql.CallableStatement#getURL(int)
	 */
	@Override
	public URL getURL(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getURL(parameterIndex);
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
	 * @see java.sql.CallableStatement#setURL(java.lang.String, java.net.URL)
	 */
	@Override
	public void setURL(final String parameterName, final URL val) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setURL(parameterName, val);
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
	 * @see java.sql.CallableStatement#setNull(java.lang.String, int)
	 */
	@Override
	public void setNull(final String parameterName, final int sqlType) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setNull(parameterName, sqlType);
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
	 * @see java.sql.CallableStatement#setBoolean(java.lang.String, boolean)
	 */
	@Override
	public void setBoolean(final String parameterName, final boolean x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setBoolean(parameterName, x);
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
	 * @see java.sql.CallableStatement#setByte(java.lang.String, byte)
	 */
	@Override
	public void setByte(final String parameterName, final byte x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setByte(parameterName, x);
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
	 * @see java.sql.CallableStatement#setShort(java.lang.String, short)
	 */
	@Override
	public void setShort(final String parameterName, final short x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setShort(parameterName, x);
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
	 * @see java.sql.CallableStatement#setInt(java.lang.String, int)
	 */
	@Override
	public void setInt(final String parameterName, final int x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setInt(parameterName, x);
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
	 * @see java.sql.CallableStatement#setLong(java.lang.String, long)
	 */
	@Override
	public void setLong(final String parameterName, final long x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setLong(parameterName, x);
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
	 * @see java.sql.CallableStatement#setFloat(java.lang.String, float)
	 */
	@Override
	public void setFloat(final String parameterName, final float x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setFloat(parameterName, x);
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
	 * @see java.sql.CallableStatement#setDouble(java.lang.String, double)
	 */
	@Override
	public void setDouble(final String parameterName, final double x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setDouble(parameterName, x);
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
	 * @see java.sql.CallableStatement#setBigDecimal(java.lang.String,
	 *      java.math.BigDecimal)
	 */
	@Override
	public void setBigDecimal(final String parameterName, final BigDecimal x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setBigDecimal(parameterName, x);
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
	 * @see java.sql.CallableStatement#setString(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void setString(final String parameterName, final String x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setString(parameterName, x);
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
	 * @see java.sql.CallableStatement#setBytes(java.lang.String, byte[])
	 */
	@Override
	public void setBytes(final String parameterName, final byte[] x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setBytes(parameterName, x);
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
	 * @see java.sql.CallableStatement#setDate(java.lang.String, java.sql.Date)
	 */
	@Override
	public void setDate(final String parameterName, final Date x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setDate(parameterName, x);
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
	 * @see java.sql.CallableStatement#setTime(java.lang.String, java.sql.Time)
	 */
	@Override
	public void setTime(final String parameterName, final Time x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setTime(parameterName, x);
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
	 * @see java.sql.CallableStatement#setTimestamp(java.lang.String,
	 *      java.sql.Timestamp)
	 */
	@Override
	public void setTimestamp(final String parameterName, final Timestamp x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setTimestamp(parameterName, x);
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
	 * @see java.sql.CallableStatement#setAsciiStream(java.lang.String,
	 *      java.io.InputStream, int)
	 */
	@Override
	public void setAsciiStream(final String parameterName, final InputStream x, final int length) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setAsciiStream(parameterName, x, length);
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
	 * @see java.sql.CallableStatement#setBinaryStream(java.lang.String,
	 *      java.io.InputStream, int)
	 */
	@Override
	public void setBinaryStream(final String parameterName, final InputStream x, final int length) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setBinaryStream(parameterName, x, length);
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
	 * @see java.sql.CallableStatement#setObject(java.lang.String,
	 *      java.lang.Object, int, int)
	 */
	@Override
	public void setObject(final String parameterName, final Object x, final int targetSqlType, final int scale) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setObject(parameterName, x, targetSqlType, scale);
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
	 * @see java.sql.CallableStatement#setObject(java.lang.String,
	 *      java.lang.Object, int)
	 */
	@Override
	public void setObject(final String parameterName, final Object x, final int targetSqlType) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setObject(parameterName, x, targetSqlType);
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
	 * @see java.sql.CallableStatement#setObject(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public void setObject(final String parameterName, final Object x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setObject(parameterName, x);
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
	 * @see java.sql.CallableStatement#setCharacterStream(java.lang.String,
	 *      java.io.Reader, int)
	 */
	@Override
	public void setCharacterStream(final String parameterName, final Reader reader, final int length) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setCharacterStream(parameterName, reader, length);
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
	 * @see java.sql.CallableStatement#setDate(java.lang.String, java.sql.Date,
	 *      java.util.Calendar)
	 */
	@Override
	public void setDate(final String parameterName, final Date x, final Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setDate(parameterName, x, cal);
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
	 * @see java.sql.CallableStatement#setTime(java.lang.String, java.sql.Time,
	 *      java.util.Calendar)
	 */
	@Override
	public void setTime(final String parameterName, final Time x, final Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setTime(parameterName, x, cal);
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
	 * @see java.sql.CallableStatement#setTimestamp(java.lang.String,
	 *      java.sql.Timestamp, java.util.Calendar)
	 */
	@Override
	public void setTimestamp(final String parameterName, final Timestamp x, final Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setTimestamp(parameterName, x, cal);
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
	 * @see java.sql.CallableStatement#setNull(java.lang.String, int,
	 *      java.lang.String)
	 */
	@Override
	public void setNull(final String parameterName, final int sqlType, final String typeName) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setNull(parameterName, sqlType, typeName);
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
	 * @see java.sql.CallableStatement#getString(java.lang.String)
	 */
	@Override
	public String getString(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getString(parameterName);
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
	 * @see java.sql.CallableStatement#getBoolean(java.lang.String)
	 */
	@Override
	public boolean getBoolean(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getBoolean(parameterName);
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
	 * @see java.sql.CallableStatement#getByte(java.lang.String)
	 */
	@Override
	public byte getByte(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getByte(parameterName);
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
	 * @see java.sql.CallableStatement#getShort(java.lang.String)
	 */
	@Override
	public short getShort(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getShort(parameterName);
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
	 * @see java.sql.CallableStatement#getInt(java.lang.String)
	 */
	@Override
	public int getInt(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getInt(parameterName);
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
	 * @see java.sql.CallableStatement#getLong(java.lang.String)
	 */
	@Override
	public long getLong(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getLong(parameterName);
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
	 * @see java.sql.CallableStatement#getFloat(java.lang.String)
	 */
	@Override
	public float getFloat(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getFloat(parameterName);
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
	 * @see java.sql.CallableStatement#getDouble(java.lang.String)
	 */
	@Override
	public double getDouble(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getDouble(parameterName);
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
	 * @see java.sql.CallableStatement#getBytes(java.lang.String)
	 */
	@Override
	public byte[] getBytes(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getBytes(parameterName);
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
	 * @see java.sql.CallableStatement#getDate(java.lang.String)
	 */
	@Override
	public Date getDate(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getDate(parameterName);
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
	 * @see java.sql.CallableStatement#getTime(java.lang.String)
	 */
	@Override
	public Time getTime(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getTime(parameterName);
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
	 * @see java.sql.CallableStatement#getTimestamp(java.lang.String)
	 */
	@Override
	public Timestamp getTimestamp(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getTimestamp(parameterName);
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
	 * @see java.sql.CallableStatement#getObject(java.lang.String)
	 */
	@Override
	public Object getObject(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getObject(parameterName);
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
	 * @see java.sql.CallableStatement#getBigDecimal(java.lang.String)
	 */
	@Override
	public BigDecimal getBigDecimal(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getBigDecimal(parameterName);
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
	 * @see java.sql.CallableStatement#getObject(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public Object getObject(final String parameterName, final Map<String, Class<?>> map) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getObject(parameterName, map);
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
	 *
	 * @see java.sql.CallableStatement#getRef(java.lang.String)
	 */
	@Override
	public Ref getRef(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getRef(parameterName);
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
	 * @see java.sql.CallableStatement#getBlob(java.lang.String)
	 */
	@Override
	public Blob getBlob(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getBlob(parameterName);
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
	 * @see java.sql.CallableStatement#getClob(java.lang.String)
	 */
	@Override
	public Clob getClob(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getClob(parameterName);
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
	 * @see java.sql.CallableStatement#getArray(java.lang.String)
	 */
	@Override
	public Array getArray(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getArray(parameterName);
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
	 * @see java.sql.CallableStatement#getDate(java.lang.String,
	 *      java.util.Calendar)
	 */
	@Override
	public Date getDate(final String parameterName, final Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getDate(parameterName, cal);
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
	 * @see java.sql.CallableStatement#getTime(java.lang.String,
	 *      java.util.Calendar)
	 */
	@Override
	public Time getTime(final String parameterName, final Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getTime(parameterName, cal);
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
	 * @see java.sql.CallableStatement#getTimestamp(java.lang.String,
	 *      java.util.Calendar)
	 */
	@Override
	public Timestamp getTimestamp(final String parameterName, final Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getTimestamp(parameterName, cal);
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
	 * @see java.sql.CallableStatement#getURL(java.lang.String)
	 */
	@Override
	public URL getURL(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getURL(parameterName);
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
	 * @see java.sql.CallableStatement#getRowId(int)
	 */
	@Override
	public RowId getRowId(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getRowId(parameterIndex);
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
	 * @see java.sql.CallableStatement#getRowId(java.lang.String)
	 */
	@Override
	public RowId getRowId(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getRowId(parameterName);
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
	 * @see java.sql.CallableStatement#setRowId(java.lang.String,
	 *      java.sql.RowId)
	 */
	@Override
	public void setRowId(final String parameterName, final RowId x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setRowId(parameterName, x);
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
	 * @see java.sql.CallableStatement#setNString(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void setNString(final String parameterName, final String value) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setNString(parameterName, value);
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
	 * @see java.sql.CallableStatement#setNCharacterStream(java.lang.String,
	 *      java.io.Reader, long)
	 */
	@Override
	public void setNCharacterStream(final String parameterName, final Reader value, final long length) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setNCharacterStream(parameterName, value, length);
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
	 * @see java.sql.CallableStatement#setNClob(java.lang.String,
	 *      java.sql.NClob)
	 */
	@Override
	public void setNClob(final String parameterName, final NClob value) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setNClob(parameterName, value);
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
	 * @see java.sql.CallableStatement#setClob(java.lang.String, java.io.Reader,
	 *      long)
	 */
	@Override
	public void setClob(final String parameterName, final Reader reader, final long length) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setClob(parameterName, reader, length);
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
	 * @see java.sql.CallableStatement#setBlob(java.lang.String,
	 *      java.io.InputStream, long)
	 */
	@Override
	public void setBlob(final String parameterName, final InputStream inputStream, final long length) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setBlob(parameterName, inputStream, length);
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
	 * @see java.sql.CallableStatement#setNClob(java.lang.String,
	 *      java.io.Reader, long)
	 */
	@Override
	public void setNClob(final String parameterName, final Reader reader, final long length) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setNClob(parameterName, reader, length);
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
	 * @see java.sql.CallableStatement#getNClob(int)
	 */
	@Override
	public NClob getNClob(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getNClob(parameterIndex);
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
	 * @see java.sql.CallableStatement#getNClob(java.lang.String)
	 */
	@Override
	public NClob getNClob(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getNClob(parameterName);
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
	 * @see java.sql.CallableStatement#setSQLXML(java.lang.String,
	 *      java.sql.SQLXML)
	 */
	@Override
	public void setSQLXML(final String parameterName, final SQLXML xmlObject) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setSQLXML(parameterName, xmlObject);
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
	 * @see java.sql.CallableStatement#getSQLXML(int)
	 */
	@Override
	public SQLXML getSQLXML(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getSQLXML(parameterIndex);
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
	 * @see java.sql.CallableStatement#getSQLXML(java.lang.String)
	 */
	@Override
	public SQLXML getSQLXML(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getSQLXML(parameterName);
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
	 * @see java.sql.CallableStatement#getNString(int)
	 */
	@Override
	public String getNString(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getNString(parameterIndex);
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
	 * @see java.sql.CallableStatement#getNString(java.lang.String)
	 */
	@Override
	public String getNString(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getNString(parameterName);
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
	 * @see java.sql.CallableStatement#getNCharacterStream(int)
	 */
	@Override
	public Reader getNCharacterStream(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getNCharacterStream(parameterIndex);
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
	 * @see java.sql.CallableStatement#getNCharacterStream(java.lang.String)
	 */
	@Override
	public Reader getNCharacterStream(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getNCharacterStream(parameterName);
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
	 * @see java.sql.CallableStatement#getCharacterStream(int)
	 */
	@Override
	public Reader getCharacterStream(final int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getCharacterStream(parameterIndex);
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
	 * @see java.sql.CallableStatement#getCharacterStream(java.lang.String)
	 */
	@Override
	public Reader getCharacterStream(final String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return this.callableStatement.getCharacterStream(parameterName);
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
	 * @see java.sql.CallableStatement#setBlob(java.lang.String, java.sql.Blob)
	 */
	@Override
	public void setBlob(final String parameterName, final Blob x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setBlob(parameterName, x);
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
	 * @see java.sql.CallableStatement#setClob(java.lang.String, java.sql.Clob)
	 */
	@Override
	public void setClob(final String parameterName, final Clob x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setClob(parameterName, x);
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
	 * @see java.sql.CallableStatement#setAsciiStream(java.lang.String,
	 *      java.io.InputStream, long)
	 */
	@Override
	public void setAsciiStream(final String parameterName, final InputStream x, final long length) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setAsciiStream(parameterName, x, length);
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
	 * @see java.sql.CallableStatement#setBinaryStream(java.lang.String,
	 *      java.io.InputStream, long)
	 */
	@Override
	public void setBinaryStream(final String parameterName, final InputStream x, final long length) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setBinaryStream(parameterName, x, length);
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
	 * @see java.sql.CallableStatement#setCharacterStream(java.lang.String,
	 *      java.io.Reader, long)
	 */
	@Override
	public void setCharacterStream(final String parameterName, final Reader reader, final long length) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setCharacterStream(parameterName, reader, length);
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
	 * @see java.sql.CallableStatement#setAsciiStream(java.lang.String,
	 *      java.io.InputStream)
	 */
	@Override
	public void setAsciiStream(final String parameterName, final InputStream x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setAsciiStream(parameterName, x);
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
	 * @see java.sql.CallableStatement#setBinaryStream(java.lang.String,
	 *      java.io.InputStream)
	 */
	@Override
	public void setBinaryStream(final String parameterName, final InputStream x) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setBinaryStream(parameterName, x);
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
	 * @see java.sql.CallableStatement#setCharacterStream(java.lang.String,
	 *      java.io.Reader)
	 */
	@Override
	public void setCharacterStream(final String parameterName, final Reader reader) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setCharacterStream(parameterName, reader);
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
	 * @see java.sql.CallableStatement#setNCharacterStream(java.lang.String,
	 *      java.io.Reader)
	 */
	@Override
	public void setNCharacterStream(final String parameterName, final Reader value) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setNCharacterStream(parameterName, value);
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
	 * @see java.sql.CallableStatement#setClob(java.lang.String, java.io.Reader)
	 */
	@Override
	public void setClob(final String parameterName, final Reader reader) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setClob(parameterName, reader);
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
	 * @see java.sql.CallableStatement#setBlob(java.lang.String,
	 *      java.io.InputStream)
	 */
	@Override
	public void setBlob(final String parameterName, final InputStream inputStream) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setBlob(parameterName, inputStream);
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
	 * @see java.sql.CallableStatement#setNClob(java.lang.String,
	 *      java.io.Reader)
	 */
	@Override
	public void setNClob(final String parameterName, final Reader reader) throws SQLException {
		SQLException sqle = null;
		try {
			this.callableStatement.setNClob(parameterName, reader);
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
	@Override
	public <T> T getObject(final int parameterIndex, final Class<T> type) throws SQLException {
		throw new SQLFeatureNotSupportedException("Not Supported");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.sql.CallableStatement#getObject(java.lang.String,
	 *      java.lang.Class)
	 */
	@Override
	public <T> T getObject(final String parameterName, final Class<T> type) throws SQLException {
		throw new SQLFeatureNotSupportedException("Not Supported");
	}
}
