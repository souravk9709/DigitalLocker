package com.dfrozensoft.locker.endpoints;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dfrozensoft.locker.blockchain.api.BlockchainApiManager;
import com.dfrozensoft.locker.common.ServerContext;
import com.dfrozensoft.locker.util.GsonProvider;

public class AbstractEndpoint {
	protected ExecutorService workerPool;
	protected BlockchainApiManager blockchainApiManager;

	public AbstractEndpoint() {
		workerPool = ServerContext.getInstance().getWorkerPool();
		blockchainApiManager = BlockchainApiManager.getInstance();
	}

	protected Response buildSuccessJsonResponse(Object o) {
		return Response.ok(GsonProvider.get().toJson(o), MediaType.APPLICATION_JSON_TYPE).build();
	}

	protected Response buildEmptySuccessResponse() {
		return Response.ok().build();
	}

	protected Response redirect(String url) {
		try {
			return Response.seeOther(new URI(url)).build();
		} catch (URISyntaxException e) {
			return Response.serverError().build();
		}
	}
}
