# SOEN-487-A3

Web-Application that integrates the QuickChart API and allows a client to create a custom chart. Implements CRUD requests following MVC Pattern and RESTful architecture.Uses Spring security to maintain a secure web service (uses Login/Logout features). Controller allows for Response to return JSON for Postman Requests and HTTP to web browser.

## Created By
Ivan Gerasymenko, Febrian Francione, Kiho Lee

## Built With
* [Java](https://www.java.com/en/) - Backend programming
* [Spring framework](https://spring.io/) - Backend framework (Spring Boot)
* [Spring Security](https://spring.io/projects/spring-security) - Spring Security
* [MySql](https://www.mysql.com/) - Database
* [Maven](https://maven.apache.org/) - Dependency Management
* [Thymeleaf](https://www.thymeleaf.org/) - Front End Templating with HTML and CSS
* [Bootstrap 4](https://getbootstrap.com/) - Responsive Front End CSS framework
* [Font Awesome](https://fontawesome.com/) - Open Source icon set and toolkit

## Installation and Configuration

This maven project is built using Spring Boot. The core framework and additional libraries were generated with Spring initializr.

The additional dependencies added to the pom.xml file are:

* QuickChart
* sendgrid-java
* spring-security-test
* mysql-connector-java

The web service generates charts and uses persistence through MySQL database. In order to connect
to the database and make SQL transactions please use the following configuration settings:

* Hostname: 127.0.0.1
* Port: 3306
* Username: root
* Password: 1234

The Database Schema to create the database and the tables is provided in the file: src/main/java/com/quickChart/persistence/database.sql

You only need to execute the SQL statements in any Database Management system and it will generate the Database.

The Web Service runs on the local port 8080.

To start the service compile and execute the class: QuickChartApplication.java
Once the Spring Boot loads all the executables and launches the Tomcat Server container you can access the main page of the application with the url: http://localhost:8080/

This will bring you to the login authentication page, where the user needs to provide his credentials to use the services.

The database already comes pre-populated with a demo user, to login please use the following credentials:

* Username: feb
* Password: 1234

## Documentation


## License 
This project is licensed under the GNU General Public License v3.0 - see the [LICENSE.md](LICENSE) file for details
