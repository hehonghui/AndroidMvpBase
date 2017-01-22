
./gradlew assemble
./gradlew assembleAndroidTest

adb install -r  app/build/outputs/apk/*-debug.apk
adb install -r  app/build/outputs/apk/*-AndroidTest.apk

adb shell am instrument -w -r   -e debug false com.simple.mvpbase.demo.test/android.support.test.runner.AndroidJUnitRunner
