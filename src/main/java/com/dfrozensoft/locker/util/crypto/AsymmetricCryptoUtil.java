package com.dfrozensoft.locker.util.crypto;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;

import org.apache.commons.codec.digest.DigestUtils;

public final class AsymmetricCryptoUtil {
	private static final String ALGORITHM = "RSA";

	public static byte[] encryptWithPublicKey(byte[] publicKey, byte[] message) throws CryptoException {
		try {
			PublicKey key = getPublicKey(publicKey);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(message);
		} catch (Exception e) {
			throw new CryptoException("AsymmetricCrypto: encryption failed");
		}
	}

	public static byte[] decryptWithPrivateKey(byte[] privateKey, byte[] encrypted) throws CryptoException {
		try {
			PrivateKey key = getPrivateKey(privateKey);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(encrypted);
		} catch (Exception e) {
			throw new CryptoException("AsymmetricCrypto: decryption failed");
		}
	}

	public static byte[] sign(byte[] privateKey, byte[] message) throws CryptoException {
		try {
			byte[] sha256Hash = DigestUtils.sha256(message);
			PrivateKey key = getPrivateKey(privateKey);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(sha256Hash);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CryptoException("AsymmetricCrypto: signing failed");
		}
	}

	public static boolean verifySign(byte[] publicKey, byte[] sign, byte[] message) {
		try {
			byte[] sha256Hash = DigestUtils.sha256(message);
			PublicKey key = getPublicKey(publicKey);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decryptedHash = cipher.doFinal(sign);
			return Arrays.equals(sha256Hash, decryptedHash);
		} catch (Exception e) {
			return false;
		}
	}

	private static PublicKey getPublicKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(key));
	}

	private static PrivateKey getPrivateKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(key));
	}

	private AsymmetricCryptoUtil() {
	}
}
