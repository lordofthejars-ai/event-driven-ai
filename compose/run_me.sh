#!/bin/sh

REVIEW_TOPIC=mongodb.shop.comment
RATING_TOPIC=mongodb.shop.rating

REVIEW_URL=http://localhost:8081/review/generate
NUM_FAKE_REVIEWS=50

echo "-----------------------------------------"
echo "step 1: inspect {products} in MongoDB 🍃 "
read -p "-----------------------------------------"
docker run -it --rm \
        --network event-driven-ai \
        mongodb/mongodb-community-server:7.0.3-ubi8 \
        mongosh mongodb:27017/shop --eval "db.product.find().limit(10)"

echo "-------------------------------------------------------------------"
echo "step 2: run kcctl 🧸 to create Debezium MongoDB 🍃 source connector "
read -p "-------------------------------------------------------------------"
docker run -it --rm \
    --network event-driven-ai \
    -v ${PWD}/connect:/home \
    debezium/tooling:latest \
    bash -c "kcctl config set-context default --cluster=http://connect:8083 && kcctl apply -f /home/mongo_source_shop_comments.json"

echo "-----------------------------------------"
echo "step 3: ⚙️ generate fake product comments "
read -p "-----------------------------------------"

for (( c=1; c<=$NUM_FAKE_REVIEWS; c++ ))
do
    curl $REVIEW_URL
    echo "\n"
done

echo "--------------------------------------"
echo "step 4: show contents of review topic "
read -p "--------------------------------------"

docker run -it --rm --network event-driven-ai quay.io/strimzi/kafka:0.35.1-kafka-3.4.0 bin/kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic $REVIEW_TOPIC --from-beginning

echo "--------------------------------------"
echo "step 5: show contents of rating topic "
read -p "--------------------------------------"

docker run -it --rm --network event-driven-ai quay.io/strimzi/kafka:0.35.1-kafka-3.4.0 bin/kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic $RATING_TOPIC --from-beginning

echo "------------------------------------------------------------------"
echo "step 6: inspect {products} with comment and ratings in MongoDB 🍃"
read -p "------------------------------------------------------------------"

docker run -it --rm \
        --network event-driven-ai \
        -v ${PWD}/mongodb:/home \
        mongodb/mongodb-community-server:7.0.3-ubi8 \
        sh -c "mongosh mongodb:27017/shop < /home/query.js"

echo "\n"

echo "---------------------------------"
echo " *** TEAR DOWN COMPOSE SETUP *** " 
echo "---------------------------------"

docker compose -f compose.yaml down
