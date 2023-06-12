FROM openjdk:11
WORKDIR /app
ARG JAR_FILE=target/limit-login.jar
COPY ${JAR_FILE} limit-login.jar
#ENTRYPOINT
CMD ["java","-jar","-Xmx1024M","/app/limit-login.jar"]
EXPOSE 1000