Github User Scraping Application 
===
Introduction
===
This application uses github's user api https://api.github.com/users to scrape users from Github and stores the result in Cassandra Database.
The application uses scheduler which starts scraping periodically. It uses RESTful web services to start the scraping process manually.

Technology Stack
===
The following frameworks have been used in this application 
<table border="1">
  <tr>
    <th>Name</th>
    <th>Version</th> 
  </tr>
  <tr>
    <td>Java</td>
    <td>1.8</td> 
  </tr>
  <tr>
    <td>Jersey</td>
    <td>2.14</td> 
  </tr>
  <tr>
    <td>Spring</td>
    <td>4.1.4.RELEASE</td> 
  </tr>
  <tr>
  	<td>Cassandra</td>
  	<td>3.7.0</td>
  </tr>
</table>

What does it do?
===
#### 1) Jersey Api
It provides a GET api which starts the scraping of users manually if it is not running.

#### 2) Cassandra Database
It uses Cassandra as database and Datastax cassandra for executing queries by establishing cluster and session to Cassandra server.

#### 3) Maven multi module project

I have used multi module structure which gives an outline of better project structure.

Build and Installation
===
Go to command prompt , go to the location of the project directory **users** and type the command given below

	mvn clean package
	or
	mvn clean install
	
If the build is successful, go to location **users/users-war/target** where the war file will be i.e. **users-service.war**

Now deploy the directory in a web container. I have used Apache Tomcat version 9  

Configuration
===
Change the values of **scrap.properties** and **database.properties** files in **users/users-service/src/main/resources** and **users/users-repository/src/main/resources** directories respectively according to your need. 

#####Cassandra create table user

	create table user(user_github_id varchar primary key,avatar_url text,blog_url text,company varchar,create_date timestamp,email varchar,followers int,followers_url text,following int,hireable boolean,id UUID,location varchar,name varchar,public_repos int,repos_url text,type varchar,update_date timestamp,url text);
	
Run Demo
===
Once you have successfully built the project, deploy the war file in a web server and start the server.

#####Basic project url
	
		http://localhost:8080/user-service/
		
######User Scrape url

	http://localhost:8080/user-service/github/scrap 