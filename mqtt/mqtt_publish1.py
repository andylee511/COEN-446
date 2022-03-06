import paho.mqtt.client as mqtt
from random import randrange, uniform
import time

#connect to mqtt broker
mqttBroker = "mqtt.eclipseprojects.io"
#give client a name
client = mqtt.Client("Temperature_inside")
client.connect(mqttBroker)

while True:
    randNumber = uniform(20.0, 21.0)
    #public randNumber to topic Temperature
    client.publish("Temperature", randNumber)
    print("Just published " + str(randNumber) + "to topic Temperature")
    time.sleep(1)