import 'package:flutter/material.dart';
import 'package:screen_settings/screen_settings.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [
            FutureBuilder<bool>(
              future: ScreenSettings.isKeptOn,
              builder: (context, snap) {
                if (snap.hasData) {
                  return Text('Wake Lock is ${snap.data! ? 'en' : 'dis'}abled');
                }
                return Text('Wake Lock status unknown');
              },
            ),
            FutureBuilder<double>(
              future: ScreenSettings.brightness,
              builder: (context, snap) {
                return Column(
                  children: [
                    Slider(
                      value: snap.data ?? 0,
                      onChanged: (newValue) async {
                        await ScreenSettings.setBrightness(newValue);
                        setState(() {});
                      },
                    ),
                    Text('Brightness: ${snap.data ?? 'unknown'}')
                  ],
                );
              },
            ),
            RaisedButton(
              onPressed: () async {
                await ScreenSettings.setKeepOn(
                    !(await ScreenSettings.isKeptOn));
                setState(() {});
              },
              child: Text('Switch Wakelock'),
            )
          ],
        ),
      ),
    );
  }
}
