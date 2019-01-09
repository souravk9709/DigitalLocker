package com.dfrozensoft.locker.blockchain.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dfrozensoft.locker.blockchain.api.NodeApi;
import com.dfrozensoft.locker.blockchain.impl.request.NodeInfoRequest;
import com.dfrozensoft.locker.blockchain.impl.response.NodeInfoResponse;
import com.dfrozensoft.locker.common.JsonRpcClient;
import com.dfrozensoft.locker.common.JsonRpcException;
import com.dfrozensoft.locker.model.Node;

public class NodeApiImpl implements NodeApi {
	private static Logger LOG = LogManager.getLogger();

	private JsonRpcClient jsonRpcClient;

	public NodeApiImpl(JsonRpcClient jsonRpcClient) {
		this.jsonRpcClient = jsonRpcClient;
	}

	@Override
	public Node getInfo() {
		try {
			NodeInfoResponse response = NodeInfoResponse.parse(jsonRpcClient.sendRequest(new NodeInfoRequest()));
			Node node = new Node();
			node.setVersion(response.getVersion());
			node.setProtocolVersion(response.getProtocolVersion());
			node.setChainName(response.getChainName());
			node.setDescription(response.getDescription());
			node.setProtocol(response.getProtocol());
			node.setPort(response.getPort());
			return node;
		} catch (JsonRpcException e) {
			return null;
		} catch (Exception e) {
			LOG.error("Unable to get node info", e);
			return null;
		}
	}
}
