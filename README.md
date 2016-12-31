# Start Android Monitor

Via Command Line in Android Studio

````
# This is because we need ANDROID_HVPROTO env variable to be set
# being exported in .bashrc
$ bash
$ gosdk
$ tools/monitor
# If still having issues
$ adb kill-server
$ adb start-server
````

# Use Lint

Via Command Line in Android Studio

````
# This is because we need lint which is available in sdk
# being exported in .bashrc
$ bash
$ lint .
````

Via GUI in Android Studio

````
Analize > Inspect Code in Android Studio
````
