## Подготовка Raspberry Pi
1)	Скачиваем образ системы Raspbian lite ```https://downloads.raspberrypi.org/raspbian_lite/images/```
2)	Распаковываем образ на micro SD карту с помощью balenaEtcher ```https://www.balena.io/etcher/```
#### включение ssh
После установки на micro SD карту образа системы, необходимо создать в директории ```/Volumes/boot/``` пустой файл с названием ```ssh```

## Первоначальная настройка Raspbian
```
Default login: pi
Default pasword: raspberry
```

####Создаем скрипт смены hostname и задания статического IP
```
touch hostname_and_ip.sh
cat >> hostname_and_ip.sh <EOF

#!/bin/sh

#задаем переменные
hostname=$1
ip=$2 # should be of format: 192.168.1.100
dns=$3 # should be of format: 192.168.1.1

# Меняем hostname
sudo hostnamectl --transient set-hostname $hostname
sudo hostnamectl --static set-hostname $hostname
sudo hostnamectl --pretty set-hostname $hostname
sudo sed -i s/raspberrypi/$hostname/g /etc/hosts

# Задаем статический IP
sudo cat <<EOT >> /etc/dhcpcd.conf
interface eth0
static ip_address=$ip/24
static routers=$dns
static domain_name_servers=$dns

EOT

EOF
```

####запускаем скрипт с переменными hostname ip dns
```
sh hostname_and_ip.sh master-pi 192.168.1.200 192.168.1.1
```

####перезапускаем Raspberry Pi
```
sudo reboot
```

#### После перезагрузки подключаемся по новому IP

####Создаем скрипт отключения SWAP
```
touch disable_swap.sh
cat >> disable_swap.sh <EOF

#!/bin/sh

# Отключаем SWAP
sudo dphys-swapfile swapoff && \
  sudo dphys-swapfile uninstall && \
  sudo update-rc.d dphys-swapfile remove
echo Adding " cgroup_enable=cpuset cgroup_enable=memory" to /boot/cmdline.txt
sudo cp /boot/cmdline.txt /boot/cmdline_backup.txt
orig="$(head -n1 /boot/cmdline.txt) cgroup_enable=cpuset cgroup_memory=1 cgroup_enable=memory"
echo $orig | sudo tee /boot/cmdline.txt

EOF
```

####запускаем скрипт 
```
sudo sh disable_swap.sh
```

####перезапускаем Raspberry Pi
```
sudo reboot
```

##### Повторяем на каждой ноде меняя hostname и ip адрес


#### На master ноде
#### Скачиваем скрипт установки k3s мастера
```
sudo curl -sfL https://get.k3s.io | sh -

sudo k3s server
sudo k3s kubectl get node
```

##Смотрим node-token для подключения нод к кластеру
```
sudo cat /var/lib/rancher/k3s/server/node-token
```

#### На worker ноде
#### Скачиваем скрипт установки k3s и задаем параметры подключения к master ноде
```
sudo curl -sfL https://get.k3s.io | K3S_URL=https://192.168.1.200:6443 K3S_TOKEN=K102dfcfa3557e38094c1180c810f344278af33dd737be1bf94d09482e739fbd699::node:100497cb196852a88e4450f4632e507d sh -
```

## Для управления кластером через kubectl c локального компьютера
копируем ```kubeconfig-system.yaml``` лежащий в директории ```/var/lib/rancher/k3s/server/node-token``` в директорию установленого ```kubectl```