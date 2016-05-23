/*
 * Copyright (c) 2009-2016 farmafene.com
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
package com.farmafene.commons.j2ee.tools.activemq;

import java.io.File;

import org.apache.activemq.xbean.BrokerFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class Broker implements InitializingBean, DisposableBean {

	private static final Logger logger = LoggerFactory.getLogger(Broker.class);
	private BrokerFactoryBean broker;
	private String config;
	private boolean start;
	private IConfigurationLocator locator = new IConfigurationLocator() {
		/**
		 * {@inheritDoc}
		 * 
		 * @see com.farmafene.commons.j2ee.tools.activemq.IConfigurationLocator#getFile(java.lang.String)
		 */
		@Override
		public String getFile(String config) {
			return config;
		}
	};

	public Broker() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.farmafene.aurius.jca.AuriusMDB#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("={");
		sb.append("config=").append(config);
		sb.append(", start=").append(start);
		sb.append(", locator=").append(locator);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(this + ".afterPropertiesSet()");
		}
		if (null == config || "".equals(config.toString())) {
			throw new IllegalArgumentException(
					"Debe establecerse una configuración del broker");
		}
		broker = new BrokerFactoryBean();
		File file = new File(locator.getFile(config));
		if (logger.isInfoEnabled()) {
			logger.info("fichero: " + file.getAbsolutePath());
		}
		Resource resource = new FileSystemResource(file);
		broker.setConfig(resource);
		broker.setStart(start);
		broker.afterPropertiesSet();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		if (broker != null) {
			broker.destroy();
		}
	}

	/**
	 * @return the config
	 */
	public String getConfig() {
		return config;
	}

	/**
	 * @param config
	 *            the config to set
	 */
	public void setConfig(String config) {
		this.config = config;
	}

	/**
	 * @return the start
	 */
	public boolean isStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(boolean start) {
		this.start = start;
	}

	public void setLocator(IConfigurationLocator locator) {
		this.locator = locator;
	}

}
