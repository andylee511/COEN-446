# COEN446-MQTT-project

## Description of files
### File
1. mqtt_smartLock.py: implements the smart lock simulator, to add information about geust's name and whether they are entering or leaving the house.
2. mqtt_thermalStat.py: impleemnts the thermal stats that will adjust the indoor temperature according to the number of people exist inside the house given by the mobile application.
3. MQTT android app: implements the applicaiton which receive the guest's name and preferred temperature, manage the number of people and calculates the preferred temperature the thermal stat should turned to.
## Software, Broker, and tools
* Python 3.10 (https://www.python.org/downloads/release/python-3100/)
* Visual Studio Code 1.66.1 (https://code.visualstudio.com/)
* Android Studio Bumblebee 2021.1.1 (https://developer.android.com/studio?gclid=Cj0KCQjwgMqSBhDCARIsAIIVN1VDfJlerP6r4Ug_PPB6B9_dKcH3cVF7pzmleOsKt5ifmTBkgZ3bqj8aAoB3EALw_wcB&gclsrc=aw.ds)
* HiveMQ Cloud 1.2.1 (https://console.hivemq.cloud/)
* Paho-MQTT Android Service 1.0.2 (https://www.eclipse.org/paho/index.php?page=clients/android/index.php)
## Operating system
* Windows 10 Pro
## Information on how to run the project
1. Turn on the Broker on HivwMQ web socket client and logini with username and password (https://websocketclient.hivemq.cloud/?username=andylee222&host=d34fba36553a4cf5b0a20358c0e6dc08.s2.eu.hivemq.cloud&port=8884)
2. Run mqtt_thermalStat.py file with py mqtt_thermalStat.py in any environment (cmd, vscode) that can run Python.
3. Run the MQTT mobile application on Emulator within Android Studio or on Android mobile phone.
4. Run mqtt_smartLock.pywith command py mqtt_smartlock.py, input guest name, preferred temperature, and whether they are entering or leaving the house. 
5. To stop simulation on python, press Ctrl + c in terminal to terminate the code.
