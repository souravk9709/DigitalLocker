package com.dfrozensoft.locker.endpoints;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dfrozensoft.locker.blockchain.api.UserApi;
import com.dfrozensoft.locker.model.User;
import com.dfrozensoft.locker.model.helper.UserHelper;
import com.dfrozensoft.locker.util.GsonProvider;
import com.google.gson.reflect.TypeToken;

@Path("user")
public class UserEndpoint extends AbstractEndpoint {
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public void getUser(@PathParam("username") String username, @Suspended AsyncResponse asyncResponse) {
		workerPool.execute(() -> {

			if (username == null || username.isEmpty()) {
				asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
				return;
			}

			User user = blockchainApiManager.getUserApi().get(username);
			if (user != null)
				asyncResponse.resume(buildSuccessJsonResponse(user));
			else
				asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build());
		});
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void createUser(String inputJson, @Suspended AsyncResponse asyncResponse) {
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

			User user = new User();
			for (Map.Entry<String, String> in : input.entrySet()) {
				String key = in.getKey();
				String value = in.getValue();

				switch (key) {
					case "username":
						user.setUsername(value);
						break;
					case "name":
						user.setName(value);
						break;
					case "email":
						user.setEmail(value);
						break;
					case "public_key":
						user.setPublicKey(value);
						break;
					case "signature":
						user.setSignature(value);
						break;
					default:
						user.addMetadata(key, value);
						break;
				}
			}

			UserApi userApi = blockchainApiManager.getUserApi();
			if (!UserHelper.validate(user)) {
				asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
				return;
			}

			if (!UserHelper.validateSignature(user)) {
				asyncResponse.resume(Response.status(Response.Status.UNAUTHORIZED).build());
				return;
			}

			if (userApi.userExists(user.getUsername())) {
				asyncResponse.resume(Response.status(Response.Status.CONFLICT).build());
				return;
			}

			user.setCreatedAt(Instant.now().getEpochSecond());
			userApi.create(user);
			asyncResponse.resume(buildEmptySuccessResponse());
		});
	}
}
