package course.examples.Services.KeyClient;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.Toast;
import android.app.Service;

import java.util.ArrayList;

public class SecondActivity extends Activity implements ExampleAdapter.OnSongListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    MediaPlayer player = new MediaPlayer();;
    String str2[] = new String[6];


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("Music Client");

        //get the data and convert bitmap into image, start recycler view
        Intent i = getIntent();
        String[] str = i.getStringArrayExtra("music_list");
        str2 = i.getStringArrayExtra("url_list");
        ArrayList<ExampleItem> exampleList = new ArrayList<>();
        for(int j = 0 ; j < 6 ; j++){
            String[] answer = str[j].split("&",-1);
            byte[] imageBytes = Base64.decode(answer[2], Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            exampleList.add(new ExampleItem(decodedImage,answer[0],answer[1]));
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList,this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    //End music when back key is pressed
    @Override
    public void onBackPressed() {
        player.release();
        finish();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    //Start the appropriate song when clicked on list item

    @Override
    public void onSongClick(int position) {

        if(player.isPlaying()){
         player.release();
        }
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        if(position == 0){
            try{
                player.setDataSource(str2[0]);
                player.prepare();
                player.start();
            }
            catch(Exception ex){}
        }
        if(position == 1){
            try{
                player.setDataSource(str2[1]);
                player.prepare();
                player.start();
            }
            catch(Exception ex){}
        }
        if(position == 2){
            try{
                player.setDataSource(str2[2]);
                player.prepare();
                player.start();
            }
            catch(Exception ex){}
        }
        if(position == 3){
            try{
                player.setDataSource(str2[3]);
                player.prepare();
                player.start();
            }
            catch(Exception ex){}
        }
        if(position == 4){
            try{
                player.setDataSource(str2[4]);
                player.prepare();
                player.start();
            }
            catch(Exception ex){}
        }
        if(position == 5){
            try{
                player.setDataSource(str2[5]);
                player.prepare();
                player.start();
            }
            catch(Exception ex){}
        }
    }


}

