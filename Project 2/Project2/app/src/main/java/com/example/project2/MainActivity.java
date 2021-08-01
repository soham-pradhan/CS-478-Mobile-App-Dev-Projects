package com.example.project2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.project2.myviewholder.x;


public class MainActivity extends AppCompatActivity {

    int mgrid = 1;
    RecyclerView rcv;
    com.example.project2.myadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Project 2");
        rcv = (RecyclerView) findViewById(R.id.r1);

        //Remembers the user preference of view everytime user changes orientation
        if(savedInstanceState!=null)
        mgrid = savedInstanceState.getInt("grid");
        if(mgrid == 1)
            rcv.setLayoutManager(new LinearLayoutManager(this));
        if(mgrid == 0)
            rcv.setLayoutManager(new GridLayoutManager(this,2));

        adapter = new com.example.project2.myadapter(dataqueue());
        rcv.setAdapter(adapter);
    }

    public ArrayList<com.example.project2.Model> dataqueue() {

        //Creating an arraylist of objects which stores all the data which needs to be put into the views

        ArrayList<com.example.project2.Model> holder = new ArrayList<>();
        com.example.project2.Model ob1 = new com.example.project2.Model();
        ob1.setDesc("Ed Sheeran");
        ob1.setHeader("Shape of you");
        ob1.setImgname(R.drawable.shape_of_you);
        holder.add(ob1);

        com.example.project2.Model ob2 = new com.example.project2.Model();
        ob2.setHeader("A day in the life");
        ob2.setDesc("The beatles");
        ob2.setImgname(R.drawable.a_day_in_the_life);
        holder.add(ob2);

        com.example.project2.Model ob3 = new com.example.project2.Model();
        ob3.setHeader("Hymm for the weekend");
        ob3.setDesc("Coldplay");
        ob3.setImgname(R.drawable.hymm_for_the_weekend);
        holder.add(ob3);

        com.example.project2.Model ob4 = new com.example.project2.Model();
        ob4.setHeader("Memories");
        ob4.setDesc("Maroon 5");
        ob4.setImgname(R.drawable.memories);
        holder.add(ob4);

        com.example.project2.Model ob5 = new com.example.project2.Model();
        ob5.setHeader("Kashmir");
        ob5.setDesc("Led Zepplin");
        ob5.setImgname(R.drawable.kashmir);
        holder.add(ob5);

        com.example.project2.Model ob6 = new com.example.project2.Model();
        ob6.setHeader("November Pain");
        ob6.setDesc("Guns N Roses");
        ob6.setImgname(R.drawable.november_pain);
        holder.add(ob6);

        com.example.project2.Model ob7 = new com.example.project2.Model();
        ob7.setHeader("Hello");
        ob7.setDesc("Adele");
        ob7.setImgname(R.drawable.hello);
        holder.add(ob7);

        com.example.project2.Model ob8 = new com.example.project2.Model();
        ob8.setHeader("Counting Stars");
        ob8.setDesc("OneRepublic");
        ob8.setImgname(R.drawable.counting_stars);
        holder.add(ob8);

        com.example.project2.Model ob9 = new com.example.project2.Model();
        ob9.setHeader("That's life");
        ob9.setDesc("Frank Sinatra");
        ob9.setImgname(R.drawable.thats_life);
        holder.add(ob9);

        com.example.project2.Model ob10 = new com.example.project2.Model();
        ob10.setHeader("Wake me up");
        ob10.setDesc("Avicii");
        ob10.setImgname(R.drawable.wake_me_up);
        holder.add(ob10);


        return holder;

    }

    //Creates view using menuinflator

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    //Used to convert grid view into list view everytime user changes preferences
    public void initListDisplay() {
        rcv = (RecyclerView) findViewById(R.id.r1);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new com.example.project2.myadapter(dataqueue());
        rcv.setAdapter(adapter);
    }

    //Used to convert list view into grid view everytime user changes preferences
    public void initGridDisplay() {
        mgrid = 0;
        rcv = (RecyclerView) findViewById(R.id.r1);
        GridLayoutManager gridlayoutmanager = new GridLayoutManager(this, 2);
        rcv.setLayoutManager(gridlayoutmanager);
        adapter = new com.example.project2.myadapter(dataqueue());
        rcv.setAdapter(adapter);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getInt("grid");
    }

    //Used to preserve user preferences after change of orientation
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("grid",mgrid);
    }

    //Reacts to change view everytime user selects an option from options menu

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.subitem1:
                initListDisplay();
                return true;
            case R.id.subitem2:
                initGridDisplay();
                return true;

        }
        return true;
    }

    //Redirects user to the browser of choice everytime user clicks on the context menu option
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int v = item.getItemId();
        if(v == 100){
            int position = x;
            if (position == 0) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/watch?v=xTvyyoF_LZY"));
                startActivity(Intent.createChooser(i,"Choose your browser"));
            }
            if (position == 1) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.youtube.com/watch?v=usNsCeOV4GM"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (position == 2) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.youtube.com/watch?v=YykjpeuMNEk"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (position == 3) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.youtube.com/watch?v=SlPhMPnQ58k"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (position == 4) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.youtube.com/watch?v=sfR_HWMzgyc"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (position == 5) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.youtube.com/watch?v=8SbUC-UaAxE"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (position == 6) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.youtube.com/watch?v=YQHsXMglC9A"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (position == 7) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.youtube.com/watch?v=hT_nvWreIhg"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (position == 8) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.youtube.com/watch?v=TnlPtaPxXfc"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (position == 9) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.youtube.com/watch?v=IcrbM1l_BoI"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            v = 0;
        }
        if(v == 101){
            int positionn = x;
            if (positionn == 0) {
                Log.d("Heyy","I'm switch case 101");
                Intent viewIntent1 = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/Shape_of_You"));
                startActivity(Intent.createChooser(viewIntent1,"Which browser?"));
            }
            if (positionn == 1) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/A_Day_in_the_Life"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (positionn == 2) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/Hymn_for_the_Weekend"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (positionn == 3) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/Memories_(Maroon_5_song)"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (positionn == 4) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/Kashmir_(song)"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (positionn == 5) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/November_Rain"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (positionn == 6) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/Hello_(Adele_song)"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (positionn == 7) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/Counting_Stars"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (positionn == 8) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/That%27s_Life_(song)"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (positionn == 9) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/Wake_Me_Up_(Avicii_song)"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            v = 0;
        }
        if(v == 102){
            int q = x;
            if (q == 0) {
                Log.d("Heyy","I'm switch case 102");
                Intent viewIntent2 = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/Ed_Sheeran"));
                startActivity(Intent.createChooser(viewIntent2,"Which browser?"));
            }
            if (q == 1) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/The_Beatles"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (q == 2) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/Coldplay"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (q == 3) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/Maroon_5"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (q == 4) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/Led_Zeppelin"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (q == 5) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/Guns_N%27_Roses"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (q == 6) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/Adele"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (q == 7) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/OneRepublic"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (q == 8) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/Frank_Sinatra"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            if (q == 9) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://en.wikipedia.org/wiki/Avicii"));
                startActivity(Intent.createChooser(viewIntent,"Which browser?"));
            }
            v = 0;
        }
        return super.onContextItemSelected(item);

    }

}
