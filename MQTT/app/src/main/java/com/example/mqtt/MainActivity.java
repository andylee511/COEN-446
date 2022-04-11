package com.example.mqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

public class MainActivity extends AppCompatActivity {
    final String host = "d34fba36553a4cf5b0a20358c0e6dc08.s2.eu.hivemq.cloud";
    final String username = "andylee222";
    final String password = "Lucy222!";
    private Button btn;

    //create an MQTT client
    final Mqtt5BlockingClient client = MqttClient.builder()
            .useMqttVersion5()
            .serverHost(host)
            .serverPort(8883)
            .sslWithDefaultConfig()
            .buildBlocking();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        btn = findViewById(R.id.btn_con);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.connectWith()
                        .simpleAuth()
                        .username(username)
                        .password(UTF_8.encode(password))
                        .applySimpleAuth()
                        .send();
               client.subscribeWith()
                        .topicFilter("my/test/topic")
                        .send();

                //set a callback that is called when a message is received (using the async API style)
                client.toAsync().publishes(ALL, publish -> {
                    Log.d("MainActivity", "Received message: " + publish.getTopic() + " -> " + UTF_8.decode(publish.getPayload().get()));

                    //disconnect the client after a message was received
                   // client.disconnect();
                });
            }
        });
    }
}