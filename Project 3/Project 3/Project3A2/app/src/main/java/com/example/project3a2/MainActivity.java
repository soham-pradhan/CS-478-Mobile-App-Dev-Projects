package com.example.project3a2;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ReceiverClass newReceiver;
    private IntentFilter myFilter;
    public static String PERMISSION_CODE = "edu.uic.cs478.s19.kaboom.";
    private Button permissionButton;
    public int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        x = intent.getIntExtra("Key", 0);

        //Checks if A2 received permission, if not exits
        if (x == 8) {

            setContentView(R.layout.activity_main);
            Button permissionButton = findViewById(R.id.PermissionButon);
            permissionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isitgranted();
                }

            });
        }
        else {
            Toast.makeText(this, "A2 can only be started by A1", Toast.LENGTH_SHORT)
                    .show();
            finish();
        }
    }


    //check if permission is granted
    private void isitgranted() {

        //if user already allowed the permission, this condition will be true
        if (ContextCompat.checkSelfPermission(this, PERMISSION_CODE)
                != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"hiii",Toast.LENGTH_SHORT);
            ActivityCompat.requestPermissions(this, new String[]{PERMISSION_CODE}, 0);
        }

            //if user didn't allow the permission yet, then ask for permission
        else if(ContextCompat.checkSelfPermission(this, PERMISSION_CODE)
                == PackageManager.PERMISSION_GRANTED){
            IntentFilter filter = new IntentFilter(PERMISSION_CODE);
            filter.setPriority(2);
            ReceiverClass receiver = new ReceiverClass();
            registerReceiver(receiver, filter);
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.project3a3");
            if (launchIntent != null) {
                launchIntent.putExtra("Key", 8);
                startActivity(launchIntent);//null pointer check in case package name was not found
            }
        }
        else
            Toast.makeText(this,"happy, are you?",Toast.LENGTH_SHORT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //USER CLICKED EITHER ALLOW OR DENY BUTTON ON THE POP UP WINDOW
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (grantResults.length > 0) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                IntentFilter filter = new IntentFilter(PERMISSION_CODE);
                filter.setPriority(2);
                ReceiverClass receiver = new ReceiverClass();
                registerReceiver(receiver, filter);

                //Launch A3
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.project3a3");
                if (launchIntent != null) {
                    launchIntent.putExtra("Key", 8);
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
                //DENY
                else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }

            }
        }

    }
}