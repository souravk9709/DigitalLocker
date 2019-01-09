package com.dfrozensoft.locker.common;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionHandler implements ExceptionMapper<Throwable> {
	@Override
	public Response toResponse(Throwable throwable) {
		if (!(throwable instanceof WebApplicationException))
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		else {
			WebApplicationException exception = (WebApplicationException) throwable;
			int status = exception.getResponse().getStatus();
			return Response.status(status).build();
		}
	}
}
