package com.example.dnd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView x,y,z,proximityText,dndStatus;
    SensorManager sm;
    AudioManager am;
    Sensor accel;
    Sensor proximitySensor;
    int nearOrFar = 0;

//    near means 1 and far means 0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x = findViewById(R.id.x);
        y = findViewById(R.id.Y);
        z = findViewById(R.id.z);
        dndStatus = findViewById(R.id.DNDStatus);
        proximityText = findViewById(R.id.proximityText);
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        am = (AudioManager) getSystemService(AUDIO_SERVICE);
        accel = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximitySensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(accel==null || proximitySensor == null){
            Toast.makeText(MainActivity.this, "Accelerometer OR Proximity Sensor not supported", Toast.LENGTH_SHORT).show();
            finish();
        }
        sm.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    if (sensorEvent.values[0] == 0) {
                        // here we are setting our status to our textview..
                        // if sensor event return 0 then object is closed
                        // to sensor else object is away from sensor.
                        proximityText.setText("Near");
                        nearOrFar = 1;
                    } else {
                        proximityText.setText("Away");
                        nearOrFar = 0;
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        }, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

        sm.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x1 = sensorEvent.values[0];
                float y1 = sensorEvent.values[1];
                float z1 = sensorEvent.values[2];
                x.setText("X axis: "+x1+"");
                y.setText("Y axis: "+y1+"");
                z.setText("Z axis: "+z1+"");
                FirebaseDatabase.getInstance().getReference().child("Xaxis")
                        .setValue(x1);
                FirebaseDatabase.getInstance().getReference().child("Yaxis")
                        .setValue(y1);
                FirebaseDatabase.getInstance().getReference().child("Zaxis")
                        .setValue(z1);
                FirebaseDatabase.getInstance().getReference().child("Proximity")
                        .setValue(nearOrFar);
                if( nearOrFar == 1 && z1 <= -7 && z1 >= -12  && am.getRingerMode()!=AudioManager.RINGER_MODE_VIBRATE){
                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    Toast.makeText(MainActivity.this, "Done Done Done", Toast.LENGTH_SHORT).show();
                    dndStatus.setText("Vibrate mode");
                    Map<String, Object> map = new HashMap<>();
                    map.put("dndStatus", 1);
                    FirebaseDatabase.getInstance().getReference().child("DND")
                            .setValue(1)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(MainActivity.this, "Succesfull", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                if(nearOrFar == 0 && !( z1 <= -7 && z1 >= -12 ) && am.getRingerMode()!=AudioManager.RINGER_MODE_NORMAL){
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    dndStatus.setText("Normal mode");
                    Map<String, Object> map = new HashMap<>();
                    map.put("dndStatus", 0);
                    FirebaseDatabase.getInstance().getReference().child("DND")
                            .setValue(0)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(MainActivity.this, "Succesfull", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        }, accel, SensorManager.SENSOR_DELAY_NORMAL);




    }
}