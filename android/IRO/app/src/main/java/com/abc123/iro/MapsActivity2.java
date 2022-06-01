package com.abc123.iro;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MapsActivity2 extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, TaskLoadedCallback{

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    double currentLatitude, currentLongitude;
    Location myLocation;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0X1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSION = 0x2;
    private double dest_lat;
    private double dest_lng;
    private String dest_name;
    private Polyline currentPolyline;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View view;
    private TextView tv_distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        //setting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //textview
        tv_distance = findViewById(R.id.distance);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        setUPGClient();
        dest_lat = getIntent().getDoubleExtra(PlaceAdapter.DESTINATION_LATITUDE,0);
        dest_lng = getIntent().getDoubleExtra(PlaceAdapter.DESTINATION_LONGITUDE,0);
        dest_name = getIntent().getStringExtra(PlaceAdapter.DESTINATION_NAME);

    }

    private void setUPGClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkPermission();
    }

    private void checkPermission() {
        int permissionLocation = ContextCompat.checkSelfPermission(MapsActivity2.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermission = new ArrayList<>();
        if(permissionLocation!= PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);

            if(!listPermission.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermission.toArray(new String[listPermission.size()]), REQUEST_ID_MULTIPLE_PERMISSION);
            }
        }
        else {
            getMyLocation();
            mMap.setMyLocationEnabled(true);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionLocation==PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
            mMap.setMyLocationEnabled(true);
        }
        else {
            checkPermission();
        }
    }

    private void getMyLocation(){
        if(mGoogleApiClient!=null) {
            if (mGoogleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(MapsActivity2.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(15*1000);
                    locationRequest.setFastestInterval(15*1000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    PendingResult<Status> statusPendingResult = LocationServices.FusedLocationApi
                            .requestLocationUpdates(mGoogleApiClient, locationRequest, (LocationListener) this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(mGoogleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(MapsActivity2.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {


                                        myLocation = LocationServices.FusedLocationApi
                                                .getLastLocation(mGoogleApiClient);


                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(MapsActivity2.this,
                                                REQUEST_CHECK_SETTINGS_GPS);


                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }


                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });

                }
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        myLocation = location;
        if(myLocation!=null){
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            LatLng current = new LatLng(currentLatitude,currentLongitude);
            LatLng destination = new LatLng(dest_lat, dest_lng);
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 14.0f));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(dest_name);
            markerOptions.position(destination);
            mMap.addMarker(markerOptions);
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current,14.0f));
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(current);
            builder.include(destination);
            LatLngBounds bounds = builder.build();
            int width = this.getResources().getDisplayMetrics().widthPixels;
            int height = this.getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.30);
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,width,height,padding);
            mMap.moveCamera(cu);
            new FetchURL(MapsActivity2.this).execute(getUrl(current,destination,"driving"),"driving");
            //mMap.addPolyline(new PolylineOptions().add(current,destination).width(10).color(Color.RED));

            //set the distance
            tv_distance.setText(String.valueOf(String.format("%.2f",distance(currentLatitude, currentLongitude, dest_lat, dest_lng)/1000))+" km");
        }
    }

    public String getUrl(LatLng origin, LatLng destination, String directionMode) {
        String str_origin = "origin=" + origin.latitude + "," +origin.longitude;
        String str_dest = "destination=" +destination.latitude + "," + destination.longitude;
        String mode = "mode=" + directionMode;
        String parameter = str_origin + "&" +str_dest + "&" +mode;
        String format = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" +format + "?"
                + parameter + "&key="+getResources().getString(R.string.google_maps_key);
        return url;

    }

    @Override
    public void onTaskDone(Object... values) {
        if(currentPolyline!=null) {
            currentPolyline.remove();
        }
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //action
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private float distance(double currentlatitude, double currentlongitude, double originLat, double originLon) {

        float[] results = new float[1];
        Location.distanceBetween(currentlatitude, currentlongitude, originLat, originLon, results);
        float distanceInMeters = results[0];

        return distanceInMeters;
    }

}
