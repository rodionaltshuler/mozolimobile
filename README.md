# mozo.li

This project contains mobile clients for <b>mozoli</b> (http://mozo.li).<br><br>
mozoli - it's about indoor climbing, both bouldering and lead. You can discover gyms and events, track you scores and rating.

### How to build iOS lib (framework)

Run from the project root:
```./gradlew build```

...and look for binaries to be included in iOS project now here:
 ```common/build/xcode-frameworks/common.framework/```

 '<b>Enable Bitcode</b>' option in XCode project build setting should be 'No'

 Please see official tutorial page regarding using iOS library:
 https://kotlinlang.org/docs/tutorials/native/mpp-ios-android.html#calling-kotlin-code-from-swift


### Primary project purpose

Evaluate whether <b>#kotlin_multiplatform</b> is a convenient solution for code sharing among native Android and iOS application. <br><br>

While there are a lot of cross-platform solutions exist (Flutter, React Native, Xamarin, NativeScript, Cordova and other), Kotlin Native seems to have two major advantages:
1. <b>Does not require additional skills from development team. </b> If you developing native mobile apps, probably you use Kotlin (with gradle) already.

2. <b>No trade-offs</b> in terms of user experience (native UI look and feel on every platform), performance and platform features available.<br> Actually, it's a hypothesis we're going to verify while developing this project.

#### Secondary project purpose

Rock climbing is awesome! And we hope this mobile version of [http://mozo.li] will help you discover rock climbing gyms in cities you visit, participate in events and observe you rating real-time.