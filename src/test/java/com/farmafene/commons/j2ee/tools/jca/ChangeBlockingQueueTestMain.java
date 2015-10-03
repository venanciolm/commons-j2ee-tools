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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ChangeBlockingQueueTestMain {

	public static void main(final String... args) {
		final int max = 3000;
		final int poolSize = 30;
		final SampleThreads sample = new SampleThreads();
		sample.setMax(max);
		sample.setPoolSize(poolSize);
		sample.doTest();
	}
}

class SampleThreads {
	private int max = 50;
	private int poolSize = 5;

	public void doTest() {
		ThreadPoolExecutor threadPool = null;
		BlockingQueue<Runnable> bq = null;
		try {
			if ((this.max - this.poolSize) > 0) {
				bq = new LinkedBlockingQueue<Runnable>(this.max - this.poolSize);
			} else {
				bq = new SynchronousQueue<Runnable>(true);
			}
			threadPool = new ThreadPoolExecutor(this.poolSize, this.poolSize, 60L, TimeUnit.SECONDS, bq);

			final CountDownLatch latch = new CountDownLatch(this.max);
			final CountDownLatch start = new CountDownLatch(1);
			for (int i = 0; i < this.max; i++) {
				System.err.println(String.format("Lanzando '%1$d'", i + 1));
				threadPool.execute(new Item(i + 1, latch, start));
			}
			System.err.println("Start!!!");
			start.countDown();
			try {
				latch.await();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			System.err.println("Hemos Terminado!");
		} finally {
			if (null != threadPool) {
				threadPool.shutdownNow();
			}
		}

	}

	/**
	 * Devuelve el valor de la propiedad 'max'
	 * @return Propiedad max
	 */
	public int getMax() {
		return this.max;
	}

	/**
	 * Asigna el valor de la propiedad 'max'
	 * @param max valor que se le quiere dar a la propiedad 'max'
	 */
	public void setMax(final int max) {
		this.max = max;
	}

	/**
	 * Devuelve el valor de la propiedad 'poolSize'
	 * @return Propiedad poolSize
	 */
	public int getPoolSize() {
		return this.poolSize;
	}

	/**
	 * Asigna el valor de la propiedad 'poolSize'
	 * @param poolSize valor que se le quiere dar a la propiedad 'poolSize'
	 */
	public void setPoolSize(final int poolSize) {
		this.poolSize = poolSize;
	}
}

class Item implements Runnable {

	private int number;
	private CountDownLatch latch;
	private CountDownLatch start;

	/**
	 *
	 */
	public Item() {
	}

	public Item(final int number, final CountDownLatch latch, final CountDownLatch start) {
		this();
		this.number = number;
		this.latch = latch;
		this.start = start;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			this.start.await();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println(String.format("Estamos en %1$d y salimos [%2$s]", this.number, Thread.currentThread().getName()));
		this.latch.countDown();
	}

	/**
	 * Devuelve el valor de la propiedad 'number'
	 * @return Propiedad number
	 */
	public int getNumber() {
		return this.number;
	}

	/**
	 * Asigna el valor de la propiedad 'number'
	 * @param number valor que se le quiere dar a la propiedad 'number'
	 */
	public void setNumber(final int number) {
		this.number = number;
	}

	/**
	 * Devuelve el valor de la propiedad 'latch'
	 * @return Propiedad latch
	 */
	public CountDownLatch getLatch() {
		return this.latch;
	}

	/**
	 * Asigna el valor de la propiedad 'latch'
	 * @param latch valor que se le quiere dar a la propiedad 'latch'
	 */
	public void setLatch(final CountDownLatch latch) {
		this.latch = latch;
	}

}
