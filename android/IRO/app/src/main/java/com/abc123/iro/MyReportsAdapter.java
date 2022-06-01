package com.abc123.iro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MyReportsAdapter extends ArrayAdapter<Reports> {
    public static final String DOC_ID = "com.abc123.iro.extra.DOC_ID";
    private LayoutInflater inflater;
    private List<Reports> reportsList;
    private List<Reports> arrayList;
    private Context mCtx;
    private ImageLoader imageLoader;
    private String userid;

    public MyReportsAdapter(List<Reports> reportsList, Context mctx, String userid) {
        super(mctx, R.layout.custom_layout9, reportsList);
        this.mCtx = mctx;
        this.reportsList = reportsList;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(reportsList);
        this.userid = userid;
    }

    @Override
    public int getCount() {
        return reportsList.size();
    }

    @Nullable
    @Override
    public Reports getItem(int position) {
        return reportsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        inflater = LayoutInflater.from(mCtx);

        if (inflater == null) {
            inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_layout9, null, true);
        }

        //creating a view with our xml layout
        //final View listViewItem = inflater.inflate(R.layout.custom_layout, null, true);
        imageLoader = AppController.getmInstance().getmImageLoader();
        NetworkImageView imageView = convertView.findViewById(R.id.image_view);
        NetworkImageView imageView2 = convertView.findViewById(R.id.image_view2);
        ImageView image_edit = convertView.findViewById(R.id.image_edit);
        ImageView image_cancel = convertView.findViewById(R.id.image_cancel);
        ImageView image_close = convertView.findViewById(R.id.image_close);
        TextView tv_name = convertView.findViewById(R.id.name);
        TextView tv_date = convertView.findViewById(R.id.date);
        TextView tv_location = convertView.findViewById(R.id.location);
        TextView tv_title = convertView.findViewById(R.id.title);
        TextView tv_desc = convertView.findViewById(R.id.desc);
        TextView tv_status = convertView.findViewById(R.id.status);

        final Reports reports = reportsList.get(position);

        image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String doc_id = String.valueOf(reports.getId());
                Intent intent = new Intent(getContext(), MyReportFormActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(DOC_ID, doc_id);
                mCtx.startActivity(intent);
            }
        });

        image_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

                builder.setTitle("Cancel Report");
                builder.setMessage("Are you sure you want to cancel your report?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

                        builder.setTitle("Confirm Cancellation");
                        builder.setMessage("Reason for cancellation");

                        //edit text
                        final EditText input = new EditText(mCtx);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        builder.setView(input);

                        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                                cancel(String.valueOf(reports.getId()), input.getText().toString(), String.valueOf(reports.getId3()));
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

        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

                builder.setTitle("Close Report");
                builder.setMessage("Are you sure you want to close your report?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        close(String.valueOf(reports.getId()), String.valueOf(reports.getId3()));
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        // Do nothing but close the dialog
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        if(reports.getId2()!=1) {
            image_cancel.setVisibility(ImageView.GONE);
        } else {
            image_cancel.setVisibility(ImageView.VISIBLE);
        }

        if(reports.getId2()==5) {
            image_close.setVisibility(ImageView.VISIBLE);
        } else {
            image_close.setVisibility(ImageView.GONE);
        }

        imageView.setImageUrl(reports.getUserImage(), imageLoader);
        imageView2.setImageUrl(reports.getReportImage(), imageLoader);
        tv_name.setText(reports.getName());
        tv_date.setText(reports.getDate());
        tv_location.setText(reports.getLocation());
        tv_title.setText(reports.getTitle());
        tv_desc.setText(reports.getDesc());
        tv_status.setText(reports.getStatus());


        return convertView;
    }

    public void close(final String id, final String id2) {
        final ProgressDialog progressDialog = new ProgressDialog(mCtx);
        //progressDialog.setMessage("Updating...");
        progressDialog.show();
        String url = "http://192.168.137.1:8080/IRO/Android/close_report.php";
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

                                builder.setTitle("Close Report");
                                builder.setMessage("Your report has been closed.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(mCtx, MyReportActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                })
        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("id2", id2);
                params.put("userid", userid);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    public void cancel(final String id, final String input, final String id2) {
        final ProgressDialog progressDialog = new ProgressDialog(mCtx);
        //progressDialog.setMessage("Updating...");
        progressDialog.show();
        String url = "http://192.168.137.1:8080/IRO/Android/cancel_report.php";
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

                                builder.setTitle("Cancellation");
                                builder.setMessage("Your report has been cancelled.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(mCtx, MyReportActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                })
        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("reason", input);
                params.put("id2", id2);
                params.put("userid", userid);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        reportsList.clear();
        if (charText.length() == 0) {
            reportsList.addAll(arrayList);
        } else {
            for (Reports reports : arrayList) {
                if (reports.getName().toLowerCase(Locale.getDefault())
                        .contains(charText) || reports.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                        /*
                        if(pets.getStatus_id()==2) {
                            cardView.setVisibility(View.VISIBLE);
                            //next.setEnabled(false);
                            next.setClickable(false);
                        } else {
                            cardView.setVisibility(View.GONE);
                            //next.setEnabled(false);
                            next.setClickable(true);
                        }
                        */
                    reportsList.add(reports);
                }
            }
        }
        notifyDataSetChanged();
    }
}
