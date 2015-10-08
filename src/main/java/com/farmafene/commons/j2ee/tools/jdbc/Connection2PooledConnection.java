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
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;
import javax.sql.StatementEvent;
import javax.sql.StatementEventListener;

/**
 * @author vlopez
 *
 */
public class Connection2PooledConnection implements PooledConnection, PooledConnectionSubject, StatementEventListener {

	private Connection connection;
	private List<StatementEventListener> statementEventListeners;
	private List<ConnectionEventListener> connectionEventListeners;
	private final Set<PreparedStatement> preparedStatements;

	/**
	 *
	 */
	private Connection2PooledConnection() {
		this.statementEventListeners = new LinkedList<StatementEventListener>();
		this.connectionEventListeners = new LinkedList<ConnectionEventListener>();
		this.preparedStatements = new HashSet<PreparedStatement>();
		addStatementEventListener(this);
	}

	/**
	 * Constuctor parametrizado
	 *
	 * @param connection
	 * @throws SQLException
	 */
	public Connection2PooledConnection(final Connection connection) throws SQLException {
		this();
		if (connection == null) {
			throw new SQLException("Connection must be not null");
		}
		this.connection = new Connection4ConnectionPooled(connection, this);

	}

	/**
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("={");
		if (this.connection != null) {
			sb.append("connection=");
			sb.append(this.connection);
			sb.append(", ");
		}
		if (this.statementEventListeners != null) {
			sb.append("statementEventListeners=");
			sb.append(this.statementEventListeners.size());
			sb.append(", ");
		}
		if (this.connectionEventListeners != null) {
			sb.append("connectionEventListeners=");
			sb.append(this.connectionEventListeners.size());
			sb.append(", ");
		}
		if (this.preparedStatements != null) {
			sb.append("preparedStatements=");
			sb.append(this.preparedStatements.size());
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.PooledConnection#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return this.connection;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.PooledConnection#addConnectionEventListener(javax.sql.
	 *      ConnectionEventListener)
	 */
	@Override
	public void addConnectionEventListener(final ConnectionEventListener listener) {
		synchronized (this.connectionEventListeners) {
			final List<ConnectionEventListener> list = new LinkedList<ConnectionEventListener>(this.connectionEventListeners);
			list.add(listener);
			this.connectionEventListeners = list;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.PooledConnection#removeConnectionEventListener(javax.sql.
	 *      ConnectionEventListener)
	 */
	@Override
	public void removeConnectionEventListener(final ConnectionEventListener listener) {
		synchronized (this.connectionEventListeners) {
			final List<ConnectionEventListener> list = new LinkedList<ConnectionEventListener>(this.connectionEventListeners);
			list.remove(listener);
			this.connectionEventListeners = list;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.PooledConnection#addStatementEventListener(javax.sql.
	 *      StatementEventListener)
	 */
	@Override
	public void addStatementEventListener(final StatementEventListener listener) {
		synchronized (this.statementEventListeners) {
			final List<StatementEventListener> list = new LinkedList<StatementEventListener>(this.statementEventListeners);
			list.add(listener);
			this.statementEventListeners = list;
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.PooledConnection#removeStatementEventListener(javax.sql.
	 *      StatementEventListener)
	 */
	@Override
	public void removeStatementEventListener(final StatementEventListener listener) {
		synchronized (this.statementEventListeners) {
			final List<StatementEventListener> list = new LinkedList<StatementEventListener>(this.statementEventListeners);
			list.remove(listener);
			this.statementEventListeners = list;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.aurius.mngt.jdbc.PooledConnectionSubject#connectionClosed
	 *      (javax.sql.ConnectionEvent)
	 */
	@Override
	public void connectionClosed(final SQLException e) {
		List<ConnectionEventListener> list = null;
		synchronized (this.connectionEventListeners) {
			list = this.connectionEventListeners;
		}
		final ConnectionEvent event = new ConnectionEvent(this, e);
		for (final ConnectionEventListener l : list) {
			l.connectionClosed(event);
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.aurius.mngt.jdbc.PooledConnectionSubject#
	 *      connectionErrorOccurred(javax.sql.ConnectionEvent)
	 */
	@Override
	public void connectionErrorOccurred(final SQLException e) {
		List<ConnectionEventListener> list = null;
		synchronized (this.connectionEventListeners) {
			list = this.connectionEventListeners;
		}
		final ConnectionEvent event = new ConnectionEvent(this, e);
		for (final ConnectionEventListener l : list) {
			l.connectionErrorOccurred(event);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.aurius.mngt.jdbc.PooledConnectionSubject#statementClosed
	 *      (javax.sql.StatementEvent)
	 */
	@Override
	public void statementClosed(final PreparedStatement stmt, final SQLException e) {
		List<StatementEventListener> list = null;
		synchronized (this.statementEventListeners) {
			list = this.statementEventListeners;
		}
		final StatementEvent event = new StatementEvent(this, stmt, e);
		for (final StatementEventListener l : list) {
			l.statementClosed(event);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.aurius.mngt.jdbc.PooledConnectionSubject#addStatement(java.sql.PreparedStatement)
	 */
	@Override
	public void addStatement(final PreparedStatement pstmt) {
		if (null == this.preparedStatements) {
			return;
		}
		// FIXME!!! Aquí empieza el caché
		System.out.println("addStatement(" + pstmt + ")");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.StatementEventListener#statementClosed(javax.sql.StatementEvent
	 *      )
	 */
	@Override
	public void statementClosed(final StatementEvent event) {
		System.out.println("statementClosed(" + event + ")");
		try {
			event.getStatement().close();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.StatementEventListener#statementErrorOccurred(javax.sql.StatementEvent)
	 */
	@Override
	public void statementErrorOccurred(final StatementEvent event) {
		System.out.println("statementErrorOccurred(" + event + ")");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.farmafene.aurius.mngt.jdbc.PooledConnectionSubject#statementErrorOccurred
	 *      (javax.sql.StatementEvent)
	 */
	@Override
	public void statementErrorOccurred(final PreparedStatement stmt, final SQLException e) {
		List<StatementEventListener> list = null;
		synchronized (this.statementEventListeners) {
			list = this.statementEventListeners;
		}
		final StatementEvent event = new StatementEvent(this, stmt, e);
		for (final StatementEventListener l : list) {
			l.statementErrorOccurred(event);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.PooledConnection#close()
	 */
	@Override
	public void close() throws SQLException {
		SQLException th = null;
		try {
			this.connection.close();
		} catch (final SQLException e) {
			th = e;
			throw th;
		} catch (final Throwable t) {
			th = new SQLException(t);
			throw th;
		} finally {
			this.connection = null;
		}
	}
}
