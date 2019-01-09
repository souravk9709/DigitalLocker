package com.dfrozensoft.locker.blockchain.impl.request;

public class RetrieveFromStreamRequest extends JsonRpcRequest {

	public static final String METHOD_NAME = "liststreamkeyitems";

	public RetrieveFromStreamRequest(String streamName, String key) {
		super(METHOD_NAME);
		params.add(streamName);
		params.add(key);
	}

}
