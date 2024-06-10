# base on your gradle version and java sdk version
FROM gradle:8-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src

# Set your app folder
WORKDIR /app

# Copy everything to docker
COPY . .

# gradle build jar
RUN ./gradlew clean bootJar

# copy it
RUN cp build/libs/*.jar /app.jar

# ENTRYPOINT to run jar
ENTRYPOINT ["java","-jar","/app.jar"]