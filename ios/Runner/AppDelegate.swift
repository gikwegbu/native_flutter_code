import UIKit
import Flutter
import CoreMotion
import LogRocket

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {
	override func application(
		_ application: UIApplication,
		didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
	) -> Bool {
		//
		
		let METHOD_CHANNEL_NAME = "app.gikwegbu.barometer/method"
		let PRESSURE_CHANNEL_NAME = "app.gikwegbu.barometer/pressure"
		let pressureStreamHandler = PressureStreamHandler()
		
		
		let controller: FlutterViewController = window?.rootViewController as! FlutterViewController
		
		let methodChannel = FlutterMethodChannel(name: METHOD_CHANNEL_NAME, binaryMessenger: controller.binaryMessenger)
		
		methodChannel.setMethodCallHandler({
			[weak self] (call: FlutterMethodCall, result: @escaping FlutterResult) -> Void in
			switch call.method {
			case "isSensorAvailable":
				result(CMAltimeter.isRelativeAltitudeAvailable())
			case "getBatteryLevel":
				self?.receiveBatteryLevel(result: result)
			case "logRocket":
				//						SDK.initialize(configuration: Configuration(appID: "s9ns7r/george-ikwegbu"))
				//						SDK.identify(userID: "28dvm2jfa", userInfo:[
				//							"name": "Jane Smith",
				//							"email": "janesmith@gmail.com",
				//							"subscriptionPlan": "premium",
				//						])
				//						result("Initialised Successfully!")
				self?.logRocketInit(call, result: result)
			default:
				result(FlutterMethodNotImplemented)
			}
		})
		
		let pressureChannel = FlutterEventChannel(name: PRESSURE_CHANNEL_NAME, binaryMessenger: controller.binaryMessenger)
		pressureChannel.setStreamHandler(pressureStreamHandler)
		//
		GeneratedPluginRegistrant.register(with: self)
		return super.application(application, didFinishLaunchingWithOptions: launchOptions)
	}
	
	private func receiveBatteryLevel(result: FlutterResult) {
		let device = UIDevice.current
		device.isBatteryMonitoringEnabled = true
		if device.batteryState == UIDevice.BatteryState.unknown {
			result(FlutterError(code: "UNAVAILABLE",
													message: "Battery level not available.",
													details: nil))
		} else {
			result(Int(device.batteryLevel * 100))
		}
	}
	
	private func logRocketInit(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
		if let args = call.arguments as? Dictionary<String, Any>,
			 let name = args["name"] as? String,
			 let email = args["email"] as? String,
			 let userId = args["userId"] as? String,
			 let logRocketAppId = args["logRocketAppId"] as? String {
			SDK.initialize(configuration: Configuration(appID: logRocketAppId))
			SDK.identify(userID: userId, userInfo:[
				"name": name,
				"email": email,
			])
			result("Initialised Successfully!")
			
		} else {
			result(FlutterError.init(code: "bad args", message: nil, details: nil))
		}
	}
}



