FROM openjdk:14-alpine
COPY target/mavenDemo-*.jar mavenDemo.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "mavenDemo.jar"]