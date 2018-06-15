package com.adtv.boothandling;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.adtv.splashscreen.SplashActivity;

public class MyBootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("TAG", "Rebooted ");
        context.startActivity(new Intent(context, SplashActivity.class));
    }
}
