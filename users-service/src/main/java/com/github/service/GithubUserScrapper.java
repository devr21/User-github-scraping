package com.github.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import com.github.exceptions.GithubScrapingException;
import com.github.interfaces.UserRepository;
import com.github.model.User;
/**
 * This is a Runnable class that fetches user data from github. 
 * It has scheduled thread pool executor which starts the process on its own
 * @author Devesh Rajput
 *
 */
@Named
@Configuration
@PropertySource(value={"classpath:scrap.properties"})
public class GithubUserScrapper implements Runnable{

	@Inject
	private UserRepository repository ;
	
	@Inject
	private Environment env;
	
	private static final String GITHUB_USERS_URL = "https://api.github.com/users";
	private static long SCRAP_COUNT; 
	private String url;
	private long userCount;
	private static String username;
	private static String password;
	private static final Logger logger = Logger.getLogger(GithubUserScrapper.class);
	private ScheduledThreadPoolExecutor sctpe;
	private long frequencyInMillis = TimeUnit.MINUTES.toMillis(60);
	private boolean schedulerShouldRun = true;
	private AtomicBoolean isCurrentlyRunning = new AtomicBoolean(false);
	private long maxUserCount;
	
	/**
	 * init method which is called when spring is finished loading.
	 *  It initializes all the required variables by getting values from scrap.properties file in classpath
	 */
	@PostConstruct
	public void init(){
		username = env.getProperty("Authentication.username");
		password = env.getProperty("Authentication.password");
		SCRAP_COUNT = Long.parseLong(env.getProperty("scrapCount"));
		maxUserCount = Long.parseLong(env.getProperty("maxUserCount"));
		userCount = repository.getUserCount();
		startScheduler();
	}
	
	/**
	 * This is the method which creates an instance of the ScheduledThreadPoolExecutor and
	 * the rate at which the execution will be started.
	 */
	private void startScheduler() {
		sctpe = new ScheduledThreadPoolExecutor(1);
		long initialDelay = TimeUnit.MINUTES.toMillis(1);
		getSctpe().scheduleAtFixedRate(this, initialDelay, frequencyInMillis/12, TimeUnit.MILLISECONDS);
	}
	/**
	 * 
	 * @return ScheduledThreadPoolExecutor
	 */
	private ScheduledThreadPoolExecutor getSctpe(){
		return sctpe;
	}
	
	/**
	 * This method is called from UserResource to initialize the scraping process manually.
	 */
	public void startScraping(){
		if(!isCurrentlyRunning.get()){
			if(schedulerShouldRun)
				getSctpe().execute(this);
		}
		else
			throw new RuntimeException("Scraping Already Running");
	}
	
	/**
	 * Fetches data from Github by first fetching a list of users.
	 * Then, it parses the data received and fetches data for every user received in the list.
	 */
	private void fetchData(){
		prepareUrl();
		String inputLine = callUrl(url);
		JSONArray usersList = new JSONArray(inputLine);
		for(int i=0;i<usersList.length();i++){
			JSONObject object = usersList.getJSONObject(i);
			logger.info("fetching data of github user: "+object.getString("login"));
			String jsonData = callUrl(prepareUrl(object.get("login").toString()));
			User user = mapUserFromJson(jsonData);
			repository.createUser(user);
		}
	}
	
	/**
	 * This method is called automatically by scheduler.
	 */
	public void run(){
		logger.info("isCurrentlyRunning ["+isCurrentlyRunning.get()+"]");
		logger.info("processed user Count: "+userCount);
		if(schedulerShouldRun && isCurrentlyRunning.compareAndSet(false, true)){
			logger.info(" was not running so execution will proceed");
			try{
				fetchData();
			}
			catch(Throwable ex){
				logger.error("Error proccessing Users from github ",ex);
			}finally{
				isCurrentlyRunning.set(false);
				logger.info("setting isCurrentlyRunning to false");
			}
		}
		else{
			logger.info("was running so execution will not proceed");
		}
		userCount = repository.getUserCount();
		logger.info("processed user Count: "+userCount);
		if(userCount >= maxUserCount){
			schedulerShouldRun = false;
			logger.info("Completed scraping process with maxUserCount: "+maxUserCount);
		}	
	}
	
	/**
	 * Parses json data and creates the User object from it.
	 * @param String json
	 * @return User
	 */
	@SuppressWarnings("null")
	private User mapUserFromJson(String json) {
		User user = null;
		if(json != null || !json.isEmpty()){
			JSONObject object = new JSONObject(json);
			user = new User();
			user.setId();
			user.setuserGithubId(object.getString("login"));
			user.setAvatarUrl(object.getString("avatar_url"));
			if(!object.isNull("blog"))
			user.setBlogUrl(object.getString("blog"));
			if(object.isNull("company"))
			user.setCompany(object.get("company").toString());
			user.setCreateDate(new Date());
			
			if(!object.isNull("email"))
				user.setEmail(object.get("email").toString());
			
			user.setFollowers(object.getInt("followers"));
			user.setFollowersUrl(object.getString("followers_url"));
			user.setFollowing(object.getInt("following"));
			
			if(!object.isNull("hireable"))
				user.setHireable(object.getBoolean("hireable"));
			
			if(!object.isNull("location"))
				user.setLocation(object.get("location").toString());
			if(!object.isNull("name"))
			user.setName(object.getString("name"));
			user.setPublicRepos(object.getInt("public_repos"));
			user.setReposUrl(object.getString("repos_url"));
			user.setType(object.getString("type"));
			user.setUrl(object.getString("url"));
		}
		
		return user;
	}
	
	/**
	 * Calls the url passed. Uses the username and password of original Github user for authentication and returns the data received.
	 * @param String url
	 * @return String 
	 */
	private String callUrl(String url){
		URL callUrl;
		try {
			String authString = username + ":" + password;
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEncoded = new String(authEncBytes);
			callUrl = new URL(url);
			HttpsURLConnection connection = (HttpsURLConnection) callUrl.openConnection();
			connection.setRequestProperty("Authorization", "Basic " + authStringEncoded);
			connection.setRequestMethod("GET");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String data = reader.readLine();
			connection.disconnect();
			return data;
		} catch (MalformedURLException e) {
			throw new GithubScrapingException("Could not create URL for "+url);
		} catch (IOException e) {
			if(e instanceof ProtocolException)
				throw new GithubScrapingException("Unable to open Connection to the Url : "+url);
			else
				throw new GithubScrapingException("Unable to read input Stream");
		}
	}
	/**
	 * main method for testing purposes.
	 * @param args
	 */
	public static void main(String[] args){
		new GithubUserScrapper().fetchData();
	}
	
	private void prepareUrl(){
		url = GITHUB_USERS_URL+"?per_page="+SCRAP_COUNT+"&since="+userCount;
	}
	
	/**
	 * Prepares url to be used to fetch data for a particular user. Takes Github user Id in as parameter.
	 * @param String userGithubId
	 * @return String
	 */
	private String prepareUrl(String userGithubId){
		String userUrl = GITHUB_USERS_URL+"/"+userGithubId;
		return userUrl;
	}
	
	@PreDestroy
	public void destroy(){
		if(schedulerShouldRun){
			getSctpe().shutdown();
		}
	}
	
}
