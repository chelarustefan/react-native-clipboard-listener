package com.reactlibrary;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;


@ReactModule(name = "ClipboardListener")
public class ClipboardListenerModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;
    private ClipboardManager clipboardMgr;
    public static final String CLIPBOARD_TEXT_CHANGED = "CLIPBOARD_TEXT_CHANGED";
    private ClipboardManager.OnPrimaryClipChangedListener listener = null;
    private Handler mHandler;


    public ClipboardListenerModule(@NonNull final ReactApplicationContext reactContext) {
        super(reactContext);
        ClipboardListenerModule.reactContext = reactContext;
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                clipboardMgr = (ClipboardManager) reactContext.getSystemService(Context.CLIPBOARD_SERVICE);
            }
        };
        Message message = mHandler.obtainMessage();
        message.sendToTarget();
    }

    @NonNull
    @Override
    public String getName() {
        return "ClipboardListener";
    }

    @ReactMethod
    public void setListener() {
        listener = new ClipboardManager.OnPrimaryClipChangedListener() {
                @Override
                public void onPrimaryClipChanged() {
                    String contents = clipboardMgr.getText().toString();
                    reactContext
                            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit(CLIPBOARD_TEXT_CHANGED, contents);
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
