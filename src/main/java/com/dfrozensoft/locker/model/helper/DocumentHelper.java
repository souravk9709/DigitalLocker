package com.dfrozensoft.locker.model.helper;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import com.dfrozensoft.locker.common.Constants;
import com.dfrozensoft.locker.model.Document;
import com.dfrozensoft.locker.model.DocumentSummary;
import com.dfrozensoft.locker.model.User;
import com.dfrozensoft.locker.util.GsonProvider;
import com.dfrozensoft.locker.util.crypto.AsymmetricCryptoUtil;

public final class DocumentHelper {
	public static boolean validate(Document document) {
		return !(document.getName() == null || document.getName().isEmpty() || document.getCreator() == null
														|| document.getCreator().isEmpty()
														|| document.getEncryptedContent() == null
														|| document.getEncryptedContent().isEmpty()
														|| document.getSignature() == null
														|| document.getSignature().isEmpty());
	}

	public static boolean validateSignature(User user, Document document) {
		if (!user.getUsername().equals(document.getCreator()))
			return false;

		String raw = document.getName() + "|" + document.getCreator() + "|" + document.getEncryptedContent();
		byte[] rawBytes = raw.getBytes();
		byte[] signature = Base64.decodeBase64(document.getSignature());
		byte[] publicKey = Base64.decodeBase64(user.getPublicKey());
		return AsymmetricCryptoUtil.verifySign(publicKey, signature, rawBytes);
	}

	public static boolean validateShareSignature(User from, String to, DocumentSummary shared, String signature) {
		String raw = to + "|" + shared.getId() + "|" + shared.getEncryptedKey();
		byte[] rawBytes = raw.getBytes();
		byte[] sign = Base64.decodeBase64(signature);
		byte[] publicKey = Base64.decodeBase64(from.getPublicKey());
		return AsymmetricCryptoUtil.verifySign(publicKey, sign, rawBytes);
	}

	public static boolean isSizeOk(Document document) {
		return document.getEncryptedContent().length() < Constants.DOCUMENT_SIZE_LIMIT;
	}

	public static String serialize(Document document) {
		return GsonProvider.get().toJson(document);
	}

	public static String serialize(DocumentSummary documentSummary) {
		return GsonProvider.get().toJson(documentSummary);
	}

	public static Document deserializeDocument(String serializedDocument) {
		return GsonProvider.get().fromJson(serializedDocument, Document.class);
	}

	public static DocumentSummary deserializeDocumentSummary(String serializedDocumentSummary) {
		return GsonProvider.get().fromJson(serializedDocumentSummary, DocumentSummary.class);
	}

	public static DocumentSummary summarize(Document document) {
		DocumentSummary summary = new DocumentSummary();
		summary.setId(document.getId());
		summary.setName(document.getName());
		summary.setCreator(document.getCreator());
		summary.setEncryptedKey(document.getEncryptedKey());
		summary.setCreatedAt(document.getCreatedAt());
		return summary;
	}

	public static String generateId(Document document) {
		String raw = document.getName() + "|" + document.getCreator() + "|" + document.getEncryptedContent();
		return DigestUtils.sha256Hex(raw);
	}

	private DocumentHelper() {
	}
}
