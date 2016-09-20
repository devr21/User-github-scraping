package com.github.repositories;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class Test {

	
	public static void main(String[] args) {
		Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
		Session session = cluster.connect("github");
		System.out.println(session.execute("select count(*) from user").one().getLong("count"));
		
	}
	
}
