package com.abc123.iro;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.layout.simple_spinner_item;

public class MyReportFormActivity extends AppCompatActivity {
    private static final int PICKFILE_RESULT_CODE = 1;
    private String report_id;
    private String user_id;
    private String displayName;
    private String title;
    private String desc;
    private String location;
    private String final_location;
    private String typeid;
    private TextInputLayout et_title;
    private TextInputLayout et_desc;
    private TextInputLayout et_date;
    private TextInputLayout et_reason;
    private Button btnAttach;
    private ProgressBar loading;
    private Button btnSave;
    private Bitmap bitmap;
    private TextView tv_file;
    private TextView tv_file_required;
    private TextView tv_file_header;
    private ImageView file_close;
    private Boolean isEmpty = false;
    private Spinner spinner;
    private ArrayList<ReportType> reportTypeArrayList;
    private ArrayList<String> type = new ArrayList<>();
    private String doc_id;
    private int doc_status_id;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_report_form);

        //setting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setting the sessions
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.USER_ID);

        //getting the intent value
        Intent intent = getIntent();
        doc_id = intent.getStringExtra(MyReportsAdapter.DOC_ID);

        //arraylist
        reportTypeArrayList = new ArrayList<>();

        //spinner
        spinner = findViewById(R.id.spinner1);

        //textview
        tv_file = findViewById(R.id.file);
        tv_file_required = findViewById(R.id.tv_file_required);
        tv_file_header = findViewById(R.id.tv_file_header);
        file_close = findViewById(R.id.file_close);

        //edit text
        et_title = findViewById(R.id.title);
        et_desc = findViewById(R.id.description);
        et_date = findViewById(R.id.date_transact);
        et_reason = findViewById(R.id.reason);

        //button
        btnAttach = findViewById(R.id.btnAttach);
        btnSave = findViewById(R.id.btnSave);

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
                //bitmap.recycle();
            }
        });

        retrieveType();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    ReportType reportType = reportTypeArrayList.get(i);
                    typeid = String.valueOf(reportType.getId());
                    //Toast.makeText(getApplicationContext(), "Type:" + typeid, Toast.LENGTH_SHORT).show();
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

        loadReportForm();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = et_title.getEditText().getText().toString().trim();
                desc = et_desc.getEditText().getText().toString().trim();
                if (!validateTitle() | !validateDesc() | !validateImage() | !validateIncident()) {
                    return;
                } else {
                    if (bitmap != null) {
                        save(getStringImage(bitmap));
                    } else {
                        save("0");
                    }
                }
            }
        });
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


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(MyReportFormActivity.this, simple_spinner_item, type) {
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

    public void acknowledged(String date) {
        et_reason.getEditText().setVisibility(EditText.GONE);
        et_date.getEditText().setText(date);
        et_date.setHint(getResources().getString(R.string.acknowledged_date));
    }

    public void closed(String date) {
        et_reason.getEditText().setVisibility(EditText.GONE);
        et_date.getEditText().setText(date);
        et_date.setHint(getResources().getString(R.string.closed_date));
    }

    public void approved(String date) {
        et_reason.getEditText().setVisibility(EditText.GONE);
        et_date.getEditText().setText(date);
        et_date.setHint(getResources().getString(R.string.approved_date));
    }

    public void cancelled(String date, String reason_cancelled) {
        et_date.getEditText().setText(date);
        et_date.setHint(getResources().getString(R.string.cancelled_date));
        et_reason.getEditText().setText(reason_cancelled);
        et_reason.setHint(getResources().getString(R.string.cancelled_reason));

    }

    public void rejected(String date, String reason_rejected) {
        et_date.getEditText().setText(date);
        et_date.setHint(getResources().getString(R.string.rejected_date));
        et_reason.getEditText().setText(reason_rejected);
        et_reason.setHint(getResources().getString(R.string.rejected_reason));
    }

    public void disableForms() {
        tv_file_header.setVisibility(TextView.VISIBLE);
        et_title.getEditText().setFocusable(false);
        et_title.getEditText().setClickable(false);
        et_desc.getEditText().setFocusable(false);
        et_desc.getEditText().setClickable(false);
        et_date.getEditText().setFocusable(false);
        et_date.getEditText().setClickable(false);
        et_reason.getEditText().setFocusable(false);
        et_reason.getEditText().setClickable(false);
        file_close.setVisibility(ImageView.GONE);
        spinner.setEnabled(false);
        //LinearLayout layout_file = (LinearLayout)this.findViewById(R.id.layout_file);
        LinearLayout layout_button = (LinearLayout) this.findViewById(R.id.layout_button);
        LinearLayout layout_attachment = (LinearLayout) this.findViewById(R.id.layout_attachment);
        LinearLayout layout_reason = (LinearLayout) this.findViewById(R.id.layout_reason);
        LinearLayout layout_date = (LinearLayout) this.findViewById(R.id.layout_date);
        layout_date.setVisibility(LinearLayout.VISIBLE);
        layout_reason.setVisibility(LinearLayout.VISIBLE);
        layout_button.setVisibility(LinearLayout.GONE);
        layout_attachment.setVisibility(LinearLayout.GONE);
    }

    public void loadReportForm() {
        String url = "http://192.168.137.1:8080/IRO/Android/my_report_form.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoption_application.php";
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
                                    report_id = String.valueOf(object.getInt("report_id"));
                                    doc_status_id = object.getInt("doc_status_id");
                                    displayName = object.getString("file_name");
                                    //load attachments
                                    tv_file.setVisibility(TextView.VISIBLE);
                                    file_close.setVisibility(ImageView.VISIBLE);
                                    tv_file.setText(displayName);
                                    //other details
                                    et_title.getEditText().setText(object.getString("title"));
                                    et_desc.getEditText().setText(object.getString("description"));
                                    if (object.getInt("report_type_id") == 2) {
                                        spinner.setSelection(1);
                                    } else if (object.getInt("report_type_id") == 3) {
                                        spinner.setSelection(2);
                                    } else {
                                        spinner.setSelection(3);
                                    }
                                    //Document status is not pending
                                    if (doc_status_id != 1) {
                                        disableForms();
                                    }

                                    if (doc_status_id == 2) {
                                        approved(object.getString("approved_date"));
                                    } else if (doc_status_id == 3) {
                                        rejected(object.getString("rejected_date"), object.getString("rejected_date"));
                                    } else if (doc_status_id == 4) {
                                        cancelled(object.getString("cancelled_date"), object.getString("cancelled_reason"));
                                    } else if (doc_status_id == 5) {
                                        acknowledged(object.getString("acknowledged_date"));
                                    } else {
                                        closed(object.getString("closed_date"));
                                    }

                                }

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
                params.put("id", doc_id);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    public void save(final String photo) {
        loading.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.GONE);
        String url = "http://192.168.137.1:8080/IRO/Android/edit_report.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoption_application.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyReportFormActivity.this);

                                builder.setTitle("Report Lost Pet");
                                builder.setMessage("Successfully saved. Your report has been updated.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        Intent intent = new Intent(getApplicationContext(), MyReportActivity.class);
                                        startActivity(intent);
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", report_id);
                params.put("docid", doc_id);
                params.put("title", title);
                params.put("desc", desc);
                params.put("photo", photo);
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
