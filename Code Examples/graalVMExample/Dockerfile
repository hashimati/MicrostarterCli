FROM oracle/graalvm-ce:20.1.0-java11 as graalvm
RUN gu install native-image

COPY . /home/app/graalvmExample
WORKDIR /home/app/graalvmExample

RUN native-image --no-server -cp build/libs/graalvmExample-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/graalvmExample/graalvmExample /app/graalvmExample
ENTRYPOINT ["/app/graalvmExample"]
