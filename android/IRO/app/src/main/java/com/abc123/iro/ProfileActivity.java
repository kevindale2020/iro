package com.abc123.iro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    SessionManager sessionManager;
    ImageLoader imageLoader = AppController.getmInstance().getmImageLoader();
    private final static String TAG = ProfileActivity.class.getSimpleName();
    private NetworkImageView imageView;
    private TextInputLayout et_fname;
    private TextInputLayout et_lname;
    private TextInputLayout et_nname;
    private TextInputLayout et_occupation;
    private TextInputLayout et_address;
    private TextInputLayout et_email;
    private TextInputLayout et_contact;
    private TextInputLayout et_username;
    private String user_id;
    private String image_url;
    private String fname;
    private String lname;
    private String nname;
    private String address;
    private String email;
    private String contact;
    private String occupation;
    private String username;
    //private NetworkImageView user_image;
    private ImageView btnEdit;
    private Button btnSave;
    private Bitmap bitmap;
    private String current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //setting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        //setting the widgets
        imageView = findViewById(R.id.user_image);
        et_fname = findViewById(R.id.et_fname);
        et_lname = findViewById(R.id.et_lname);
        et_nname = findViewById(R.id.et_nname);
        et_occupation = findViewById(R.id.et_occupation);
        et_address = findViewById(R.id.et_address);
        et_email = findViewById(R.id.et_email);
        et_contact = findViewById(R.id.et_contact);
        et_username = findViewById(R.id.et_username);
        btnEdit = findViewById(R.id.btnEdit);
        btnSave = findViewById(R.id.btnSave);


        //for the session values
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.USER_ID);
        StringBuilder stringBuilder = new StringBuilder("http://192.168.137.1:8080/IRO/Android/images/users/");
        //StringBuilder stringBuilder = new StringBuilder("http://192.168.43.44:8080/IRO/Android/images/users/");
        stringBuilder.append(user.get(sessionManager.USER_IMAGE));
        current = stringBuilder.toString();
        //Toast.makeText(getApplicationContext(), current, Toast.LENGTH_SHORT).show();
        //load user details
        loadUserDetails(user_id);

        //edit
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });

        //save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parsing
                fname = et_fname.getEditText().getText().toString().trim();
                lname = et_lname.getEditText().getText().toString().trim();
                nname = et_nname.getEditText().getText().toString().trim();
                occupation = et_occupation.getEditText().getText().toString().trim();
                address = et_address.getEditText().getText().toString().trim();
                email = et_email.getEditText().getText().toString().trim();
                contact = et_contact.getEditText().getText().toString().trim();
                username = et_username.getEditText().getText().toString().trim();
                if (bitmap != null) {
                    if (!validateFirstName() | !validateLastName() | !validateAddress() | !validateEmail() | !validateContact() | !validateUsername() | !validateNickname() | !validateOccupation()) {
                        return;
                    }
                    SaveEditDetail(user_id, getStringImage(bitmap));
                } else {
                    if (!validateFirstName() | !validateLastName() | !validateAddress() | !validateEmail() | !validateContact() | !validateUsername() | !validateNickname() | !validateOccupation()) {
                        return;
                    }
                    SaveEditDetail(user_id, "0");
                }
            }
        });

    }

    public boolean validateFirstName() {
        if (fname.isEmpty()) {
            //et_fname.setError(Html.fromHtml("<font color='#FF0000'>This is required.</font>"));
            et_fname.setError("This is required.");
            return false;
        } else if (!fname.matches("[a-zA-Z0-9.? ]*")) {
            et_fname.setError("No special characters are allowed");
            return false;
        } else {
            et_fname.setError(null);
            et_fname.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateLastName() {
        if (lname.isEmpty()) {
            et_lname.setError("This is required.");
            return false;
        } else if (!lname.matches("[a-zA-Z0-9.? ]*")) {
            et_lname.setError("No special characters are allowed");
            return false;
        } else {
            et_lname.setError(null);
            et_lname.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateAddress() {
        if (address.isEmpty()) {
            et_address.setError("This is required.");
            return false;
        } else if (!address.matches("[a-zA-Z0-9.?#\\- ]*")) {
            et_address.setError("No special characters are allowed");
            return false;
        } else {
            et_address.setError(null);
            et_address.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateEmail() {
        if (email.isEmpty()) {
            et_email.setError("This is required.");
            return false;
        } else if (!email.matches("[a-zA-Z0-9.?@_ ]*")) {
            et_email.setError("No special characters are allowed");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Invalid email address");;
            return false;
        } else {
            et_email.setError(null);
            et_email.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateContact() {
        if (contact.isEmpty()) {
            et_contact.setError("This is required.");
            return false;
        } else if (!contact.matches("[a-zA-Z0-9.? ]*")) {
            et_contact.setError("No special characters are allowed");
            return false;
        } else {
            et_contact.setError(null);
            et_contact.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateUsername() {
        if (username.isEmpty()) {
            et_username.setError("This is required.");
            return false;
        } else if (!username.matches("[a-zA-Z0-9.?_ ]*")) {
            et_username.setError("No special characters are allowed");
            return false;
        } else if (username.length() > 16) {
            et_username.setError("Username is too long");
            return false;
        } else {
            et_username.setError(null);
            et_username.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateNickname() {
        if (nname.isEmpty()) {
            et_nname.setError("This is required.");
            return false;
        } else if (!nname.matches("[a-zA-Z0-9.?_ ]*")) {
            et_nname.setError("No special characters are allowed");
            return false;
        } else {
            et_nname.setError(null);
            et_nname.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateOccupation() {
        if (occupation.isEmpty()) {
            et_occupation.setError("This is required.");
            return false;
        } else if (!occupation.matches("[a-zA-Z0-9.?_ ]*")) {
            et_occupation.setError("No special characters are allowed");
            return false;
        } else {
            et_occupation.setError(null);
            et_occupation.setErrorEnabled(false);
            return true;
        }
    }

    private void SaveEditDetail(final String id, final String photo) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();
        String url = "http://192.168.137.1:8080/IRO/Android/edit_user.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/edit_user.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                loadUserDetails(user_id);
                                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

                                builder.setTitle("Edit Profile");
                                builder.setMessage("Updated successfully");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        progressDialog.dismiss();
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("photo", photo);
                params.put("fname", fname);
                params.put("lname", lname);
                params.put("nname", nname);
                params.put("occupation", occupation);
                params.put("address", address);
                params.put("email", email);
                params.put("contact", contact);
                params.put("username", username);
                return params;
            }
        };
        //stringRequest.setShouldCache(false);
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filepath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imageView.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }

            //UploadPicture(user_id, getStringImage(bitmap));
        }
    }

    public String getStringImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile,menu);
        return super.onCreateOptionsMenu(menu);
    }
    */

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

    public void loadUserDetails(final String id) {
        String url = "http://192.168.137.1:8080/IRO/Android/users.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/users.php";
        final int time = (int) (System.currentTimeMillis());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.i(TAG, response.toString());
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
                                        stringBuilder.append("?timestamp=" + String.valueOf(time));
                                        image_url = stringBuilder.toString();
                                    }
                                    imageView.setImageUrl(image_url, imageLoader);
                                    et_fname.getEditText().setText(object.getString("user_fname"));
                                    et_lname.getEditText().setText(object.getString("user_lname"));
                                    et_nname.getEditText().setText(object.getString("user_nname"));
                                    et_occupation.getEditText().setText(object.getString("user_occupation"));
                                    et_address.getEditText().setText(object.getString("user_address"));
                                    et_email.getEditText().setText(object.getString("user_email"));
                                    et_contact.getEditText().setText(object.getString("user_contact"));
                                    et_username.getEditText().setText(object.getString("user_username"));

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
        //stringRequest.setShouldCache(false);
        //AppController.getmInstance().getmRequestQueue().getCache().remove(url);
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

}
