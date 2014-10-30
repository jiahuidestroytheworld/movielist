package com.jiahui.movielists;

import java.util.Set;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;

import org.glassfish.jersey.media.multipart.MultiPartFeature;



import com.jiahui.movielists.resources.*;
import com.jiahui.movielists.config.*;

@EnableAutoConfiguration
@ComponentScan
public class MovieService {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		new SpringApplicationBuilder(MovieService.class)
        .showBanner(false)
        .run(args);
	}

	 @Bean
	    public ServletRegistrationBean jerseyServlet() {
	        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/*");
	        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
	        return registration;
	    }

}
