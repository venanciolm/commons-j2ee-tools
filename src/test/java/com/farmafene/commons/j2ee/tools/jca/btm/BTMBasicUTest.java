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
package com.farmafene.commons.j2ee.tools.jca.btm;

import java.sql.SQLException;

import javax.resource.spi.XATerminator;
import javax.resource.spi.work.WorkManager;
import javax.transaction.TransactionManager;
import javax.transaction.TransactionSynchronizationRegistry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.farmafene.commons.j2ee.tools.jca.wm.IXAWorkAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:com/farmafene/commons/j2ee/tools/jca/WM_Geronimo3__BTMXATerminator.xml" })
public class BTMBasicUTest {

	private static final Logger logger = LoggerFactory
			.getLogger(BTMBasicUTest.class);

	@Autowired
	private ConfigurableApplicationContext ctx;
	private static ConfigurableApplicationContext CTX;

	public BTMBasicUTest() {
		logger.info("BTMBasicUTest<init>");
	}

	@BeforeClass
	public static void beforeClass() throws SQLException {
		logger.info("Init!");
	}

	@AfterClass
	public static void afterClass() {
		logger.info("Destroy!");
		CTX.close();
	}

	@Test
	public void beanFactory1Test() {
		Assert.assertNotNull(this.ctx);
		CTX = this.ctx;
		Assert.assertNotNull(this.ctx.getBean(TransactionManager.class));
		logger.info("TxManager: {}", this.ctx.getBean(TransactionManager.class));
		Assert.assertNotNull(this.ctx.getBean(WorkManager.class));
		logger.info("WorkManager: {}", this.ctx.getBean(WorkManager.class));
		Assert.assertNotNull(this.ctx.getBean(IXAWorkAdapter.class));
		logger.info("IXAWorkAdapter: {}",
				this.ctx.getBean(IXAWorkAdapter.class));
		Assert.assertNotNull(this.ctx.getBean(XATerminator.class));
		logger.info("XATerminator: {}", this.ctx.getBean(XATerminator.class));
		Assert.assertNotNull(this.ctx
				.getBean(TransactionSynchronizationRegistry.class));
		logger.info("TransactionSynchronizationRegistry: {}",
				this.ctx.getBean(TransactionSynchronizationRegistry.class));
	}
}
