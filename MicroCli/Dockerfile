FROM openjdk:14-alpine
COPY build/libs/MicroCli-*-all.jar MicroCli.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "MicroCli.jar"]