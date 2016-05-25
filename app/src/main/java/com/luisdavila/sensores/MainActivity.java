package com.luisdavila.sensores;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView sensores, datos;
    LinearLayout l1;
    String orientacion= " ";
    String acelerometro= " ";
    String magnetico= " ";
    String temperatura= " ";
    String proximidad= " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sensores = (TextView) findViewById(R.id.tSensores);
        datos = (TextView) findViewById(R.id.tValores);
        l1 = (LinearLayout) findViewById(R.id.lDinamico);

        SensorManager sensorManager = (SensorManager)
                getSystemService(SENSOR_SERVICE);
        List<Sensor> listaSensores = sensorManager.
                getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor: listaSensores) {
            listar(sensor.getName());
        }


        listaSensores = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        if (!listaSensores.isEmpty()) {
            Sensor orientationSensor = listaSensores.get(0);
            sensorManager.registerListener(this, orientationSensor,
                    SensorManager.SENSOR_DELAY_UI);}

        listaSensores = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (!listaSensores.isEmpty()) {
            Sensor acelerometerSensor = listaSensores.get(0);
            listar("Fabricante acelerometro: "+ acelerometerSensor.getVendor());
            listar("Potencia: "+ acelerometerSensor.getPower()+ "mA");


//            log(acelerometerSensor.get)
            sensorManager.registerListener(this, acelerometerSensor,
                    SensorManager.SENSOR_DELAY_UI);}

        listaSensores = sensorManager.getSensorList(Sensor.TYPE_PROXIMITY);
        if (!listaSensores.isEmpty()) {
            Sensor proximitySensor = listaSensores.get(0);
            sensorManager.registerListener(this, proximitySensor,
                    SensorManager.SENSOR_DELAY_UI);}

        listaSensores = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
        if (!listaSensores.isEmpty()) {
            Sensor magneticSensor = listaSensores.get(0);
            sensorManager.registerListener(this, magneticSensor,
                    SensorManager.SENSOR_DELAY_UI);}

        listaSensores = sensorManager.getSensorList(Sensor.TYPE_TEMPERATURE);
        if (!listaSensores.isEmpty()) {
            Sensor temperatureSensor = listaSensores.get(0);
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_UI);}



    }




    private void listar(String string) {
        sensores.append(string + "\n");
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            switch(event.sensor.getType()) {
                case Sensor.TYPE_ORIENTATION:
                    orientacion="Orientación ";
                    for (int i=0 ; i<3 ; i++) {
                        orientacion+=i + ": " + event.values[i]+ "\n";
                    }
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    acelerometro="Acelerómetro \n";
                    acelerometro+="X: "+event.values[0]+ "\n";
                    acelerometro+="Y: "+event.values[1]+ "\n";
                    acelerometro+="Z: "+event.values[2]+ "\n";
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    magnetico="Magnetismo \n";
                    magnetico+="Intensidad eje X: "+event.values[0]+ "\n";
                    magnetico+="Intensidad eje Y: "+event.values[1]+ "\n";
                    magnetico+="Intensidad eje Z: "+event.values[2]+ "\n";
                    break;

                case Sensor.TYPE_PROXIMITY://colocar como default
                    if(event.values[0]==1.0) {
                        proximidad = "Distancia mayor a 5cm " + "\n";
                        //l1.setBackgroundColor(Color.BLUE);
                    }else{
                        proximidad = "Distancia menor a 5cm " + "\n";
                        //l1.setBackgroundColor(Color.RED);
                    }
                    break;

                default:
                    temperatura="Temperatura ";
                    for (int i=0 ; i<event.values.length ; i++) {
                        temperatura+=i+": "+event.values[i]+ "\n";
                    }
            }
        }
        datos.setText(String.valueOf(orientacion+magnetico+acelerometro+proximidad+temperatura));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
