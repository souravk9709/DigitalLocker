package com.dfrozensoft.locker.endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dfrozensoft.locker.model.Node;

@Path("health-check")
public class HealthEndpoint extends AbstractEndpoint {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public void healthCheck(@Suspended AsyncResponse asyncResponse) {
		workerPool.execute(() -> {
			Node node = blockchainApiManager.getNodeApi().getInfo();
			if (node != null)
				asyncResponse.resume(buildSuccessJsonResponse(node));
			else
				asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).build());
		});
	}
}
