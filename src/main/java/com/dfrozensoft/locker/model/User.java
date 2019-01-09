package com.dfrozensoft.locker.model;

import java.util.HashMap;
import java.util.Map;

public final class User {
	private String username;
	private String name;
	private String email;
	private Map<String, String> metadata;
	private String publicKey;
	private String signature;
	private long createdAt;

	public User() {
		metadata = new HashMap<>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public void addMetadata(String key, String value) {
		metadata.put(key, value);
	}

	public String getMetadata(String key) {
		return metadata.get(key);
	}
}
