# AGA Examples

This repo contains example projects for the Automotive Grade Android SDK. All projects should be importable to Android Studio without too much effort.

Examples:

1. Speed, a simple JAR-based AGA app that displays the current speed using a text size suitable for the current distraction level.

(More examples will be added, feel free to push your own!)

## Getting Started

Most of these examples use the JAR version of the AGA SDK, so you can compile them using the regular Android SDK (unless otherwise noted). The AGA dependencies should be handled by Gradle, so you do not have to install any JAR files. 

To run the examples: 

1. Import the project in Android Studio, compile it and run it on an Android virtual device (AVD). 

2. Download and run the simulator. It is implemented in Java, so you can use either the Linux or Windows version on Mac. Note that it is compiled for Java 8. If you have trouble running it, you can run it from the command line (terminal) by

```
cd <main simulator dir>/app
java -jar simulator-fx-1.0.jar
```

3. Forward the required ports on the AVD using adb on the command line.

```
adb forward tcp:9898 tcp:9898
adb forward tcp:9899 tcp:9899
adb forward tcp:8251 tcp:8251
```

Note, you have to do this every time you restart the AVD. You might have to provide a full path to adb (in platform-tools in the Android SDK, e.g. `Applications/Android\ Studio.app/sdk/platform-tools/adb` on Mac).

4. Connect the simulator to the app by pressing the connect button. If it works, the little circle should turn blue. 

5. Add the required signals using the Add/Remove button and pick From database. For the Speed project you want the Wheel based speed and Driver distraction signals (at least).

6. Press play, and change the values using sliders and drop-downs. These values should now update in the app. If not, check if you missed any steps.

Note that if you want to restart your app, you **must** remove the signals in the simulator and add them again. 
