#import "ScreenSettingsPlugin.h"
#if __has_include(<screen_settings/screen_settings-Swift.h>)
#import <screen_settings/screen_settings-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "screen_settings-Swift.h"
#endif

@implementation ScreenSettingsPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftScreenSettingsPlugin registerWithRegistrar:registrar];
}
@end
