FROM openjdk:17
COPY target/Wallet-Service-1.0-SNAPSHOT.jar /Wallet-service.jar
CMD ["java","-jar","Wallet-service.jar"]