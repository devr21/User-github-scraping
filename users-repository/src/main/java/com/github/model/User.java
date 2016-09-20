package com.github.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -149636550515181252L;
	
	private UUID id;
	private String userGithubId;
	private String url;
	private String avatarUrl;
	private String followersUrl;
	private String reposUrl;
	private String type;
	private String name;
	private String company;
	private String blogUrl;
	private String location;
	private String email;
	private boolean hireable;
	private int publicRepos;
	private int followers;
	private int following;
	private Date createDate;
	private Date updateDate;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public void setId(){
		this.id = UUID.randomUUID();
	}
	public String getuserGithubId() {
		return userGithubId;
	}
	public void setuserGithubId(String userGithubId) {
		this.userGithubId = userGithubId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getFollowersUrl() {
		return followersUrl;
	}
	public void setFollowersUrl(String followersUrl) {
		this.followersUrl = followersUrl;
	}
	public String getReposUrl() {
		return reposUrl;
	}
	public void setReposUrl(String reposUrl) {
		this.reposUrl = reposUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getBlogUrl() {
		return blogUrl;
	}
	public void setBlogUrl(String blogUrl) {
		this.blogUrl = blogUrl;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean getHireable() {
		return hireable;
	}
	public void setHireable(boolean hireable) {
		this.hireable = hireable;
	}
	public int getPublicRepos() {
		return publicRepos;
	}
	public void setPublicRepos(int publicRepos) {
		this.publicRepos = publicRepos;
	}
	public int getFollowers() {
		return followers;
	}
	public void setFollowers(int followers) {
		this.followers = followers;
	}
	public int getFollowing() {
		return following;
	}
	public void setFollowing(int following) {
		this.following = following;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
}
