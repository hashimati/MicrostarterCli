FROM openjdk:14-alpine
COPY build/libs/io.hashimati.micronautsecurityjwt-*-all.jar MicronautSecurityJwt.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "MicronautSecurityJwt.jar"]