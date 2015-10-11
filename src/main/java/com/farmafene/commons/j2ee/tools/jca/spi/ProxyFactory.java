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
/**
 * 
 */
package com.farmafene.commons.j2ee.tools.jca.spi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import com.farmafene.commons.j2ee.tools.jca.IManagedDriver;

/**
 * Utilidad para la creación de proxies dinámicos
 * 
 * @author vlopez@farmafene.com
 * 
 */
public class ProxyFactory {

	private Class<?>[] interfaces;

	public ProxyFactory() {
		this.interfaces = null;
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
		sb.append("interfaces=").append(Arrays.toString(interfaces));
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Método factoría
	 * 
	 * @param obj
	 * @return instancia del objeto
	 */
	public <Driver extends IManagedDriver> Driver newInstance(
			ConnectionSPI<Driver> obj) {
		Object newProxyInstance = obj;
		if (null != obj) {
			if (Proxy.isProxyClass(obj.getClass())) {
				InvocationHandler h = null;
				if ((h = Proxy.getInvocationHandler(obj)) instanceof Handler) {
					@SuppressWarnings("unchecked")
					Driver result = (Driver) makeProxy(
							(ConnectionSPI<Driver>) ((Handler) h).obj, h);
					newProxyInstance = result;
				}
			} else {
				newProxyInstance = makeProxy(obj, new Handler(this, obj));
			}
		}
		@SuppressWarnings("unchecked")
		Driver returnValue = (Driver) newProxyInstance;
		return returnValue;
	}

	private <Driver extends IManagedDriver> Driver makeProxy(
			ConnectionSPI<Driver> obj, InvocationHandler handler) {

		Class<?>[] interfaces = getInterfaces() == null ? obj.getClass()
				.getInterfaces() : getInterfaces();

		@SuppressWarnings("unchecked")
		Driver newProxyInstance = (Driver) Proxy.newProxyInstance(obj
				.getClass().getClassLoader(), interfaces, handler);
		return newProxyInstance;
	}

	public Class<?>[] getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Class<?>... interfaces) {
		this.interfaces = null;
		if (null != interfaces && interfaces.length > 0) {
			this.interfaces = interfaces;
		}
	}
}
