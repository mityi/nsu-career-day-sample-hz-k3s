helm install stable/nginx-ingress --name my-nginx

./mvnw package 
./mvnw dockerfile:build
 
helm upgrade --dry-run --debug --install demo  ./chart/demo
helm upgrade --debug --install demo  ./chart/demo