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
package com.farmafene.commons.j2ee.tools.jca.spi;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnectionMetaData;

import com.farmafene.commons.j2ee.tools.jca.IManagedDriver;

@SuppressWarnings("serial")
public class ConnectionSPI<Driver extends IManagedDriver> implements
		Serializable, ICloseable {

	private UUID uUID;
	private boolean closed;
	private ManagedConnectionSPI<Driver, ? extends ConnectionRequestInfo, ? extends ManagedConnectionMetaData> managedConnection;
	private Driver driver;

	protected ConnectionSPI() {
		this.uUID = UUID.randomUUID();
		this.closed = false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("=[");
		sb.append("UUID=").append(managedConnection.getUUID());
		sb.append(":").append(uUID);
		sb.append(", closed=").append(closed);
		sb.append(", ").append(managedConnection);
		sb.append("]");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((managedConnection == null) ? 0 : managedConnection
						.hashCode());
		result = prime * result + ((uUID == null) ? 0 : uUID.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj instanceof ConnectionSPI) {
			equals = this.hashCode() == obj.hashCode();
		}
		return equals;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see ICloseable#close()
	 */
	@Override
	public void close() throws IOException {
		if (!closed) {
			closed = true;
			managedConnection.close(driver);
		}
	}

	/**
	 * @return the managedConnection
	 */
	public ManagedConnectionSPI<Driver, ? extends ConnectionRequestInfo, ? extends ManagedConnectionMetaData> getManagedConnection() {
		return managedConnection;
	}

	/**
	 * @param managedConnection
	 *            the managedConnection to set
	 */
	public void setManagedConnection(
			ManagedConnectionSPI<Driver, ? extends ConnectionRequestInfo, ? extends ManagedConnectionMetaData> managedConnection) {
		this.managedConnection = managedConnection;
	}

	/**
	 * @return the driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * @param driver the driver to set
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
}
