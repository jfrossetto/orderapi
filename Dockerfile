FROM amazoncorretto:21-alpine

RUN mkdir /apps

WORKDIR /apps

ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} ./orderapi.jar
COPY ./build/resources/main/application.properties ./application.properties
COPY ./build/resources/main/log4j2.xml ./log4j2.xml

EXPOSE 8080

CMD java -Dserver.port=8080 \
         -DapplicationProperties=application.properties \
         -DappLogDir=logs \
         -Dreactor.netty.http.server.accessLogEnabled=true \
         -Dlog4j.configurationFile=log4j2.xml \
         -Dfile.encoding=UTF-8 \
         -DinstanceName=OrderApi \
         -Xms128m \
         -Xmx256m \
         -XX:+UseG1GC \
    -jar orderapi.jar

