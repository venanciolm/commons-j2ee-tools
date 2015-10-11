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

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.UnavailableException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.ExecutionContext;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkManager;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivationSpecLog implements ActivationSpec {

	private static final Logger logger = LoggerFactory
			.getLogger(ActivationSpecLog.class);
	private ResourceAdapter resourceAdapter;
	private Map<Xid, XAResource> xaResources = new ConcurrentHashMap<Xid, XAResource>();

	private BlockingQueue<MessageRALog> in;
	private BlockingQueue<String> out;
	private WorkManager workManager;
	private TransactionManager transactionManager;
	private AtomicBoolean running = new AtomicBoolean(true);
	private MessageEndpointFactory messageEndPointFactory;

	public ActivationSpecLog() {
		in = new SynchronousQueue<MessageRALog>();
		out = new SynchronousQueue<String>();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("={");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.ResourceAdapterAssociation#getResourceAdapter()
	 */
	@Override
	public ResourceAdapter getResourceAdapter() {
		logger.info("getResourceAdapter()");
		return this.resourceAdapter;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.ResourceAdapterAssociation#setResourceAdapter(javax.resource.spi.ResourceAdapter)
	 */
	@Override
	public void setResourceAdapter(final ResourceAdapter ra)
			throws ResourceException {
		this.resourceAdapter = ra;
		logger.info("setResourceAdapter(" + ra + ")");
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see javax.resource.spi.ActivationSpec#validate()
	 */
	@Override
	public void validate() throws InvalidPropertyException {
		logger.info("validate()");
	}

	public void start() {
		try {
			workManager.startWork(new Work() {
				/**
				 * {@inheritDoc}
				 * 
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					while (running.get()) {
						try {
							final MessageRALog m = in.take();
							logger.info("Hemos recibido un mensaje");
							XAResourceLog xaResource = null;
							ExecutionContext eCtx = null;

							Xid id = m.getTransaction();
							if (null != id) {
								xaResource = (XAResourceLog) xaResources
										.get(id);
								if (null == xaResource) {
									xaResource = new XAResourceLog(id,ActivationSpecLog.this);
									xaResources.put(id, xaResource);
								}
								eCtx = new ExecutionContext();
								eCtx.setXid(id);
							}
							final XAResource xa = xaResource;
							logger.info("Generando el trabajo");
							workManager.doWork(new Work() {
								@Override
								public void run() {
									try {
										MessageEPIntf mep = (MessageEPIntf) messageEndPointFactory
												.createEndpoint(xa);
										out.put(mep.echo(m.getInput()));
									} catch (UnavailableException e) {
										e.printStackTrace();
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}

								@Override
								public void release() {
									// do nothing
								}
							}, WorkManager.INDEFINITE, eCtx, null);
							logger.info("ha terminado");
						} catch (UnsupportedOperationException e) {
							// send end!
						} catch (WorkException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see javax.resource.spi.work.Work#release()
				 */
				@Override
				public void release() {
					// do nothing
				}
			});
		} catch (WorkException e) {
			logger.error("Se ha producido un error fatal:", e);
		}
	}

	public void stop() {
		logger.info("Stop on ActivationSpecLog");
		running.set(false);
		in.add(new MessageRALog() {
			/**
			 * {@inheritDoc}
			 * 
			 * @see com.farmafene.commons.j2ee.tools.jca.MessageRALog#getInput()
			 */
			public String getInput() {
				throw new UnsupportedOperationException();
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @see com.farmafene.commons.j2ee.tools.jca.MessageRALog#getTransaction()
			 */
			@Override
			public Xid getTransaction() {
				throw new UnsupportedOperationException();
			}
		});
	}

	/**
	 * @return the in
	 */
	public BlockingQueue<MessageRALog> getIn() {
		return in;
	}

	/**
	 * @return the out
	 */
	public BlockingQueue<String> getOut() {
		return out;
	}

	/**
	 * @param in
	 *            the in to set
	 */
	public void setIn(BlockingQueue<MessageRALog> in) {
		this.in = in;
	}

	/**
	 * @param out
	 *            the out to set
	 */
	public void setOut(BlockingQueue<String> out) {
		this.out = out;
	}

	/**
	 * @return the workManager
	 */
	public WorkManager getWorkManager() {
		return workManager;
	}

	/**
	 * @param workManager
	 *            the workManager to set
	 */
	public void setWorkManager(WorkManager workManager) {
		this.workManager = workManager;
	}

	public void setMessageEndpointFactory(MessageEndpointFactory endpointFactory) {
		this.messageEndPointFactory = endpointFactory;
	}

	/**
	 * @return the transactionManager
	 */
	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	/**
	 * @param transactionManager
	 *            the transactionManager to set
	 */
	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	/**
	 * @return the xaResources
	 */
	public Map<Xid, XAResource> getXAResources() {
		return xaResources;
	}
}
