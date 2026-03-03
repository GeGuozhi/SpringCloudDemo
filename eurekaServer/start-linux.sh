#!/bin/bash
echo "启动Eureka Server - Linux环境"
echo "服务器IP: 192.168.157.129"
echo "访问地址: http://192.168.157.129:8761"
java -jar -Dspring.profiles.active=linux eurekaServer-1.0-SNAPSHOT.jar
