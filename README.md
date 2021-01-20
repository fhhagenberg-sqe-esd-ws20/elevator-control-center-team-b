# Elevator Control Center Team B
### SQE Project - WS 2020/2021

### Authors
- Philipp Höbart (S1910567006)
- Lukas Karel (S1910567019)
- Dominik Grubinger (S1910567004)

### Brief
A GUI for communicating with the backend of a remote elevator control. The GUI is adaptive to the numbers of floors and elevators set in the backend. Apart from that the operating mode can be switched from automatic mode to manual mode. In automatic mode the elevator works paternoster style.

### Usage Instructions
In order to run the software, go to the release section of github and download the ***ecc-team-b-...jar*** file (in most cases you need to open the drop down menu Assets). Please use the file that comes with the most recent release for best experience. The file is also available by [directly clicking this link](https://github.com/fhhagenberg-sqe-esd-ws20/elevator-control-center-team-b/releases/download/v1.0/ecc-team-b-0.0.1-SNAPSHOT.jar).

Open a command shell and navigate to the directory where you've stored the downloaded file. To run the file, you need to execute the command ***java -jar ecc-team-b ... .jar*** (**NOTE** that the version number in the file name can change). Please also note that at least Java Version 11 is **required**.

### Testing
The test concept basically splits up in two parts: testing of the functionalities, models, etc. on the one hand and testing of the GUI on the other. The functionalities of the implemented controllers and models have, for instance, been provided by implementing mock objects where the expected result was preset (like a testing oracle). Another test strategy was to create an object of a building model with a certain number of floors and elevators and to verify these parameters afterwards. Most of the tests have been performed as white box tests. This results from trying to gain as much code coverage as possible on the one side and from the fact that we had lots of insight on the code on the other hand. Apart from that, please note that the GUI tests have been performed as black box tests, as a JavaFX Robot clicks, for example on a floor and we expect the elevator to move towards this floor. Another example is the test of the automatic mode. In these testcases for example pushing the down button is simulated and the goal is to verify that the pushed down button will be shown on the GUI as well as the elevator starts moving.


### sonarcloud Metrics
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fhhagenberg-sqe-esd-ws20_elevator-control-center-team-b&metric=alert_status)](https://sonarcloud.io/dashboard?id=fhhagenberg-sqe-esd-ws20_elevator-control-center-team-b)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fhhagenberg-sqe-esd-ws20_elevator-control-center-team-b&metric=coverage)](https://sonarcloud.io/dashboard?id=fhhagenberg-sqe-esd-ws20_elevator-control-center-team-b)

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=fhhagenberg-sqe-esd-ws20_elevator-control-center-team-b&metric=code_smells)](https://sonarcloud.io/dashboard?id=fhhagenberg-sqe-esd-ws20_elevator-control-center-team-b)

### Maven Status
![Java CI with Maven](https://github.com/fhhagenberg-sqe-esd-ws20/elevator-control-center-team-b/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)

