package com.example.mqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.lang.Integer.parseInt;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final String host = "d34fba36553a4cf5b0a20358c0e6dc08.s2.eu.hivemq.cloud";
    final String username = "andylee222";
    final String password = "Lucy222!";
    private Button btn, btn1, btn2;
    private String message;
    private int guest_num;
    private int desired_temp;
    private ArrayList<String> guests = new ArrayList<String>();
    private String[] receive_msg;
    private ArrayList<Integer> temperature = new ArrayList<Integer>();
    private String fin;
    private int i = 0;
    private boolean connected = false;

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
        btn1 = findViewById(R.id.btn_dis);
        btn2 = findViewById(R.id.btn_guest);

        //display guests
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
        //disconnect btn
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //disconnect the client after a message was received
                if(connected) {
                    client.disconnect();
                    Toast.makeText(getApplicationContext(), "Successfully disconnected", Toast.LENGTH_SHORT).show();
                    connected = false;
                }
                else{
                    Toast.makeText(getApplicationContext(), "Not connected yet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!connected) {
                    client.connectWith()
                            .simpleAuth()
                            .username(username)
                            .password(UTF_8.encode(password))
                            .applySimpleAuth()
                            .send();
                    Toast.makeText(getApplicationContext(), "Connected to MQTT", Toast.LENGTH_SHORT).show();
                    connected = true;
                    //subscribe to guest coming or leaving
                    client.subscribeWith()
                            .topicFilter("my/test/guest")
                            .send();


                    //set a callback that is called when a message is received (using the async API style)
                    client.toAsync().publishes(ALL, publish -> {
                        Log.d("MainActivity", "Received message: " + publish.getTopic() + " -> " + UTF_8.decode(publish.getPayload().get()));
                        message = UTF_8.decode(publish.getPayload().get()).toString();

                        fin = String.valueOf(cal(message));
                        //disconnect the client after a message was received
                        // client.disconnect();
                        //Log.d("value", fin);
                        //publish current guest number and calculated desire temperature
                        client.publishWith()
                                .topic("my/test/temp")
                                .payload(UTF_8.encode(fin))
                                .send();
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "Already connected!", Toast.LENGTH_SHORT).show();
                }





            }
        });
    }
    private int cal(String msg) {
        //temp string to store guests name


        Log.d("MainActivity", msg);
        receive_msg = msg.split(",");
        Log.d("MainActivity", receive_msg[2]);
       //should first check if the guest if leaving or entering the house
        if (receive_msg[2].equals("entering")) {
            //update guest name and preferred temperature
            guests.add(receive_msg[0]);
            temperature.add(Integer.parseInt(receive_msg[1]));
            if (guests.size() == 1) {
                desired_temp = Integer.parseInt(receive_msg[1]);
            } else {
                desired_temp = getTemp();
                Log.d("MainActivity", "Value is " + desired_temp);
            }
        }
        //if next guest if leaving, delete her/his name and preferred temperature
        else if(receive_msg[2].equals("leaving")){
            for (int a = 0, b = 0; a < guests.size() ; a++) {
                if (guests.get(a).equals(receive_msg[0])) {
                    guests.remove(a);
                    temperature.remove(a);
                    break;
                }
            }
            if (guests.size() == 0) {
                desired_temp = 15;
            }else
                desired_temp = getTemp();
        }
        return desired_temp;
    }

    private int getTemp(){
        int tem=0;
        for(int a = 0; a < temperature.size(); a++){
            tem = tem + temperature.get(a);
        }
        int value = tem/temperature.size();
        return value;
    }

    private void check(){
        String view = "";
        for (int i = 0; i < guests.size(); i++){
            view = view + "\nname: " + guests.get(i) + ", Preferred temperature: "
                    + temperature.get(i);
        }
        Toast.makeText(getApplicationContext(),"Geust: " + view,Toast.LENGTH_SHORT).show();

    }
}