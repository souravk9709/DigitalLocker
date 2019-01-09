package com.dfrozensoft.locker.endpoints;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dfrozensoft.locker.blockchain.api.DocumentApi;
import com.dfrozensoft.locker.blockchain.api.ShareApi;
import com.dfrozensoft.locker.blockchain.api.UserApi;
import com.dfrozensoft.locker.model.Document;
import com.dfrozensoft.locker.model.DocumentSummary;
import com.dfrozensoft.locker.model.User;
import com.dfrozensoft.locker.model.helper.DocumentHelper;
import com.dfrozensoft.locker.util.GsonProvider;
import com.google.gson.reflect.TypeToken;

@Path("share")
public class ShareEndpoint extends AbstractEndpoint {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void shareDocument(String inputJson, @Suspended AsyncResponse asyncResponse) {
		workerPool.execute(() -> {

			Map<String, String> input = null;
			try {
				Type type = new TypeToken<HashMap<String, String>>() {
				}.getType();
				input = GsonProvider.get().fromJson(inputJson, type);
				if (input == null)
					throw new NullPointerException();
			} catch (Exception e) {
				asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
				return;
			}

			String to = input.get("to");
			String documentId = input.get("document_id");
			String encryptedKey = input.get("encrypted_key");
			String signature = input.get("signature");

			if (to == null || to.isEmpty() || documentId == null || documentId.isEmpty() || encryptedKey == null
															|| encryptedKey.isEmpty()) {
				asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
				return;
			}

			UserApi userApi = blockchainApiManager.getUserApi();
			DocumentApi documentApi = blockchainApiManager.getDocumentApi();
			ShareApi shareApi = blockchainApiManager.getShareApi();

			Document document = documentApi.get(documentId);
			if (document == null) {
				asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build());
				return;
			}

			DocumentSummary share = DocumentHelper.summarize(document);
			share.setEncryptedKey(encryptedKey);

			User creator = userApi.get(document.getCreator());

			if (!DocumentHelper.validateShareSignature(creator, to, share, signature)) {
				asyncResponse.resume(Response.status(Response.Status.UNAUTHORIZED).build());
				return;
			}

			share.setCreatedAt(Instant.now().getEpochSecond());
			shareApi.share(to, share);
			asyncResponse.resume(buildEmptySuccessResponse());

		});
	}
}
