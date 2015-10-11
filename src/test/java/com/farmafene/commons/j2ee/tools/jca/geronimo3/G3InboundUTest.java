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

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.farmafene.commons.j2ee.tools.jca.common.InboundBean;
import com.farmafene.commons.j2ee.tools.jca.common.StringPrintStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:com/farmafene/commons/j2ee/tools/jca/geronimo3/inbound.xml" })
public class G3InboundUTest implements InitializingBean {

	private static final Logger logger = LoggerFactory
			.getLogger(G3InboundUTest.class);

	@Autowired
	private ConfigurableApplicationContext ctx;
	private static ConfigurableApplicationContext CTX;

	@BeforeClass
	public static void beforeClass() throws SQLException {
		logger.info("Init!");
	}

	@AfterClass
	public static void afterClass() {
		logger.info("Destroy!");
		CTX.close();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (logger.isInfoEnabled()) {
			StringPrintStream ps = new StringPrintStream();
			ps.println();
			ps.println("/*--------------------------------------------+|");
			ps.println("|| Begin of Test                              ||");
			ps.print("+---------------------------------------------*/");
			logger.info("{}", ps);
		}
		Assert.assertNotNull(this.ctx);
		CTX = this.ctx;
	}

	@Test
	public void test() throws Exception {
		InboundBean bean = new InboundBean();
		bean.setCtx(ctx);
		bean.initTest();
		bean.inBoundTest();
	}
}
