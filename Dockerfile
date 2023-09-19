FROM openjdk:17
LABEL maintainer ="jwtService"
ADD target/JWT-token-management-0.0.1-SNAPSHOT.jar jwtService.jar
ENTRYPOINT [ "java","-jar","jwtService.jar" ]