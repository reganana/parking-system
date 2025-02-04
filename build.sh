#!/bin/bash

cd ./infrastructure/service_registry || exit
mvn clean install -DskipTests
docker build -t service_registry .

cd ../../service/gate || exit
mvn clean install
docker build -t gate .

cd ../permit || exit
mvn clean package
docker build -t permit .

cd ../payment || exit
mvn clean install
docker build -t payment .

cd ../violation || exit
mvn clean package
docker build -t violation .

cd ../visitor_access || exit
mvn clean package
docker build -t visitor_access .