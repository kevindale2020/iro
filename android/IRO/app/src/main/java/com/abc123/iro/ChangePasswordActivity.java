package com.abc123.iro;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    private TextInputLayout et_current_password;
    private TextInputLayout et_new_password;
    private TextInputLayout et_confirm_password;
    private Button btnSave;
    private ProgressBar loading;
    private String current_password;
    private String new_password;
    private String confirm_password;
    private String userid;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        //for the session values
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        userid = user.get(sessionManager.USER_ID);

        //setting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setting the button
        btnSave = findViewById(R.id.btnSave);

        //setting the edittext
        et_current_password = findViewById(R.id.et_current_password);
        et_new_password = findViewById(R.id.et_new_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        et_current_password.getEditText().requestFocus();
        loading = findViewById(R.id.loading);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //parsing
                current_password = et_current_password.getEditText().getText().toString().trim();
                new_password = et_new_password.getEditText().getText().toString().trim();
                confirm_password = et_confirm_password.getEditText().getText().toString().trim();

                if(!validateCurrentPassword() | !validateNewPassword() | !validateConfirmPassword()) {
                    return;
                } else {
                    changePassword(userid);
                }
            }
        });
    }

    public boolean validateCurrentPassword() {
        if(current_password.isEmpty()) {
            et_current_password.setError("This is required.");
            return false;
        } else {
            et_current_password.setError(null);
            et_current_password.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateNewPassword() {
        if(new_password.isEmpty()) {
            et_new_password.setError("This is required.");
            return false;
        }
        else if(!new_password.equals(confirm_password)) {
            et_new_password.setError("Password does not match with the confirm password");
            return false;
        } else if (new_password.length() < 8) {
            et_new_password.setError("Password must be at least 8-16 characters");
            return false;
        } else if (new_password.length() > 16) {
            et_new_password.setError("Password is too long");
            return false;
        } else {
            et_new_password.setError(null);
            et_new_password.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateConfirmPassword() {
        if(confirm_password.isEmpty()) {
            et_confirm_password.setError("This is required.");
            return false;
        } else if (confirm_password.length() < 8) {
            et_confirm_password.setError("Password must be at least 8-16 characters");
            return false;
        } else if (confirm_password.length() > 16) {
            et_confirm_password.setError("Password is too long");
            return false;
        } else {
            et_confirm_password.setError(null);
            et_confirm_password.setErrorEnabled(false);
            return true;
        }
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

    public void changePassword(String id) {
        loading.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.GONE);
        String url = "http://192.168.137.1:8080/IRO/Android/change_password.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/change_password.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if(success.equals("1")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);

                                builder.setTitle("Change Password");
                                builder.setMessage("Password successfully changed.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        et_current_password.getEditText().setText("");
                                        et_new_password.getEditText().setText("");
                                        et_confirm_password.getEditText().setText("");
                                        et_current_password.getEditText().requestFocus();
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                                loading.setVisibility(View.GONE);
                                btnSave.setVisibility(View.VISIBLE);
                            } else {
                                et_current_password.getEditText().requestFocus();
                                et_current_password.setError(message);
                                loading.setVisibility(View.GONE);
                                btnSave.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Failed" +e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btnSave.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btnSave.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", userid);
                params.put("current_password", current_password);
                params.put("new_password", new_password);
                params.put("confirm_password", confirm_password);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }


}
