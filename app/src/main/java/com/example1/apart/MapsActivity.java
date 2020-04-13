package com.example1.apart;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.w3c.dom.Text;


public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback ,
//public class MapsActivity extends FragmentActivity
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {


    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;


    private long UPDATE_INTERVAL = 200;
    private long FASTEST_INTERVAL = 200;
    private LocationManager locationManager;
    private LatLng latLng;
    private boolean isPermission;
    DatabaseReference databaseReference;
    DatabaseReference ref1;
    public WebView webView;

    long maxid=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maps);
       if(requestSinglePermission()){

                   //startActivity(new Intent(getApplicationContext(), MapsActivity.class));

                   //handler.sendEmptyMessage(0);


          /* Bundle bundle =getIntent().getExtras();
           int fv11=bundle.getInt("key");
           Toast.makeText(this, "Location not detected"+fv11, Toast.LENGTH_SHORT).show();*/

          /*  WebView webview =new WebView(this);
            setContentView(webview);
          // webview.loadUrl("3.16.1.63:5000/");
           webview.loadUrl("https://stackoverflow.com/questions/23586031/calling-activity-class-method-from-service-class");*/



            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                   // .findFragmentById(R.id.map);
           // mapFragment.getMapAsync(this);

           mGoogleApiClient = new GoogleApiClient.Builder(this)
                  .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
           .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                    .addApi(LocationServices.API)
                    .build();

            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            checkLocation();

            ref1=FirebaseDatabase.getInstance().getReference().child("APART/USERS");
            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                        maxid = (dataSnapshot.getChildrenCount());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


               }
           };






    private boolean checkLocation() {

        if(!isLocationEnabled()) {
            showAlert();

        }
        return isLocationEnabled();
    }

    private void showAlert() {


    }

    private boolean isLocationEnabled() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


    }

    private boolean requestSinglePermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        isPermission = true;

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                        isPermission = false;

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {


                    }
                }).check();
        return isPermission;


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(latLng!=null){

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null){
            startLocationUpdates();
        }
        else{
            //Toast.makeText(this, "Location not detected", Toast.LENGTH_SHORT).show();
        }

    }

    private void startLocationUpdates() {

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)!=
                        PackageManager.PERMISSION_GRANTED){
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest,this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

     @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(MapsActivity.this,"Location Saved",Toast.LENGTH_SHORT).show();


        String msg = "Updated Location" +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());



        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        Toast.makeText(MapsActivity.this,"Location Updated",Toast.LENGTH_SHORT).show();

        view_map();

         /* WebView webview =new WebView(this);
          setContentView(webview);
         webview.loadUrl("https://www.keil.com/download/");*/

        // DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/seh");
        // DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("/seh");

        // ref1.setValue(location.getLatitude());
        // ref.setValue(location.getLongitude());



        location_helper helper = new location_helper(
                location.getLongitude(),
                location.getLatitude()
        );

        //final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/sss");
        //final Location location = locationResult.getLastLocation();
        //ref.setValue(location);
        //FirebaseDatabase.getInstance().getReference("/APART/USERS")

        Bundle bundle =getIntent().getExtras();
        int fv=bundle.getInt("key");

       // ref1.child(String.valueOf(maxid)).child("/Locations")
        ref1.child(String.valueOf(fv)).child("/Locations")
                //.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("/locationss")
                .setValue(latLng).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MapsActivity.this,"Location Saved",Toast.LENGTH_SHORT).show();

                }
                else{

                    Toast.makeText(MapsActivity.this,"Location Not Saved",Toast.LENGTH_SHORT).show();
                }
            }
        });


        latLng = new LatLng(location.getLatitude(), location.getLongitude());



    }
    @Override
    protected void onStart(){
        super.onStart();

        if(mGoogleApiClient != null){
            mGoogleApiClient.connect();

        }

    }

    @Override
    protected void onStop(){
        super.onStop();

        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }



    public void view_map(){
        //class web extends Activity {




           // @Override
           // protected void onCreate(Bundle savedInstanceState) {
                //super.onCreate(savedInstanceState);


        ////////////////////////////////////////////////////////////////////
                /*requestWindowFeature(Window.FEATURE_NO_TITLE);
                //setContentView(R.layout.activity_web);

                webView = (WebView)findViewById(R.id.webView);
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webView.loadUrl("https://www.timeanddate.com/worldclock/india");
                webView.setWebViewClient(new WebViewClient());*/








                /////////////////////////////////////////////////////////

                //WebView webview =new WebView(this);
                //setContentView(webview);
                // webview.loadUrl("3.16.1.63:5000/");
                //webview.loadUrl("https://www.timeanddate.com/worldclock/india");

            }
        }








/*class threeads implements Runnable{

   // public static Object MapsActivity();

    public void main(String[] args) {

        Thread fyp1 = new Thread("FYP1");
        Thread fyp2 = new Thread("FYP2");

        fyp1.start();
        fyp2.start();


        //Toast.makeText("heyy", Toast.LENGTH_SHORT).show();
        //Toast.makeText(threeads.this,"Location Not Saved",Toast.LENGTH_SHORT).show();
        //Toast.makeText()






    }
    @Override
    public void run(){

    }


}
//////////////////////////////////////////////////////////////////////////////////////////////////






/*import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,

    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    com.google.android.gms.location.LocationListener {


    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2000;
    private long FASTEST_INTERVAL = 5000;
    private LocationManager locationManager;
    private LatLng latLng;
    private boolean isPermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        if(requestSinglePermission()){

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

           /* mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                    .addApi(LocationServices.API)
                    .build();

            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            checkLocation();





        }

}

    private boolean checkLocation() {

        if(!isLocationEnabled()) {
            showAlert();

        }
        return isLocationEnabled();
    }

    private void showAlert() {


    }

    private boolean isLocationEnabled() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


    }

    private boolean requestSinglePermission() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        isPermission = true;

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                        isPermission = false;

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {


                    }
                }).check();
        return isPermission;


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(latLng!=null){

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null){
            startLocationUpdates();
    }
        else{
            Toast.makeText(this, "Location not detected", Toast.LENGTH_SHORT).show();
        }

    }

    private void startLocationUpdates() {

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)!=
                        PackageManager.PERMISSION_GRANTED){
            return;
        }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest,this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        location_helper helper = new location_helper(
                location.getLongitude(),
                location.getLatitude()
        );


        FirebaseDatabase.getInstance().getReference("/sendhh")
                //.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MapsActivity.this,"Location Saved",Toast.LENGTH_SHORT).show();

                }
                else{

                    Toast.makeText(MapsActivity.this,"Location Not Saved",Toast.LENGTH_SHORT).show();
                }
            }
        });


        latLng = new LatLng(location.getLatitude(), location.getLongitude());



    }
    @Override
    protected void onStart(){
        super.onStart();

        if(mGoogleApiClient != null){
            mGoogleApiClient.connect();

        }

    }

    @Override
    protected void onStop(){
        super.onStop();

        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }


}*/




