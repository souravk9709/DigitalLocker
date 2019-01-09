package com.dfrozensoft.locker.blockchain.api;

import java.util.List;

import com.dfrozensoft.locker.model.Document;
import com.dfrozensoft.locker.model.DocumentSummary;

public interface DocumentApi {
	void create(Document document);

	Document get(String id);

	List<DocumentSummary> getAll(String username);
}
