package com.example.native_flutter_code;

import static java.sql.DriverManager.println;

import io.flutter.app.FlutterApplication;
import android.app.Application;
import com.logrocket.core.SDK;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class LogRocketClass extends Application {


  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);

    println("George, this is from the LogRocket class...");

    SDK.init(
            this,
            base,
            options -> {
              // options.setAppID("<APP_SLUG>");
              options.setAppID("s9ns7r/george-ikwegbu");
//              options.setAppID("jrpbsi/amaze");
            }
    );


    Map<String, String> userData = new HashMap<>();

    userData.put("name", "George Chinedu Ikwegbu");
    userData.put("email", "g.ikwegbu@gmail.com");
    userData.put("subscriptionPlan", "premium");

    println("George these are the user's data: " +userData);

    SDK.identify("THE_USER_ID_IN_YOUR_APP", userData);

  }
}