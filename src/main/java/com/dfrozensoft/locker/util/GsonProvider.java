package com.dfrozensoft.locker.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonProvider {
	private static GsonBuilder gsonBuilder;
	private static Gson gson;

	static {
		gsonBuilder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

		gson = gsonBuilder.create();
	}

	public static Gson get() {
		return gson;
	}

	public static GsonBuilder getGsonBuilder() {
		return gsonBuilder;
	}
}
