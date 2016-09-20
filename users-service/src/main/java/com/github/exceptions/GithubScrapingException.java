package com.github.exceptions;

/**
 * 
 * @author Devesh Rajput
 *
 */

public class GithubScrapingException extends RuntimeException{ 
	/**
	 *	Serial Version UID 
	 */
	private static final long serialVersionUID = -797708174092280802L;
	
	public GithubScrapingException(){
		super();
	}
	
	public GithubScrapingException(String exception){
		super(exception);
	}
	
}
