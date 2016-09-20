package com.github.interfaces;

import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

@Configuration
@PropertySource(value={"classpath:database.properties"})
public abstract class CassandraConnector implements UserRepository{

	@Inject
	private Environment env;
	
	private String host ;
	private String keyspace;
	private Cluster cluster;
	protected Session session;
	private Logger logger = Logger.getLogger(CassandraConnector.class);
	
	public void init(){
		logger.info("Initializing host, keyspace values");
		host = env.getProperty("cassandraHost");
		keyspace = env.getProperty("keyspace");
		cluster= Cluster.builder().addContactPoint(host).build();
		session = cluster.connect(keyspace);
	}
	
	public  Session connect(){
		//logger.info(env.getProperty("cassandraHost").toUpperCase());
		
			init();
		
		return session;
	}
	
	public void destroy(){
		session.close();
		cluster.close();
	}
	
	public Session getSession(){
		return session==null?connect():session;
	}
}