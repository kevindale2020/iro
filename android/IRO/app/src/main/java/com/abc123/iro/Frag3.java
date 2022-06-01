package com.abc123.iro;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class Frag3 extends Fragment {
    public static final String DOC_ID = "com.abc123.iro.extra.DOC_ID";
    private TextView name;
    private TextView date;
    private TextView status;
    private TextView email;
    private TextView no_post;
    private TextView active;
    private String user_id;
    private String doc_id;
    private String image_url;
    ImageLoader imageLoader = AppController.getmInstance().getmImageLoader();
    private NetworkImageView imageView;
    private ImageView icon_next;
    private ImageView icon_cancel;
    private ImageView icon_open;
    private ImageView icon_leave;
    private ImageView icon_activate;
    private ImageView image_no_post;
    private LinearLayout linearLayout;
    private CardView cancel;
    private CardView open;
    private CardView icon_leave_cover;
    private CardView icon_active_cover;
    private CardView icon_inactive_cover;
    private CardView icon_activate_cover;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag3_layout, container, false);

        //session values
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.USER_ID);

        //text view
        name = view.findViewById(R.id.name);
        date = view.findViewById(R.id.date);
        email = view.findViewById(R.id.email);
        status = view.findViewById(R.id.status);
        active = view.findViewById(R.id.active);

        //Cardview
        cancel = view.findViewById(R.id.cancel);
        open = view.findViewById(R.id.open);
        icon_active_cover = view.findViewById(R.id.image_active_cover);
        icon_inactive_cover = view.findViewById(R.id.image_inactive_cover);
        icon_leave_cover = view.findViewById(R.id.icon_leave_cover);
        icon_activate_cover = view.findViewById(R.id.icon_activate_cover);

        //image view
        no_post = view.findViewById(R.id.no_post);
        icon_next = view.findViewById(R.id.icon_next);
        icon_cancel = view.findViewById(R.id.icon_cancel);
        icon_open = view.findViewById(R.id.icon_open);
        icon_leave = view.findViewById(R.id.icon_leave);
        icon_activate = view.findViewById(R.id.icon_activate);
        image_no_post = view.findViewById(R.id.image_no_post);
        imageView = view.findViewById(R.id.image_view);

        //linearlayout
        linearLayout = view.findViewById(R.id.layout_volunteer);

        loadHistory(user_id);

        icon_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), VolunteerFormActivity.class);
                intent.putExtra(DOC_ID, doc_id);
                startActivity(intent);
            }
        });

        icon_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Cancel Application Form");
                builder.setMessage("Are you sure you want to cancel your application form?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setTitle("Confirm Cancellation");
                        builder.setMessage("Reason for cancellation");

                        //edit text
                        final EditText input = new EditText(getContext());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        builder.setView(input);

                        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                                cancel(doc_id, input.getText().toString());
                                dialog.dismiss();
                            }
                        });

                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
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
            }
        });

        icon_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Hello World!", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Resubmission");
                builder.setMessage("Do you want to resubmit your application form?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        open(user_id);
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
            }
        });

        icon_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Volunteer Committee");
                builder.setMessage("Are you sure you want to leave your committee?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setTitle("Confirm Leave");
                        builder.setMessage("Reason for leaving");

                        //edit text
                        final EditText input = new EditText(getContext());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        builder.setView(input);

                        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                                leave(user_id, input.getText().toString());
                                dialog.dismiss();
                            }
                        });

                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
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
            }
        });

        icon_activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Volunteer Committee");
                builder.setMessage("Are you sure you want to set your account to active?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        activate(user_id);
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
            }
        });

        return view;
    }

    public void cancel(final String id, final String input) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        //progressDialog.setMessage("Updating...");
        progressDialog.show();
        String url = "http://192.168.137.1:8080/IRO/Android/cancel_volunteer.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoptions.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle("Cancellation");
                                builder.setMessage("Your application form has been cancelled.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(getContext(), VolunteerActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                        Toast.makeText(getContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("reason", input);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    public void open(final String id) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        //progressDialog.setMessage("Updating...");
        progressDialog.show();
        String url = "http://192.168.137.1:8080/IRO/Android/resubmit_volunteer.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoptions.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle("Resubmission");
                                builder.setMessage("Your application form has been resubmitted.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(getContext(), VolunteerActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                        Toast.makeText(getContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", doc_id);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    public void leave(final String id, final String input) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        //progressDialog.setMessage("Updating...");
        progressDialog.show();
        String url = "http://192.168.137.1:8080/IRO/Android/leave_volunteer.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoptions.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle("Leave Volunteer Committee");
                                builder.setMessage("You are no longer a part of this committee. Your account has been set to inactive.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(getContext(), VolunteerActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                        Toast.makeText(getContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("reason", input);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    public void activate(final String id) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        //progressDialog.setMessage("Updating...");
        progressDialog.show();
        String url = "http://192.168.137.1:8080/IRO/Android/activate_volunteer.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoptions.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Hello world", response.toString() + " dar amaw");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle("Volunteer Committee");
                                builder.setMessage("Welcome back. Your account has been set to active.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(getContext(), VolunteerActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                        Toast.makeText(getContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    public void loadHistory(final String id) {
        String url = "http://192.168.137.1:8080/IRO/Android/volunteer_history.php";
        final int time = (int) (System.currentTimeMillis());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
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
                                    doc_id = object.getString("doc_id");
                                    name.setText(object.getString("user_fname") + " " + object.getString("user_lname"));
                                    email.setText(object.getString("user_email"));
                                    date.setText(object.getString("date_submitted"));
                                    status.setText(object.getString("doc_status_desc"));
                                    if (object.getInt("doc_status_id") != 1) {
                                        cancel.setVisibility(CardView.GONE);
                                    }
                                    if (object.getInt("doc_status_id") == 4) {
                                        open.setVisibility(CardView.VISIBLE);
                                    }
                                    if (object.getInt("doc_status_id") == 2) {
                                        if (object.getInt("is_active") == 1) {
                                            icon_leave_cover.setVisibility(CardView.VISIBLE);
                                            icon_activate_cover.setVisibility(CardView.GONE);
                                            icon_inactive_cover.setVisibility(CardView.GONE);
                                            icon_active_cover.setVisibility(CardView.VISIBLE);
                                            active.setVisibility(TextView.VISIBLE);
                                            active.setText("Active");
                                        } else {
                                            icon_leave_cover.setVisibility(CardView.GONE);
                                            icon_activate_cover.setVisibility(CardView.VISIBLE);
                                            icon_active_cover.setVisibility(CardView.GONE);
                                            icon_inactive_cover.setVisibility(CardView.VISIBLE);
                                            active.setVisibility(TextView.VISIBLE);
                                            active.setText("Inactive");
                                        }
                                    }
                                }
                            } else {
                                linearLayout.setVisibility(LinearLayout.GONE);
                                image_no_post.setVisibility(ImageView.VISIBLE);
                                no_post.setVisibility(TextView.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
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
    }
}
