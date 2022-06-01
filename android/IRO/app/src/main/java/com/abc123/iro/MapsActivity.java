package com.abc123.iro;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.Inflater;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    double currentLatitude, currentLongitude;
    Location myLocation;
    private Geocoder geocoder;

    private final static int REQUEST_CHECK_SETTINGS_GPS = 0X1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSION = 0x2;

    private List<Places> placesList;
    //private List<Address> addressList;
    private ListView listView;
    private  TextView text;
    private String photo_reference;
    private String imageURL;
    private String imageUrl;
    private String contact;
    private String temp;
    private String fname;
    private String lname;
    private String fullname;
    private String path;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private ProgressDialog dialog;
    private LinearLayout placeLayout;
    private boolean flag = false;
    private boolean alreadyExecuted = false;
    SessionManager sessionManager;
    ImageLoader imageLoader = AppController.getmInstance().getmImageLoader();
    private DrawerLayout drawer;
    //private Button btnBack;
    private View view;
    private NavigationView navigationView;
    private ImageView btnEdit;
    private String user_id;
    private String image_url;
    private NetworkImageView imageView;
    private TextView tv_fullname;
    private TextView tv_notification_badge;
    private int counter;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //handler
        handler = new Handler();

        //for the session values
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.USER_ID);
        //for the side nav
        drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //to set the navigation to control its components
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        view = navigationView.getHeaderView(0);
        //////////////////////////////////////////////
        imageView = view.findViewById(R.id.user_image);
        tv_fullname = view.findViewById(R.id.user_fullname);

        listView = findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty));
        placesList = new ArrayList<Places>();
        text = findViewById(R.id.text);
        placeLayout = findViewById(R.id.placeLayout);
        //btnBack = findViewById(R.id.btnBack);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);

        setUPGClient();
        //getCurrentAddress();
        //AppController.getmInstance().cancelPendingRequest(TAG);
        path = user.get(sessionManager.USER_IMAGE);
        //Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
        if(path=="") {
            imageURL = "http://192.168.137.1:8080/IRO/Android/user_none.png";
            //imageURL = "http://192.168.43.44:8080/IRO/Android/user_none.png";
        } else {
            imageURL = "http://192.168.137.1:8080/IRO/Android/images/users/"+path;
            //imageURL = "http://192.168.43.44:8080/IRO/Android/images/users/"+path;
        }
        fname = user.get(sessionManager.FIRST_NAME);
        lname = user.get(sessionManager.LAST_NAME);
        fullname = fname+" "+lname;

        btnEdit = view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
               startActivity(intent);
               //recreate();
            }
        });

        //bottom navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(a);
                        break;

                    case R.id.navigation_locator:
                        Intent b = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(b);
                        break;

                    case R.id.navigation_adoption:
                        Intent c = new Intent(getApplicationContext(), AdoptionActivity.class);
                        startActivity(c);
                        break;

                    case R.id.navigation_shop:
                        Intent d = new Intent(getApplicationContext(), ItemActivity.class);
                        startActivity(d);
                        break;

                    case R.id.navigation_events:
                        Intent e = new Intent(getApplicationContext(), EventsActivity.class);
                        startActivity(e);
                        break;

                }
                return false;
            }
        });
        /*
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeLayout.setVisibility(LinearLayout.VISIBLE);
                mMap.clear();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 14.0f));
                getNearbyAnimalCare();
                flag = true;
                btnBack.setVisibility(Button.GONE);
            }
        });
        */

        loadUserDetails(user_id);

        content();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adoption, menu);
        //notification
        final MenuItem menuItem1 = menu.findItem(R.id.notification);
        View actionView = MenuItemCompat.getActionView(menuItem1);
        tv_notification_badge = actionView.findViewById(R.id.notification_badge);
        setupBadge();
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem1);
            }
        });
        return true;
    }

    //update notification
    public void updateNotification2(final String userid) {
        String url = "http://192.168.137.1:8080/IRO/Android/update_notification2.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/my_adoptions.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                Log.d("Result","Success: "+success);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid", userid);
                return params;
            }
        };

        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    //count notifications
    public void countNotifications() {
        String url = "http://192.168.137.1:8080/IRO/Android/count_notification.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoption_details.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                counter = jsonObject.getInt("counter");
                            } else {
                                counter = 0;
                            }
                            //Toast.makeText(getApplicationContext(), success, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", user_id);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    private void setupBadge() {

        if (tv_notification_badge!= null) {
            if (counter == 0) {
                if (tv_notification_badge.getVisibility() != View.GONE) {
                    tv_notification_badge.setVisibility(View.GONE);
                }
            } else {
                tv_notification_badge.setText(String.valueOf(Math.min(counter, 99)));
                if (tv_notification_badge.getVisibility() != View.VISIBLE) {
                    tv_notification_badge.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void content() {
        countNotifications();
        setupBadge();
        refresh(1000 * 1);
    }

    private void refresh(int i) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //pushNotification();
                //updateNotification(user_id, String.valueOf(notification_type_id));
                content();
            }

        };
        handler.postDelayed(runnable, i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                Intent intent = new Intent(MapsActivity.this, NotificationActivity.class);
                startActivity(intent);
                updateNotification2(user_id);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadUserDetails(final String id) {
        String url = "http://192.168.137.1:8080/IRO/Android/users.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/users.php";
        final int time = (int) (System.currentTimeMillis());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                JSONArray resultArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < resultArray.length(); i++) {
                                    JSONObject object = resultArray.getJSONObject(i);
                                    if (object.getString("user_image").isEmpty()) {
                                        StringBuilder stringBuilder = new StringBuilder("http://192.168.137.1:8080/IRO/Android/images/users/");
                                        //StringBuilder stringBuilder = new StringBuilder("http://192.168.43.44:8080/IRO/Android/images/users/");
                                        stringBuilder.append("user_none.png");
                                        image_url = stringBuilder.toString();
                                        //Toast.makeText(getApplicationContext(), "user_none.png", Toast.LENGTH_SHORT).show();
                                    } else {
                                        StringBuilder stringBuilder = new StringBuilder("http://192.168.137.1:8080/IRO/Android/images/users/");
                                        //StringBuilder stringBuilder = new StringBuilder("http://192.168.43.44:8080/IRO/Android/images/users/");
                                        stringBuilder.append(object.getString("user_image"));
                                        stringBuilder.append("?timestamp="+String.valueOf(time));
                                        image_url = stringBuilder.toString();
                                    }
                                    imageView.setImageUrl(image_url, imageLoader);
                                    tv_fullname.setText(object.getString("user_fname") + " " + object.getString("user_lname"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);

        /*
        NetworkImageView imageView = view.findViewById(R.id.user_image);
        TextView tv_fullname = (TextView) view.findViewById(R.id.user_fullname);
        //TextView tv_email = (TextView) view.findViewById(R.id.user_email);
        imageView.setImageUrl(imageURL,imageLoader);
        tv_fullname.setText(fullname);
        tv_email.setText(email);
        */


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
        // LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkPermission();
    }

    private void checkPermission() {
        int permissionLocation = ContextCompat.checkSelfPermission(MapsActivity.this,
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
                int permissionLocation = ContextCompat.checkSelfPermission(MapsActivity.this,
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
                                            .checkSelfPermission(MapsActivity.this,
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
                                        status.startResolutionForResult(MapsActivity.this,
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
            //BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.navigation);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 14.0f));
            //MarkerOptions markerOptions = new MarkerOptions();
            //markerOptions.position(new LatLng(currentLatitude, currentLongitude));
            //markerOptions.title("You");
            //markerOptions.icon(icon);
            //mMap.addMarker(markerOptions);
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 14.0f));
            if(!alreadyExecuted) {
                getNearbyAnimalCare();
                loadplaceList();
                alreadyExecuted = true;
            }
            String lat = "10.292935";
            String lng = "123.9706674";
            //getPhone(getApplicationContext(),Double.parseDouble(lat), Double.parseDouble(lng));


        }
    }


    @Override
    public void onBackPressed(){
        /*
        if(flag) {
            super.onBackPressed();
        } else {
            placeLayout.setVisibility(LinearLayout.VISIBLE);
            mMap.clear();
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 14.0f));
            getNearbyAnimalCare();
            flag = true;
        }
        */
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void getNearbyAnimalCare() {
        /*
        StringBuilder stringBuilder =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location="+String.valueOf(currentLatitude)+", "+String.valueOf(currentLongitude));
        stringBuilder.append("&radius=1500");
        stringBuilder.append("&type=veterinary_care");
        stringBuilder.append("&key="+getResources().getString(R.string.google_maps_key));

        String url = stringBuilder.toString();
        */
        String url = "http://192.168.137.1:8080/IRO/Android/nearby_place.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/nearby_place.php";
        Object dataTransfer[] = new Object[4];
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
        dataTransfer[2] = currentLatitude;
        dataTransfer[3] = currentLongitude;

        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(dataTransfer);
        //Toast.makeText(getApplicationContext(),"Showing Nearby Veterinary Care", Toast.LENGTH_SHORT).show();

    }

    private void loadplaceList() {
        String url = "http://192.168.137.1:8080/IRO/Android/nearby_place.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/nearby_place.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //.makeText(getApplicationContext(), "Url: "+url, Toast.LENGTH_LONG).show();

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray resultArray = obj.getJSONArray("data");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < resultArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject jsonObject = resultArray.getJSONObject(i);
                                String lat = jsonObject.getString("place_lat");
                                String lng = jsonObject.getString("place_lng");
                                float final_distance = distance(currentLatitude,currentLongitude,Double.parseDouble(lat),Double.parseDouble(lng));
                                if(final_distance<1500){

                                    StringBuilder stringBuilder = new StringBuilder("http://192.168.137.1:8080/IRO/Android/images/places/");
                                    //StringBuilder stringBuilder = new StringBuilder("http://192.168.43.44:8080/IRO/Android/images/places/");
                                    stringBuilder.append(jsonObject.getString("place_image"));
                                    imageUrl = stringBuilder.toString();
                                    Places places = new Places(imageUrl,jsonObject.getString("place_name"),jsonObject.getString("place_vicinity"),jsonObject.getString("place_contact"),jsonObject.getDouble("place_lat"), jsonObject.getDouble("place_lng"));
                                    placesList.add(places);
                                    /*
                                    LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

                                    MarkerOptions markerOptions = new MarkerOptions();
                                    markerOptions.title(jsonObject.getString("place_name"));
                                    markerOptions.position(latLng);

                                    mMap.addMarker(markerOptions);
                                    */
                                }

                            }
                            //creating custom adapter object
                            PlaceAdapter adapter = new PlaceAdapter(placesList, getApplicationContext(), currentLatitude, currentLongitude,mMap, placeLayout);

                            //adding the adapter to listview

                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        AppController.getmInstance().addToRequestQueue(stringRequest);
        //AppController.getmInstance().cancelPendingRequest(TAG);
        //alreadyExecuted = true;
    }

    public static void getPhone(Context context, double LATITUDE, double LONGITUDE) {

        //Set Address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {



                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                //String phone = addresses.get(0).getPhone();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                String phone = addresses.get(0).getPhone();

                Toast.makeText(context,phone,Toast.LENGTH_SHORT).show();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private float distance(double currentlatitude, double currentlongitude, double originLat, double originLon) {

        float[] results = new float[1];
        Location.distanceBetween(currentlatitude, currentlongitude, originLat, originLon, results);
        float distanceInMeters = results[0];

        return distanceInMeters;
    }

    //side navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);

                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to logout?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        sessionManager.logout();
                        finish();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                break;

            case R.id.nav_password:
                Intent a = new Intent(MapsActivity.this, ChangePasswordActivity.class);
                startActivity(a);
                break;

            case R.id.nav_adoption:
                Intent b = new Intent(MapsActivity.this, MyAdoptionActivity.class );
                startActivity(b);
                break;

            case R.id.nav_report1:
                Intent c = new Intent(MapsActivity.this, Report1Activity.class);
                startActivity(c);
                break;

            case R.id.nav_volunteer:
                Intent d = new Intent(MapsActivity.this, VolunteerActivity.class);
                startActivity(d);
                break;

            case R.id.nav_guidelines:
                Intent e = new Intent(MapsActivity.this, GuidelinesActivity.class);
                startActivity(e);
                break;

            case R.id.nav_my_reports:
                Intent f = new Intent(MapsActivity.this, MyReportActivity.class);
                startActivity(f);
                break;

            case R.id.nav_donation:
                Intent g = new Intent(MapsActivity.this, DonationActivity.class);
                startActivity(g);
                break;
        }
        return  true;
    }

    @Override
    public void onRestart() {
        super.onRestart();
        loadUserDetails(user_id);
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        //Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        //startActivity(intent);
        //finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }
}
