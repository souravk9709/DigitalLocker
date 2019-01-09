package com.dfrozensoft.locker.blockchain.api;

import java.util.List;

import com.dfrozensoft.locker.model.DocumentSummary;

public interface ShareApi {
	void share(String username, DocumentSummary document);

	List<DocumentSummary> getSharedDocuments(String username);
}
