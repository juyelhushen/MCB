FROM openjdk:11
WORKDIR /app
ARG JAR_FILE=target/mcb.jar
COPY ${JAR_FILE} mcb.jar
#ENTRYPOINT
CMD ["java","-jar","-Xmx1024M","/app/mcb.jar"]
EXPOSE 8080