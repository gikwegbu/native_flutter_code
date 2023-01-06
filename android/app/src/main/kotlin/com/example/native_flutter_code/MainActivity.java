package com.example.native_flutter_code;

import android.os.Bundle;


import androidx.annotation.NonNull;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;


public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "app.gikwegbu.barometer/method";

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);

        new MethodChannel(getFlutterEngine().getDartExecutor().getBinaryMessenger(), CHANNEL).setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
                if (call.method.equals("logRocket")) {
                    String logRocket = callLogRocket();
                    result.success(logRocket);
                }
            }
        });
    }

    private String callLogRocket() {
        LogRocketClass  lG = new LogRocketClass();
        lG.attachBaseContext(getBaseContext());

        return "Initialization Complete!";
    }
}
