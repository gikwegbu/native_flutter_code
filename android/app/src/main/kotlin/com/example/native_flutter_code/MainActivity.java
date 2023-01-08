package com.example.native_flutter_code;

import static java.sql.DriverManager.println;

import android.os.Bundle;


import androidx.annotation.NonNull;

import java.util.Map;

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
                final Map<String, Object> args = call.arguments();
                if (call.method.equals("logRocket")) {
                    String name = (String) args.get("name");
                    String email = (String) args.get("email");
                    String userId = (String) args.get("userId");
                    String logRocketAppId = (String) args.get("logRocketAppId");
                    String logRocket = callLogRocket(name, email, userId, logRocketAppId);
                    result.success(logRocket);
                }
            }
        });
    }

    private String callLogRocket(String name, String email, String userId, String logRocketAppId) {
//        LogRocketClass  lG = new LogRocketClass(name, email, userId, logRocketAppId);
        LogRocketClass  lG = new LogRocketClass()
                .setName(name)
                .setEmail(email)
                .setUserId(userId)
                .setAppId(logRocketAppId);

        lG.attachBaseContext(getBaseContext());

        return "Initialization Complete! Name: "+name +", email: "+email + ", userId: "+userId;
    }
}
