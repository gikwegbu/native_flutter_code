package com.example.native_flutter_code;

import static java.sql.DriverManager.println;


import io.flutter.app.FlutterApplication;
import android.app.Application;
import com.logrocket.core.SDK;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class LogRocketClass extends Application {
  String name, email, userId, logRocketAppId;

//  public LogRocketClass(String name, String email, String userId, String logRocketAppId) {
//      this.name = name;
//      this.email = email;
//      this.userId = userId;
//      this.logRocketAppId = logRocketAppId;
//  }

  public LogRocketClass setName(String s) {
      name = s;
      return new LogRocketClass();
  }
    public LogRocketClass setEmail(String s) {
        email = s;
        return new LogRocketClass();
    }

    public LogRocketClass setUserId(String s) {
        userId = s;
        return new LogRocketClass();
    }

    public LogRocketClass setAppId(String s) {
        logRocketAppId = s;
        return new LogRocketClass();
    }

//  @Override
//  protected void attachBaseContext(Context base) {
//    super.attachBaseContext(base);
//    Map<String, String> userData = new HashMap<>();
//
//    SDK.init(
//            this,
//            base,
//            options -> {
////              options.setAppID("s9ns7r/george-ikwegbu");
//              options.setAppID(logRocketAppId);
////              options.setAppID("jrpbsi/amaze");
//            }
//    );
//    userData.put("name", name);
//    userData.put("email", email);
//
//    SDK.identify(userId, userData);
//
//  }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        SDK.init(
                this,
                base,
                options -> {
                    options.setAppID(logRocketAppId);
                }
        );


        Map<String, String> userData = new HashMap<>();

        userData.put("name", name);
        userData.put("email", email);

        SDK.identify(userId, userData);

    }
}