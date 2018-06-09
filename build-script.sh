#!/bin/bash

cd frontend
sudo npm update
sudo npm install
sudo npm run build
cd ..
sudo gradle clean build
