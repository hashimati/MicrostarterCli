FROM openjdk:14-alpine
COPY build/libs/rabbitmq-*-all.jar rabbitmq.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "rabbitmq.jar"]