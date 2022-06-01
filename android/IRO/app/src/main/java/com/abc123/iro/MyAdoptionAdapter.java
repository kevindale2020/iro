package com.abc123.iro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MyAdoptionAdapter extends ArrayAdapter<MyAdoptions> {

    private LayoutInflater inflater;
    private List<MyAdoptions> myAdoptionsList;
    private List<MyAdoptions> arrayList;
    private Context mCtx;
    private ImageLoader imageLoader;
    public static final String USER_ID = "com.abc123.iro.extra.USER_ID";
    public static final String DOC_ID = "com.abc123.iro.extra.DOC_ID";

    public MyAdoptionAdapter(List<MyAdoptions> myAdoptionsList, Context mCtx) {
        super(mCtx, R.layout.custom_layout3, myAdoptionsList);
        this.mCtx = mCtx;
        this.myAdoptionsList = myAdoptionsList;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(myAdoptionsList);
    }

    @Override
    public int getCount() {
        return myAdoptionsList.size();
    }

    @Nullable
    @Override
    public MyAdoptions getItem(int position) {
        return myAdoptionsList.get(position);
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
            convertView = inflater.inflate(R.layout.custom_layout3, null, true);
        }

        //creating a view with our xml layout
        //final View listViewItem = inflater.inflate(R.layout.custom_layout, null, true);
        imageLoader = AppController.getmInstance().getmImageLoader();
        NetworkImageView imageView = convertView.findViewById(R.id.image_view);
        TextView tv_name = convertView.findViewById(R.id.name);
        TextView tv_breed = convertView.findViewById(R.id.breed);
        TextView tv_age = convertView.findViewById(R.id.age);
        TextView tv_status = convertView.findViewById(R.id.status);
        ImageView next = convertView.findViewById(R.id.icon_next);
        ImageView cancel = convertView.findViewById(R.id.icon_cancel);
        CardView cardView = convertView.findViewById(R.id.icon_cover);
        final MyAdoptions myAdoptions = myAdoptionsList.get(position);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String doc_id = String.valueOf(myAdoptions.getId2());
                Intent intent = new Intent(getContext(), MyAdoptionFormActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(DOC_ID, doc_id);
                mCtx.startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

                builder.setTitle("Cancel Application Form");
                builder.setMessage("Are you sure you want to cancel your application form?");

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
                                cancel(String.valueOf(myAdoptions.getId2()), input.getText().toString());
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
        if(myAdoptions.getId3()!=1) {
            cardView.setVisibility(View.GONE);
        } else{
            cardView.setVisibility(View.VISIBLE);
        }
        /*
        if (pets.getStatus_id() == 2) {
            cardView.setVisibility(View.VISIBLE);
            //next.setEnabled(false);
            next.setClickable(false);
        }
        */
        /*
        Flow of re open form
        if the status is cancel
        set to visible open button
        set to gone cancel button

        if the status is cancel and the pet is no longer available
        set to gone cancel button
        */
        imageView.setImageUrl(myAdoptions.getImage(), imageLoader);
        tv_name.setText(myAdoptions.getName());
        tv_breed.setText(myAdoptions.getBreed());
        tv_age.setText(myAdoptions.getAge());
        tv_status.setText(myAdoptions.getStatus());


        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        myAdoptionsList.clear();
        if (charText.length() == 0) {
            myAdoptionsList.addAll(arrayList);
        } else {
            for (MyAdoptions myAdoptions : arrayList) {
                if (myAdoptions.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    myAdoptionsList.add(myAdoptions);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void cancel(final String id, final String input) {
        final ProgressDialog progressDialog = new ProgressDialog(mCtx);
        //progressDialog.setMessage("Updating...");
        progressDialog.show();
        String url = "http://192.168.137.1:8080/IRO/Android/cancel_adoption.php";
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
                                builder.setMessage("Your application form has been cancelled.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(mCtx, MyAdoptionActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }
}
