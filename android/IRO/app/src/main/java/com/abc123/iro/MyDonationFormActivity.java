package com.abc123.iro;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
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

public class MyDonationFormActivity extends AppCompatActivity {
    private static final int PICKFILE_RESULT_CODE = 1;
    private String user_id;
    private String donation_id;
    private String typeid;
    private String amount;
    private String name;
    private String number;
    private String displayName;
    private Spinner spinner;
    private TextInputLayout et_amount;
    private TextInputLayout et_account_name;
    private TextInputLayout et_account_number;
    private TextInputLayout et_date;
    private ProgressBar loading;
    private Button btnAttach;
    private Button btnSave;
    private TextView tv_file;
    private TextView tv_file_required;
    private TextView tv_file_header;
    private ImageView file_close;
    private Boolean isEmpty = false;
    private Bitmap bitmap;
    private ArrayList<PaymentMethod> paymentMethodArrayList;
    private ArrayList<String> type = new ArrayList<>();
    private LinearLayout layout_number;
    private LinearLayout layout_name;
    private LinearLayout layout_remittance_header;
    private LinearLayout layout_recipient_name;
    private LinearLayout layout_address;
    private LinearLayout layout_contact;
    private LinearLayout layout_bank_header;
    private LinearLayout layout_bank_name;
    private LinearLayout layout_bank_account_number;
    private LinearLayout layout_bank_account_name;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donation_form);

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
        donation_id = intent.getStringExtra(DonationAdapter.DONATION_ID);

        //arraylist
        paymentMethodArrayList = new ArrayList<>();

        //spinner
        spinner = findViewById(R.id.spinner1);
        typeid = "";

        //textview
        tv_file = findViewById(R.id.file);
        tv_file_required = findViewById(R.id.tv_file_required);
        tv_file_header = findViewById(R.id.tv_file_header);
        file_close = findViewById(R.id.file_close);

        //edit text
        et_amount = findViewById(R.id.amount);
        et_date = findViewById(R.id.date);
        et_account_name = findViewById(R.id.account_name);
        et_account_number = findViewById(R.id.account_number);

        //linearlayout
         layout_name = (LinearLayout) this.findViewById(R.id.layout_name);
         layout_number = (LinearLayout) this.findViewById(R.id.layout_number);
        layout_remittance_header = (LinearLayout) this.findViewById(R.id.layout_remittance_header);
        layout_recipient_name = (LinearLayout) this.findViewById(R.id.layout_recipient_name);
        layout_address = (LinearLayout) this.findViewById(R.id.layout_address);
        layout_contact = (LinearLayout) this.findViewById(R.id.layout_contact);
        layout_bank_header = (LinearLayout) this.findViewById(R.id.layout_bank_header);
        layout_bank_name = (LinearLayout) this.findViewById(R.id.layout_bank_name);
        layout_bank_account_number = (LinearLayout) this.findViewById(R.id.layout_bank_account_number);
        layout_bank_account_name = (LinearLayout) this.findViewById(R.id.layout_bank_account_name);

        //button
        btnAttach = findViewById(R.id.btnAttach);
        btnSave = findViewById(R.id.btnSave);

        //progressbar
        loading = findViewById(R.id.loading);

        //attach button is clicked
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
                    PaymentMethod paymentMethod = paymentMethodArrayList.get(i);
                    //Toast.makeText(getApplicationContext(), "" + liveType.getId(), Toast.LENGTH_SHORT).show();
                    typeid = String.valueOf(paymentMethod.getId());
                    //bank is selected
                    if (paymentMethod.getId() == 3) {
                        //hide remittance
                        layout_remittance_header.setVisibility(LinearLayout.GONE);
                        layout_recipient_name.setVisibility(LinearLayout.GONE);
                        layout_address.setVisibility(LinearLayout.GONE);
                        layout_contact.setVisibility(LinearLayout.GONE);

                        //show bank
                        layout_bank_header.setVisibility(LinearLayout.VISIBLE);
                        layout_bank_name.setVisibility(LinearLayout.VISIBLE);
                        layout_bank_account_number.setVisibility(LinearLayout.VISIBLE);
                        layout_bank_account_name.setVisibility(LinearLayout.VISIBLE);


                        layout_name.setVisibility(LinearLayout.VISIBLE);
                        layout_number.setVisibility(LinearLayout.VISIBLE);

                    } else {

                        //show remittance
                        layout_remittance_header.setVisibility(LinearLayout.VISIBLE);
                        layout_recipient_name.setVisibility(LinearLayout.VISIBLE);
                        layout_address.setVisibility(LinearLayout.VISIBLE);
                        layout_contact.setVisibility(LinearLayout.VISIBLE);

                        //hide bank
                        layout_bank_header.setVisibility(LinearLayout.GONE);
                        layout_bank_name.setVisibility(LinearLayout.GONE);
                        layout_bank_account_number.setVisibility(LinearLayout.GONE);
                        layout_bank_account_name.setVisibility(LinearLayout.GONE);

                        layout_name.setVisibility(LinearLayout.GONE);
                        layout_number.setVisibility(LinearLayout.GONE);
                    }
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

        loadDonationForm();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = et_amount.getEditText().getText().toString().trim();
                name = et_account_name.getEditText().getText().toString().trim();
                number = et_account_number.getEditText().getText().toString().trim();
                if(!validateAmount() | !validatePaymentMethod() | !validateImage()) {
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

    public void verified(String date) {
        et_date.getEditText().setText(date);
        et_date.setHint(getResources().getString(R.string.date_verified));
    }

    public void disableForms() {
        tv_file_header.setVisibility(TextView.VISIBLE);
        et_amount.getEditText().setFocusable(false);
        et_amount.getEditText().setClickable(false);
        et_date.getEditText().setFocusable(false);
        et_date.getEditText().setClickable(false);

        et_account_name.getEditText().setFocusable(false);
        et_account_name.getEditText().setClickable(false);

        et_account_number.getEditText().setFocusable(false);
        et_account_number.getEditText().setClickable(false);

        file_close.setVisibility(ImageView.GONE);
        spinner.setEnabled(false);
        //LinearLayout layout_file = (LinearLayout)this.findViewById(R.id.layout_file);
        LinearLayout layout_button = (LinearLayout)this.findViewById(R.id.layout_button);
        LinearLayout layout_attachment = (LinearLayout)this.findViewById(R.id.layout_attachment);
        LinearLayout layout_reason = (LinearLayout)this.findViewById(R.id.layout_reason);
        LinearLayout layout_date = (LinearLayout)this.findViewById(R.id.layout_date);
        layout_button.setVisibility(LinearLayout.GONE);
        layout_attachment.setVisibility(LinearLayout.GONE);
        layout_date.setVisibility(LinearLayout.VISIBLE);
    }

    public void loadDonationForm() {
        String url = "http://192.168.137.1:8080/IRO/Android/my_donation_form.php";
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
                                    displayName = object.getString("image");
                                    //load spinner
                                    if(object.getInt("donation_type_id")==2||object.getInt("donation_type_id")==3||object.getInt("donation_type_id")==2) {
                                        layout_name.setVisibility(LinearLayout.VISIBLE);
                                        layout_number.setVisibility(LinearLayout.VISIBLE);
                                    } else {
                                        layout_name.setVisibility(LinearLayout.GONE);
                                        layout_number.setVisibility(LinearLayout.GONE);
                                    }
                                    if(object.getInt("donation_type_id")==2) {
                                        spinner.setSelection(1);
                                    } else if(object.getInt("donation_type_id")==3) {
                                        spinner.setSelection(2);
                                    } else if(object.getInt("donation_type_id")==4) {
                                        spinner.setSelection(3);
                                    } else if(object.getInt("donation_type_id")==5) {
                                        spinner.setSelection(4);
                                    } else if(object.getInt("donation_type_id")==6) {
                                        spinner.setSelection(5);
                                    } else if(object.getInt("donation_type_id")==7) {
                                        spinner.setSelection(6);
                                    } else {
                                        spinner.setSelection(7);
                                    }
                                    //load account name
                                    et_account_name.getEditText().setText(object.getString("account_name"));
                                    et_account_number.getEditText().setText(object.getString("account_number"));
                                    //load current amount
                                    et_amount.getEditText().setText(String.valueOf(String.format("%.2f",object.getDouble("amount"))));
                                    //load attachments
                                    tv_file.setVisibility(TextView.VISIBLE);
                                    file_close.setVisibility(ImageView.VISIBLE);
                                    tv_file.setText(displayName);
                                    if(!object.getString("is_verified").equals("0")) {
                                        disableForms();
                                    }
                                    if(object.getString("is_verified").equals("1")) {
                                        verified(object.getString("date_verified"));
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
                params.put("id", donation_id);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    public void retrieveType() {
        String url = "http://192.168.137.1:8080/IRO/Android/payment_methods.php";
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
                                    //ReportType reportType = new ReportType(object.getInt("report_type_id"), object.getString("report_type_desc"));
                                    //reportTypeArrayList.add(reportType);
                                    PaymentMethod paymentMethod = new PaymentMethod(object.getInt("donation_type_id"), object.getString("donation_type_desc"));
                                    paymentMethodArrayList.add(paymentMethod);
                                }

                                for (int i = 0; i < paymentMethodArrayList.size(); i++) {
                                    type.add(paymentMethodArrayList.get(i).getType());
                                }


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), simple_spinner_item, type) {
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

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICKFILE_RESULT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                            //bitmapList.add(bitmap);
                            cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
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
                Toast.makeText(getApplicationContext(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Something went wrong " + e, Toast.LENGTH_LONG).show();
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

    public void save(final String photo) {
        loading.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.GONE);
        String url = "http://192.168.137.1:8080/IRO/Android/edit_donation.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoption_application.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyDonationFormActivity.this);

                                builder.setTitle("Donation");
                                builder.setMessage("Successfully saved. Your donation has been updated.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        //Intent intent = new Intent(getApplicationContext(), MyDonationFormActivity.class);
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
                params.put("id", donation_id);
                params.put("name", name);
                params.put("number", number);
                params.put("amount", amount);
                params.put("type", typeid);
                params.put("photo", photo);
                params.put("filename", displayName);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    public boolean validateAmount() {
        if(amount.isEmpty()) {
            et_amount.setError("This is required.");
            return false;
        } else {
            et_amount.setError(null);
            et_amount.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateImage() {
        if(tv_file.getText().toString().length()==0) {
            tv_file_required.setVisibility(TextView.VISIBLE);
            tv_file_required.setText("Image required.");
            tv_file_required.setTextColor(Color.parseColor("#FFDD2C00"));
            return false;
        } else {
            return true;
        }
    }

    public boolean validatePaymentMethod() {
        if(isEmpty==true) {
            spinner.requestFocus();
            TextView errorText = (TextView)spinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("This is required");//changes the selected item text to this
            return  false;
        } else {
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
}
