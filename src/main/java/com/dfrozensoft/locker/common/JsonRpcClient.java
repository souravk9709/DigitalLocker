package com.dfrozensoft.locker.common;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dfrozensoft.locker.blockchain.impl.request.JsonRpcRequest;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class JsonRpcClient {
	private static Logger LOG = LogManager.getLogger();

	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final String AUTH_HEADER = "Authorization";

	private static JsonRpcClient instance;

	public static void init(Gson gson, String blockchainRpcUrl, String blockchainRpcUsername,
													String blockChainRpcPassword) {
		instance = new JsonRpcClient(gson, blockchainRpcUrl, blockchainRpcUsername, blockChainRpcPassword);
	}

	public static JsonRpcClient getInstance() {
		return instance;
	}

	private OkHttpClient okHttpClient;
	private Gson gson;
	private String blockchainRpcUrl;

	private JsonRpcClient(Gson gson, String blockchainRpcUrl, String blockchainRpcUsername,
													String blockChainRpcPassword) {
		this.blockchainRpcUrl = blockchainRpcUrl;
		this.gson = gson;

		okHttpClient = new OkHttpClient();
		okHttpClient.interceptors().add(chain -> {
			Request request = chain.request().newBuilder().addHeader(AUTH_HEADER, getAuthorizationHeader(
															blockchainRpcUsername, blockChainRpcPassword)).build();

			return chain.proceed(request);
		});
	}

	public String sendRequest(JsonRpcRequest jsonRpcRequest) throws JsonRpcException {
		RequestBody body = RequestBody.create(JSON, gson.toJson(jsonRpcRequest));
		LOG.info("[BLOCKCHAIN REQUEST] <<<< " + gson.toJson(jsonRpcRequest));

		Request request = new Request.Builder().url(blockchainRpcUrl).post(body).build();

		Response response = null;
		try {
			response = okHttpClient.newCall(request).execute();
		} catch (IOException e) {
			LOG.error("[BLOCKCHAIN RESPONSE] >>>> JSON-RPC call error", e);
			throw new JsonRpcException("JSON-RPC call error : " + e.getMessage());
		}

		if (response.code() != 200) {
			LOG.error("[BLOCKCHAIN RESPONSE] >>>> JSON-RPC call error : response code is not 200, rather it is "
															+ response.code());
			throw new JsonRpcException("JSON-RPC call error : response code is not 200");
		}

		try {
			String responseBody = response.body().string().replace("\n", "").replace("\r", "").trim();
			LOG.info("[BLOCKCHAIN RESPONSE] >>>> " + responseBody);
			return responseBody;
		} catch (IOException e) {
			LOG.error("[BLOCKCHAIN RESPONSE] >>>> JSON-RPC response malformed", e);
			throw new JsonRpcException("JSON-RPC response malformed : " + e.getMessage());
		}
	}

	private static String getAuthorizationHeader(String username, String password) {
		return "Basic bXVsdGljaGFpbnJwYzo5ZjZMR2V6MlNuVnZ3MVRiU29ORWRuQ2N2TkJxc3N4QVVlZTN2ZWZwU1kyYQ==";
	}
}
