FROM maven:3.8.1-openjdk-17 as build

WORKDIR /workspace/app

COPY pom.xml .
COPY src src

RUN mvn clean package

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar

FROM eclipse-temurin:17-jre-alpine

ARG DEPENDENCY=workplace/app/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF/lib /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/lib

ENTRYPOINT ["java", "-cp", "app:app/lib/*","de.shop.Application"]