package com.dfrozensoft.locker.logging;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResponseLogger implements ContainerResponseFilter {
	private static Logger LOG = LogManager.getRootLogger();

	private static final String RESPONSE_LOG_FORMAT = "[RESPONSE] %s %d %d >>>> %s";
	private static final String RESPONSE_LOG_FORMAT_NO_BODY = "[RESPONSE] %s %d %d";

	@Override
	public void filter(ContainerRequestContext containerRequestContext,
													ContainerResponseContext containerResponseContext)
													throws IOException {
		String requestId = containerRequestContext.getProperty("request_id").toString();
		long requestStartTime = Long.parseLong(containerRequestContext.getProperty("request_start_time").toString());
		long timeTaken = System.currentTimeMillis() - requestStartTime;

		String uri = containerRequestContext.getUriInfo().getPath();
		int httpStatus = containerResponseContext.getStatus();

		try {
			String entity = (String) containerResponseContext.getEntity();
			if (entity == null || entity.isEmpty())
				throw new NullPointerException();

			LOG.info(formatLog(requestId, httpStatus, timeTaken, containerResponseContext.getEntity()));
		} catch (Exception e) {
			LOG.info(formatLog(requestId, httpStatus, timeTaken, null));
		}
	}

	private String formatLog(String requestId, int httpStatus, long timeTaken, Object body) {
		if (body == null)
			return String.format(RESPONSE_LOG_FORMAT_NO_BODY, requestId, httpStatus, timeTaken);
		else
			return String.format(RESPONSE_LOG_FORMAT, requestId, httpStatus, timeTaken, body);
	}
}
