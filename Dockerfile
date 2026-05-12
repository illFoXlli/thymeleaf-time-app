FROM tomcat:9.0

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/thymeleaf-time-app-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080