package com.sarcastic_noodle.screen_settings

import android.app.Activity
import android.content.Context
import android.provider.Settings
import android.view.WindowManager
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** ScreenSettingsPlugin */
class ScreenSettingsPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private var activity: Activity? = null

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "github.com/sarcastic_noodle/screen_settings")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "getBrightness" -> {
                val brightness = activity?.window?.attributes?.screenBrightness
                if (brightness != null) {
                    result.success(brightness)
                } else {
                    result.error("Activity Error", "no activity to get the brightness from", "an activity is required for retrieving brightness")
                }
            }
            "setBrightness" -> {
                call.argument<Double>("brightness")?.let { brightness ->
                    val attrs = activity?.window?.attributes;
                    attrs?.screenBrightness = brightness.toFloat()
                    activity?.window?.attributes = attrs
                }
                result.success(null)
            }
            "isKeptOn" -> {
                val flags = activity?.window?.attributes?.flags
                if (flags != null) {
                    result.success((flags and WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) != 0)
                } else {
                    result.success(false)
                }
            }
            "keepOn" -> {
                val keepOn = call.argument<Boolean>("on") ?: false
                if (keepOn) {
                    activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                } else {
                    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
                result.success(null)
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onDetachedFromActivity() {
        activity = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onDetachedFromActivityForConfigChanges() {
        activity = null
    }
}
