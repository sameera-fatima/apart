package com.example1.apart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

//public class MainActivity extends AppCompatActivity implements LocationListener {
public class MainActivity extends AppCompatActivity {
    private static int Time_Out = 3000;

   // private Handler handler1;


    // private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, Login_Form.class);
                startActivity(homeIntent);
                finish();

            }
        },Time_Out);


        /////////////////////////////////////////startloginActivity();
      /* handler1 = new Handler();
        public void session1(){
            Thread thread1 = new Thread(){
                @Override
                public void run() {
                    for(int i=0;i<10;i++)
                    {
                        try {
                            sleep(100);

                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        handler1.post(new Runnable() {
                            @Override
                            public void run() {


                            }
                        })


                    }

                }
            };*/

        }
        //Toast.makeText(this, "GPS tracking enabled1111", Toast.LENGTH_SHORT).show();
        //startThread();

        //stopThread();



    /*public void startThread() {

        Toast.makeText(this, "GPS tracking enabled1111", Toast.LENGTH_SHORT).show();
    }
    public void stopThread() {
        Toast.makeText(this, "GPS tracking enabled2222", Toast.LENGTH_SHORT).show();
    }*/




       // startSignupActivity();


        /*WebView webView = (WebView)
        findViewById(webView);
        webView.loadUrl("https://www.youtube.com/results?search_query=tu+yaad+aaya+lyrics");
        */


        //startTrackerService();


    private void startTrackerService() {
        startActivity(new Intent(this, MapsActivity.class));



        Toast.makeText(this, "GPS tracking enabled", Toast.LENGTH_SHORT).show();


        // finish();

    }



   // public void startSignupActivity(){
       // startActivity(new Intent(MainActivity.this,Signup_Form.class));
        //startActivity(new Intent(MainActivity.this , MapsActivity.class));
        //finish();
    //}

    public void startloginActivity(){

        startActivity(new Intent(MainActivity.this,Login_Form.class));


        //startActivity(new Intent(MainActivity.this , MapsActivity.class));
        //finish();
    }
   /* public void webpage(){

        String url = "https://www.youtube.com/results?search_query=tu+yaad+aaya+lyrics";
        WebView webView = (WebView);
        findViewById(R.id.webview);
        webView.loadUrl("https://www.youtube.com/results?search_query=tu+yaad+aaya+lyrics");

        //WebView web = (WebView) findViewById(R.id.webView);
    }*/
    // MainActivity(new Intent(this, MapsActivity .class));
}

        /* locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
      Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        onLocationChanged(location);




    }

    @Override
    public void onLocationChanged(Location location) {
        double longitude=location.getLongitude();
        double latitude=location.getLatitude();

        location_helper update = new location_helper(
                longitude,
                latitude
        );

        FirebaseDatabase.getInstance().getReference("/new")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())



                //.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(update).addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(MainActivity.this, "Registration complete",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {


    }*/



