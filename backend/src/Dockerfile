# Use a base image with Java installed
FROM adoptopenjdk:11-jdk-hotspot

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from the target directory to the container
COPY target/mcb.jar .

# Expose the port on which the Spring Boot application listens
EXPOSE 8080

# Define the command to run when the container starts
CMD ["java", "-jar", "mcb.jar"]
