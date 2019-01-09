package com.dfrozensoft.locker.common;

import java.util.concurrent.ExecutorService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerContext {
	private static Logger LOG = LogManager.getRootLogger();

	public static ServerContext instance;

	public static void init(ExecutorService workerPool) {
		instance = new ServerContext(workerPool);
		LOG.debug("[ServerContext initialized]");
	}

	public static void destroy() {
		getInstance().workerPool.shutdownNow();

		LOG.debug("[ServerContext destroyed]");
	}

	public static ServerContext getInstance() {
		if (instance == null) {
			LOG.fatal("[ServerContext not initialized]");
			throw new IllegalStateException("ServerContext not initialized");
		}
		return instance;
	}

	private ExecutorService workerPool;

	private ServerContext(ExecutorService workerPool) {
		this.workerPool = workerPool;
	}

	public ExecutorService getWorkerPool() {
		return workerPool;
	}
}
