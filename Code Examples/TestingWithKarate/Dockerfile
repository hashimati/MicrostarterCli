FROM openjdk:14-alpine
COPY build/libs/testingwithKarate-*-all.jar testingwithKarate.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "testingwithKarate.jar"]