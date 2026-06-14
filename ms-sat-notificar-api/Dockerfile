FROM eclipse-temurin:25-jdk

RUN apt-get update && \
    apt-get install -y tzdata fonts-dejavu-core && \
    rm -rf /var/lib/apt/lists/*

ENV TZ='America/Lima'
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 9030

ADD /target/ms-sat-web-api.jar ms-sat-web-api.jar
ENTRYPOINT ["java","-jar","ms-sat-web-api.jar"]
