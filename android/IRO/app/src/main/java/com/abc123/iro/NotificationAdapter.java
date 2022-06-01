package com.abc123.iro;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationAdapter extends ArrayAdapter<Notifications> {
    private LayoutInflater inflater;
    private List<Notifications> notificationsList;
    private Context mCtx;
    private ImageView imageView;
    private String userid;

    public NotificationAdapter(List<Notifications> notificationsList, Context mctx, String userid) {
        super(mctx, R.layout.custom_layout10, notificationsList);
        this.mCtx = mctx;
        this.notificationsList = notificationsList;
        this.userid = userid;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        inflater = LayoutInflater.from(mCtx);

        if (inflater == null) {
            inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_layout10, null, true);
        }

        //creating a view with our xml layout
        //final View listViewItem = inflater.inflate(R.layout.custom_layout, null, true);
        ImageView imageView = convertView.findViewById(R.id.image_view);
        final ImageView delete = convertView.findViewById(R.id.icon_delete);
        final ImageView accept = convertView.findViewById(R.id.icon_accept);
        final ImageView decline = convertView.findViewById(R.id.icon_decline);
        TextView tv_subject = convertView.findViewById(R.id.subject);
        TextView tv_content = convertView.findViewById(R.id.content);
        TextView tv_date = convertView.findViewById(R.id.date);

        final Notifications notifications = notificationsList.get(position);

        if (notifications.getType() == 8) {
            accept.setVisibility(ImageView.VISIBLE);
            decline.setVisibility(ImageView.VISIBLE);
        } else {
            accept.setVisibility(ImageView.GONE);
            decline.setVisibility(ImageView.GONE);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

                builder.setTitle("Delete Notification");
                builder.setMessage("Are you sure you want to delete this notification?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        delete(String.valueOf(notifications.getId()));
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

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //accept home visit
                //accept(userid, String.valueOf(notifications.getId()));
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

                builder.setTitle("Home Visit");
                builder.setMessage("Agree to home visit?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        accept(userid, String.valueOf(notifications.getId()));
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

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //decline home visit
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

                builder.setTitle("Home Visit");
                builder.setMessage("Reasons for declining");

                //edit text
                final EditText input = new EditText(mCtx);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                builder.setView(input);

                builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        //cancel(String.valueOf(myAdoptions.getId2()), input.getText().toString());
                        decline(userid, input.getText().toString(), String.valueOf(notifications.getId()));
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
            }
        });

        tv_subject.setText(notifications.getSubject());
        tv_content.setText(notifications.getContent());
        tv_date.setText(notifications.getDate());

        return convertView;
    }

    public void delete(final String id) {
        final ProgressDialog progressDialog = new ProgressDialog(mCtx);
        //progressDialog.setMessage("Updating...");
        progressDialog.show();
        String url = "http://192.168.137.1:8080/IRO/Android/delete_notification.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoptions.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

                                builder.setTitle("Notification");
                                builder.setMessage("This notification has been deleted.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(mCtx, NotificationActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mCtx.startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(mCtx, "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                        Toast.makeText(mCtx, "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
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

    public void accept(final String id, final String id2) {
        final ProgressDialog progressDialog = new ProgressDialog(mCtx);
        //progressDialog.setMessage("Updating...");
        progressDialog.show();
        String url = "http://192.168.137.1:8080/IRO/Android/accept_visit.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoptions.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

                                builder.setTitle("Home Visit");
                                builder.setMessage("Agreed to home visit");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(mCtx, NotificationActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mCtx.startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(mCtx, "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                        Toast.makeText(mCtx, "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("id2", id2);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    public void decline(final String id, final String content, final String id2) {
        final ProgressDialog progressDialog = new ProgressDialog(mCtx);
        //progressDialog.setMessage("Updating...");
        progressDialog.show();
        String url = "http://192.168.137.1:8080/IRO/Android/decline_visit.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoptions.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

                                builder.setTitle("Home Visit");
                                builder.setMessage("You have declined home visit.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(mCtx, NotificationActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mCtx.startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(mCtx, "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                        Toast.makeText(mCtx, "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("content", content);
                params.put("id2", id2);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }
}
