package com.example.project3a2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

public class ReceiverClass extends BroadcastReceiver {
    int count = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(count == 1)
            return;
        Log.i("temp","hii");
        String message = "Application 2 has received permission";
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        count++;

    }
}