import 'dart:async';

import 'package:flutter/services.dart';

/// Manage brightness and wake lock
class ScreenSettings {
  static const MethodChannel _channel =
      MethodChannel('github.com/sarcastic_noodle/screen_settings');

  /// Get the current brightness of the device in %
  ///
  /// Returns -1 if the value is managed by the system
  static Future<double> get brightness async =>
      (await _channel.invokeMethod<double>('getBrightness'))!;

  /// Set the screens brightness
  ///
  /// If set to -1 the value managed by the system will be set
  static Future<void> setBrightness(double brightness) =>
      _channel.invokeMethod('setBrightness', {'brightness': brightness});

  /// Check if wake lock is enabled
  static Future<bool> get isKeptOn async =>
      (await _channel.invokeMethod<bool>('isKeptOn'))!;

  /// Enable or disable wake lock
  static Future<void> setKeepOn(bool keepOn) =>
      _channel.invokeMethod('keepOn', {'on': keepOn});
}
