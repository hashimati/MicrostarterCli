FROM openjdk:14-alpine
COPY build/libs/TestingWithSpock-*-all.jar TestingWithSpock.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "TestingWithSpock.jar"]