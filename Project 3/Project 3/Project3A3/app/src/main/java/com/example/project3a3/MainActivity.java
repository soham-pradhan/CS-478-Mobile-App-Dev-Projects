package com.example.project3a3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TvShowsFragment.ListSelectionListener{
    public static String[] mTvShowArray;
    public static int[] mImageArray = {R.drawable.b99, R.drawable.himym, R.drawable.friends, R.drawable.office, R.drawable.pandr, R.drawable.sc};

    FragmentManager mFragmentManager;
    private int temp = -1;
    private ImagesFragment mImageFragment;
    private TvShowsFragment mTvShowFragment;
    private int x;
    private FrameLayout mTvShowFrameLayout, mImagesFrameLayout;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "MainActivity";
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ": entered onCreate()");
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        x = intent.getIntExtra("Key", 0);
        if (x == 8) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_mood_24);// set drawable icon
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            //Get list of tv shows
            mTvShowArray = getResources().getStringArray(R.array.tvshows);

            setContentView(R.layout.activity_main);

            // Get references to the ImageFragment and TvShowsFragment
            mTvShowFrameLayout = (FrameLayout) findViewById(R.id.tvshow_fragment_container);
            mImagesFrameLayout = (FrameLayout) findViewById(R.id.image_fragment_container);

            // Get a reference to the SupportFragmentManager instead of original FragmentManager
            mFragmentManager = getSupportFragmentManager();

            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

            // Commit the FragmentTransaction
            mFragmentManager.addOnBackStackChangedListener(
                    new FragmentManager.OnBackStackChangedListener() {
                        public void onBackStackChanged() {
                            setLayout();
                        }
                    });

            //recording recent state of TvshowsFragment
            mTvShowFragment = (TvShowsFragment) mFragmentManager.findFragmentByTag("Hello");
            if(mTvShowFragment==null){
                mTvShowFragment = new TvShowsFragment();
                fragmentTransaction.replace(
                        R.id.tvshow_fragment_container,
                        mTvShowFragment,"Hello");
                fragmentTransaction.commit();
                mFragmentManager.executePendingTransactions();
            }

            mImageFragment = (ImagesFragment) mFragmentManager.findFragmentByTag("Hella");

            //Initializaing if null
            if(mImageFragment==null){
                mImageFragment = new ImagesFragment();


            }
            else{
                if(!mImageFragment.isAdded()){
                    fragmentTransaction.add(
                            R.id.image_fragment_container,
                            mImageFragment,"Hella");
                    fragmentTransaction.commit();
                    fragmentTransaction.addToBackStack(null);
                    mFragmentManager.executePendingTransactions();
                }


            }

            setLayout();

        } else {
            Toast.makeText(this, "A3 can only be started by A2", Toast.LENGTH_SHORT)
                    .show();
            finish();
        }



    }

    //Options Menu to launch A1 and A2

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            //Selecting URl according to the choice of user
            case R.id.item1:
                if(temp == 0){
                    Intent intent = new Intent("edu.uic.cs478.s19.kaboom.");
                    intent.putExtra("Url", "http://en.wikipedia.org/wiki/Brooklyn_Nine-Nine");
                    sendOrderedBroadcast(intent, null);
                }
                if(temp == 1){
                    Intent intent = new Intent("edu.uic.cs478.s19.kaboom.");
                    intent.putExtra("Url", "https://en.wikipedia.org/wiki/How_I_Met_Your_Mother");
                    sendOrderedBroadcast(intent, null);
                }
                if(temp == 2){
                    Intent intent = new Intent("edu.uic.cs478.s19.kaboom.");
                    intent.putExtra("Url", "https://en.wikipedia.org/wiki/Friends");
                    sendOrderedBroadcast(intent, null);
                }
                if(temp == 3){
                    Intent intent = new Intent("edu.uic.cs478.s19.kaboom.");
                    intent.putExtra("Url", "https://en.wikipedia.org/wiki/The_Office_(American_TV_series)");
                    sendOrderedBroadcast(intent, null);
                }
                if(temp == 4){
                    Intent intent = new Intent("edu.uic.cs478.s19.kaboom.");
                    intent.putExtra("Url", "https://en.wikipedia.org/wiki/Parks_and_Recreation");
                    sendOrderedBroadcast(intent, null);
                }
                if(temp ==5){
                    Intent intent = new Intent("edu.uic.cs478.s19.kaboom.");
                    intent.putExtra("Url", "https://en.wikipedia.org/wiki/Schitt%27s_Creek");
                    sendOrderedBroadcast(intent, null);
                }
                return true;
            case R.id.item2:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setLayout() {
        // Determine whether the ImageFragment has been added
        if (!mImageFragment.isAdded()) {
            mTvShowFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            mImagesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //1/2, 2/3
            mTvShowFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 1f));
            mImagesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT, 2f));
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //separate
            mTvShowFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
            mImagesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));

        }
    }
    @Override
    public void onListSelection(int index) {
        temp = index;
        // If the ImageFragment has not been added, add it now
        if (!mImageFragment.isAdded()) {
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();
            fragmentTransaction.add(R.id.image_fragment_container,
                    mImageFragment, "Hella");
        // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }

        if (mImageFragment.getShownIndex() != index) {

            mImageFragment.showQuoteAtIndex(index);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("qwerty",temp);
    }
}