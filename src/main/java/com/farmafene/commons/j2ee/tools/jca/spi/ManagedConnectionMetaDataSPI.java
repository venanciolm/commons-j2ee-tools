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

import javax.resource.ResourceException;
import javax.resource.spi.ManagedConnectionMetaData;

public abstract class ManagedConnectionMetaDataSPI implements ManagedConnectionMetaData {

	private String eISProductName;
	private String eISProductVersion;
	private int maxConnections;
	private String userName;

	public ManagedConnectionMetaDataSPI() {
		this.eISProductName = "AuriusCommon - Client";
		this.eISProductVersion = "1.0.0";
		this.maxConnections = 1;
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
		sb.append("EISProductName=").append(eISProductName);
		sb.append(", EISProductVersion=").append(eISProductVersion);
		sb.append(", maxConnections=").append(maxConnections);
		if (null != userName && !"".equals(userName.trim())) {
			sb.append(", userName=").append(userName);
		}
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnectionMetaData#getEISProductName()
	 */
	@Override
	public String getEISProductName() throws ResourceException {
		return eISProductName;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnectionMetaData#getEISProductVersion()
	 */
	@Override
	public String getEISProductVersion() throws ResourceException {
		return eISProductVersion;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnectionMetaData#getMaxConnections()
	 */
	@Override
	public int getMaxConnections() throws ResourceException {
		return maxConnections;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.resource.spi.ManagedConnectionMetaData#getUserName()
	 */
	@Override
	public String getUserName() throws ResourceException {
		return userName;
	}

	/**
	 * @param maxConnections
	 *            the maxConnections to set
	 */
	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
