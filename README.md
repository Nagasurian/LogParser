# LogParser
Spring-batch based application to parse log file and find IP addresses based on the give threshold

# SQL
---

(1) Write MySQL query to find IPs that mode more than a certain number of requests for a given time period.

    SQL queries are in src/main/resources/spring/job/spring-parser.xml

(2) Write MySQL query to find requests made by a given IP

	select * from parser.log where ip='192.168.162.248';

# Need to following to run
Gradle 2.10 and above
MySQL
Java 1.8 and above

# Database setup
Run the mysql-schema.sql under src/main/resources
Change the database properties in src/main/resources/spring/config/spring-datasource.xml

# Eclipse project setup
Run the create_eclipse_project.bat or type command gradle eclipse

# Build jar
Run the build_jar.bat or type command gradle shadowJar


