# Custom Android Keyboard App

This is a custom Android keyboard app that captures keystrokes and sends them to a specified server.

## Features

- Custom keyboard implementation with full QWERTY layout
- Keystroke logging and server transmission
- Material Design UI
- Shift key functionality for uppercase letters
- Special keys (space, backspace, enter)
- Easy setup process with guided instructions

## Prerequisites

- Android Studio (latest version)
- Android SDK (minimum SDK 24 - Android 7.0)
- Java Development Kit (JDK) 8 or higher

## Setup Instructions

1. Clone or download this project
2. Open Android Studio
3. Select "Open an Existing Project"
4. Navigate to and select the "final_app" folder
5. Wait for the project to sync and build

### Configure Server URL

Before running the app, update the server URL in `KeyboardService.java`:

```java
private static final String SERVER_URL = "https://your-render-server.com/api/keystrokes";
```

Replace `https://your-render-server.com/api/keystrokes` with your actual render.com server endpoint.

## Running the App

1. Connect an Android device or start an emulator
2. Click the "Run" button (green play icon) in Android Studio
3. Select your device/emulator and click OK
4. Wait for the app to install and launch

## Enabling the Keyboard

1. When you first open the app, you'll see instructions
2. Click the "Enable Keyboard" button
3. This will take you to Android's Input Method settings
4. Find "Custom Keyboard" in the list
5. Enable it by toggling the switch
6. Set it as your default keyboard when prompted

## Project Structure

```
final_app/
├── app/
│   ├── build.gradle            # App-level build configuration
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/
│           │   └── com/example/customkeyboard/
│           │       ├── MainActivity.java       # Main app activity
│           │       └── KeyboardService.java    # Keyboard implementation
│           └── res/
│               ├── drawable/
│               │   └── key_background.xml      # Key button styling
│               ├── layout/
│               │   ├── activity_main.xml      # Main screen layout
│               │   └── keyboard_layout.xml    # Keyboard layout
│               ├── values/
│               │   ├── colors.xml            # Color definitions
│               │   ├── strings.xml           # String resources
│               │   └── styles.xml           # Style definitions
│               └── xml/
│                   └── method.xml           # Input method configuration
├── build.gradle                # Project-level build configuration
└── settings.gradle             # Project settings
```

## Troubleshooting

1. **Build Issues**
   - Make sure all dependencies are properly synced
   - Try "File > Invalidate Caches / Restart"
   - Ensure Android Studio is updated

2. **Runtime Issues**
   - Check that internet permission is granted
   - Verify the server URL is correct
   - Look for error messages in Logcat

3. **Keyboard Not Showing**
   - Make sure the keyboard is enabled in system settings
   - Try restarting the device
   - Check if any other keyboard app is conflicting

## Security Considerations

- All keystroke data is sent over HTTPS
- Consider implementing encryption for sensitive data
- Be mindful of data privacy regulations
- Implement proper server-side security measures

## Server Requirements

Your render.com server should:
- Accept POST requests
- Handle JSON data
- Implement proper security measures
- Have adequate storage for keystroke data

Example server endpoint format:
```json
{
  "key": "a",
  "timestamp": 1234567890123
}
```

## Contributing

Feel free to submit issues and enhancement requests.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
