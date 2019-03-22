
#####before
```
helm install stable/nginx-ingress --name my-nginx
```
#####cleanUP
```
./mvnw clean 
helm upgrade --debug --install demo --set replicaCount=0 ./chart/demo
```
#####install
```
./mvnw package 
./mvnw dockerfile:build
docker push docker.io/mityi/sample-spring-hz-k8s:0.0.1-SNAPSHOT

helm upgrade --dry-run --debug --install demo --set replicaCount=1 ./chart/demo
helm upgrade --debug --install demo --set replicaCount=1 ./chart/demo
```