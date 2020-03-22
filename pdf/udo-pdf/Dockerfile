FROM maven:3.6.3-jdk-8 as build
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml -DskipTests clean package

FROM openjdk:8  
COPY --from=build /usr/src/app/target/udo-pdf-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/app/udo-pdf.jar
EXPOSE 8080  
ENTRYPOINT ["java","-jar","/usr/app/udo-pdf.jar"]  
