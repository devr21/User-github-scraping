package com.github.repositories;

import java.util.UUID;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.github.interfaces.CassandraConnector;
import com.github.model.User;

/**
 * Cassandra Implementation of User Repository
 * @author Devesh Rajput
 *
 */
public class UserCassandraRepository extends CassandraConnector{

	private static final String INSERT_STATEMENT = "insert into user(id,avatar_url,blog_url,company,create_date,email,"
			+ "followers,followers_url,following,hireable,location,name,public_repos,repos_url,type,update_date,"
			+ "url,user_github_id) values(:id,:avatarUrl,:blogUrl,:company,:createDate,:email,:followers,:followersUrl,"
			+ ":following,:hireable,:location,:name,:publicRepos,:reposUrl,:type,:updateDate,:url,:userGithubId)";	
	
	/**
	 * Executes the query Select count(*) from user to return the number of users in cassandra.
	 * @return long
	 */
	public long getUserCount(){
		long userCount = getSession().execute("select count(*) from user").one().getLong("count");
		return userCount;
	}
	
	/**
	 * Inserts the User data in user table in cassandra.
	 * @param User 
	 */
	public void createUser(User user) {
		PreparedStatement stmt = getSession().prepare(INSERT_STATEMENT);
		BoundStatement boundStmt = prepareStatement(stmt, user);
		session.execute(boundStmt);
	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}

	public void removeUserById(UUID id) {
		// TODO Auto-generated method stub
		
	}

	public void removeUserId(String userGithubId) {
		// TODO Auto-generated method stub
		
	}

	public User getUserById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Prepares the bound statement 
	 * @param PreparedStatement stmt
	 * @param user
	 * @return BoundStatement
	 */
	private BoundStatement prepareStatement(PreparedStatement stmt,User user){
		return new BoundStatement(stmt).setUUID("id",user.getId()).
				setString("avatarUrl",user.getAvatarUrl()).
				setString("blogUrl",user.getBlogUrl()).
				setString("company",user.getCompany()).
				setTimestamp("createDate",user.getCreateDate()).
				setString("email",user.getEmail()).
				setInt("followers",user.getFollowers()).
				setString("followersUrl",user.getFollowersUrl()).
				setInt("following",user.getFollowing()).
				setBool("hireable",user.getHireable()).
				setString("location",user.getLocation()).
				setString("name",user.getName()).
				setInt("publicRepos",user.getPublicRepos()).
				setString("reposUrl",user.getReposUrl()).
				setString("type",user.getType()).
				setTimestamp("updateDate",user.getUpdateDate()).
				setString("url",user.getUrl()).
				setString("userGithubId",user.getuserGithubId());
	}
	
	public void closeSession(){
		session.close();
		
	}

}
