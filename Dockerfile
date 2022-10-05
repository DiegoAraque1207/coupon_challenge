FROM        maven:3.6.3-openjdk-15 as compiler

WORKDIR     /root/coupon/
COPY  pom.xml .
RUN   mvn dependency:go-offline

COPY  src/ /root/coupon/src/
RUN   mvn clean package -DskipTests

FROM        openjdk:15-jdk-oracle
RUN         mkdir /root/app
COPY        --from=compiler /root/coupon/target/couponChallenge-0.0.1-SNAPSHOT.jar /root/app/couponChallenge-0.0.1-SNAPSHOT.jar
RUN         ln -snf /usr/share/zoneinfo/America/Bogota /etc/localtime &&  echo America/Bogota > /etc/timezone
ENTRYPOINT  ["java", "-jar", "/root/app/couponChallenge-0.0.1-SNAPSHOT.jar"]
EXPOSE      8080
