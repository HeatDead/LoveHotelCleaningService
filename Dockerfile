FROM openjdk:17-oracle
VOLUME /LoveHotelCleaningService
EXPOSE 8080
ARG JAR_FILE=target/LoveHotelCleaningService-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} cl-serv.jar
ENTRYPOINT ["java","-jar","/cl-serv.jar"]