package com.github.users;

import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.EncodingFilter;

public class Application extends ResourceConfig{

	
	@SuppressWarnings("unchecked")
	public Application(){
		
		packages("com.github.users");
		EncodingFilter.enableFor(this, GZipEncoder.class);
	}
	
	
}
