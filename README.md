#####Before you start
[install k3s](k3s.install.md)

You need apply the Role Binding with the following command
```k apply -f chart/rbac.yaml```

#####Build and deploy to cluster
Do not forget to change to your account  <mityi>
```
./mvnw package 
./mvnw dockerfile:build
docker push docker.io/mityi/sample-hz-arm:0.0.3

helm upgrade --debug --install demo --set replicaCount=3 ./chart/demo
```

#####Start in local (or on x86) kubernetes  
```
./mvnw package 
./mvnw dockerfile:build -Ddockerfile=docker/Dockerfile_x86 -Ddocker.image.prefix=local

helm upgrade --debug --install demo --set replicaCount=3 --set image.repository=local/sample-hz-arm ./chart/demo
```

#####Start on local machine (or in ide)
add VM option ```-Dhazelcast.config=<path to project>/sample-hz-arm/forTest/hz-local.xml```

#####Kubernetes cli
Get all pods | services | logs ``` k get pods ``` | ``` k get service``` | ```k logs demo-0 -f```

#####CleanUP
```
./mvnw clean 
helm upgrade --debug --install demo --set replicaCount=0 ./chart/demo
helm delete --purge demo
```
