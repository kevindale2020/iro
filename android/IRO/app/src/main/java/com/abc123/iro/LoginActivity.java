package com.abc123.iro;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout et_username;
    private TextInputLayout et_password;
    private Button btnLogin;
    //private TextView text;
    private String username;
    private String password;
    private ProgressBar loading;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        sessionManager = new SessionManager(this);

        //setting the widgets
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btnLogin);
        //text = findViewById(R.id.text);
        loading = findViewById(R.id.loading);

        //focus
        et_username.getEditText().requestFocus();

        //login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parsing
                username = et_username.getEditText().getText().toString().trim();
                password = et_password.getEditText().getText().toString().trim();

                if (!validateUsername() | !validatePassword()) {
                    return;
                }
                login();
            }
        });
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public boolean validateUsername() {
        if (username.isEmpty()) {
            et_username.setError("Please enter your username.");
            return false;
        } else {
            et_username.setError(null);
            et_username.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validatePassword() {
        if (password.isEmpty()) {
            et_password.setError("Please enter your password.");
            return false;
        } else {
            et_password.setError(null);
            et_password.setErrorEnabled(false);
            return true;
        }
    }

    public void login() {
        loading.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);
        String url = "http://192.168.137.1:8080/IRO/Android/login.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if (success.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    int id = object.getInt("user_id");
                                    String image = object.getString("user_image");
                                    String fname = object.getString("user_fname");
                                    String lname = object.getString("user_lname");
                                    String nname = object.getString("user_nname");
                                    String occupation = object.getString("user_occupation");
                                    String address = object.getString("user_address");
                                    String email = object.getString("user_email");
                                    String contact = object.getString("user_contact");
                                    String username = object.getString("user_username");

                                    sessionManager.createSession(String.valueOf(id), image, fname, lname, nname, occupation, address, email, contact, username);

                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            } else if (success.equals("2")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                                builder.setTitle("Failed to login");
                                builder.setMessage(message);

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        et_username.getEditText().setText("");
                                        et_password.getEditText().setText("");
                                        et_username.getEditText().requestFocus();
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                                loading.setVisibility(View.GONE);
                                btnLogin.setVisibility(View.VISIBLE);
                            } else if (success.equals("3")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                                builder.setTitle("Failed to login");
                                builder.setMessage(message);

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        et_username.getEditText().setText("");
                                        et_password.getEditText().setText("");
                                        et_username.getEditText().requestFocus();
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                                loading.setVisibility(View.GONE);
                                btnLogin.setVisibility(View.VISIBLE);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                                builder.setTitle("Failed to login");
                                builder.setMessage("Invalid credentials");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        et_username.getEditText().setText("");
                                        et_password.getEditText().setText("");
                                        et_username.getEditText().requestFocus();
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                                loading.setVisibility(View.GONE);
                                btnLogin.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btnLogin.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btnLogin.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

}
