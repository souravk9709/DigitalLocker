package com.dfrozensoft.locker.blockchain.impl.response;

import com.dfrozensoft.locker.util.GsonProvider;
import com.google.gson.annotations.SerializedName;

public class PublishToStreamResponse {
	@SerializedName("result")
	private String transactionId;

	public String getTransactionId() {
		return transactionId;
	}

	public static PublishToStreamResponse parse(String responseJson) {
		return GsonProvider.get().fromJson(responseJson, PublishToStreamResponse.class);
	}

}
