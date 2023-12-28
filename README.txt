STEP1: Start docker

sudo update-alternatives --config iptables
Write 1 and enter
sudo service docker start

STEP 2: Go to the docker config folder

cd "/mnt/c/PERSONAL/MASTER/ANUL2/CLOUD/ProiectLaborator/ProiectCloud/docker-config"

STEP 3: Build image for mysql

docker build -t mysqldb:latest -f Dockerfile .

STEP 4: Go to pet microservice in the root folder and build the pet image

cd 
cd "/mnt/c/PERSONAL/MASTER/ANUL2/CLOUD/ProiectLaborator/ProiectCloud/pet"
docker build -t pet-service:latest -f Dockerfile .

STEP 5: Repeat step 4 for health, users , adoption services

STEP 6: Go back to docker-config folder

STEP 7: Start all containers using docker-compose

docker compose -f docker-compose.yml up

Note: CTRL + Z stops the current command