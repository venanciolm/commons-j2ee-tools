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

import java.lang.reflect.Field;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.BitronixTransactionSynchronizationRegistry;
import bitronix.tm.Configuration;
import bitronix.tm.TransactionManagerServices;
import bitronix.tm.internal.ThreadContext;

/**
 * Localizador de Clases para BTM.
 *
 * Adapta las búsquedas a BTM en una única clase.
 *
 */
abstract class BTMLocator {

	private static final Logger logger = LoggerFactory
			.getLogger(BTMLocator.class);

	private BTMLocator() {

	}

	public static Configuration getBitronixConfiguration() {
		return TransactionManagerServices.getConfiguration();
	}

	public static BitronixTransactionManager getBitronixTransactionManager() {
		return TransactionManagerServices.getTransactionManager();
	}

	public static BitronixTransactionSynchronizationRegistry getTransactionSynchronizationRegistry() {
		return TransactionManagerServices
				.getTransactionSynchronizationRegistry();
	}

	public static Map<Thread, ThreadContext> getContexts() {
		final BitronixTransactionManager tm = getBitronixTransactionManager();
		Map<Thread, ThreadContext> context = null;
		try {
			final Field f = BitronixTransactionManager.class
					.getDeclaredField("contexts");
			final boolean accesible = f.isAccessible();
			if (!accesible) {
				f.setAccessible(true);
			}
			try {
				@SuppressWarnings("unchecked")
				final Map<Thread, ThreadContext> ctx = (Map<Thread, ThreadContext>) f
						.get(tm);
				context = ctx;
			} catch (final IllegalArgumentException e) {
				logger.error("No se ha conseguido el contexto", e);
			} catch (final IllegalAccessException e) {
				logger.error("No se ha conseguido el contexto", e);
			}
		} catch (final SecurityException e) {
			logger.error("No se ha conseguido el contexto", e);
		} catch (final NoSuchFieldException e) {
			logger.error("No se ha conseguido el contexto", e);
		}
		return context;
	}
}
