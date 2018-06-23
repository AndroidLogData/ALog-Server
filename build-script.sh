#!/bin/bash

os=`cat /etc/issue | head -n 1`
if [[ `expr "$os" : "\([Ubuntu]*\)"` == "Ubuntu" ]]
    then {
        sudo apt-get install -y npm gradle
        cd frontend
        sudo npm update
        sudo npm install
        sudo npm run build
        cd ..
        sudo gradle clean build
    }
elif [[ `expr "$os" : "\([CentOS]*\)"` == "CentOS" ]]
    then {
        sudo yum install -y npm gradle
        cd frontend
        sudo npm update
        sudo npm install
        sudo npm run build
        cd ..
        sudo gradle clean build
    }
fi

