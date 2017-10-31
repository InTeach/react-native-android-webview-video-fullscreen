package io.getknow.inteachwebview;


/**
 * Created by quentinvalmori on 20/12/2016.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.webkit.WebView;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.views.webview.ReactWebViewManager;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.HashMap;
import android.util.Log;

public class InTeachWebviewModule extends ReactWebViewManager {
    private ThemedReactContext mReactContext;

    @Override
    public String getName() {
        return "RCTInTeachWebview";
    }

    @Override
    protected WebView createViewInstance(ThemedReactContext reactContext) {
        WebView root = super.createViewInstance(reactContext);
        root.setWebChromeClient(new VideoWebChromeClient(reactContext.getCurrentActivity(), root, reactContext));
        this.mReactContext = reactContext;
        return root;
    }

    @Override
    public void setSource(WebView view, @Nullable ReadableMap source) {
    if (source != null) {
      if (source.hasKey("html")) {
        String html = source.getString("html");
        if (source.hasKey("baseUrl")) {
          view.loadDataWithBaseURL(
              source.getString("baseUrl"), html, "text/html", "UTF-8", null);
        } else {
          view.loadData(html, "text/html", "UTF-8");
        }
        return;
      }
      if (source.hasKey("uri")) {
        String url = source.getString("uri");
        String previousUrl = view.getUrl();
        if (previousUrl != null && previousUrl.equals(url)) {
          return;
        }
        if (source.hasKey("method")) {
          String method = source.getString("method");
          if (method.equals("POST")) {
            byte[] postData = null;
            if (source.hasKey("body")) {
              String body = source.getString("body");
              try {
                postData = body.getBytes("UTF-8");
              } catch (UnsupportedEncodingException e) {
                postData = body.getBytes();
              }
            }
            if (postData == null) {
              postData = new byte[0];
            }
            view.postUrl(url, postData);
            return;
          }
        }
        HashMap<String, String> headerMap = new HashMap<>();
        if (source.hasKey("headers")) {
          ReadableMap headers = source.getMap("headers");
          ReadableMapKeySetIterator iter = headers.keySetIterator();
          while (iter.hasNextKey()) {
            String key = iter.nextKey();
            if ("user-agent".equals(key.toLowerCase(Locale.ENGLISH))) {
              if (view.getSettings() != null) {
                view.getSettings().setUserAgentString(headers.getString(key));
              }
            } else {
              headerMap.put(key, headers.getString(key));
            }
          }
        }
        view.loadUrl(url, headerMap);
        return;
      }
    }
    view.loadUrl("about:blank");
  }

}
