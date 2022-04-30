package com.johnoye742.aider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.media.MediaRecorder;
import android.os.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import org.jsoup.*;
import org.jsoup.nodes.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
MediaRecorder mr;
boolean rec = false;

    File audio;

   public static double longi;
    public static double lat;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
 
            Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/CocoBiker.ttf");
            TextView tb_txt = findViewById(R.id.tb_text);
            tb_txt.setTypeface(tf);
            Button btn = findViewById(R.id.broadcast_btn);
            btn.setTypeface(tf);
			FrameLayout wrapper = findViewById(R.id.btn_wrapper);
            reqPerm();
            reqPerm();
            reqPerm();
            
           /*
            ValueAnimator va = ValueAnimator.ofInt(180, 200);
            va.setDuration(1500);
			va.setRepeatCount(ValueAnimator.INFINITE);
            va.start();
            va.addUpdateListener((animation -> {
				
                int val = (int) animation.getAnimatedValue();
				
                
				
            }));
			*/

            btn.setOnClickListener((v) -> {
				Random rand = new Random();
				int i1 = rand.nextInt();
				 String file = "test24324" + "_aider_.3gp";
				 File path = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
                audio = new File(path, file);

                mr = new MediaRecorder();
               

                mr.setOutputFile(audio.getAbsolutePath());

                mr.setAudioSource(MediaRecorder.AudioSource.MIC);
                mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mr.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                if (!rec) {
                    rec = true;
                    Toast.makeText(getApplicationContext(), "Broadcasting....", Toast.LENGTH_SHORT).show();
                    btn.setText(R.string.stop);
                    

StringBuilder sb = new StringBuilder();
 reqPerm();

                    try {
						
                        mr.prepare();
						mr.start();
						
                    } catch (IOException e) {
                        e.printStackTrace();
         
                for(StackTraceElement e1 : e.getStackTrace()) {
				sb.append(e1.toString());	
                    
                }
         Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_LONG).show();  
		   }
          

                } else if (rec == true) {
                    Toast.makeText(getApplicationContext(), "Stopped saved to " + audio.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    btn.setText(R.string.broadcast);
					rec = false;
					
					try {
						
                    mr.stop();
                    mr.release();
					
					} catch (Exception e) {
						e.printStackTrace();
					}
                  


                      
                         

                    try {
                        String ip = "2347.23"; //InetAddress.getLocalHost().getHostAddress();

                        FusedLocationProviderClient flp = LocationServices.getFusedLocationProviderClient(this);
                        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            flp.getLastLocation().addOnSuccessListener(this, location -> {
                              longi = location.getLongitude();
                              lat = location.getLatitude();
							  Toast.makeText(getApplicationContext(), "latitude:" + lat + "\nlongitute:" +longi + "\nIp:" + ip, Toast.LENGTH_LONG).show();
                             
							 new upload().execute("johnoye742", ""+lat, ""+longi, ip);
                            });
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        StringBuilder sb = new StringBuilder();


                            for(StackTraceElement e1 : e.getStackTrace()) {
                                sb.append(e1.toString());

                            }
                            Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_LONG).show();
                        }


				   

                   

                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
		/*
        Runnable r = () -> {
            while (1 == 1) {

                new notify().execute();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r).start();
		*/
    }

    private void reqPerm() {
        if(Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED ) {
                this.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 742);
            } else if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
				this.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 742);
			} else if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 742);
            }
            
        } else {
			Toast.makeText(getApplicationContext(), "Do not!", Toast.LENGTH_SHORT).show();
		}
    }
   public class upload extends AsyncTask<String, Integer, String> {
        String result = "";
		StringBuilder sb = new StringBuilder();
        protected String doInBackground(String... params) {
			
            try {

                
               Document doc = Jsoup.connect("http://sfaccgaladimawa.great-site.net/aider/upload.php")
                        .userAgent("Mozilla")
                       .data("username", params[0])
                       .data("lat", params[1])
                       .data("longi", params[2])
                       .data("ip", params[3])
                       .data("file", audio.getName(), new FileInputStream(audio))

                        .post();
                result = "Body: \n" + doc.body();
            } catch (Exception e) {
                e.printStackTrace();
         
                for(StackTraceElement e1 : e.getStackTrace()) {
				sb.append(e1.toString());	
                    
                }
           result = sb.toString(); 
		   }
			
			
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }
    private class notify extends AsyncTask<String, Integer, String> {
        String result;
        StringBuilder sb;
        @Override
        protected String doInBackground(String... strings) {
            try {
                Document d = Jsoup.connect("http://sfaccgaladimawa.great-site.net/aider/notification.php")
                        .get();
                result = d.body().toString();
            } catch(Exception e) {
                e.printStackTrace();

                for(StackTraceElement e1 : e.getStackTrace()) {
                    sb.append(e1.toString());

                }
                result = sb.toString();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }
}
