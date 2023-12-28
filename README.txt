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