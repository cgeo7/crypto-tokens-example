## Introduction

This project is a demonstration of how crypto market ticker board could work with live data feeds and updates.

The purpose of this project is to demonstrate the following:

1. Usage of the `Spring Boot` framework and `Websockets` to facilitate live data update
1. Data flow implementation using a pub/sub event based pattern w/Websockets
1. Usage of `Angular` as a frontend including minor state management and usage of the `ng-bootstrap` component library
1. Usage of `Docker` to enable easy deployment

## Installation instructions

As described above installation deployment was performed (within the context of the scope of the project) 
with the least steps possible. Using a linux shell (e.g. `bash`) the steps are the following.

1. Clone the project locally using:

    `git clone https://github.com/cgeo7/crypto-tokens-example.git`

2. From the root folder `crypto-tokens-example` build the docker containers with:

    `./1.build_images.sh`

3. After images are built you can deploy all containers using:
    `./2.start_applications.sh`

4. After containers are deployed, the applications can be accessed at:
   `http://localhost:4200`
