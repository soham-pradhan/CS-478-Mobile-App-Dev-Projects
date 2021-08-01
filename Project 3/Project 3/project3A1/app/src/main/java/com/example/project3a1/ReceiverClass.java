package com.example.project3a1;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class ReceiverClass extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        String message = intent.getExtras().getString("Url");

        //Proof that onReceiver Function gets the correct URL, there is some issue with webview
        Log.i("temp",message);

        if(message !=null){
            Intent x = new Intent(context,web.class);
            x.putExtra("Key",message);
            context.startActivity(x);
        }
        else if(message == null)
            Toast.makeText(context,"No item selected",Toast.LENGTH_SHORT).show();
    }
}
