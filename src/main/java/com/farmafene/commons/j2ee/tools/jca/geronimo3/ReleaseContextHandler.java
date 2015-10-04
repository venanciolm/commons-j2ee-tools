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
package com.farmafene.commons.j2ee.tools.jca.geronimo3;

import javax.resource.spi.work.WorkCompletedException;
import javax.resource.spi.work.WorkContext;

import org.apache.geronimo.connector.work.WorkContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ReleaseContextHandler implements
		WorkContextHandler<ReleaseContext> {

	private static final Logger logger = LoggerFactory
			.getLogger(ReleaseContext.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.geronimo.connector.work.WorkContextHandler#before(javax.resource.spi.work.WorkContext)
	 */
	@Override
	public void before(ReleaseContext workContext)
			throws WorkCompletedException {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.geronimo.connector.work.WorkContextHandler#after(javax.resource
	 *      .spi.work.WorkContext)
	 */
	@Override
	public void after(final ReleaseContext workContext)
			throws WorkCompletedException {
		if (null != workContext.getInnerWork()) {
			workContext.getExecutor().execute(new Runnable() {
				/**
				 * {@inheritDoc}
				 * 
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					try {
						workContext.getInnerWork().release();
					} catch (Throwable th) {
						logger.warn("Error en el release", th);
					}
				}

			});
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.geronimo.connector.work.WorkContextHandler#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<? extends WorkContext> clazz) {
		return ReleaseContext.class.isAssignableFrom(clazz);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.geronimo.connector.work.WorkContextHandler#required()
	 */
	@Override
	public boolean required() {
		return false;
	}
}