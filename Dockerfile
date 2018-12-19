FROM openjdk:8-jdk-alpine
ADD /target/bookStore-*.jar /
RUN echo "networkaddress.cache.ttl=0" >> /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/java.security

EXPOSE 8080
ENTRYPOINT java -Dspring.profiles.active=${SPA} -Dnetworkaddress.cache.ttl=0 -jar bookStore-*.jar
