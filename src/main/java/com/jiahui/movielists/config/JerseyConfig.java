package com.jiahui.movielists.config;

import java.util.Set;
import java.util.logging.Logger;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;




public class JerseyConfig extends ResourceConfig {
	private static final int MAX_ENTITY_LOG_BYTES = 8 * 1024;
	public JerseyConfig (){
	 register(RequestContextFilter.class);
     packages("com.jiahui.movielists.resources");
     this.register(new LoggingFilter(Logger.getLogger("com.jiahui.movielists"), MAX_ENTITY_LOG_BYTES));
     this.register(MultiPartFeature.class);

	}
	
	
}
