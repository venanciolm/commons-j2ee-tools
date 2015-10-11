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
package com.farmafene.commons.j2ee.tools.jca.spi.inout;

import javax.resource.spi.XATerminator;

import com.farmafene.commons.j2ee.tools.jca.ActivationSpecLog;
import com.farmafene.commons.j2ee.tools.jca.spi.ConnectionRequestInfoDummy;
import com.farmafene.commons.j2ee.tools.jca.spi.IManagedDriverDummy;
import com.farmafene.commons.j2ee.tools.jca.spi.ManagedConnectionFactoryDummy;
import com.farmafene.commons.j2ee.tools.jca.spi.ManagedConnectionMetaDataSPI;
import com.farmafene.commons.j2ee.tools.jca.spi.ManagedConnectionSPI;

@SuppressWarnings("serial")
public class ManagedConnectionFactoryIODummy extends
		ManagedConnectionFactoryDummy {
	private XATerminator xATerminator;
	private ActivationSpecLog activationSpec;


	/**
	 * {@inheritDoc}
	 * 
	 * @see com.farmafene.commons.j2ee.tools.jca.spi.ManagedConnectionFactorySPI#createInstanceOfManagedConnectionSPI()
	 */
	@Override
	public ManagedConnectionSPI<IManagedDriverDummy, ConnectionRequestInfoDummy, ManagedConnectionMetaDataSPI> createInstanceOfManagedConnectionSPI() {
		ManagedConnectionIODummy mc = new ManagedConnectionIODummy();
		mc.setXATerminator(xATerminator);
		mc.setActivationSpec(activationSpec);
		return mc;
	}

	/**
	 * @return the xATerminator
	 */
	public XATerminator getXATerminator() {
		return xATerminator;
	}

	/**
	 * @param xATerminator
	 *            the xATerminator to set
	 */
	public void setXATerminator(XATerminator xATerminator) {
		this.xATerminator = xATerminator;
	}

	/**
	 * @return the activationSpec
	 */
	public ActivationSpecLog getActivationSpec() {
		return activationSpec;
	}

	/**
	 * @param activationSpec
	 *            the activationSpec to set
	 */
	public void setActivationSpec(ActivationSpecLog activationSpec) {
		this.activationSpec = activationSpec;
	}
}
