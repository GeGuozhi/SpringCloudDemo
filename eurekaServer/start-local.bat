@echo off
echo 启动Eureka Server - Local环境
java -jar -Dspring.profiles.active=local target/eurekaServer-1.0-SNAPSHOT.jar
pause
