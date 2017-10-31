package io.getknow.inteachwebview;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;
public class InTeachWebviewPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactApplicationContext) {
        return new ArrayList<>();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactApplicationContext) {
        return Arrays.<ViewManager>asList(
                new InTeachWebviewModule()
        );
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Arrays.asList();
   }
}
