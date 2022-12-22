package com.example.native_flutter_code

//import android.app.Application
//import com.logrocket.core.SDK


import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.BatteryManager
import android.os.Build
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel


class MainActivity : FlutterActivity() {
    private val METHOD_CHANNEL_NAME = "app.gikwegbu.barometer/method"
    private val PRESSURE_CHANNEL_NAME = "app.gikwegbu.barometer/pressure"

    private var methodChannel: MethodChannel? = null
    private lateinit var sensorManager: SensorManager
    private var pressureChannel: EventChannel? = null
    private var pressureStreamHandler: StreamHandler? = null


    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        // Setup Channels
        setupChannels(this, flutterEngine.dartExecutor.binaryMessenger)
    }

    override fun onDestroy() {
        teardownChannels()
        super.onDestroy()
    }

    private fun setupChannels(context: Context, messenger: BinaryMessenger) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        methodChannel = MethodChannel(messenger, METHOD_CHANNEL_NAME)

        methodChannel!!.setMethodCallHandler { call, result ->
 
            if (call.method == "isSensorAvailable") {
                result.success(sensorManager.getSensorList(Sensor.TYPE_PRESSURE).isNotEmpty())
            } else if(call.method == "getBatteryLevel") {
                val args = call.arguments() as Map<String, String>
                val name = args["name"]
                val batteryLevel = getBatteryLevel()
//                result.success("$name says: $batteryLevel")
                result.success(batteryLevel)
            }
            else {
            // This happens when we unintentionally call something that does not exist
                result.notImplemented()
            }
        }

        // Pressure section

        // Setting up the channel with EventChannel
        pressureChannel = EventChannel(messenger, PRESSURE_CHANNEL_NAME)
        // Creating an instance of the StreamHandler
        pressureStreamHandler = StreamHandler(sensorManager!!, Sensor.TYPE_PRESSURE)
        // Linking the handler to the channel
        pressureChannel!!.setStreamHandler(pressureStreamHandler)
    }

    private fun getBatteryLevel(): Int {
        val batteryLevel: Int
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            batteryLevel = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        }
        return batteryLevel
    }

    private fun teardownChannels() {
        // the !!. force opens the method for us...
        methodChannel!!.setMethodCallHandler(null)
        pressureChannel!!.setStreamHandler(null)
    }
}
