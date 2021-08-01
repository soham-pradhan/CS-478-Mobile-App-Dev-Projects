package course.examples.Services.KeyClient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


import course.examples.Services.KeyCommon.KeyGenerator;


public class KeyServiceUser extends Activity {

	protected static final String TAG = "KeyServiceUser";
	protected static final int PERMISSION_REQUEST = 0;
	public static KeyGenerator mKeyGeneratorService;
	private boolean mIsBound = false;
	private String[] str = new String[10];
	private String[] temp = {"","Item 1","Item 2","Item 3","Item 4","Item 5","Item 6"};
	private String[] str2 = new String[6];
	public int counter = 0 ;
	public int counter_2 = 0;

	//Used to restore state on configuration change (Extra feature addded)



	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.main);

		setTitle("Music Client");

		final TextView output = (TextView) findViewById(R.id.output);
		final Button goButton = (Button) findViewById(R.id.go_button);
		final Button unbind = (Button) findViewById(R.id.go_button2);
		final Button list = (Button) findViewById(R.id.go_button3);
		final Spinner dropdown = findViewById(R.id.spinner);
		final TextView indicator = findViewById(R.id.indicator);
	;

		//Spinner used to select and display data of a particular item
		ArrayAdapter<String>adapter = new ArrayAdapter<String>(KeyServiceUser.this, android.R.layout.simple_spinner_item,temp);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dropdown.setAdapter(adapter);
		dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				if(position>0){
					try {
						counter  = 1;
						String sftr = mKeyGeneratorService.getKeyById(position);
						String[] answer = sftr.split("&",-1);
						TextView output1 = findViewById(R.id.output1);
						TextView output2 = findViewById(R.id.output2);
						output1.setText(answer[0]);
						output2.setText(answer[1]);
						ImageView image = findViewById(R.id.mImage);
						byte[] imageBytes = Base64.decode(answer[2], Base64.DEFAULT);
						Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
						image.setImageBitmap(decodedImage);
						output1.setVisibility(view.VISIBLE);
						output2.setVisibility(view.VISIBLE);
						image.setVisibility(view.VISIBLE);
					} catch (RemoteException exception) {
						exception.printStackTrace();
					}

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

		//Bind service, if not not bound, bind it
		goButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {


					if (mIsBound) {
						list.setVisibility(v.VISIBLE);
						dropdown.setVisibility(v.VISIBLE);
						TextView t = findViewById(R.id.indicator);
						t.setText("The service has successfully been bounded");
						t.setGravity(Gravity.CENTER);
						counter_2 = 1;
					}
					else {
						Log.i(TAG, "Ugo says that the service was not bound!");
						requestPermissions(new String[] {"course.examples.Services.KeyService.GEN_ID"},0);
						checkBindingAndBind();
						list.setVisibility(v.VISIBLE);
						dropdown.setVisibility(v.VISIBLE);
						TextView t = findViewById(R.id.indicator);
						t.setText("The service has successfully been bounded");

					}

			}


		});


		//Unbind the service and gray out parts of UI
		unbind.setOnClickListener(new OnClickListener() {
			public void onClick(View v){
				if(counter_2 == 0){
					TextView t = findViewById(R.id.indicator);
					t.setText("Service cannot be unbinded if it was never bounded");
					return;
				}
				mIsBound = false;
				TextView t = findViewById(R.id.indicator);
				t.setText("The service has successfully unbounded");
				list.setVisibility(View.GONE);
				dropdown.setVisibility(View.GONE);
				if(counter == 1){
					TextView temp = findViewById(R.id.output1);
					temp.setVisibility(View.GONE);
					TextView temp2 = findViewById(R.id.output2);
					temp2.setVisibility(View.GONE);
					ImageView image = findViewById(R.id.mImage);
					image.setVisibility(View.GONE);
				}

				pleasestop();
			}
		});

		//Get the data of every item on the list and start intent for second activity and pass the data obtained via intent

		list.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					str = mKeyGeneratorService.getEveryKey();
					str2 = mKeyGeneratorService.getUrl();
					Intent i = new Intent(getApplicationContext(),SecondActivity.class);
					i.putExtra("music_list",str);
					i.putExtra("url_list",str2);
					startActivity(i);
				}
				catch(RemoteException ex){}
			}
		});

	}


	//Used to toast if initial permission is denied.

	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

		switch (requestCode) {
			case PERMISSION_REQUEST: {

				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

				}
				else {
					Toast.makeText(this, "BUMMER: No Permission :-(", Toast.LENGTH_LONG).show() ;
				}
			}
			default: {
				// do nothing
			}
		}
	}



	// Bind to KeyGenerator Service
	@Override
	protected void onStart() {
		super.onStart();

		if (checkSelfPermission("course.examples.Services.KeyService.GEN_ID")
				!= PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this,
					new String[]{"course.examples.Services.KeyService.GEN_ID"},
					PERMISSION_REQUEST);
		}
		else {
			checkBindingAndBind();
		}
	}


	protected void checkBindingAndBind() {
		if (!mIsBound) {

			boolean b = false;
			Intent i = new Intent(KeyGenerator.class.getName());
			ResolveInfo info = getPackageManager().resolveService(i, 0);
			i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));

			b = bindService(i, this.mConnection, Context.BIND_AUTO_CREATE);
			if (b) {
				Log.i(TAG, "Ugo says bindService() succeeded!");
			} else {
				Log.i(TAG, "Ugo says bindService() failed!");
			}
		}
	}





	// Unbind from KeyGenerator Service

	protected void pleasestop() {

		super.onPause();

		if (mIsBound) {
			unbindService(this.mConnection);
		}
	}

	public final ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder iservice) {

			mKeyGeneratorService = KeyGenerator.Stub.asInterface(iservice);
			mIsBound = true;

		}

		public void onServiceDisconnected(ComponentName className) {

			mKeyGeneratorService = null;
			mIsBound = false;
			SecondActivity sa = new SecondActivity();
			sa.onBackPressed();

		}
	};

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}


}