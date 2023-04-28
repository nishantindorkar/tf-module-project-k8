FROM ubuntu:latest

RUN mkdir /opt/tomcat/
RUN apt update -y
RUN apt install curl zip -y
RUN curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" && \
    unzip awscliv2.zip && \
    ./aws/install

WORKDIR /opt/tomcat
RUN apt update && apt upgrade -y
RUN apt install openjdk-11-jdk -y
RUN curl -O https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.88/bin/apache-tomcat-8.5.88.tar.gz
RUN tar -xvzf apache*.tar.gz
RUN mv apache-tomcat-8.5.88/* /opt/tomcat/.

WORKDIR /opt/tomcat/webapps
ARG BUILD_ID
RUN aws s3 cp s3://artifact-studentui/student-${BUILD_ID}.war .

WORKDIR /opt/tomcat/lib
RUN curl -O https://s3-us-west-2.amazonaws.com/studentapi-cit/mysql-connector.jar

ARG LINE_NO=21
RUN pwd
RUN ls -la
# COPY ./data.txt /opt/tomcat
# RUN sed -i "${LINE_NO}r /opt/tomcat/data.txt" /opt/tomcat/conf/context.xml

EXPOSE 8080

CMD ["/opt/tomcat/bin/catalina.sh", "run"]