package com.dfrozensoft.locker.model.helper;

import org.apache.commons.codec.binary.Base64;

import com.dfrozensoft.locker.model.User;
import com.dfrozensoft.locker.util.GsonProvider;
import com.dfrozensoft.locker.util.crypto.AsymmetricCryptoUtil;

public final class UserHelper {
	public static boolean validate(User user) {
		return !(user.getUsername() == null || user.getUsername().isEmpty() || user.getName() == null
														|| user.getName().isEmpty() || !isValidEmail(user.getEmail())
														|| user.getPublicKey() == null || user.getPublicKey().isEmpty()
														|| user.getSignature() == null
														|| user.getSignature().isEmpty());
	}

	public static boolean validateSignature(User user) {
		String raw = user.getUsername() + "|" + user.getName() + "|" + user.getEmail();
		byte[] rawBytes = raw.getBytes();
		byte[] signature = Base64.decodeBase64(user.getSignature());
		byte[] publicKey = Base64.decodeBase64(user.getPublicKey());
		return AsymmetricCryptoUtil.verifySign(publicKey, signature, rawBytes);
	}

	public static String serialize(User user) {
		return GsonProvider.get().toJson(user);
	}

	public static User deserialize(String serializedUser) {
		return GsonProvider.get().fromJson(serializedUser, User.class);
	}

	private static boolean isValidEmail(String email) {
		return email != null && !email.isEmpty();
	}

	private UserHelper() {
	}
}
