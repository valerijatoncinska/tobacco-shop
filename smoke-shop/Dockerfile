FROM maven:3.8.1-openjdk-17 as build

WORKDIR smoke-shop/workspace/app

COPY pom.xml .
COPY smoke-shop/src src

RUN mvn -DskipTests=true clean package

RUN mkdir -p smoke-shop/target/dependency && (cd smoke-shop/target/dependency; jar -xf ../*.jar

FROM eclipse-temurin:17-jre-alpine

ARG DEPENDENCY=smoke-shop/workplace/app/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF/ app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java", "-cp", "app:app/lib/*","de.shop.Application"]