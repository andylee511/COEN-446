import paho.mqtt.client as mqtt
from random import randrange, uniform
import time

#connect to mqtt broker
mqttBroker = "mqtt.eclipseprojects.io"
#give client a name
client = mqtt.Client("Temperature_outside")
client.connect(mqttBroker)

while True:
    randNumber = randrange(10)
    #public randNumber to topic Temperature
    client.publish("Temperature", randNumber)
    print("Just published " + str(randNumber) + "to topic Temperature")
    time.sleep(1)