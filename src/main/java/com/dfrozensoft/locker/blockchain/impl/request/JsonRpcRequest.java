package com.dfrozensoft.locker.blockchain.impl.request;

import java.util.ArrayList;
import java.util.List;

import com.dfrozensoft.locker.common.Constants;
import com.google.gson.annotations.SerializedName;

public class JsonRpcRequest {
	@SerializedName("id")
	protected final String id;

	@SerializedName("method")
	protected final String method;

	@SerializedName("params")
	protected final List<Object> params;

	@SerializedName("chain_name")
	protected final String chainName;

	public JsonRpcRequest(String method, List<Object> params) {
		this.id = generateRequestId();
		this.method = method;
		this.params = params;
		this.chainName = Constants.CHAIN_NAME;
	}

	public JsonRpcRequest(String method) {
		this(method, new ArrayList<>());
	}

	private static String generateRequestId() {
		return System.nanoTime() + "_" + (int) (10000 * Math.random());
	}
}
