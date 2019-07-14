#!/usr/bin/env bash
./gradlew jibDockerBuild
set -o allexport; source .env; set +o allexport
docker tag hello-micronaut:0.1 eu.gcr.io/"$GOOGLE_CLOUD_PROJECT_ID"/hello-micronaut:0.1
docker push eu.gcr.io/"$GOOGLE_CLOUD_PROJECT_ID"/hello-micronaut:0.1
kubectl patch deployment hello-micronaut -p \
  "{\"spec\":{\"template\":{\"metadata\":{\"annotations\":{\"date\":\"`date +'%s'`\"}}}}}"
