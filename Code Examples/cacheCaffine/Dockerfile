FROM openjdk:14-alpine
COPY build/libs/cacheCaffine-*-all.jar cacheCaffine.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "cacheCaffine.jar"]