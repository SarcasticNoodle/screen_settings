# screen_settings

Control wakelock and brightness

## Getting Started

Add ```screen_settings: ^0.1.0``` as a dependency to your pubspec.yaml, no additional setup required.

## Usage

```dart
import 'package:screen_settings/screen_settings.dart';

final brightness = await ScreenSettings.brightness;

ScreenSettings.setBrightness(0.7);

final keepingOn = await ScreenSettings.isKeptOn;

Screen.setKeepOn(false);
```

## Info

This package is heavily inspired from [flutter_screen](https://github.com/clovisnicolas/flutter_screen)
but supports non-nullability and android embedding v2. iOS is not supported, because I don't have a mac.
contributions adding iOS support are welcome just open a PR.
