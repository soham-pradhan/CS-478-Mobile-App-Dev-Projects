package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;
import android.provider.ContactsContract;

public class MainActivity extends AppCompatActivity {
    public boolean redo = false;
    public String contact_name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent MainActivity2Intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivityForResult(MainActivity2Intent, 1);
            }
        });
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(redo == false){
                    System.out.println(contact_name);
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, contact_name);
                    if(intent.resolveActivity(getPackageManager()) == null)
                        startActivity(intent);
                    else
                        Toast.makeText(getApplicationContext(), "There is no contacts app", Toast.LENGTH_LONG).show();
                }

                else if(redo == true){
                    Toast.makeText(getApplicationContext(), "You entered an illegal name", Toast.LENGTH_LONG).show();
                    redo = false;
                }


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_CANCELED) {
            redo = true;
        }
        if(data.hasExtra("contact_name"))
            contact_name = data.getExtras().getString("contact_name");
    }
}