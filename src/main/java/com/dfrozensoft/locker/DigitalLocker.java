package com.dfrozensoft.locker;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.dfrozensoft.locker.blockchain.impl.request.PublishToStreamRequest;
import com.dfrozensoft.locker.blockchain.impl.request.RetrieveFromStreamRequest;
import com.dfrozensoft.locker.blockchain.impl.response.PublishToStreamResponse;
import com.dfrozensoft.locker.blockchain.impl.response.RetrieveFromStreamResponse;
import com.dfrozensoft.locker.common.JsonRpcClient;
import com.dfrozensoft.locker.common.JsonRpcException;
import com.dfrozensoft.locker.exception.UnknownException;
import com.dfrozensoft.locker.model.Document;
import com.dfrozensoft.locker.model.DocumentSummary;
import com.dfrozensoft.locker.model.User;
import com.dfrozensoft.locker.model.helper.DocumentHelper;
import com.dfrozensoft.locker.model.helper.UserHelper;
import com.dfrozensoft.locker.util.crypto.AsymmetricCryptoUtil;
import com.dfrozensoft.locker.util.crypto.SymmetricCryptoUtil;

public class DigitalLocker {
	private static JsonRpcClient jsonRpcClient;
	
	public static void main(String[] args) throws Exception {
		// String to = "dhanraj";
		// String docId = "a2581b62839c4bc8c05847bba6ccde55294da12a7928fca87e4f1d901749660a";
		// String encKey =
		// "lC8IPZEzdS5PA8aVFPiaR5g/P2YUYi/9U6lWIt7wMKugbq3/WOhnldx8Ihwi6UZJm+en/JI/aac15DAJyGeiiF6tuCZbduDJrksYErdLKd7a9ynQMSJ0aUiQlZzXqGRTsorv5e1cIdBY/d9N5/1kAKeL4yt6RxKaYILhSkHuiMQ=";
		// String raw = to + "|" + docId + "|" + encKey;
		//
		// String privateKey =
		// "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIsNHRMy907p60/fg6+mjB4Dc1ZJv+HTrsD0apvLbjyhZpQ17J5ikcO0GlnpXptQW9wV8kOqkyKXrPSjT0KXwfSxsbfgYLEovJgZB93QTt9aAYgrM54BL2k67qj70lmq1bBPQamlUfGrzVWpl1yZgWYRdUYfy+HmQD5SEUn0ATcHAgMBAAECgYB3RK88qYmQ6XmJ0Cjuv8cNc145lBZ+yAfrv3m12f7bCIGo/LvRerCWkWPM3Svlwj6Yf0aGnceIc1rJl05Dy1Fzla7ji/xYizRUN3q3Pb4IPLIeF7FPMC8EXuMQ4eyF6SL4eplPzq05cAFaHlllLbldJF+s7ws2sFO9ev8OqBdVcQJBAPlDGUtIl2TvxWXhWhDZUikf7SJxyEZMgXfvZiIGru43cD088U/9TWs9ikYaMfsuB1S3McXLhx/XehGpt7fZKm8CQQCOz1opfX2pReMzQNMyXf5EHun4relE+saCEAtIFvJzrPK4iSbQ5aX9pi+8NIRXyghQsCNSe1y7GEq3o0Nz6+jpAkAbwNXTJQz+RxgihNnjYF8ehxh/Kk4TTbdopDYp+baGfZO4rc1si5aQJzT7IzWHcxOL4i3fiQ7Ng89qogv7o2lvAkBqWhUFLQV2GCvZMX+W1NTUmkkX/zvnOPK4TYZ5S02Hw9aGgu07SKrTOP33pQyh7D1BCctkU6Z9Vtvd4mILZDZxAkEAmfHnHgLLmP94gCOxot4Il8bPzpoHdoQqiK4D69m6d9B/G6unpLVACBpEDRsQDBD2djDQLEBBKHdkQHUn8q19gw==";
		// byte[] sign = AsymmetricCryptoUtil.sign(Base64.decodeBase64(privateKey), raw.getBytes());
		// System.out.println(Base64.encodeBase64String(sign));
		oldmethod();
	}
	
	private static void oldmethod() throws Exception {
		File file = new File("/users/dhanraj/bitcoin.pdf");
		byte[] contents = IOUtils.toByteArray(new FileInputStream(file));
		System.out.println("File Size : " + contents.length);
		
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLDR0TMvdO6etP34OvpoweA3NWSb/h067A9Gqby248oWaUNeyeYpHDtBpZ6V6bUFvcFfJDqpMil6z0o09Cl8H0sbG34GCxKLyYGQfd0E7fWgGIKzOeAS9pOu6o+9JZqtWwT0GppVHxq81VqZdcmYFmEXVGH8vh5kA+UhFJ9AE3BwIDAQAB";
		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIsNHRMy907p60/fg6+mjB4Dc1ZJv+HTrsD0apvLbjyhZpQ17J5ikcO0GlnpXptQW9wV8kOqkyKXrPSjT0KXwfSxsbfgYLEovJgZB93QTt9aAYgrM54BL2k67qj70lmq1bBPQamlUfGrzVWpl1yZgWYRdUYfy+HmQD5SEUn0ATcHAgMBAAECgYB3RK88qYmQ6XmJ0Cjuv8cNc145lBZ+yAfrv3m12f7bCIGo/LvRerCWkWPM3Svlwj6Yf0aGnceIc1rJl05Dy1Fzla7ji/xYizRUN3q3Pb4IPLIeF7FPMC8EXuMQ4eyF6SL4eplPzq05cAFaHlllLbldJF+s7ws2sFO9ev8OqBdVcQJBAPlDGUtIl2TvxWXhWhDZUikf7SJxyEZMgXfvZiIGru43cD088U/9TWs9ikYaMfsuB1S3McXLhx/XehGpt7fZKm8CQQCOz1opfX2pReMzQNMyXf5EHun4relE+saCEAtIFvJzrPK4iSbQ5aX9pi+8NIRXyghQsCNSe1y7GEq3o0Nz6+jpAkAbwNXTJQz+RxgihNnjYF8ehxh/Kk4TTbdopDYp+baGfZO4rc1si5aQJzT7IzWHcxOL4i3fiQ7Ng89qogv7o2lvAkBqWhUFLQV2GCvZMX+W1NTUmkkX/zvnOPK4TYZ5S02Hw9aGgu07SKrTOP33pQyh7D1BCctkU6Z9Vtvd4mILZDZxAkEAmfHnHgLLmP94gCOxot4Il8bPzpoHdoQqiK4D69m6d9B/G6unpLVACBpEDRsQDBD2djDQLEBBKHdkQHUn8q19gw==";
		String key = "secret";
		byte[] encryptedKey = AsymmetricCryptoUtil.encryptWithPublicKey(Base64.decodeBase64(publicKey), key.getBytes());
		System.out.println("Encrypted Key\n" + Base64.encodeBase64String(encryptedKey));
		byte[] dec = AsymmetricCryptoUtil.decryptWithPrivateKey(Base64.decodeBase64(privateKey), encryptedKey);
		System.out.println(new String(dec));
		
		String name = "bitcoin.pdf";
		String creator = "dhanraj";
		String encryptedContents = Base64.encodeBase64String(SymmetricCryptoUtil.encrypt(key, contents));
		System.out.println("\nEncrypted Content\n" + encryptedContents);
		System.out.println("Encrypted COntent Size: " + encryptedContents.length());
		
		String raw = name + "|" + creator + "|" + encryptedContents;
		byte[] rawBytes = raw.getBytes();
		byte[] signature = AsymmetricCryptoUtil.sign(Base64.decodeBase64(privateKey), rawBytes);
		System.out.println("\nSignature\n" + Base64.encodeBase64String(signature));
	}
	
	private static void registerUser(JsonRpcClient jsonRpcClient) {
		User user = new User();
		user.setCreatedAt(System.currentTimeMillis());
		user.setEmail("dhanraj.dadhich78@gmail.com");
		user.setMetadata(new HashMap<>());
		user.setName("Dhanraj");
		user.setPublicKey("Public Key Dhanraj");
		user.setSignature("Signature Dhanraj");
		user.setUsername("dhanrajd");
		
		try {
			String sendRequestData = jsonRpcClient.sendRequest(new PublishToStreamRequest("users", user.getUsername(),
															UserHelper.serialize(user)));
			PublishToStreamResponse response = PublishToStreamResponse.parse(sendRequestData);
		} catch (JsonRpcException e) {
			throw new UnknownException();
		} catch (Exception e) {
			throw new UnknownException();
		}
	}
	
	private static void getUser(JsonRpcClient jsonRpcClient) {
		try {
			RetrieveFromStreamResponse response = RetrieveFromStreamResponse.parse(jsonRpcClient.sendRequest(
															new RetrieveFromStreamRequest("users", "dhanraj")));
			if (response.getResults().size() == 0)
				System.out.printf("No such User");
			else
				System.out.println(UserHelper.deserialize(response.getResult(0).getData()));
			
		} catch (JsonRpcException e) {
			throw new UnknownException();
		} catch (Exception e) {
			throw new UnknownException();
		}
	}
	
	private static void createDocument(JsonRpcClient jsonRpcClient) {
		Document document = new Document();
		document.setCreatedAt(System.currentTimeMillis());
		document.setCreator("dhanrajd");
		document.setEncryptedContent("Encrypted Document 2");
		document.setEncryptedKey("Encryped key for Document 2");
		document.setId("2");
		document.setName("Document 2");
		document.setSignature("Signature 1");
		
		try {
			PublishToStreamResponse publishToStreamResponse = PublishToStreamResponse.parse(jsonRpcClient.sendRequest(
															new PublishToStreamRequest("documents", document.getId(),
																											DocumentHelper.serialize(document))));
			
			DocumentSummary documentSummary = DocumentHelper.summarize(document);
			PublishToStreamResponse response = PublishToStreamResponse.parse(jsonRpcClient.sendRequest(
															new PublishToStreamRequest("user_documents", document
																											.getCreator(),
																											DocumentHelper.serialize(documentSummary))));
		} catch (JsonRpcException e) {
			throw new UnknownException();
		} catch (Exception e) {
			throw new UnknownException();
		}
	}
	
	private static void getDocument(JsonRpcClient jsonRpcClient) {
		try {
			RetrieveFromStreamResponse response = RetrieveFromStreamResponse.parse(jsonRpcClient.sendRequest(
															new RetrieveFromStreamRequest("documents", "3")));
			if (response.getResults().size() == 0)
				System.out.println("No such Document");
		} catch (JsonRpcException e) {
			throw new UnknownException();
		} catch (Exception e) {
			throw new UnknownException();
		}
	}
	
	private static void getAllDocumentSummaries(JsonRpcClient jsonRpcClient) {
		try {
			RetrieveFromStreamResponse response = RetrieveFromStreamResponse.parse(jsonRpcClient.sendRequest(
															new RetrieveFromStreamRequest("user_documents", "dhanraj")));
			
			List<DocumentSummary> documentSummaryList = new ArrayList<>();
			for (RetrieveFromStreamResponse.Result result : response.getResults())
				documentSummaryList.add(DocumentHelper.deserializeDocumentSummary(result.getData()));
			
			System.out.println(documentSummaryList.size());
		} catch (JsonRpcException e) {
			throw new UnknownException();
		} catch (Exception e) {
			throw new UnknownException();
		}
	}
	
	private static void shareDocument(JsonRpcClient jsonRpcClient) {
		DocumentSummary documentSummary = new DocumentSummary();
		documentSummary.setCreatedAt(System.currentTimeMillis());
		documentSummary.setCreator("dhanrajd");
		documentSummary.setEncryptedKey("Symmetric Key encrypted with dhanraj's Public Key");
		documentSummary.setId("2");
		documentSummary.setName("Document 2");
		
		try {
			PublishToStreamResponse response = PublishToStreamResponse.parse(jsonRpcClient.sendRequest(
															new PublishToStreamRequest("shared_documents", "dhanraj",
																											DocumentHelper.serialize(documentSummary))));
		} catch (JsonRpcException e) {
			throw new UnknownException();
		} catch (Exception e) {
			throw new UnknownException();
		}
	}
	
	private static void getSharedDocuments(JsonRpcClient jsonRpcClient) {
		try {
			RetrieveFromStreamResponse response = RetrieveFromStreamResponse.parse(jsonRpcClient.sendRequest(
															new RetrieveFromStreamRequest("shared_documents",
																											"dhanrajd")));
			
			List<DocumentSummary> documentSummaryList = new ArrayList<>();
			for (RetrieveFromStreamResponse.Result result : response.getResults())
				documentSummaryList.add(DocumentHelper.deserializeDocumentSummary(result.getData()));
			
			System.out.println(documentSummaryList.size());
		} catch (JsonRpcException e) {
			throw new UnknownException();
		} catch (Exception e) {
			throw new UnknownException();
		}
	}
	
}
