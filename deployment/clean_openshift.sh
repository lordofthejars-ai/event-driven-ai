#!/bin/bash

kubectl delete -f 10_openshift_routes.yaml
kubectl delete -f 6_kafka_ui_deployment.yaml
kubectl delete -f 5_mongodb_source_connector.yaml
kubectl delete -f 4_rating_service_deployment.yaml
kubectl delete -f 4_review_service_deployment.yaml
kubectl delete -f 3_debezium_connect.yaml
kubectl delete -f 2_mongodb_deployment.yaml
kubectl delete -f 1_kafka_deployment.yaml