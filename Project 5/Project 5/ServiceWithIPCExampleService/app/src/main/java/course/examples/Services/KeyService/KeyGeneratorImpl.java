package course.examples.Services.KeyService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


import course.examples.Services.KeyCommon.KeyGenerator;

//Class storing all the data to be sent
class Information {
	private String title;
	private String artist;
	private String images;

	public Information(String title, String artist, String images){
		this.title = title;
		this.artist = artist;
		this.images = images;
	}


	public String giveans(){

		return this.title+"&"+this.artist+"&"+this.images;
	}

}

public class KeyGeneratorImpl extends Service{

	private static String CHANNEL_ID = "Music player style" ;
	private Notification notification ;
	private static final int NOTIFICATION_ID = 1;


	//Foreground service and notification channel for server

	@Override
	public void onCreate() {
		super.onCreate();
		this.createNotificationChannel();

		notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
				.setSmallIcon(android.R.drawable.ic_media_play)
				.setOngoing(true).setContentTitle("Music Playing")
				.setContentText("Click to Access Music Player")
				.setTicker("Music is playing!")
				.build();
		startForeground(NOTIFICATION_ID, notification);
	}



	// Set of already assigned IDs
	// Note: These keys are not guaranteed to be unique if the Service is killed 
	// and restarted.
	
	private final static Set<Information> mIDs = new HashSet<Information>();

	// Implement the Stub for this Object
	private final KeyGenerator.Stub mBinder = new KeyGenerator.Stub() {



	
		// Remote method for fetching all the data
		public String[] getEveryKey() {
		

			Information[] arr = new Information[10];
			Drawable d = getResources().getDrawable(R.drawable.a_day_in_the_life);
			arr[0] = new Information("A day in the life","The beatles",convertToString(d));
			d = getResources().getDrawable(R.drawable.counting_stars);
			arr[1] = new Information("Counting stars","OneRepublic", convertToString(d));
			d = getResources().getDrawable(R.drawable.hello);
			arr[2] = new Information("Hello","Adele",convertToString(d));
			d = getResources().getDrawable(R.drawable.hymm_for_the_weekend);
			arr[6] = new Information("Hymm for the weekend","Coldplay",convertToString(d));
			d = getResources().getDrawable(R.drawable.kashmir);
			arr[5] = new Information("Immigrant Song","Led Zepplin",convertToString(d));
			d = getResources().getDrawable(R.drawable.memories);
			arr[9] = new Information("Memories","Maroon 5",convertToString(d));
			d = getResources().getDrawable(R.drawable.november_pain);
			arr[3] = new Information("November Pain","Guns N' Roses",convertToString(d));
			d = getResources().getDrawable(R.drawable.shape_of_you);
			arr[7] = new Information("Shape of you","Ed Sheeran",convertToString(d));
			d = getResources().getDrawable(R.drawable.thats_life);
			arr[8] = new Information("That's life","Frank Sinatra",convertToString(d));
			d = getResources().getDrawable(R.drawable.wake_me_up);
			arr[4] = new Information("Wake me up","Avicii",convertToString(d));
			// Acquire lock to ensure exclusive access to mIDs
			// Then examine and modify mIDs
			Information id;

			checkCallingPermission("course.examples.Services.KeyService.GEN_ID") ;
			synchronized (mIDs) {
				
				do {
				
					id = arr[0];
				
				} while (mIDs.contains(id));

				mIDs.add(id);
			}
			String[] s = new String[10];
			for(int i = 0 ; i < 10 ; i++){
				id = arr[i];
				s[i] = id.giveans();
			}

			return s;
		}


		//Remote method fetching data by the ID provided by the client


		@Override
		public String getKeyById(int pos)  {
			Information[] arr = new Information[10];
			Drawable d = getResources().getDrawable(R.drawable.a_day_in_the_life);
			arr[0] = new Information("A day in the life","The beatles",convertToString(d));
			d = getResources().getDrawable(R.drawable.counting_stars);
			arr[1] = new Information("Counting stars","OneRepublic", convertToString(d));
			d = getResources().getDrawable(R.drawable.hello);
			arr[2] = new Information("Hello","Adele",convertToString(d));
			d = getResources().getDrawable(R.drawable.hymm_for_the_weekend);
			arr[6] = new Information("Hymm for the weekend","Coldplay",convertToString(d));
			d = getResources().getDrawable(R.drawable.kashmir);
			arr[5] = new Information("Immigrant Song","Led Zepplin",convertToString(d));
			d = getResources().getDrawable(R.drawable.memories);
			arr[9] = new Information("Memories","Maroon 5",convertToString(d));
			d = getResources().getDrawable(R.drawable.november_pain);
			arr[3] = new Information("November Pain","Guns N' Roses",convertToString(d));
			d = getResources().getDrawable(R.drawable.shape_of_you);
			arr[7] = new Information("Shape of you","Ed Sheeran",convertToString(d));
			d = getResources().getDrawable(R.drawable.thats_life);
			arr[8] = new Information("That's life","Frank Sinatra",convertToString(d));
			d = getResources().getDrawable(R.drawable.wake_me_up);
			arr[4] = new Information("Wake me up","Avicii",convertToString(d));
			Information id;
			checkCallingPermission("course.examples.Services.KeyService.GEN_ID") ;
			synchronized (mIDs) {

				do {

					id = arr[0];

				} while (mIDs.contains(id));

				mIDs.add(id);
			}
			String s;
			id = arr[pos-1];
			s = id.giveans();

			return s;
		}

		//Remote mehod which sends all the URLs to the client

		public String[] getUrl(){
			String[] arr = new String[6];
			arr[0]= "https://upload.wikimedia.org/wikipedia/en/e/ef/A_Day_in_the_Life_verse_-_Beatles.ogg";
			arr[1] = "https://upload.wikimedia.org/wikipedia/en/0/08/OneRepublic_-_Counting_Stars.ogg";
			arr[2] = "https://upload.wikimedia.org/wikipedia/en/f/f4/Adele_-_Hello_Clip.ogg";
			arr[3] = "https://upload.wikimedia.org/wikipedia/en/f/ff/Guns_N%27_Roses-_November_Rain.ogg";
			arr[4] = "https://upload.wikimedia.org/wikipedia/en/5/5d/Avicii_-_Wake_Me_Up.ogg";
			arr[5] = "https://upload.wikimedia.org/wikipedia/en/e/e5/Immigrant_Song_by_Led_Zeppelin.ogg";
			return arr;
		}
	};



	// Return the Stub defined above
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	//Converting every image from bitmap to string in order to pass it to client

	public String convertToString(Drawable d){


		Bitmap bitmap = ((BitmapDrawable)d).getBitmap();

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// In case you want to compress your image, here it's at 40%
		bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream.toByteArray();

		return Base64.encodeToString(byteArray, Base64.DEFAULT);

	}

	private void createNotificationChannel() {
		// Create the NotificationChannel, but only on API 26+ because
		// the NotificationChannel class is new and not in the support library
		CharSequence name = "Music player notification";
		String description = "The channel for music player notifications";
		int importance = NotificationManager.IMPORTANCE_DEFAULT;
		NotificationChannel channel = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			channel = new NotificationChannel(CHANNEL_ID, name, importance);
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			channel.setDescription(description);
		}
		// Register the channel with the system; you can't change the importance
		// or other notification behaviors after this
		NotificationManager notificationManager = getSystemService(NotificationManager.class);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			notificationManager.createNotificationChannel(channel);
		}
	}
}
