package com.github.users.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import com.github.service.GithubUserScrapper;
import org.springframework.stereotype.Component;

/**
 * This is User Resource providing an api to scrape manually.
 * 
 * @author Devesh Rajput
 *
 */

@Component
@Path("/github")
public class UserResource {

	@Inject
	private GithubUserScrapper scrapper;
	
	/**
	 * URL : localhost:8080/user-service/github/scrap
	 * This is a get request handler which called upon starts the scraping process of Github Users
	 * @return Response
	 */
	
	@Path("/scrap")
	@GET
	public Response startScraping(){
		
		scrapper.startScraping();
		return Response.ok().build(); 
	}
	
}
