package com.dfrozensoft.locker.blockchain.api;

import com.dfrozensoft.locker.blockchain.impl.DocumentApiImpl;
import com.dfrozensoft.locker.blockchain.impl.NodeApiImpl;
import com.dfrozensoft.locker.blockchain.impl.ShareApiImpl;
import com.dfrozensoft.locker.blockchain.impl.UserApiImpl;
import com.dfrozensoft.locker.common.Constants;
import com.dfrozensoft.locker.common.JsonRpcClient;
import com.dfrozensoft.locker.util.GsonProvider;

public class BlockchainApiManager {
	private static BlockchainApiManager instance;

	public static BlockchainApiManager getInstance() {
		if (instance == null)
			instance = new BlockchainApiManager();

		return instance;
	}

	/* Blockchain APIs */
	private NodeApi nodeApi;
	private UserApi userApi;
	private DocumentApi documentApi;
	private ShareApi shareApi;

	private BlockchainApiManager() {
		JsonRpcClient.init(GsonProvider.get(), Constants.BLOCKCHAIN_RPC_URL, Constants.BLOCKCHAIN_RPC_USERNAME,
														Constants.BLOCKCHAIN_RPC_PASSWORD);
	}

	public NodeApi getNodeApi() {
		if (nodeApi == null)
			nodeApi = new NodeApiImpl(JsonRpcClient.getInstance());

		return nodeApi;
	}

	public UserApi getUserApi() {

		if (userApi == null)
			userApi = new UserApiImpl(JsonRpcClient.getInstance());

		return userApi;
	}

	public DocumentApi getDocumentApi() {

		if (documentApi == null)
			documentApi = new DocumentApiImpl(JsonRpcClient.getInstance());

		return documentApi;
	}

	public ShareApi getShareApi() {
		if (shareApi == null)
			shareApi = new ShareApiImpl(JsonRpcClient.getInstance());

		return shareApi;
	}
}
