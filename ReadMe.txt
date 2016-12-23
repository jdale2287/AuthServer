John Daley's Authentication Server:
For this technical assignment, I chose to implement the Authentication Server.
I have had some experience working with web applications that protect secured resources, however I thought that this would be a good opportunity to learn more about JWT (JSON Web Tokens). 
I also thought this project would present a unique problem that I have not solved in the past. 

System Requirements:
Windows Desktop operating system (XP or later)
Java JRE 1.8 or later
Internet Explorer 11 or HTML compliant web browser

Usage:
Locate and download the "authentication-0.0.1-SNAPSHOT.jar" file.
Open a command prompt and change directory to the location where you downloaded the file in the previous step (i.e. C:\User\Desktop).
Type the following command into the command prompt:
java -jar authentication-0.0.1-SNAPSHOT.jar

Note: Alternatively, you can try double clicking on the downloaded "authentication-0.0.1-SNAPSHOT.jar" file. It should locate your Java installation and run the application.

After launching the application, open your web browser and browse to one of these two addresses "http://localhost:8080" or "https://localhost:8443"
You will be presented with the welcome page of the web application.
After reading the introduction, click on the "Click Here" link.
You will be presented with a form that requires a user-name and password.
Enter "admin" into the username text box.
Enter "admin" into the password text box.
Click the "Sign In" button.
After authenticating, you will be presented with a protected page that contains three buttons.
Click the "Generate JWT" button to generate a new JWT (JSON Web Token). The JWT will be displayed in a text box.
Click the "Validate JWT" button to validate the JWT. A message will appear on the screen indicating the validation status. After one minute, the JWT will expire and on the next button click the message will reflect the new validation status.
Click the "Sign Out" button to destroy the authenticated session on the server and return to the login screen.   
You can shutdown the application by Typing Ctrl-C in the command prompt or by opening task manager and stopping the "Java Platform (SE) binary process"
Design and Approach:
For this application I decided use Java and open source frameworks / libraries to perform most of the heavy lifting.
Spring Boot provided me with a great starting point to launch and embedded Tomcat HTTP server and handle HTTP/S requests
Spring Security allowed me to set up user authentication, create a session on the server and validate the session using a browser cookie. This ensured that protected resources were served to authorized requests without requiring re-authentication.
Spring MVC allowed me to implement the Model View controller design pattern and persist JWT values in the HTTP session and display them to the user.
The JJWT library allowed me generate and parse JWTs.

I chose to forward all HTTP requests on port 8080 to HTTPS on port 8443 for added security. This will ensure that the username and password on the login screen will be passed to the server with with encryption.
The JWTs are generated using a signing key and the authenticated user's user-name. 
For demonstration purposes I added the generated JWT to the server session model so that it could be displayed on screen. However, the JWT also gets added an HTTP header named "JSON-WEB-TOKEN" after clicking the "Generate JWT" button or executing an authorized HTTP GET to "/token/generate"
Also for demo purposes, the JWT can only be validated with an existing authenticated session, because the username was used tas one of the JWT claims.   
This application uses in-memory user credential storage and only contains a single set of user-name/password. 

License Info / Credits:

Spring Boot
1.4.2.RELEASE
Provided all required Spring libraries and minimized configuration complexities. Also, allowed me to build the source code into a stand-alone application for ease of deployment. 
Apache License Version 2.0, January 2004
http://projects.spring.io/spring-boot/

JJWT (JSON Web Token for Java and Android)
0.6.0
Provided capability to generate and parse JSON Web tokens
Apache License Version 2.0, January 2004
https://github.com/jwtk/jjwt



