# Hierarchy service

## Running procedure
1. run db `docker-compose -f infrastructure/docker-compose.yml up -d db`
1. run app `./gradlew build bootRun`

## Info
* Application uses basic auth `user:password`
* Application run on port `8110`
* For tests we use [embedded postgres](https://github.com/opentable/otj-pg-embedded)

## Endpoints provided
1. pushing new hierarchy `curl "localhost:8110/relationship/" -v --user user:password --data '{"Tom":"Jerry","Jerry":"Mix"}' -H "Content-type: application/json"`
1. getting supervisors `curl "localhost:8110/relationship/supervisors?employeeName=Tom" -v --user user:password`

