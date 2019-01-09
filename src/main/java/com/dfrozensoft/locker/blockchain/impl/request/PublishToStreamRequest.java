package com.dfrozensoft.locker.blockchain.impl.request;

import org.apache.commons.codec.binary.Hex;

public class PublishToStreamRequest extends JsonRpcRequest {
	private static final String METHOD_NAME = "publish";

	public PublishToStreamRequest(String streamName, String key, String value) {
		super(METHOD_NAME);
		params.add(streamName);
		params.add(key);
		params.add(toHexString(value));
	}

	private static String toHexString(String s) {
		return Hex.encodeHexString(s.getBytes());
	}
}
