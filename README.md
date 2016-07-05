# weather
##### Simple app presenting a 5-day weather forecast

To build the app from command line:
* make sure you have the JDK, Android SDK and Gradle in you `PATH`
* have Android API level 23 platform downloaded and installed (which is the target SDK version to build against, altough the app is compatible all the way down to API level 15)
* run the Gradle script `./gradlew assembleDebug` from the root of the repository
* the resulting .apk should be under `app/build/outputs/apk/app-debug.apk`
* transfer to your device of choice and enjoy

More info here https://developer.android.com/studio/build/building-cmdline.html

### or

Grab Android Studio (https://developer.android.com/studio/index.html), open the project and click "play".

 
##### I wish I could have:
- Included a few more UI instrumentation tests
- Optimised the layouts for different screen configurations
- Added support for multiple locations
- Implemented scheduled data refresh in background tasks
- Created a detailed hourly view
- Provided option for rain notifications
- Test app in Google's Firebase device matrix
- Looked into creating a wearable component
- For the paginated view, set a lightened version of the current temperature color as the screen backround, and have a transition effect between pages by fading from one color to the next.
- Spiced up the loading dialog with quotes like "Weather forecast for tonight: dark" -George Carlin
