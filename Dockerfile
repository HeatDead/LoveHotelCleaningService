#
# Build stage
#
FROM maven:3.8.4-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true

#
# Package stage
#
FROM openjdk:17-oracle
COPY --from=build /home/app/target/LoveHotelCleaningService-0.0.1-SNAPSHOT.jar /usr/local/lib/cl-serv.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/cl-serv.jar"]