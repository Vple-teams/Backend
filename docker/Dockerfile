FROM openjdk:11-jdk-slim

ARG JAR_FILE=build/libs/vple-0.0.1-SNAPSHOT.jar

RUN mkdir -p /opt/apps
WORKDIR /opt/apps

COPY ${JAR_FILE} /opt/apps/app.jar
COPY --chmod=0777 docker/entrypoint.sh /opt/apps/entrypoint.sh

ENTRYPOINT ["/opt/apps/entrypoint.sh"]


