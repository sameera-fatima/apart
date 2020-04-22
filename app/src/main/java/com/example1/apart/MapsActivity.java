package com.example1.apart;


import androidx.annotation.NonNull;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {
    Handler mHandler = new Handler();


    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;


    private long UPDATE_INTERVAL = 100;
    private long FASTEST_INTERVAL = 100;
    private LocationManager locationManager;
    private LatLng latLng;
    private boolean isPermission;
    DatabaseReference databaseReference;
    DatabaseReference ref1;
    public WebView webView;

    long maxid = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //String fvww = "-android";

        Bundle bundle = getIntent().getExtras();
        int fvv = bundle.getInt("key1");
        String fvvs = String.valueOf(fvv);

        webView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://3.22.98.19:5000/sss?myid="+fvvs);
        //webView.loadUrl("https://www.timeanddate.com/time/zone/india/new-delhi");
        //Toast.makeText(MapsActivity.this,"this is fvv"+fvv,Toast.LENGTH_SHORT).show();
        //webView.setWebViewClient(new WebViewClient());



        if (requestSinglePermission()) {

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                    .addApi(LocationServices.API)
                    .build();

            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            checkLocation();

            ref1 = FirebaseDatabase.getInstance().getReference().child("APART/USERS");
            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                        maxid = (dataSnapshot.getChildrenCount());

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


        }
    }

    ;


    private boolean checkLocation() {

        if (!isLocationEnabled()) {
            showAlert();

        }
        return isLocationEnabled();
    }

    private void showAlert() {


    }

    private boolean isLocationEnabled() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
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

        if (latLng != null) {

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

        if (mLocation == null) {
            startLocationUpdates();
        } else {
            //Toast.makeText(this, "Location not detected", Toast.LENGTH_SHORT).show();
        }

    }

    private void startLocationUpdates() {

        webView.setWebViewClient(new WebViewClient());

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onLocationChanged(Location location) {

        Toast.makeText(MapsActivity.this, "Location Updated", Toast.LENGTH_SHORT).show();

        location_helper helper = new location_helper(
                location.getLongitude(),
                location.getLatitude()
        );

        Bundle bundle = getIntent().getExtras();
        int fv = bundle.getInt("key");
        //Toast.makeText(MapsActivity.this,"this is fv"+fv,Toast.LENGTH_SHORT).show();

        ref1.child(String.valueOf(fv)).child("/Locations")

                .setValue(latLng).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MapsActivity.this, "Location Saved", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(MapsActivity.this, "Location Not Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });


        latLng = new LatLng(location.getLatitude(), location.getLongitude());

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();

        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

}
