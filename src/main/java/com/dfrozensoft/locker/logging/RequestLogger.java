package com.dfrozensoft.locker.logging;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@PreMatching
public class RequestLogger implements ContainerRequestFilter {
	private static Logger LOG = LogManager.getRootLogger();

	private static final String REQUEST_LOG_FORMAT = "[REQUEST] %s %s %s <<<< %s";
	private static final String REQUEST_LOG_FORMAT_NO_BODY = "[REQUEST] %s %s %s";

	@Override
	public void filter(ContainerRequestContext containerRequestContext) throws IOException {
		String requestId = getRequestId();
		long startTime = System.currentTimeMillis();
		containerRequestContext.setProperty("request_id", requestId);
		containerRequestContext.setProperty("request_start_time", String.valueOf(startTime));

		String uri = containerRequestContext.getUriInfo().getPath();
		String method = containerRequestContext.getMethod();

		if (method.equalsIgnoreCase("POST")) {
			BufferedReader br = new BufferedReader(new InputStreamReader(containerRequestContext.getEntityStream()));
			StringBuilder requestBuilder = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null)
				requestBuilder.append(line.trim());
			String request = requestBuilder.toString();

			LOG.info(formatLog(requestId, "POST", uri, request));

			containerRequestContext.setEntityStream(new ByteArrayInputStream(request.getBytes()));
		} else
			LOG.info(formatLog(requestId, "GET", uri, null));
	}

	private String getRequestId() {
		return (int) (Math.random() * 100000) + "_" + System.nanoTime();
	}

	private String formatLog(String requestId, String httpMethod, String uri, String body) {
		if (body == null || body.isEmpty())
			return String.format(REQUEST_LOG_FORMAT_NO_BODY, requestId, httpMethod, uri);
		else
			return String.format(REQUEST_LOG_FORMAT, requestId, httpMethod, uri, body);
	}
}
