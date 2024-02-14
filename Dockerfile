FROM openjdk:17-oracle
WORKDIR /usr/src/app
COPY front/build/libs/front-0.0.1-SNAPSHOT.jar .
COPY front/src/main/resources/application.properties .
RUN sed -i 's/localhost/host.docker.internal/g' application.properties
CMD ["java", "-jar", "front-0.0.1-SNAPSHOT.jar"]
EXPOSE 8020