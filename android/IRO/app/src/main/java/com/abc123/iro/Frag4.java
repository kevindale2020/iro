package com.abc123.iro;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_spinner_item;
import static android.app.Activity.RESULT_OK;

public class Frag4 extends Fragment {
    private static final int PICKFILE_RESULT_CODE = 1;
    private String user_id;
    private String typeid;
    private String amount;
    private String name;
    private String number;
    private String displayName;
    private Spinner spinner;
    private TextInputLayout et_amount;
    private TextInputLayout et_account_name;
    private TextInputLayout et_account_number;
    private ProgressBar loading;
    private Button btnAttach;
    private Button btnSubmit;
    private TextView tv_file;
    private TextView tv_file_required;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag4_layout, container, false);

        //for the session values
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.USER_ID);

        //arraylist
        paymentMethodArrayList = new ArrayList<>();

        //spinner
        spinner = view.findViewById(R.id.spinner1);
        typeid = "";

        //textview
        tv_file = view.findViewById(R.id.file);
        tv_file_required = view.findViewById(R.id.tv_file_required);
        file_close = view.findViewById(R.id.file_close);

        //edit text
        et_amount = view.findViewById(R.id.amount);
        et_account_name = view.findViewById(R.id.account_name);
        et_account_number = view.findViewById(R.id.account_number);

        //linearlayout
        layout_name = (LinearLayout) view.findViewById(R.id.layout_name);
        layout_number = (LinearLayout) view.findViewById(R.id.layout_number);
        layout_remittance_header = (LinearLayout) view.findViewById(R.id.layout_remittance_header);
        layout_recipient_name = (LinearLayout) view.findViewById(R.id.layout_recipient_name);
        layout_address = (LinearLayout) view.findViewById(R.id.layout_address);
        layout_contact = (LinearLayout) view.findViewById(R.id.layout_contact);
        layout_bank_header = (LinearLayout) view.findViewById(R.id.layout_bank_header);
        layout_bank_name = (LinearLayout) view.findViewById(R.id.layout_bank_name);
        layout_bank_account_number = (LinearLayout) view.findViewById(R.id.layout_bank_account_number);
        layout_bank_account_name = (LinearLayout) view.findViewById(R.id.layout_bank_account_name);

        //button
        btnAttach = view.findViewById(R.id.btnAttach);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        //progressbar
        loading = view.findViewById(R.id.loading);

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
                bitmap.recycle();
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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = et_amount.getEditText().getText().toString().trim();
                name = et_account_name.getEditText().getText().toString().trim();
                number = et_account_number.getEditText().getText().toString().trim();

                if (typeid.equals("2") || typeid.equals("3") || typeid.equals("4")) {
                    if (!validateAmount() | !validatePaymentMethod() | !validateImage() | !validateName() | !validateNumber()) {
                        return;
                    }

                } else {
                    if (!validateAmount() | !validatePaymentMethod() | !validateImage()) {
                        return;
                    }

                }
                submit();

            }
        });


        return view;
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


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), simple_spinner_item, type) {
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
                            bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                            //bitmapList.add(bitmap);
                            cursor = getContext().getContentResolver().query(uri, null, null, null, null);
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
                Toast.makeText(getContext(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong " + e, Toast.LENGTH_LONG).show();
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

    public void submit() {
        loading.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);
        String url = "http://192.168.137.1:8080/IRO/Android/donate.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoption_application.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle("Donation");
                                builder.setMessage("Successfully submitted. Thank you for you donation.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        Intent intent = new Intent(getContext(), HomeActivity.class);
                                        startActivity(intent);
                                        dialog.dismiss();
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
                            Toast.makeText(getContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btnSubmit.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", user_id);
                params.put("type", typeid);
                params.put("name", name);
                params.put("number", number);
                params.put("amount", amount);
                params.put("photo", getStringImage(bitmap));
                params.put("filename", displayName);
                params.put("type", typeid);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    public boolean validateNumber() {
        if (number.isEmpty()) {
            et_account_number.setError("This is required.");
            return false;
        } else {
            et_account_number.setError(null);
            et_account_number.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateName() {
        if (name.isEmpty()) {
            et_account_name.setError("This is required.");
            return false;
        } else {
            et_account_name.setError(null);
            et_account_name.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateAmount() {
        if (amount.isEmpty()) {
            et_amount.setError("This is required.");
            return false;
        } else {
            et_amount.setError(null);
            et_amount.setErrorEnabled(false);
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

    public boolean validatePaymentMethod() {
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
}
