# **Cloud Project**

The documentation for the project can be found [here](/ProiectLaboratorDocumentatieCloud.pdf)
 
To open Zipkin go to `http://<IP_ADDRESS>:9411/`
 
## **Steps to run the project on docker without Docker Swarm**
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
 
**STEP 7**: Start all containers using docker-compose (if Docker Swarm is not wanted)
 
`docker compose -f docker-compose.yml up`
 
_Note_: CTRL + Z or CTRL + C stops the current process
 
## **Steps to run the project on docker with Docker Swarm**
 
After the images was build like in the previous Steps, they need to pe uploaded on Docker Hub Repository
 
`docker tag mysqldb_user:latest atasieoana2001/cloud_project:mysqldb_user-latest`
 
`docker tag mysqldb_pet:latest atasieoana2001/cloud_project:mysqldb_pet-latest`
 
`docker tag mysqldb_adoption:latest atasieoana2001/cloud_project:mysqldb_adoption-latest`
 
`docker tag mysqldb_health:latest atasieoana2001/cloud_project:mysqldb_health-latest`
 
`docker tag user-service:latest atasieoana2001/cloud_project:user-service-latest`
 
`docker tag pet-service:latest atasieoana2001/cloud_project:pet-service-latest`
 
`docker tag health-service:latest atasieoana2001/cloud_project:health-service-latest`
 
`docker tag adoption-service:latest atasieoana2001/cloud_project:adoption-service-latest`
 
To push the images:
 
`docker push atasieoana2001/cloud_project:health-service-latest`
 
`docker push atasieoana2001/cloud_project:pet-service-latest`
 
`docker push atasieoana2001/cloud_project:adoption-service-latest`
 
`docker push atasieoana2001/cloud_project:user-service-latest`
 
`docker push atasieoana2001/cloud_project:mysqldb_user-latest`
 
`docker push atasieoana2001/cloud_project:mysqldb_pet-latest`
 
`docker push atasieoana2001/cloud_project:mysqldb_health-latest`
 
`docker push atasieoana2001/cloud_project:mysqldb_adoption-latest`
 
After all the images was pushed to start the application with Docker Swarm:
 
From the folder that contains the docker-compose file:
 
`docker-compose build`
 
`docker swarm init --advertise-addr 172.27.217.137`
 
`docker stack deploy --compose-file docker-compose.yml swarm-project`
 
To check if the services are running and inside the stack:
 
`docker stack services swarm-project`
 
`docker service logs swarm-project_pet`
 
To remove the services from stack if it needed:
 
`docker stack rm swarm-project`
 
`docker swarm leave --force`
 
To remove the images and the volumes:
 
`docker-compose down`
 
`docker volume prune`
 
`docker volume rm $(docker volume ls -q)`
 
`docker rm -f $(docker ps -aq)`
 
`docker rmi -f $(docker images -aq)`
