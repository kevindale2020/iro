package com.abc123.iro;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = AppController.class.getSimpleName();
    private TextInputLayout et_fname;
    private TextInputLayout et_lname;
    private TextInputLayout et_address;
    private TextInputLayout et_email;
    private TextInputLayout et_contact;
    private TextInputLayout et_username;
    private TextInputLayout et_password;
    private TextInputLayout et_nname;
    private TextInputLayout et_occupation;
    private Button btnRegister;
    private String fname;
    private String lname;
    private String nname;
    private String occupation;
    private String address;
    private String email;
    private String contact;
    private String username;
    private String password;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().hide();

        //setting the widgets
        btnRegister = findViewById(R.id.btnRegister);
        et_fname = findViewById(R.id.et_fname);
        et_lname = findViewById(R.id.et_lname);
        et_nname = findViewById(R.id.et_nname);
        et_occupation = findViewById(R.id.et_occupation);
        et_address = findViewById(R.id.et_address);
        et_email = findViewById(R.id.et_email);
        et_contact = findViewById(R.id.et_contact);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        loading = findViewById(R.id.loading);

        //focus
        et_fname.getEditText().requestFocus();

        //Register

        btnRegister.setOnClickListener(new View.OnClickListener() {
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
                password = et_password.getEditText().getText().toString().trim();
                /*
                if(!validateFirstName() | !validateLastName() | !validateAddress() | !validateEmail() | !validateContact() | !validateUsername() | !validatePassword1() | !validatePassword2()) {
                    return;
                }
                */

                if (!validateFirstName() | !validateLastName() | !validateAddress() | !validateEmail() | !validateContact() | !validateUsername() | !validatePassword1() | !validateNickname() | !validateOccupation()) {
                    return;
                }

                register();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //action
                break;
        }
        return super.onOptionsItemSelected(item);
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
        } else if (!address.matches("[a-zA-Z0-9.?#'#,/\\- ]*")) {
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

    public boolean validatePassword1() {
        if (password.isEmpty()) {
            et_password.setError("This is required.");
            return false;
        } else if (password.length() < 8) {
            et_password.setError("Password must be at least 8-16 characters");
            return false;
        } else if (password.length() > 16) {
            et_password.setError("Password is too long");
            return false;
        } else {
            et_password.setError(null);
            et_password.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateNickname() {
        if (nname.isEmpty()) {
            et_nname.setError("This is required.");
            return false;
        } else if (!nname.matches("[a-zA-Z0-9.?#'#,/\\- ]*")) {
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
        } else if (!occupation.matches("[a-zA-Z0-9.?#'#,/\\- ]*")) {
            et_occupation.setError("No special characters are allowed");
            return false;
        } else {
            et_occupation.setError(null);
            et_occupation.setErrorEnabled(false);
            return true;
        }
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void register() {
        loading.setVisibility(View.VISIBLE);
        btnRegister.setVisibility(View.GONE);
        String url = "http://192.168.137.1:8080/IRO/Android/register.php";
        // url = "http://192.168.43.44:8080/IRO/Android/register.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString() + " dar amaw");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if (success.equals("1")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

                                builder.setTitle("A verification link has been sent to your email account");
                                builder.setMessage("Please click on the link that has just been sent to your email account to verify your email and continue the registration process.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                        //dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();

                            } else if (success.equals("2")) {
                                et_username.setError(message);
                                loading.setVisibility(View.GONE);
                                btnRegister.setVisibility(View.VISIBLE);
                            } else {
                                et_email.setError(message);
                                loading.setVisibility(View.GONE);
                                btnRegister.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btnRegister.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btnRegister.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fname", fname);
                params.put("lname", lname);
                params.put("nname", nname);
                params.put("occupation", occupation);
                params.put("address", address);
                params.put("email", email);
                params.put("contact", contact);
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        //requestQueue.add(stringRequest);
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    public void clear() {
        et_fname.getEditText().setText("");
        et_lname.getEditText().setText("");
        et_nname.getEditText().setText("");
        et_occupation.getEditText().setText("");
        et_address.getEditText().setText("");
        et_email.getEditText().setText("");
        et_contact.getEditText().setText("");
        et_username.getEditText().setText("");
        et_password.getEditText().setText("");
    }

}
