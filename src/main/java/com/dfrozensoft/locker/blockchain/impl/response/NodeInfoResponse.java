package com.dfrozensoft.locker.blockchain.impl.response;

import com.dfrozensoft.locker.util.GsonProvider;
import com.google.gson.annotations.SerializedName;

public class NodeInfoResponse {
	@SerializedName("result")
	private Result result;

	public String getVersion() {
		return result.version;
	}

	public int getProtocolVersion() {
		return result.protocolVersion;
	}

	public String getChainName() {
		return result.chainName;
	}

	public String getDescription() {
		return result.description;
	}

	public String getProtocol() {
		return result.protocol;
	}

	public int getPort() {
		return result.port;
	}

	private static class Result {
		@SerializedName("version")
		private String version;

		@SerializedName("protocolversion")
		private int protocolVersion;

		@SerializedName("chainname")
		private String chainName;

		@SerializedName("description")
		private String description;

		@SerializedName("protocol")
		private String protocol;

		@SerializedName("port")
		private int port;
	}

	public static NodeInfoResponse parse(String json) {
		return GsonProvider.get().fromJson(json, NodeInfoResponse.class);
	}
}
