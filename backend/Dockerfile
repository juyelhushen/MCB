FROM openjdk:11
COPY target/mcb.jar /opt/mcb/lib/mcb.jar
EXPOSE 8080
#ENTRYPOINT
CMD ["java","-jar","-Xmx1024M","/target/mcb.jar"]
