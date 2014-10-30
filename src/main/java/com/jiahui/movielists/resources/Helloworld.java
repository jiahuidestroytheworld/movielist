package com.jiahui.movielists.resources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;


@Path("/hello")
@Component
public class Helloworld {

	
	@Inject
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String sayHtmlHello() {
    	System.out.println("hello world");
        return "hello";
    }
}