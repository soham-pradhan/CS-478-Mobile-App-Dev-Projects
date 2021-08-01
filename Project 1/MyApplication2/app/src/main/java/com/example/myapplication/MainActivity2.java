package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.view.KeyEvent;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TextView;
import android.view.inputmethod.EditorInfo;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EditText editText = (EditText) findViewById(R.id.txtSub);
        editText.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                boolean isCorrect = true;
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    String value = editText.getText().toString().trim();
                    char[] charArray = value.toCharArray();
                    int count = 0;
                    for(int i = 0 ; i < value.length() ; i++){
                        if(count>2) {
                            isCorrect = false;
                            break;
                        }
                        char ch = charArray[i];
                        if(ch == ' ')
                            count++;
                        if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch==' '))) {
                            isCorrect = false;
                            break;
                        }
                    }
                    Intent data = new Intent();
                    if(isCorrect == false){
                        setResult(RESULT_CANCELED,data);
                        finish();
                    }
                    data.putExtra("contact_name",value);
                    setResult(RESULT_OK,data);
                    finish();
                }
                return false;
            }
        });
    }
}