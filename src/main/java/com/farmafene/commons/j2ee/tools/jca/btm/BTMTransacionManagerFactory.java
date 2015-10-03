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

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import bitronix.tm.Configuration;

public class BTMTransacionManagerFactory implements InitializingBean,
		DisposableBean, FactoryBean<BTMXATerminator> {

	private BTMXATerminator xATerminator;

	public BTMTransacionManagerFactory() {
	}

	private Configuration getConfiguration() {
		return BTMLocator.getBitronixConfiguration();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	@Override
	public BTMXATerminator getObject() throws Exception {
		return xATerminator;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	@Override
	public Class<?> getObjectType() {
		return BTMXATerminator.class;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		xATerminator.shutdown();
		xATerminator = null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		xATerminator = new BTMXATerminator();
	}

	/**
	 * @return the serverId
	 */
	public String getServerId() {
		return getConfiguration().getServerId();
	}

	/**
	 * @param serverId
	 *            the serverId to set
	 */
	public void setServerId(String serverId) {
		getConfiguration().setServerId(serverId);
	}

	/**
	 * @return the logPart1Filename
	 */
	public String getLogPart1Filename() {
		return getConfiguration().getLogPart1Filename();
	}

	/**
	 * @param logPart1Filename
	 *            the logPart1Filename to set
	 */
	public void setLogPart1Filename(String logPart1Filename) {
		getConfiguration().setLogPart1Filename(logPart1Filename);
	}

	/**
	 * @return the logPart2Filename
	 */
	public String getLogPart2Filename() {
		return getConfiguration().getLogPart2Filename();
	}

	/**
	 * @param logPart2Filename
	 *            the logPart2Filename to set
	 */
	public void setLogPart2Filename(String logPart2Filename) {
		getConfiguration().setLogPart2Filename(logPart2Filename);
	}

	/**
	 * @return the forcedWriteEnabled
	 */
	public boolean isForcedWriteEnabled() {
		return getConfiguration().isForcedWriteEnabled();
	}

	/**
	 * @param forcedWriteEnabled
	 *            the forcedWriteEnabled to set
	 */
	public void setForcedWriteEnabled(boolean forcedWriteEnabled) {
		getConfiguration().setForcedWriteEnabled(forcedWriteEnabled);
	}

	/**
	 * @return the forceBatchingEnabled
	 */
	public boolean isForceBatchingEnabled() {
		return getConfiguration().isForceBatchingEnabled();
	}

	/**
	 * @param forceBatchingEnabled
	 *            the forceBatchingEnabled to set
	 */
	public void setForceBatchingEnabled(boolean forceBatchingEnabled) {
		getConfiguration().setForceBatchingEnabled(forceBatchingEnabled);
	}

	/**
	 * @return the maxLogSizeInMb
	 */
	public int getMaxLogSizeInMb() {
		return getConfiguration().getMaxLogSizeInMb();
	}

	/**
	 * @param maxLogSizeInMb
	 *            the maxLogSizeInMb to set
	 */
	public void setMaxLogSizeInMb(int maxLogSizeInMb) {
		getConfiguration().setMaxLogSizeInMb(maxLogSizeInMb);
	}

	/**
	 * @return the filterLogStatus
	 */
	public boolean isFilterLogStatus() {
		return getConfiguration().isFilterLogStatus();
	}

	/**
	 * @param filterLogStatus
	 *            the filterLogStatus to set
	 */
	public void setFilterLogStatus(boolean filterLogStatus) {
		getConfiguration().setFilterLogStatus(filterLogStatus);
	}

	/**
	 * @return the skipCorruptedLogs
	 */
	public boolean isSkipCorruptedLogs() {
		return getConfiguration().isSkipCorruptedLogs();
	}

	/**
	 * @param skipCorruptedLogs
	 *            the skipCorruptedLogs to set
	 */
	public void setSkipCorruptedLogs(boolean skipCorruptedLogs) {
		getConfiguration().setSkipCorruptedLogs(skipCorruptedLogs);
	}

	/**
	 * @return the asynchronous2Pc
	 */
	public boolean isAsynchronous2Pc() {
		return getConfiguration().isAsynchronous2Pc();
	}

	/**
	 * @param asynchronous2Pc
	 *            the asynchronous2Pc to set
	 */
	public void setAsynchronous2Pc(boolean asynchronous2Pc) {
		getConfiguration().setAsynchronous2Pc(asynchronous2Pc);
	}

	/**
	 * @return the warnAboutZeroResourceTransaction
	 */
	public boolean isWarnAboutZeroResourceTransaction() {
		return getConfiguration().isWarnAboutZeroResourceTransaction();
	}

	/**
	 * @param warnAboutZeroResourceTransaction
	 *            the warnAboutZeroResourceTransaction to set
	 */
	public void setWarnAboutZeroResourceTransaction(
			boolean warnAboutZeroResourceTransaction) {
		getConfiguration().setWarnAboutZeroResourceTransaction(
				warnAboutZeroResourceTransaction);
	}

	/**
	 * @return the debugZeroResourceTransaction
	 */
	public boolean isDebugZeroResourceTransaction() {
		return getConfiguration().isDebugZeroResourceTransaction();
	}

	/**
	 * @param debugZeroResourceTransaction
	 *            the debugZeroResourceTransaction to set
	 */
	public void setDebugZeroResourceTransaction(
			boolean debugZeroResourceTransaction) {
		getConfiguration().setDebugZeroResourceTransaction(
				debugZeroResourceTransaction);
	}

	/**
	 * @return the defaultTransactionTimeout
	 */
	public int getDefaultTransactionTimeout() {
		return getConfiguration().getDefaultTransactionTimeout();
	}

	/**
	 * @param defaultTransactionTimeout
	 *            the defaultTransactionTimeout to set
	 */
	public void setDefaultTransactionTimeout(int defaultTransactionTimeout) {
		getConfiguration().setDefaultTransactionTimeout(
				defaultTransactionTimeout);
	}

	/**
	 * @return the gracefulShutdownInterval
	 */
	public int getGracefulShutdownInterval() {
		return getConfiguration().getGracefulShutdownInterval();
	}

	/**
	 * @param gracefulShutdownInterval
	 *            the gracefulShutdownInterval to set
	 */
	public void setGracefulShutdownInterval(int gracefulShutdownInterval) {
		getConfiguration()
				.setGracefulShutdownInterval(gracefulShutdownInterval);
	}

	/**
	 * @return the backgroundRecoveryIntervalSeconds
	 */
	public int getBackgroundRecoveryIntervalSeconds() {
		return getConfiguration().getBackgroundRecoveryIntervalSeconds();
	}

	/**
	 * @param backgroundRecoveryIntervalSeconds
	 *            the backgroundRecoveryIntervalSeconds to set
	 */
	public void setBackgroundRecoveryIntervalSeconds(
			int backgroundRecoveryIntervalSeconds) {
		getConfiguration().setBackgroundRecoveryIntervalSeconds(
				backgroundRecoveryIntervalSeconds);
	}

	/**
	 * @return the disableJmx
	 */
	public boolean isDisableJmx() {
		return getConfiguration().isDisableJmx();
	}

	/**
	 * @param disableJmx
	 *            the disableJmx to set
	 */
	public void setDisableJmx(boolean disableJmx) {
		getConfiguration().setDisableJmx(disableJmx);
	}

	/**
	 * @return the jndiUserTransactionName
	 */
	public String getJndiUserTransactionName() {
		return getConfiguration().getJndiUserTransactionName();
	}

	/**
	 * @param jndiUserTransactionName
	 *            the jndiUserTransactionName to set
	 */
	public void setJndiUserTransactionName(String jndiUserTransactionName) {
		getConfiguration().setJndiUserTransactionName(jndiUserTransactionName);
	}

	/**
	 * @return the jndiTransactionSynchronizationRegistryName
	 */
	public String getJndiTransactionSynchronizationRegistryName() {
		return getConfiguration()
				.getJndiTransactionSynchronizationRegistryName();
	}

	/**
	 * @param jndiTransactionSynchronizationRegistryName
	 *            the jndiTransactionSynchronizationRegistryName to set
	 */
	public void setJndiTransactionSynchronizationRegistryName(
			String jndiTransactionSynchronizationRegistryName) {
		getConfiguration().setJndiTransactionSynchronizationRegistryName(
				jndiTransactionSynchronizationRegistryName);
	}

	/**
	 * @return the journal
	 */
	public String getJournal() {
		return getConfiguration().getJournal();
	}

	/**
	 * @param journal
	 *            the journal to set
	 */
	public void setJournal(String journal) {
		getConfiguration().setJournal(journal);
	}

	/**
	 * @return the exceptionAnalyzer
	 */
	public String getExceptionAnalyzer() {
		return getConfiguration().getExceptionAnalyzer();
	}

	/**
	 * @param exceptionAnalyzer
	 *            the exceptionAnalyzer to set
	 */
	public void setExceptionAnalyzer(String exceptionAnalyzer) {
		getConfiguration().setExceptionAnalyzer(exceptionAnalyzer);
	}

	/**
	 * @return the currentNodeOnlyRecovery
	 */
	public boolean isCurrentNodeOnlyRecovery() {
		return getConfiguration().isCurrentNodeOnlyRecovery();
	}

	/**
	 * @param currentNodeOnlyRecovery
	 *            the currentNodeOnlyRecovery to set
	 */
	public void setCurrentNodeOnlyRecovery(boolean currentNodeOnlyRecovery) {
		getConfiguration().setCurrentNodeOnlyRecovery(currentNodeOnlyRecovery);
	}

	/**
	 * @return the allowMultipleLrc
	 */
	public boolean isAllowMultipleLrc() {
		return getConfiguration().isAllowMultipleLrc();
	}

	/**
	 * @param allowMultipleLrc
	 *            the allowMultipleLrc to set
	 */
	public void setAllowMultipleLrc(boolean allowMultipleLrc) {
		getConfiguration().setAllowMultipleLrc(allowMultipleLrc);
	}

	/**
	 * @return the resourceConfigurationFilename
	 */
	public String getResourceConfigurationFilename() {
		return getConfiguration().getResourceConfigurationFilename();
	}

	/**
	 * @param resourceConfigurationFilename
	 *            the resourceConfigurationFilename to set
	 */
	public void setResourceConfigurationFilename(
			String resourceConfigurationFilename) {
		getConfiguration().setResourceConfigurationFilename(
				resourceConfigurationFilename);
	}
}
