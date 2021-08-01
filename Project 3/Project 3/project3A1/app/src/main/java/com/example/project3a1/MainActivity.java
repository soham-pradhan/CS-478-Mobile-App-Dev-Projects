package com.example.project3a1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String  x = "edu.uic.cs478.s19.kaboom.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        TextView textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isgranted();
            }
        });

    }

    public void isgranted(){

        if (ContextCompat.checkSelfPermission(this, x)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{x}, 0);
        }
        else {


            IntentFilter filter = new IntentFilter(x);
            filter.setPriority(1);
            ReceiverClass receiver = new ReceiverClass();
            registerReceiver(receiver, filter);


            //Launch A2
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.project3a2");
            if (launchIntent != null) {
                launchIntent.putExtra("Key", 8);
                startActivity(launchIntent);//null pointer check in case package name was not found
            }


        }

    }

    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults){
        if (grantResults.length > 0)
        {
            //ALLOW
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //Create and register programmatically
                IntentFilter filter = new IntentFilter(x);
                filter.setPriority(1);
                ReceiverClass receiver = new ReceiverClass();
                registerReceiver(receiver, filter);


                //Launch A2
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.project3a2");
                if (launchIntent != null) {
                    launchIntent.putExtra("Key", 8);
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
                else{
                    Toast.makeText(this, "Wtf is wrong?!!!", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            //Deny
            else
            {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT)
                        .show();
                finishAndRemoveTask();
            }
        }
    }
}