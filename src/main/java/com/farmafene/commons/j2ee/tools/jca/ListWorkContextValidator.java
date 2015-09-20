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
package com.farmafene.commons.j2ee.tools.jca;

import java.util.List;

import javax.resource.spi.work.WorkContext;

public class ListWorkContextValidator implements IWorkContextValidator {

	private List<Class<? extends WorkContext>> contexts;

	public ListWorkContextValidator() {
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
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.farmafene.commons.jca.IWorkContextValidator#isContextSupported(java.lang.Class)
	 */
	@Override
	public boolean isContextSupported(
			Class<? extends WorkContext> workContextClass) {
		boolean isContextSupported = false;
		for (Class<? extends WorkContext> clazz : contexts) {
			if (clazz.isAssignableFrom(workContextClass)) {
				isContextSupported = true;
				break;
			}
		}
		return isContextSupported;
	}

	/**
	 * @return the contexts
	 */
	public List<Class<? extends WorkContext>> getContexts() {
		return contexts;
	}

	/**
	 * @param contexts
	 *            the contexts to set
	 */
	public void setContexts(List<Class<? extends WorkContext>> contexts) {
		this.contexts = contexts;
	}

}
