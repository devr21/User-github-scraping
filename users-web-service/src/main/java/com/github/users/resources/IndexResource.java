package com.github.users.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.github.service.interfaces.TestService;
/**
 * This is an Index Resource 
 * @author Devesh Rajput
 *
 */
@Component
@Path("/index")
public class IndexResource {

	@Inject
	private TestService service;
	
	/**
	 * URL : localhost:8080/user-service/index
	 * @return Response
	 */
	@GET
	@Path("/")
	public Response getText(){
		return Response.ok().entity(service.getText()).build();
	}
	
}
