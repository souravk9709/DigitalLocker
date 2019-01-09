package com.dfrozensoft.locker.util.crypto;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;

public final class SymmetricCryptoUtil {
	private static final String CHARSET = "UTF-8";
	private static final String KEY_SPEC = "AES";
	private static final String ALGORITHM = "AES/CBC/PKCS5PADDING";
	private static final int KEY_LEN = 16;
	private static final int IV_LEN = 16;

	public static byte[] encrypt(String secret, byte[] message) throws CryptoException {
		try {
			IvParameterSpec iv = new IvParameterSpec(getIv(secret));
			SecretKeySpec skeySpec = new SecretKeySpec(getKey(secret), KEY_SPEC);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			return cipher.doFinal(message);
		} catch (Exception e) {
			throw new CryptoException("SymmetricCrypto: encryption failed");
		}
	}

	public static byte[] decrypt(String secret, byte[] encrypted) throws CryptoException {
		try {
			IvParameterSpec iv = new IvParameterSpec(getIv(secret));
			SecretKeySpec skeySpec = new SecretKeySpec(getKey(secret), KEY_SPEC);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			return cipher.doFinal(encrypted);
		} catch (Exception e) {
			throw new CryptoException("SymmetricCrypto: decryption failed");
		}
	}

	private static byte[] getKey(String secret) throws UnsupportedEncodingException {
		return Arrays.copyOf(DigestUtils.sha256Hex(secret).getBytes(CHARSET), KEY_LEN);
	}

	private static byte[] getIv(String secret) throws UnsupportedEncodingException {
		return Arrays.copyOfRange(DigestUtils.sha256Hex(secret).getBytes(CHARSET), KEY_LEN, KEY_LEN + IV_LEN);
	}

	private SymmetricCryptoUtil() {
	}
}
