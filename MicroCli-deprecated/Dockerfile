FROM oracle/graalvm-ce:20.1.0-java11 as graalvm
RUN gu install native-image

COPY . /home/app/MicroCli
WORKDIR /home/app/MicroCli

RUN native-image --no-server -cp build/libs/MicroCli-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/MicroCli/MicroCli /app/MicroCli
ENTRYPOINT ["/app/MicroCli"]
