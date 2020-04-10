FROM maven:3.6.3-jdk-14 as build
COPY src /usr/src/app/src  
COPY \
	package.json \
	package-lock.json \
	pom.xml \
	src \
	webpack.config.js \
	webpack.generated.js \
	/usr/src/app/  
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:14
RUN yum install -y jq
COPY --from=build \
	/usr/src/app/target/udo-editor.jar \
	/usr/src/app/main/resources/application-docker.properties \
	/usr/src/app/main/resources/static/stepconfig.json \
	/usr/app/
COPY health-check.sh /usr/app/
WORKDIR /usr/app
RUN mkdir log

HEALTHCHECK \
	--interval=15s \
	--timeout=15s \
	--start-period=30s \
	CMD /usr/app/health-check.sh
EXPOSE 8080  
ENTRYPOINT ["java","-jar","/usr/app/udo-editor.jar", "--spring.config.location=/usr/app/application-docker.properties"]  
