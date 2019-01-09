package com.dfrozensoft.locker.blockchain.impl.request;

public class NodeInfoRequest extends JsonRpcRequest {
	private static final String METHOD_NAME = "getinfo";

	public NodeInfoRequest() {
		super(METHOD_NAME);
	}
}
