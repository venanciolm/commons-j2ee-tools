/**
 * 
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
 * @author vlopezm
 * 
 */
public class CallableStatement4ConnectionPooled extends
		PreparedStatement4ConnectionPooled implements CallableStatement {

	private CallableStatement callableStatement;

	public CallableStatement4ConnectionPooled(
			CallableStatement callableStatement, PooledConnectionSubject subject) {
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
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("={");
		if (getSubject() != null) {
			sb.append("subject=");
			sb.append(getSubject());
			sb.append(", ");
		}
		if (callableStatement != null) {
			sb.append("callableStatement=");
			sb.append(callableStatement);
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
	public void registerOutParameter(int parameterIndex, int sqlType)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.registerOutParameter(parameterIndex, sqlType);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void registerOutParameter(int parameterIndex, int sqlType, int scale)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.registerOutParameter(parameterIndex, sqlType,
					scale);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
			return callableStatement.wasNull();
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public String getString(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getString(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public boolean getBoolean(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getBoolean(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public byte getByte(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getByte(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public short getShort(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getShort(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public int getInt(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getInt(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public long getLong(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getLong(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public float getFloat(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getFloat(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public double getDouble(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getDouble(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public BigDecimal getBigDecimal(int parameterIndex, int scale)
			throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getBigDecimal(parameterIndex, scale);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public byte[] getBytes(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getBytes(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Date getDate(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getDate(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Time getTime(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getTime(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Timestamp getTimestamp(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getTimestamp(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Object getObject(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getObject(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getBigDecimal(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Object getObject(int parameterIndex, Map<String, Class<?>> map)
			throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getObject(parameterIndex, map);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Ref getRef(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getRef(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Blob getBlob(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getBlob(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Clob getClob(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getClob(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Array getArray(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getArray(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getDate(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getTime(parameterIndex, cal);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Timestamp getTimestamp(int parameterIndex, Calendar cal)
			throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getTimestamp(parameterIndex, cal);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void registerOutParameter(int parameterIndex, int sqlType,
			String typeName) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.registerOutParameter(parameterIndex, sqlType,
					typeName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void registerOutParameter(String parameterName, int sqlType)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.registerOutParameter(parameterName, sqlType);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void registerOutParameter(String parameterName, int sqlType,
			int scale) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.registerOutParameter(parameterName, sqlType,
					scale);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void registerOutParameter(String parameterName, int sqlType,
			String typeName) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.registerOutParameter(parameterName, sqlType,
					typeName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public URL getURL(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getURL(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setURL(String parameterName, URL val) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setURL(parameterName, val);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setNull(String parameterName, int sqlType) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setNull(parameterName, sqlType);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setBoolean(String parameterName, boolean x) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setBoolean(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setByte(String parameterName, byte x) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setByte(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setShort(String parameterName, short x) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setShort(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setInt(String parameterName, int x) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setInt(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setLong(String parameterName, long x) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setLong(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setFloat(String parameterName, float x) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setFloat(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setDouble(String parameterName, double x) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setDouble(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setBigDecimal(String parameterName, BigDecimal x)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setBigDecimal(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setString(String parameterName, String x) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setString(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setBytes(String parameterName, byte[] x) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setBytes(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setDate(String parameterName, Date x) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setDate(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setTime(String parameterName, Time x) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setTime(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setTimestamp(String parameterName, Timestamp x)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setTimestamp(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setAsciiStream(String parameterName, InputStream x, int length)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setAsciiStream(parameterName, x, length);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setBinaryStream(String parameterName, InputStream x, int length)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setBinaryStream(parameterName, x, length);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setObject(String parameterName, Object x, int targetSqlType,
			int scale) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setObject(parameterName, x, targetSqlType, scale);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setObject(String parameterName, Object x, int targetSqlType)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setObject(parameterName, x, targetSqlType);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setObject(String parameterName, Object x) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setObject(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setCharacterStream(String parameterName, Reader reader,
			int length) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setCharacterStream(parameterName, reader, length);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setDate(String parameterName, Date x, Calendar cal)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setDate(parameterName, x, cal);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setTime(String parameterName, Time x, Calendar cal)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setTime(parameterName, x, cal);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setTimestamp(parameterName, x, cal);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setNull(String parameterName, int sqlType, String typeName)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setNull(parameterName, sqlType, typeName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public String getString(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getString(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public boolean getBoolean(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getBoolean(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public byte getByte(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getByte(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public short getShort(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getShort(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public int getInt(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getInt(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public long getLong(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getLong(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public float getFloat(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getFloat(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public double getDouble(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getDouble(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public byte[] getBytes(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getBytes(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Date getDate(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getDate(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Time getTime(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getTime(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Timestamp getTimestamp(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getTimestamp(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Object getObject(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getObject(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public BigDecimal getBigDecimal(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getBigDecimal(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Object getObject(String parameterName, Map<String, Class<?>> map)
			throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getObject(parameterName, map);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Ref getRef(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getRef(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Blob getBlob(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getBlob(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Clob getClob(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getClob(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Array getArray(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getArray(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Date getDate(String parameterName, Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getDate(parameterName, cal);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Time getTime(String parameterName, Calendar cal) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getTime(parameterName, cal);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Timestamp getTimestamp(String parameterName, Calendar cal)
			throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getTimestamp(parameterName, cal);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public URL getURL(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getURL(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public RowId getRowId(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getRowId(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public RowId getRowId(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getRowId(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setRowId(String parameterName, RowId x) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setRowId(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setNString(String parameterName, String value)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setNString(parameterName, value);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setNCharacterStream(String parameterName, Reader value,
			long length) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setNCharacterStream(parameterName, value, length);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setNClob(String parameterName, NClob value) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setNClob(parameterName, value);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setClob(String parameterName, Reader reader, long length)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setClob(parameterName, reader, length);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setBlob(String parameterName, InputStream inputStream,
			long length) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setBlob(parameterName, inputStream, length);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setNClob(String parameterName, Reader reader, long length)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setNClob(parameterName, reader, length);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public NClob getNClob(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getNClob(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public NClob getNClob(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getNClob(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setSQLXML(String parameterName, SQLXML xmlObject)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setSQLXML(parameterName, xmlObject);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public SQLXML getSQLXML(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getSQLXML(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public SQLXML getSQLXML(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getSQLXML(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public String getNString(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getNString(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public String getNString(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getNString(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Reader getNCharacterStream(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getNCharacterStream(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Reader getNCharacterStream(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getNCharacterStream(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Reader getCharacterStream(int parameterIndex) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getCharacterStream(parameterIndex);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public Reader getCharacterStream(String parameterName) throws SQLException {
		SQLException sqle = null;
		try {
			return callableStatement.getCharacterStream(parameterName);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setBlob(String parameterName, Blob x) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setBlob(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setClob(String parameterName, Clob x) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setClob(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setAsciiStream(String parameterName, InputStream x, long length)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setAsciiStream(parameterName, x, length);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setBinaryStream(String parameterName, InputStream x, long length)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setBinaryStream(parameterName, x, length);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setCharacterStream(String parameterName, Reader reader,
			long length) throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setCharacterStream(parameterName, reader, length);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setAsciiStream(String parameterName, InputStream x)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setAsciiStream(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setBinaryStream(String parameterName, InputStream x)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setBinaryStream(parameterName, x);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setCharacterStream(String parameterName, Reader reader)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setCharacterStream(parameterName, reader);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setNCharacterStream(String parameterName, Reader value)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setNCharacterStream(parameterName, value);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setClob(String parameterName, Reader reader)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setClob(parameterName, reader);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setBlob(String parameterName, InputStream inputStream)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setBlob(parameterName, inputStream);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	public void setNClob(String parameterName, Reader reader)
			throws SQLException {
		SQLException sqle = null;
		try {
			callableStatement.setNClob(parameterName, reader);
		} catch (SQLException e) {
			sqle = e;
			throw sqle;
		} catch (Throwable th) {
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
	 * @see java.sql.CallableStatement#getObject(int, java.lang.Class)
	 */
	public <T> T getObject(int parameterIndex, Class<T> type)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Not Supported");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.sql.CallableStatement#getObject(java.lang.String,
	 *      java.lang.Class)
	 */
	public <T> T getObject(String parameterName, Class<T> type)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Not Supported");
	}
}
