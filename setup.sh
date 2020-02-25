#!/bin/bash

sudo apt-get update
sudo apt-get install     apt-transport-https     ca-certificates     curl     gnupg-agent     software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo apt-key fingerprint 0EBFCD88
sudo add-apt-repository    "deb [arch=bionic] https://download.docker.com/linux/ubuntu \
$(lsb_release -cs) \
stable"
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable"
apt-cache madison docker-ce
sudo apt-get install docker-ce docker-ce-cli containerd.io
sudo docker run hello-world
sudo usermod -aG docker ${USER}
