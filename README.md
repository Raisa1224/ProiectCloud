# **Cloud Project**

## **Steps to run the project on docker**

**STEP 1**: Start docker

`sudo update-alternatives --config iptables` \
Write 1 and press enter

`sudo service docker start`

**STEP 2**: Go to the docker-config folder

Example: \
`cd "/mnt/c/PERSONAL/MASTER/ANUL2/CLOUD/ProiectLaborator/ProiectCloud/docker-config"`

**STEP 3**: Build images for MySQL for the databases for all the projects

`cd userDocker` \
`docker build -t mysqldb_user:latest -f Dockerfile .`

`cd petDocker` \
`docker build -t mysqldb_pet:latest -f Dockerfile .`

`cd healthDocker` \
`docker build -t mysqldb_health:latest -f Dockerfile .`

`cd adoptionDocker` \
`docker build -t mysqldb_adoption:latest -f Dockerfile .`

**STEP 4**: Go to pet microservice in the root folder of the project and build the Pet image

Example: \
`cd "/mnt/c/PERSONAL/MASTER/ANUL2/CLOUD/ProiectLaborator/ProiectCloud/pet"`

`docker build -t pet-service:latest -f Dockerfile .`

**STEP 5**: Repeat _STEP 4_ for Health, Users and Adoption services

`docker build -t user-service:latest -f Dockerfile .`

`docker build -t health-service:latest -f Dockerfile .`

`docker build -t adoption-service:latest -f Dockerfile .`

**STEP 6**: Go back to docker-config folder

**STEP 7**: Start all containers using docker-compose

`docker compose -f docker-compose.yml up`

_Note_: CTRL + Z or CTRL + C stops the current process

To open Zipkin go to `http://localhost:9411/`
