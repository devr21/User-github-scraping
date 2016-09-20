package com.github.service;

import javax.inject.Named;
import com.github.service.interfaces.TestService;
/**
 * Test Class to understand Named annotation usage which binds this bean to wherever TestService reference is.
 * @author Devesh Rajput
 *
 */
@Named
public class Test implements TestService{

	public String getText() {
		
		return "Index Page";
	}

	
	
}
