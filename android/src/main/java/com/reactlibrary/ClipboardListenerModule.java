package com.reactlibrary;

import android.content.ClipboardManager;
import android.content.Context;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class ClipboardListenerModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;
    private ClipboardManager clipboardMgr;
    public static final String CLIPBOARD_TEXT_CHANGED = "CLIPBOARD_TEXT_CHANGED";
    private ClipboardManager.OnPrimaryClipChangedListener listener = null;


    public ClipboardListenerModule(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
        ClipboardListenerModule.reactContext = reactContext;
        clipboardMgr = (ClipboardManager) reactContext.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @NonNull
    @Override
    public String getName() {
        return "ClipboardListener";
    }

    @ReactMethod
    void setListener() {
        listener = new ClipboardManager.OnPrimaryClipChangedListener() {
                @Override
                public void onPrimaryClipChanged() {
                    String contents = clipboardMgr.getText().toString();
                    reactContext
                            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(CLIPBOARD_TEXT_CHANGED, null);
                }
            };
        clipboardMgr.addPrimaryClipChangedListener(listener);
    }

    @ReactMethod
    void removeListener() {
        if(listener != null){
            clipboardMgr.removePrimaryClipChangedListener(listener);
        }
    }

}
