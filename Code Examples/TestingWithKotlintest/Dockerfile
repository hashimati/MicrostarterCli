FROM openjdk:14-alpine
COPY build/libs/TestingWithKotlinTest-*-all.jar TestingWithKotlinTest.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "TestingWithKotlinTest.jar"]