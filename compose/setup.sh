#!/bin/sh

JDK_VERSION=21
WORKING_DIR=`pwd`

if ! (java -version 2>&1 | grep -q "build $JDK_VERSION\.\d.\d"); then
    echo "! JDK $JDK_VERSION required !"
    exit 1
fi

set -eo pipefail

echo "---------------------------------------"
echo " *** BUILDING QUARKUS APPLICATIONS *** " 
echo "---------------------------------------"

echo "=> building review-service ..."
cd $WORKING_DIR/../review-service
./mvnw clean package -DskipTests

echo "=> building sentiment-service ..."
cd $WORKING_DIR/../text-sentiment-djl
./mvnw clean package -DskipTests

echo "----------------------------------------------------------"
echo " *** PREPARING CONTAINER IMAGES AND RUN COMPOSE SETUP *** " 
echo "----------------------------------------------------------"

cd $WORKING_DIR

docker compose -f compose.yaml build

docker compose -f compose.yaml up
