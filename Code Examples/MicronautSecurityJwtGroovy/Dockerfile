FROM openjdk:14-alpine
COPY build/libs/io.hashimati.micronautsecurityjwtgroovy-*-all.jar MicronautSecurityJwtGroovy.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "MicronautSecurityJwtGroovy.jar"]