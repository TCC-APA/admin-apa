FROM tomcat:8.0.20-jre8

USER root

COPY ./conf/tomcat-users.xml /usr/local/tomcat/conf/

COPY ./conf/settings.xml /usr/local/tomcat/conf/

COPY ./target/apa.war /usr/local/tomcat/webapps/apa.war
