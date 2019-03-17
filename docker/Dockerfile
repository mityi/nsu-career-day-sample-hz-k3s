#FROM openjdk:11.0.2-jre-stretch
FROM azul/zulu-openjdk-alpine:11.0.2
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENV HZ_OPTS = "\
--add-modules java.se \
--add-exports java.base/jdk.internal.ref=ALL-UNNAMED \
--add-opens java.base/java.lang=ALL-UNNAMED \
--add-opens java.base/java.nio=ALL-UNNAMED \
--add-opens java.base/sun.nio.ch=ALL-UNNAMED \
--add-opens java.management/sun.management=ALL-UNNAMED \
--add-opens jdk.management/com.sun.management.internal=ALL-UNNAMED"
ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar $HZ_OPTS"]
