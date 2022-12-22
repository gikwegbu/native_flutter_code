package com.example.native_flutter_code

import android.hardware.*
import io.flutter.plugin.common.EventChannel

//class StreamHandler(private val sensorManger: SensorManager, sensorType: Int, private var interval: Int = SensorManager.SENSOR_DELAY_NORMAL):
//    EventChannel.StreamHandler, SensorEventLister {
//    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
//
//    }
//
//    override fun onCancel(arguments: Any?) {
//        TODO("Not yet implemented")
//    }
//
//}

class StreamHandler(
    private val sensorManager: SensorManager,
    sensorType: Int,
    private var interval: Int = SensorManager.SENSOR_DELAY_NORMAL
) :
    EventChannel.StreamHandler, SensorEventListener {
    private val sensor = sensorManager.getDefaultSensor(sensorType)
    private var eventSink: EventChannel.EventSink? = null

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
       if(sensor != null) {
           eventSink = events
           sensorManager.registerListener(this, sensor, interval)
       }
    }

    override fun onCancel(arguments: Any?) {
        sensorManager.unregisterListener(this)
        eventSink = null
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        print(p0)
        val sensorValues = p0!!.values[0]
        eventSink?.success(sensorValues)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

}