package com.dfrozensoft.locker.common;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.dfrozensoft.locker.endpoints.DocumentEndpoint;
import com.dfrozensoft.locker.endpoints.HealthEndpoint;
import com.dfrozensoft.locker.endpoints.ShareEndpoint;
import com.dfrozensoft.locker.endpoints.UserEndpoint;
import com.dfrozensoft.locker.logging.RequestLogger;
import com.dfrozensoft.locker.logging.ResponseLogger;

@ApplicationPath(Constants.BASE_URI)
public class ProtoApplication extends Application {
	public ProtoApplication() {
	}

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new HashSet<>();

		/* Logger */
		resources.add(RequestLogger.class);
		resources.add(ResponseLogger.class);

		/* Endpoints */
		resources.add(HealthEndpoint.class);
		resources.add(UserEndpoint.class);
		resources.add(DocumentEndpoint.class);
		resources.add(ShareEndpoint.class);

		/* Error Handler */
		resources.add(DefaultExceptionHandler.class);

		return resources;
	}
}