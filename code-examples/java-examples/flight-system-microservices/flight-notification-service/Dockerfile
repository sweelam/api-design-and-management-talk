FROM adoptopenjdk/openjdk21:alpine-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8881
ENTRYPOINT ["java","-jar","/application.jar"]
