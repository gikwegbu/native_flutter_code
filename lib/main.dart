import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Native Code In Flutter',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key}) : super(key: key);

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const methodChannel = MethodChannel("app.gikwegbu.barometer/method");
  static const pressureChannel =
      EventChannel("app.gikwegbu.barometer/pressure");

  String _sensorAvailable = 'Unknown';
  String _batteryLevel = 'Unknown';
  double _pressureReading = 0;

  late StreamSubscription pressureSubscription;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SizedBox(
        width: double.infinity,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            const Text(
              "Implementing Native codes in Flutter",
              textAlign: TextAlign.center,
            ),
            const SizedBox(
              height: 10,
            ),
            Text("Battery Level: $_batteryLevel"),
            const SizedBox(
              height: 10,
            ),
            ElevatedButton(
              onPressed: _getBatteryLevel,
              child: const Text("Get Battery Level"),
            ),
            const SizedBox(
              height: 10,
            ),
            Text("Sensor Status: $_sensorAvailable"),
            const SizedBox(
              height: 20,
            ),
            ElevatedButton(
              onPressed: _checkAvailability,
              child: const Text("Check Sensor Available"),
            ),
            const SizedBox(
              height: 70,
            ),
            if (_sensorAvailable == 'true')
              Text("Sensor Reading: $_pressureReading"),
            if (_sensorAvailable == 'true')
              ElevatedButton(
                onPressed: _pressureReading == 0 ? _startReading : _stopReading,
                child: Text(
                    "${_pressureReading == 0 ? 'Start' : 'Stop'} Reading Pressure"),
              ),
          ],
        ),
      ),
    );
  }

  Future<void> _getBatteryLevel() async {
    final args = {
      "name": "George Ikwegbu",
      "apiKey": "lol this should be fun sha lol"
    };
    try {
      final int _ = await methodChannel.invokeMethod("getBatteryLevel", args);
      _batteryLevel = _.toString() + '%';
      setState(() {});
    } on PlatformException catch (e) {
      debugPrint("George, channel error ");
    }
  }

  Future<void> _checkAvailability() async {
    try {
      var available = await methodChannel.invokeMethod('isSensorAvailable');
      _sensorAvailable = available.toString();
      setState(() {});
    } on PlatformException catch (e) {
      debugPrint("George, channel error ");
    }
  }

  _startReading() {
    pressureSubscription =
        pressureChannel.receiveBroadcastStream().listen((event) {
      _pressureReading = event;
      setState(() {});
    });
  }

  _stopReading() {
    _pressureReading = 0;
    setState(() {});
    pressureSubscription.cancel();
  }
}
