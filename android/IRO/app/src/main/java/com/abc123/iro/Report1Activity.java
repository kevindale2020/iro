package com.abc123.iro;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.R.layout.simple_spinner_item;

public class Report1Activity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final int PICKFILE_RESULT_CODE = 1;
    private String user_id;
    private String displayName;
    private String title;
    private String desc;
    private String location;
    private String final_location;
    private String typeid;
    private TextInputLayout et_title;
    private TextInputLayout et_desc;
    private Button btnAttach;
    private ProgressBar loading;
    private Button btnSubmit;
    private Bitmap bitmap;
    private TextView tv_file;
    private TextView tv_file_required;
    private ImageView file_close;
    private Boolean isEmpty = false;
    private Spinner spinner;
    private ArrayList<ReportType> reportTypeArrayList;
    private ArrayList<String> type = new ArrayList<>();
    GoogleApiClient mGoogleApiClient;
    public double currentLatitude, currentLongitude;
    Location myLocation;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0X1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSION = 0x2;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report1);
        setUPGClient();

        //custom toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //for the session values
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.USER_ID);

        //arraylist
        reportTypeArrayList = new ArrayList<>();

        //spinner
        spinner = findViewById(R.id.spinner1);

        //textview
        tv_file = findViewById(R.id.file);
        tv_file_required = findViewById(R.id.tv_file_required);
        file_close = findViewById(R.id.file_close);

        //edit text
        et_title = findViewById(R.id.title);
        et_desc = findViewById(R.id.description);

        //button
        btnAttach = findViewById(R.id.btnAttach);
        btnSubmit = findViewById(R.id.btnSubmit);

        //progressbar
        loading = findViewById(R.id.loading);

        //button attach is clicked
        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });

        file_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_file.setText(null);
                tv_file.setVisibility(TextView.GONE);
                file_close.setVisibility(ImageView.GONE);
                bitmap.recycle();
            }
        });

        retrieveType();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    ReportType reportType = reportTypeArrayList.get(i);
                    //Toast.makeText(getApplicationContext(), "" + liveType.getId(), Toast.LENGTH_SHORT).show();
                    typeid = String.valueOf(reportType.getId());
                    isEmpty = false;
                } else {
                    TextView tv = (TextView) view;
                    tv.setTextColor(Color.GRAY);
                    isEmpty = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = et_title.getEditText().getText().toString().trim();
                desc = et_desc.getEditText().getText().toString().trim();
                final_location = getLocation(getApplicationContext(), currentLatitude, currentLongitude);

                if (!validateTitle() | !validateDesc() | !validateImage() | !validateIncident() | !validateLocation()) {
                    return;
                }
                submit();
            }
        });
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkPermission();
    }

    private void checkPermission() {
        int permissionLocation = ContextCompat.checkSelfPermission(Report1Activity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermission = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);

            if (!listPermission.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermission.toArray(new String[listPermission.size()]), REQUEST_ID_MULTIPLE_PERMISSION);
            }
        } else {
            getMyLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        } else {
            checkPermission();
        }
    }

    private void getMyLocation() {
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(Report1Activity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    //locationRequest.setInterval(3000);
                    //locationRequest.setFastestInterval(3000);
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
                                            .checkSelfPermission(Report1Activity.this,
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
                                        status.startResolutionForResult(Report1Activity.this,
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
        if (myLocation != null) {
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public String getLocation(Context context, double LATITUDE, double LONGITUDE) {

        //Set Address
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {


                location = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                //String city = addresses.get(0).getLocality();
                //String state = addresses.get(0).getAdminArea();
                //String country = addresses.get(0).getCountryName();
                //String phone = addresses.get(0).getPhone();
                //String postalCode = addresses.get(0).getPostalCode();
                //String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL


                // Toast.makeText(context,phone,Toast.LENGTH_SHORT).show();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICKFILE_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        try {
            // When an Image is picked
            if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK
                    && null != data) {
                //one image
                if (data.getData() != null) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            //bitmapList.add(bitmap);
                            cursor = this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                //imagesEncodedList.add(displayName);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                    }
                    //new image upload code
                    //textViews[counter].setVisibility(TextView.VISIBLE);
                    //textViews[counter].setText(displayName);
                    //imgs_file_close[counter].setVisibility(ImageView.VISIBLE);
                    //Data dt = new Data(getStringImage(bitmap), displayName);
                    //imageList.add(dt);
                    //newDataArray = gson.toJson(imageList);
                    //counter++;
                    //if(counter==5) {
                    //btnAttach.setEnabled(false);
                    //btnAttach.setBackgroundColor(Color.LTGRAY);
                    //}
                    tv_file.setVisibility(TextView.VISIBLE);
                    tv_file.setText(displayName);
                    file_close.setVisibility(ImageView.VISIBLE);
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong " + e, Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getStringImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;
    }

    public boolean validateTitle() {
        if (title.isEmpty()) {
            et_title.setError("This is required.");
            return false;
        } else if (!title.matches("[a-zA-Z0-9.?#'#,\\- ]*")) {
            et_title.setError("No special characters are allowed");
            return false;
        } else {
            et_title.setError(null);
            et_title.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateDesc() {
        if (desc.isEmpty()) {
            et_desc.setError("This is required.");
            return false;
        } else if (!desc.matches("[a-zA-Z0-9.?#'#,\\- ]*")) {
            et_desc.setError("No special characters are allowed");
            return false;
        } else {
            et_desc.setError(null);
            et_desc.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateImage() {
        if (tv_file.getText().toString().length() == 0) {
            tv_file_required.setVisibility(TextView.VISIBLE);
            tv_file_required.setText("Image required.");
            tv_file_required.setTextColor(Color.parseColor("#FFDD2C00"));
            return false;
        } else {
            return true;
        }
    }

    public boolean validateIncident() {
        if (isEmpty == true) {
            spinner.requestFocus();
            TextView errorText = (TextView) spinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("This is required");//changes the selected item text to this
            return false;
        } else {
            return true;
        }
    }

    public boolean validateLocation() {
        if (final_location == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Report1Activity.this);

            builder.setTitle("Message");
            builder.setMessage("Please turn on your GPS or find a strong signal");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
            return false;
        } else {
            return true;
        }
    }

    public void retrieveType() {
        String url = "http://192.168.137.1:8080/IRO/Android/report_type.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/type.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                JSONArray resultArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < resultArray.length(); i++) {
                                    JSONObject object = resultArray.getJSONObject(i);
                                    ReportType reportType = new ReportType(object.getInt("report_type_id"), object.getString("report_type_desc"));
                                    reportTypeArrayList.add(reportType);
                                }

                                for (int i = 0; i < reportTypeArrayList.size(); i++) {
                                    type.add(reportTypeArrayList.get(i).getType());
                                }


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Report1Activity.this, simple_spinner_item, type) {
                                    @Override
                                    public boolean isEnabled(int position) {
                                        if (position == 0) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    }

                                    @Override
                                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                        View view = super.getDropDownView(position, convertView, parent);
                                        TextView tv = (TextView) view;
                                        if (position == 0) {
                                            // Set the hint text color gray
                                            tv.setTextColor(Color.GRAY);
                                        } else {
                                            tv.setTextColor(Color.BLACK);
                                        }
                                        return view;
                                    }
                                };
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(spinnerArrayAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        AppController.getmInstance().addToRequestQueue(stringRequest);

    }

    public void submit() {
        loading.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);
        String url = "http://192.168.137.1:8080/IRO/Android/report1.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoption_application.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Report1Activity.this);

                                builder.setTitle("Incident Report");
                                builder.setMessage("Your report has been submitted.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        //Intent intent = new Intent(getApplicationContext(), AdoptionActivity.class);
                                        //startActivity(intent);
                                        finish();
                                        //dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();

                            } /*else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AdoptionFormActivity.this);

                                builder.setTitle("Failed to submit");
                                builder.setMessage(message);

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        Intent intent = new Intent(getApplicationContext(), AdoptionActivity.class);
                                        startActivity(intent);
                                        finish();
                                        //dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btnSubmit.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", user_id);
                params.put("title", title);
                params.put("desc", desc);
                params.put("location", final_location);
                params.put("photo", getStringImage(bitmap));
                params.put("filename", displayName);
                params.put("type", typeid);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
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
}
