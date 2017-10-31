# react-native-android-webview-video-fullscreen

Add in your MainActivity.java 
```
import io.getknow.inteachwebview.InTeachWebviewPackage;


public List<ReactPackage> createAdditionalReactPackages() {
  ....
   new InTeachWebviewPackage(),
}
```

Where you want to use the project : 
```
import InTeachWebview from 'react-native-android-webview-video-fullscreen';
const WVComponent = Platform.OS === 'android' ? InTeachWebview : WebView;
```