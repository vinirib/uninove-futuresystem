FROM maven:3.9-amazoncorretto-8
# Use the maven image to build the application
FROM maven:3.9-amazoncorretto-8 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project file
COPY . /app

# Build the application
RUN mvn clean package

# Use the adoptopenjdk image as the base image
FROM amazoncorretto:8-alpine-jdk AS production

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/controle-de-escolares.jar app.jar

# Expose the port on which the Spring Boot application will run (if needed)
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]