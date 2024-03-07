#!/bin/bash

echo "Installing MongoDB and Kafka"

kubectl apply -f 1_kafka_deployment.yaml
kubectl apply -f 2_mongodb_deployment.yaml

kubectl wait --for=condition=ready pod -l app=kafka
kubectl wait --for=condition=ready pod -l app=mongodb

echo "Installing Review and Rating services"

kubectl apply -f 3_review_service_deployment.yaml
kubectl apply -f 4_rating_service_deployment.yaml

kubectl wait --for=condition=ready pod -l app.kubernetes.io/name=review-service
kubectl wait --for=condition=ready pod -l app.kubernetes.io/name=text-sentiment-djl

echo "Installing Kafka UI"

kubectl apply -f 5_kafka_ui_deployment.yaml
kubectl wait --for=condition=ready pod -l app=kafka-ui

echo "Adding OpenShift Routes"

kubectl apply -f 10_openshift_routes.yaml

reviewHost=`oc get route review-service -o jsonpath={.spec.host}`
reviewUrl="https://$reviewHost"

read -p "Show products before any review"

curl "$reviewUrl/productsWithComments" | python -m json.tool

read -p "Create a random review"

curl "$reviewUrl/review/generate" | python -m json.tool

read -p "Show products after the review"

curl "$reviewUrl/productsWithComments" | python -m json.tool

read -p "Show contents of review topic"

kubectl run kafka-consumer -ti --image=quay.io/strimzi/kafka:0.33.0-kafka-3.3.2 --rm=true --restart=Never -- bin/kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic review --from-beginning

read -p "Show contents of rating topic"

kubectl run kafka-consumer -ti --image=quay.io/strimzi/kafka:0.33.0-kafka-3.3.2 --rm=true --restart=Never -- bin/kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic rating --from-beginning

read -p "Done :)"
