#### Stage 1: Build the application
FROM eclipse-temurin:17.0.8.1_1-jdk-alpine as jdk17

# Set the current working directory inside the image
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless
# the pom.xml file has changed.
RUN ./mvnw dependency:go-offline -B

# Copy the project source
COPY src src

# Package the application
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

#### Stage 2: A minimal docker image with command to run the app
FROM eclipse-temurin:17.0.8.1_1-jre-alpine

ARG DEPENDENCY=/app/target/dependency

# Copy project dependencies from the build stage
COPY --from=jdk17 ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=jdk17 ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=jdk17 ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","me.thiagorigonatti.tasksspring.TasksSpringApplication"]

EXPOSE 8080
