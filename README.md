#####Build and deploy to cluster
Do not forget to change to your account  <mityi>
```
./mvnw package 
./mvnw dockerfile:build
docker push docker.io/mityi/sample-hz-arm:0.0.1

helm upgrade --debug --install demo --set replicaCount=1 ./chart/demo
```
#####Start in local (or on x86) kubernetes  
```
./mvnw package 
./mvnw dockerfile:build -Ddockerfile=docker/Dockerfile_x86 -Ddocker.image.prefix=local

helm upgrade --debug --install demo --set replicaCount=1 ./chart/demo
```
#####Kubernetes cli
Get all pods | services | logs ``` k get pods ``` | ``` k get service``` | ```k logs demo-0 -f```
#####CleanUP
```
./mvnw clean 
helm upgrade --debug --install demo --set replicaCount=0 ./chart/demo
helm delete --purge demo
```
