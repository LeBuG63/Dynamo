package iut.ipi.runnergame.Sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class Accelerometer implements SensorEventListener {
    private final long DELAY_UPDATE = 100;
    private final int SHAKE_THRESHOLD = 100;
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;

    private float[] lastValues = new float[3];
    private long lastUpdate = 0;

    private boolean isShaked = false;
    private float speed = 0.0f;

    public Accelerometer(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        long curTime = System.currentTimeMillis();

        if ((curTime - lastUpdate) > DELAY_UPDATE) {
            Sensor sensor = sensorEvent.sensor;

            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                speed = Math.abs(x + y + z - lastValues[0] - lastValues[1] - lastValues[2]) / diffTime * 1000;

                if (speed > SHAKE_THRESHOLD) {
                    isShaked = true;
                    Log.d("ACCELEROMETER", "SHAKED");
                } else {
                    isShaked = false;
                }

                lastValues = sensorEvent.values.clone();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public boolean isShaked() {
        return isShaked;
    }

    public float getSpeed() {
        return speed;
    }
}
