# AGA Examples

This repo contains example projects for the Automotive Grade Android SDK. All projects should be importable to Android Studio without too much effort. The folder `JARExamples` contains examples that use JAR files while the folder `ROMExamples` contains examples that use the AGA SDK and ROM images. The two folders will contain the same examples whenever it makes sense (e.g., when it does not rely on specific features of either version of the SDK).

Examples:

1. Speed, a simple app that displays the current speed using a text size suitable for the current distraction level.

(More examples will be added, feel free to push your own!)

## Getting Started

You can compile the JARExamples using the regular Android SDK (unless otherwise noted). The AGA dependencies should be handled by Gradle, so you do not have to install any JAR files. To compile the ROMExamples you need to install the AGA SDK (for Windows or Linux, I have not tried the examples with a hybrid Mac SDK yet). 

To run the examples: 

1. Import the project in Android Studio, compile it and run it on an Android virtual device (AVD). Note that if you use the AGA SDK, you have to create the AVD image using that SDK; you cannot reuse an existing one that was created with the regular Android SDK. You also cannot run it on your phone/device unless you flash it with a custom ROM.

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

Note that if you want to restart your app, you **must** remove the signals in the simulator and add them again. Note that if you use the ROMExamples, the Simulator connects to the phone, not the app, so you might not have to add/remove signals every time you restart.



