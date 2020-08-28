FROM openjdk:14-alpine
COPY build/libs/javawithKotlintest-*-all.jar javawithKotlintest.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "javawithKotlintest.jar"]