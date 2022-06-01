package com.abc123.iro;

import android.Manifest;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

import static android.R.layout.simple_spinner_item;

public class MyAdoptionFormActivity extends AppCompatActivity {
    private static final int PICKFILE_RESULT_CODE = 1;
    private MaterialSpinner spinner;
    private ArrayList<LiveType> liveTypeArrayList;
    private ArrayList<String> type = new ArrayList<>();
    private RadioGroup radioGroup;
    private RadioGroup radioGroup2;
    private RadioGroup radioGroup3;
    private RadioGroup radioGroup4;
    private RadioButton radioButton;
    private RadioButton radioButton2;
    private RadioButton radioButton4;
    private RadioButton radioButton3;
    private RadioButton radio_yes;
    private RadioButton radio_no;
    private RadioButton radio_yes2;
    private RadioButton radio_no2;
    private RadioButton radio_yes3;
    private RadioButton radio_no3;
    private RadioButton radio_yes4;
    private RadioButton radio_no4;
    private TextInputLayout et_vet_name;
    private TextInputLayout et_vet_address;
    private TextInputLayout et_yes_visist;
    private TextInputLayout et_no_visist;
    private TextInputLayout et_name_applicant;
    private TextInputLayout et_date_today;
    private TextInputLayout et_children_age;
    private TextInputLayout et_address;
    private TextInputLayout et_contact;
    private TextInputLayout et_occupation;
    private TextInputLayout et_pet_name;
    private TextInputLayout et_pet_gender;
    private TextInputLayout et_pet_age;
    private TextInputLayout et_pet_reason;
    private TextInputLayout et_pet_house;
    private TextInputLayout et_pet_past;
    private TextInputLayout et_pet_describe;
    private TextInputLayout et_pet_circumstances;
    private TextInputLayout et_pet_no_visit;
    private TextInputLayout et_pet_yes_visit;
    private TextInputLayout et_pet_comments;
    private TextInputLayout et_attached_file;
    private TextInputLayout et_date_transact;
    private TextInputLayout et_reason2;
    private TextView tv_file_required;
    private TextView tv_file;
    private TextView tv_file2;
    private TextView tv_file3;
    private TextView tv_file4;
    private TextView tv_file5;
    private TextView tv_file_header;
    private ImageView img_file_close;
    private ImageView img_file_close2;
    private ImageView img_file_close3;
    private ImageView img_file_close4;
    private ImageView img_file_close5;
    //private CheckBox checkBox;
    private ProgressBar loading;
    private Button btnSave;
    private Button btnAttach;
    private Calendar calendar;
    private String date;
    private String fullname;
    private String address;
    private String contact;
    private String occupation;
    private String pet_id;
    private String user_id;
    private String adoption_id;
    private String children_age;
    private String reason;
    private String pet_name;
    private String pet_age;
    private String pet_gender;
    private String pet_house;
    private String pet_past;
    private String pet_describe;
    private String pet_circumstances;
    private String pet_no_visit;
    private String pet_yes_visit;
    private String comments;
    private String typeid;
    private Boolean isEmpty = false;
    private String have_yard;
    private String have_fenced;
    private String have_vet;
    private String is_agreed;
    private String vet_name;
    private String vet_address;
    private String displayName;
    private String date_submitted;
    private String filename;
    private String date_transact;
    private String reason2;
    private String file1;
    private String file2;
    private String file3;
    private String file4;
    private String file5;
    private int live_type_id;
    private String newDataArray;
    private Bitmap bitmap;
    private List<String> imagesEncodedList;
    private List<Bitmap> bitmapList;
    private List<Data> imageList;
    private String doc_id;
    private int result1;
    private int result2;
    private int result3;
    private int result4;
    private int doc_status_id;
    private int counter;
    private Boolean flag = false;
    private Gson gson;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_adoption_form);

        //setting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setting the progressbar
        loading = findViewById(R.id.loading);

        //setting the list
        gson = new Gson();
        imagesEncodedList = new ArrayList<>();
        bitmapList = new ArrayList<>();
        imageList = new ArrayList<>();
        counter = 0;
        file1 = "0";
        file2 = "0";
        file3 = "0";
        file4 = "0";
        file5 = "0";

        //setting the textview and close button icon
        tv_file_required = findViewById(R.id.tv_file_required);
        tv_file = findViewById(R.id.file);
        tv_file2 = findViewById(R.id.file2);
        tv_file3 = findViewById(R.id.file3);
        tv_file4 = findViewById(R.id.file4);
        tv_file5 = findViewById(R.id.file5);
        tv_file_header = findViewById(R.id.tv_file_header);
        img_file_close = findViewById(R.id.file_close);
        img_file_close2 = findViewById(R.id.file_close2);
        img_file_close3 = findViewById(R.id.file_close3);
        img_file_close4 = findViewById(R.id.file_close4);
        img_file_close5 = findViewById(R.id.file_close5);

        //setting the button
        btnSave = findViewById(R.id.btnSave);
        btnAttach = findViewById(R.id.btnAttach);

        //setting the checkbox
        //checkBox = findViewById(R.id.checkbox);

        //setting the edittext
        et_vet_name = findViewById(R.id.pet_vet_name);
        et_vet_address = findViewById(R.id.pet_vet_address);
        et_name_applicant = findViewById(R.id.name_applicant);
        et_date_today = findViewById(R.id.date_today);
        et_children_age = findViewById(R.id.children_age);
        et_address = findViewById(R.id.address);
        et_contact = findViewById(R.id.contact);
        et_occupation = findViewById(R.id.occupation);
        et_pet_name = findViewById(R.id.pet_name);
        et_pet_gender = findViewById(R.id.pet_gender);
        et_pet_age = findViewById(R.id.pet_age);
        et_pet_reason = findViewById(R.id.reason);
        et_pet_house = findViewById(R.id.pet_house);
        et_pet_past = findViewById(R.id.pet_past);
        et_pet_describe = findViewById(R.id.pet_describe);
        et_pet_circumstances = findViewById(R.id.pet_circumstances);
        et_pet_no_visit = findViewById(R.id.pet_no_visit);
        et_pet_yes_visit = findViewById(R.id.pet_yes_visit);
        et_pet_comments = findViewById(R.id.pet_comments);
        //et_attached_file = findViewById(R.id.attached_file);
        et_date_transact = findViewById(R.id.date_transact);
        et_reason2 = findViewById(R.id.reason2);

        //set focus
        //et_children_age.getEditText().requestFocus();

        //getting the intent values
        Intent intent = getIntent();
        doc_id = intent.getStringExtra(MyAdoptionAdapter.DOC_ID);

        //get current date
        date = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        //et_date_today.getEditText().setText(date);

        //disabling
        et_name_applicant.getEditText().setFocusable(false);
        et_name_applicant.getEditText().setClickable(false);
        et_address.getEditText().setFocusable(false);
        et_address.getEditText().setClickable(false);
        et_contact.getEditText().setFocusable(false);
        et_contact.getEditText().setClickable(false);
        et_occupation.getEditText().setFocusable(false);
        et_occupation.getEditText().setClickable(false);
        et_pet_name.getEditText().setFocusable(false);
        et_pet_name.getEditText().setClickable(false);
        et_pet_age.getEditText().setFocusable(false);
        et_pet_age.getEditText().setClickable(false);
        et_pet_gender.getEditText().setFocusable(false);
        et_pet_gender.getEditText().setClickable(false);
        et_date_today.getEditText().setFocusable(false);
        et_date_today.getEditText().setClickable(false);
        //setting the sessions
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.USER_ID);
        fullname = user.get(sessionManager.FIRST_NAME) + " " + user.get(sessionManager.LAST_NAME);
        address = user.get(sessionManager.ADDRESS);
        contact = user.get(sessionManager.CONTACT);
        occupation = user.get(sessionManager.OCCUPATION);
        et_name_applicant.getEditText().setText(fullname);
        et_address.getEditText().setText(address);
        et_contact.getEditText().setText(contact);
        et_occupation.getEditText().setText(occupation);

        //setting the radios
        radioGroup = findViewById(R.id.radio_group);
        radioGroup2 = findViewById(R.id.radio_group2);
        radioGroup3 = findViewById(R.id.radio_group3);
        radioGroup4 = findViewById(R.id.radio_group4);
        radio_yes = findViewById(R.id.radio_yes);
        radio_no = findViewById(R.id.radio_no);
        radio_yes2 = findViewById(R.id.radio_yes2);
        radio_no2 = findViewById(R.id.radio_no2);
        radio_yes3 = findViewById(R.id.radio_yes3);
        radio_no3 = findViewById(R.id.radio_no3);
        radio_yes4 = findViewById(R.id.radio_yes4);
        radio_no4 = findViewById(R.id.radio_no4);


        //setting the arraylist
        liveTypeArrayList = new ArrayList<>();

        //setting the spinner
        spinner = (MaterialSpinner) findViewById(R.id.spinner);

        //load type
        retrieveType();
        //spinner.setSelection(3);

        //loadFormData
        loadFormData(doc_id);

        //loadFormAttachment
        loadAttachment(doc_id);

        //Toast.makeText(getApplicationContext(), "Counter: "+counter, Toast.LENGTH_SHORT).show();

        //disable
        final LinearLayout linearLayout3 = (LinearLayout) this.findViewById(R.id.layout3);
        linearLayout3.setVisibility(LinearLayout.GONE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    LiveType liveType = liveTypeArrayList.get(i);
                    //Toast.makeText(getApplicationContext(), "" + liveType.getId(), Toast.LENGTH_SHORT).show();
                    typeid = String.valueOf(liveType.getId());
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
        //deselect image
        final TextView[] textViews = {tv_file, tv_file2, tv_file3, tv_file4, tv_file5};
        img_file_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == true) {
                    if (imageList.isEmpty()) {
                        counter--;
                        file1 = textViews[0].getText().toString();
                        textViews[0].setText(null);textViews[0].setVisibility(TextView.GONE);
                        img_file_close.setVisibility(ImageView.GONE);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    } else {
                        counter--;
                        file1 = textViews[0].getText().toString();
                        textViews[0].setText(null);
                        textViews[0].setVisibility(TextView.GONE);
                        img_file_close.setVisibility(ImageView.GONE);
                        bitmapList.clear();
                        imageList.set(0, null);
                        newDataArray = gson.toJson(imageList);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                        if (counter < 5) {
                            btnAttach.setEnabled(true);
                            btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                        }
                    }
                } else {
                    if (imageList.isEmpty()) {
                        counter--;
                        file1 = textViews[0].getText().toString();
                        textViews[0].setText(null);
                        textViews[0].setVisibility(TextView.GONE);
                        img_file_close.setVisibility(ImageView.GONE);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    } else {
                        counter--;
                        file1 = textViews[0].getText().toString();
                        textViews[0].setText(null);
                        textViews[0].setVisibility(TextView.GONE);
                        img_file_close.setVisibility(ImageView.GONE);
                        imageList.set(0, null);
                        bitmap.recycle();
                        newDataArray = gson.toJson(imageList);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                        if (counter < 5) {
                            btnAttach.setEnabled(true);
                            btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                        }
                    }
                }
            }
        });
        img_file_close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == true) {
                    if (imageList.isEmpty()) {
                        counter--;
                        file2 = textViews[1].getText().toString();
                        textViews[1].setText(null);
                        textViews[1].setVisibility(TextView.GONE);
                        img_file_close2.setVisibility(ImageView.GONE);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    } else {
                        counter--;
                        file2 = textViews[1].getText().toString();
                        textViews[1].setText(null);
                        textViews[1].setVisibility(TextView.GONE);
                        img_file_close2.setVisibility(ImageView.GONE);
                        bitmapList.clear();
                        imageList.set(1, null);
                        newDataArray = gson.toJson(imageList);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                        if (counter < 5) {
                            btnAttach.setEnabled(true);
                            btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                        }
                    }
                } else {
                    if (imageList.isEmpty()) {
                        counter--;
                        file2 = textViews[1].getText().toString();
                        textViews[1].setText(null);
                        textViews[1].setVisibility(TextView.GONE);
                        img_file_close2.setVisibility(ImageView.GONE);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    } else {
                        counter--;
                        file2 = textViews[1].getText().toString();
                        textViews[1].setText(null);
                        textViews[1].setVisibility(TextView.GONE);
                        img_file_close2.setVisibility(ImageView.GONE);
                        imageList.set(1, null);
                        bitmap.recycle();
                        newDataArray = gson.toJson(imageList);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                        if (counter < 5) {
                            btnAttach.setEnabled(true);
                            btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                        }
                    }
                }
            }
        });
        img_file_close3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == true) {
                    if (imageList.isEmpty()) {
                        counter--;
                        file3 = textViews[2].getText().toString();
                        textViews[2].setText(null);
                        textViews[2].setVisibility(TextView.GONE);
                        img_file_close3.setVisibility(ImageView.GONE);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    } else {
                        counter--;
                        file3 = textViews[2].getText().toString();
                        textViews[2].setText(null);
                        textViews[2].setVisibility(TextView.GONE);
                        img_file_close3.setVisibility(ImageView.GONE);
                        bitmapList.clear();
                        imageList.set(2, null);
                        newDataArray = gson.toJson(imageList);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                        if (counter < 5) {
                            btnAttach.setEnabled(true);
                            btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                        }
                    }
                } else {
                    if (imageList.isEmpty()) {
                        counter--;
                        file3 = textViews[2].getText().toString();
                        textViews[2].setText(null);
                        textViews[2].setVisibility(TextView.GONE);
                        img_file_close3.setVisibility(ImageView.GONE);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    } else {
                        counter--;
                        file3 = textViews[2].getText().toString();
                        textViews[2].setText(null);
                        textViews[2].setVisibility(TextView.GONE);
                        img_file_close3.setVisibility(ImageView.GONE);
                        imageList.set(2, null);
                        bitmap.recycle();
                        newDataArray = gson.toJson(imageList);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                        if (counter < 5) {
                            btnAttach.setEnabled(true);
                            btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                        }
                    }
                }
            }
        });
        img_file_close4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == true) {
                    if (imageList.isEmpty()) {
                        counter--;
                        file4 = textViews[3].getText().toString();
                        textViews[3].setText(null);
                        textViews[3].setVisibility(TextView.GONE);
                        img_file_close4.setVisibility(ImageView.GONE);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    } else {
                        counter--;
                        file4 = textViews[3].getText().toString();
                        textViews[3].setText(null);
                        textViews[3].setVisibility(TextView.GONE);
                        img_file_close4.setVisibility(ImageView.GONE);
                        bitmapList.clear();
                        imageList.set(3, null);
                        newDataArray = gson.toJson(imageList);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                        if (counter < 5) {
                            btnAttach.setEnabled(true);
                            btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                        }
                    }
                } else {
                    if (imageList.isEmpty()) {
                        counter--;
                        file4 = textViews[3].getText().toString();
                        textViews[3].setText(null);
                        textViews[3].setVisibility(TextView.GONE);
                        img_file_close4.setVisibility(ImageView.GONE);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    } else {
                        counter--;
                        file4 = textViews[3].getText().toString();
                        textViews[3].setText(null);
                        textViews[3].setVisibility(TextView.GONE);
                        img_file_close4.setVisibility(ImageView.GONE);
                        imageList.set(3, null);
                        bitmap.recycle();
                        newDataArray = gson.toJson(imageList);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                        if (counter < 5) {
                            btnAttach.setEnabled(true);
                            btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                        }
                    }
                }
            }
        });
        img_file_close5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == true) {
                    if (imageList.isEmpty()) {
                        counter--;
                        file5 = textViews[4].getText().toString();
                        textViews[4].setText(null);
                        textViews[4].setVisibility(TextView.GONE);
                        img_file_close5.setVisibility(ImageView.GONE);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    } else {
                        counter--;
                        file5 = textViews[4].getText().toString();
                        textViews[4].setText(null);
                        textViews[4].setVisibility(TextView.GONE);
                        img_file_close5.setVisibility(ImageView.GONE);
                        bitmapList.clear();
                        imageList.set(4, null);
                        newDataArray = gson.toJson(imageList);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                        if (counter < 5) {
                            btnAttach.setEnabled(true);
                            btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                        }
                    }
                } else {
                    if (imageList.isEmpty()) {
                        counter--;
                        file5 = textViews[4].getText().toString();
                        textViews[4].setText(null);
                        textViews[4].setVisibility(TextView.GONE);
                        img_file_close5.setVisibility(ImageView.GONE);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    } else {
                        counter--;
                        file5 = textViews[4].getText().toString();
                        textViews[4].setText(null);
                        textViews[4].setVisibility(TextView.GONE);
                        img_file_close5.setVisibility(ImageView.GONE);
                        imageList.set(4, null);
                        bitmap.recycle();
                        newDataArray = gson.toJson(imageList);
                        Toast.makeText(getApplicationContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                        if (counter < 5) {
                            btnAttach.setEnabled(true);
                            btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                        }
                    }
                }
            }
        });
        //submit button event
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                children_age = et_children_age.getEditText().getText().toString().trim();
                reason = et_pet_reason.getEditText().getText().toString().trim();
                pet_house = et_pet_house.getEditText().getText().toString().trim();
                pet_past = et_pet_past.getEditText().getText().toString().trim();
                pet_describe = et_pet_describe.getEditText().getText().toString().trim();
                vet_name = et_vet_name.getEditText().getText().toString().trim();
                vet_address = et_vet_address.getEditText().getText().toString().trim();
                pet_circumstances = et_pet_circumstances.getEditText().getText().toString().trim();
                pet_no_visit = et_pet_no_visit.getEditText().getText().toString().trim();
                pet_yes_visit = et_pet_yes_visit.getEditText().getText().toString().trim();
                comments = et_pet_comments.getEditText().getText().toString().trim();
                if (isEmpty == true | !validateChildrenAge() | !validatePetReason() | !validatePetHouse() | !validatePetPast() | !validatePetDescribe() | !validatePetCircumstances() | !validateRadioGroup() | !validateRadioGroup2() | !validateRadioGroup3() | !validateRadioGroup4() | !validateVetName() | !validateVetAddress() | !validateAgree() | !validateDisagree() | !validateComments()) {
                    spinner.requestFocus();
                    spinner.setError("This is required.");
                    return;
                } else {
                    /*
                    if (checkBox.isChecked()) {


                        submit();
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MyAdoptionFormActivity.this);

                        builder.setTitle("Terms and Conditions");
                        builder.setMessage("You must agree with the terms and conditions first before you can submit your application form.");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    */
                    if (counter == 0) {
                        tv_file_required.setVisibility(TextView.VISIBLE);
                        tv_file_required.setText("Image required.");
                        tv_file_required.setTextColor(Color.parseColor("#FFDD2C00"));
                    } else {
                        if (newDataArray != null && !newDataArray.isEmpty()) {
                            save(doc_id, adoption_id, newDataArray);
                        } else {
                            save(doc_id, adoption_id, "0");
                        }
                    }
                }
            }
        });

        //to check if checkbox is checked
        /*
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    LinearLayout linear_attachment = findViewById(R.id.layout_attachment);
                    linear_attachment.setVisibility(LinearLayout.VISIBLE);

                    LinearLayout linear_file = findViewById(R.id.layout_file);
                    linear_file.setVisibility(LinearLayout.VISIBLE);
                } else {
                    LinearLayout linear_attachment = findViewById(R.id.layout_attachment);
                    linear_attachment.setVisibility(LinearLayout.GONE);

                    LinearLayout linear_file = findViewById(R.id.layout_file);
                    linear_file.setVisibility(LinearLayout.GONE);
                    tv_file.setText("");
                }
            }
        });
        */

        //attach file
        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });
    }

    public void approved(String date) {
        //LinearLayout layout_other_details = (LinearLayout)this.findViewById(R.id.layout_other_details);
        //LinearLayout layout_reason2 = (LinearLayout)this.findViewById(R.id.layout_reason2);
        //LinearLayout layout_file = (LinearLayout)this.findViewById(R.id.layout_file);
        //layout_other_details.setVisibility(LinearLayout.VISIBLE);
        //layout_reason2.setVisibility(LinearLayout.VISIBLE);
        //layout_file.setVisibility(LinearLayout.VISIBLE);
        //et_attached_file.getEditText().setText(displayName);
        //et_attached_file.setHint(getResources().getString(R.string.attached_file));
        //et_attached_file.getEditText().setFocusable(false);
        et_date_transact.getEditText().setHint("Approved Date");
        et_date_transact.getEditText().setText(date);
        et_date_transact.setHint(getResources().getString(R.string.approved_date));
        et_date_transact.getEditText().setFocusable(false);
        et_date_transact.getEditText().setClickable(false);
        et_reason2.getEditText().setVisibility(EditText.GONE);
        /*
        final ImageView[] imgs_file_close = {img_file_close,img_file_close2,img_file_close3,img_file_close4,img_file_close5};
        for(int i=0; i<imgs_file_close.length; i++) {
            imgs_file_close[i].setVisibility(ImageView.GONE);
        }
        */

    }

    public void cancelled(String date, String reason_cancelled) {
        //LinearLayout layout_other_details = (LinearLayout)this.findViewById(R.id.layout_other_details);
        //LinearLayout layout_reason2 = (LinearLayout)this.findViewById(R.id.layout_reason2);
        //LinearLayout layout_file = (LinearLayout)this.findViewById(R.id.layout_file);
        //layout_other_details.setVisibility(LinearLayout.VISIBLE);
        //layout_reason2.setVisibility(LinearLayout.VISIBLE);
        //layout_file.setVisibility(LinearLayout.VISIBLE);
        //et_attached_file.getEditText().setText(displayName);
        //et_attached_file.setHint(getResources().getString(R.string.attached_file));
        //et_attached_file.getEditText().setFocusable(false);
        et_date_transact.getEditText().setText(date);
        et_date_transact.setHint(getResources().getString(R.string.cancelled_date));
        et_date_transact.getEditText().setFocusable(false);
        et_date_transact.getEditText().setClickable(false);
        et_reason2.getEditText().setText(reason_cancelled);
        et_reason2.setHint(getResources().getString(R.string.cancelled_reason));
        et_reason2.getEditText().setFocusable(false);
        et_reason2.getEditText().setClickable(false);
    }

    public void rejected(String date, String reason_rejected) {
        //LinearLayout layout_other_details = (LinearLayout)this.findViewById(R.id.layout_other_details);
        //LinearLayout layout_reason2 = (LinearLayout)this.findViewById(R.id.layout_reason2);
        //LinearLayout layout_file = (LinearLayout)this.findViewById(R.id.layout_file);
        //layout_other_details.setVisibility(LinearLayout.VISIBLE);
        //layout_reason2.setVisibility(LinearLayout.VISIBLE);
        //layout_file.setVisibility(LinearLayout.VISIBLE);
        //et_attached_file.getEditText().setText(displayName);
        //et_attached_file.setHint(getResources().getString(R.string.attached_file));
        //et_attached_file.getEditText().setFocusable(false);
        et_date_transact.getEditText().setText(date);
        et_date_transact.setHint(getResources().getString(R.string.rejected_date));
        et_date_transact.getEditText().setFocusable(false);
        et_date_transact.getEditText().setClickable(false);
        et_reason2.getEditText().setText(reason_rejected);
        et_reason2.setHint(getResources().getString(R.string.rejected_reason));
        et_reason2.getEditText().setFocusable(false);
        et_reason2.getEditText().setClickable(false);

        //tv_file_header.setVisibility(TextView.VISIBLE);
        /*
        final ImageView[] imgs_file_close = {img_file_close,img_file_close2,img_file_close3,img_file_close4,img_file_close5};
        for(int i=0; i<imgs_file_close.length; i++) {
            imgs_file_close[i].setVisibility(ImageView.GONE);
        }
        */
    }

    public void disableForms() {
        et_children_age.getEditText().setFocusable(false);
        et_children_age.getEditText().setClickable(false);
        et_pet_reason.getEditText().setFocusable(false);
        et_pet_reason.getEditText().setClickable(false);
        spinner.setEnabled(false);
        et_pet_house.getEditText().setFocusable(false);
        et_pet_house.getEditText().setClickable(false);
        et_pet_past.getEditText().setFocusable(false);
        et_pet_past.getEditText().setClickable(false);
        et_pet_describe.getEditText().setFocusable(false);
        et_pet_describe.getEditText().setClickable(false);
        //disable radiobutton1
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            ((RadioButton) radioGroup.getChildAt(i)).setEnabled(false);
        }
        //disable radioButton2
        for (int i = 0; i < radioGroup2.getChildCount(); i++) {
            ((RadioButton) radioGroup2.getChildAt(i)).setEnabled(false);
        }
        //disable radioButton3
        for (int i = 0; i < radioGroup3.getChildCount(); i++) {
            ((RadioButton) radioGroup3.getChildAt(i)).setEnabled(false);
        }
        //disable radioButton4
        for (int i = 0; i < radioGroup4.getChildCount(); i++) {
            ((RadioButton) radioGroup4.getChildAt(i)).setEnabled(false);
        }
        et_vet_name.getEditText().setFocusable(false);
        et_vet_name.getEditText().setClickable(false);
        et_vet_address.getEditText().setFocusable(false);
        et_vet_address.getEditText().setClickable(false);
        et_pet_circumstances.getEditText().setFocusable(false);
        et_pet_circumstances.getEditText().setClickable(false);
        et_pet_yes_visit.getEditText().setFocusable(false);
        et_pet_yes_visit.getEditText().setClickable(false);
        et_pet_no_visit.getEditText().setFocusable(false);
        et_pet_no_visit.getEditText().setClickable(false);
        et_pet_comments.getEditText().setFocusable(false);
        et_pet_comments.getEditText().setClickable(false);
        //btnAttach.setVisibility(Button.GONE);
        //tv_file.setVisibility(TextView.GONE);
        //btnSave.setVisibility(Button.GONE);
        //LinearLayout layout_file = (LinearLayout)this.findViewById(R.id.layout_file);
        LinearLayout layout_button = (LinearLayout) this.findViewById(R.id.layout_button);
        LinearLayout layout_attachment = (LinearLayout) this.findViewById(R.id.layout_attachment);
        LinearLayout layout_other_details = (LinearLayout) this.findViewById(R.id.layout_other_details);
        LinearLayout layout_reason2 = (LinearLayout) this.findViewById(R.id.layout_reason2);
        //layout_file.setVisibility(LinearLayout.GONE);
        layout_button.setVisibility(LinearLayout.GONE);
        layout_attachment.setVisibility(LinearLayout.GONE);
        layout_other_details.setVisibility(LinearLayout.VISIBLE);
        layout_reason2.setVisibility(LinearLayout.VISIBLE);

        tv_file_header.setVisibility(TextView.VISIBLE);
        /*
        //filename
        final TextView[] textViews = {tv_file,tv_file2,tv_file3,tv_file4,tv_file5};
        for(int i=0; i<textViews.length; i++) {
            textViews[i].setVisibility(TextView.VISIBLE);
        }

        //cancel icon
        final ImageView[] imgs_file_close = {img_file_close,img_file_close2,img_file_close3,img_file_close4,img_file_close5};
        for(int i=0; i<imgs_file_close.length; i++) {
            imgs_file_close[i].setVisibility(ImageView.GONE);
        }
        */
    }

    public void loadFormData(final String id) {
        String url = "http://192.168.137.1:8080/IRO/Android/my_adoption_form.php";
        final TextView[] textViews = {tv_file, tv_file2, tv_file3, tv_file4, tv_file5};
        final ImageView[] imgs_file_close = {img_file_close, img_file_close2, img_file_close3, img_file_close4, img_file_close5};
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final String result = response.toString();
                        Log.d("response", "result: " + result);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    //check doc status id
                                    doc_status_id = object.getInt("doc_status_id");
                                    //variable
                                    adoption_id = object.getString("adoption_id");
                                    //fullname = object.getString("user_fname")+" "+object.getString("user_lname");
                                    date_submitted = object.getString("date_submitted");
                                    children_age = object.getString("children_age");
                                    //address = object.getString("user_address");
                                    //contact = object.getString("user_contact");
                                    //occupation = object.getString("user_occupation");
                                    pet_name = object.getString("pet_name");
                                    pet_age = object.getString("pet_age");
                                    pet_gender = object.getString("pet_gender");
                                    reason = object.getString("reason");
                                    live_type_id = object.getInt("pet_live_type_id");
                                    pet_house = object.getString("current_pet");
                                    pet_past = object.getString("past_pet");
                                    pet_describe = object.getString("past_pet_details");
                                    result1 = object.getInt("have_yard");
                                    result2 = object.getInt("is_fenced");
                                    result3 = object.getInt("have_vet");
                                    vet_name = object.getString("vet_name");
                                    vet_address = object.getString("vet_address");
                                    pet_circumstances = object.getString("circumstances");
                                    result4 = object.getInt("is_agreed");
                                    pet_no_visit = object.getString("reason_disagree");
                                    pet_yes_visit = object.getString("home_visit_time");
                                    comments = object.getString("comments");
                                    //displayName = object.getString("file_name");

                                    //setting the edit text and other widgets
                                    //et_name_applicant.getEditText().setText(fullname);
                                    et_date_today.getEditText().setText(date_submitted);
                                    et_children_age.getEditText().setText(children_age);
                                    et_pet_name.getEditText().setText(pet_name);
                                    et_pet_age.getEditText().setText(pet_age);
                                    et_pet_gender.getEditText().setText(pet_gender);
                                    et_pet_reason.getEditText().setText(reason);
                                    if (live_type_id == 2) {
                                        spinner.setSelection(1);
                                    } else if (live_type_id == 3) {
                                        spinner.setSelection(2);
                                    } else {
                                        spinner.setSelection(3);
                                    }
                                    //spinner.setSelection(live_type_id);
                                    et_pet_house.getEditText().setText(pet_house);
                                    et_pet_past.getEditText().setText(pet_past);
                                    et_pet_describe.getEditText().setText(pet_describe);
                                    //result1
                                    if (result1 == 1) {
                                        radio_yes.setChecked(true);
                                        radio_no.setChecked(false);
                                        have_yard = "1";
                                    } else {
                                        radio_no.setChecked(true);
                                        radio_yes.setChecked(false);
                                        have_yard = "0";
                                    }
                                    //result2
                                    if (result2 == 1) {
                                        radio_yes2.setChecked(true);
                                        radio_no2.setChecked(false);
                                        have_fenced = "1";
                                    } else {
                                        radio_no2.setChecked(true);
                                        radio_yes2.setChecked(false);
                                        have_fenced = "0";
                                    }
                                    //result3
                                    if (result3 == 1) {
                                        radio_yes3.setChecked(true);
                                        radio_no3.setChecked(false);
                                        have_vet = "1";
                                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout1);
                                        linearLayout.setVisibility(LinearLayout.VISIBLE);

                                        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.layout2);
                                        linearLayout2.setVisibility(LinearLayout.VISIBLE);

                                    } else {
                                        radio_no3.setChecked(true);
                                        radio_yes3.setChecked(false);
                                        have_vet = "0";
                                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout1);
                                        linearLayout.setVisibility(LinearLayout.GONE);

                                        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.layout2);
                                        linearLayout2.setVisibility(LinearLayout.GONE);
                                    }
                                    et_vet_name.getEditText().setText(vet_name);
                                    et_vet_address.getEditText().setText(vet_address);
                                    et_pet_circumstances.getEditText().setText(pet_circumstances);
                                    //result4
                                    if (result4 == 1) {
                                        radio_yes4.setChecked(true);
                                        radio_no4.setChecked(false);
                                        is_agreed = "1";
                                        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.layout3);
                                        linearLayout3.setVisibility(LinearLayout.GONE);

                                        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.layout4);
                                        linearLayout4.setVisibility(LinearLayout.VISIBLE);
                                    } else {
                                        radio_no4.setChecked(true);
                                        radio_yes4.setChecked(false);
                                        is_agreed = "0";
                                        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.layout4);
                                        linearLayout4.setVisibility(LinearLayout.GONE);

                                        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.layout3);
                                        linearLayout3.setVisibility(LinearLayout.VISIBLE);
                                    }
                                    et_pet_no_visit.getEditText().setText(pet_no_visit);
                                    et_pet_yes_visit.getEditText().setText(pet_yes_visit);
                                    et_pet_comments.getEditText().setText(comments);
                                    //tv_file.setText(displayName);

                                    if (doc_status_id != 1) {
                                        disableForms();
                                    }
                                    //check what transaction status
                                    if (doc_status_id == 2) {
                                        date_transact = object.getString("approved_date");
                                        approved(date_transact);
                                    } else if (doc_status_id == 4) {
                                        date_transact = object.getString("cancelled_date");
                                        reason2 = object.getString("cancelled_reason");
                                        cancelled(date_transact, reason2);
                                    } else if (doc_status_id == 3) {
                                        date_transact = object.getString("rejected_date");
                                        reason2 = object.getString("rejected_reason");
                                        rejected(date_transact, reason2);
                                    }
                                    //display the attached file
                                    //textViews[i].setVisibility(TextView.VISIBLE);
                                    //textViews[i].setText(displayName);
                                    //imgs_file_close[i].setVisibility(ImageView.VISIBLE);
                                }
                            }
                            //Toast.makeText(getApplicationContext(), success, Toast.LENGTH_SHORT).show();
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
                params.put("id", id);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);

    }

    public void loadAttachment(final String id) {
        String url = "http://192.168.137.1:8080/IRO/Android/my_adoption_form_attachment.php";
        final TextView[] textViews = {tv_file, tv_file2, tv_file3, tv_file4, tv_file5};
        final ImageView[] imgs_file_close = {img_file_close, img_file_close2, img_file_close3, img_file_close4, img_file_close5};
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final String result = response.toString();
                        Log.d("response", "result: " + result);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    if (doc_status_id == 1) {
                                        textViews[i].setVisibility(TextView.VISIBLE);
                                        imgs_file_close[i].setVisibility(ImageView.VISIBLE);
                                        textViews[i].setText(object.getString("file_name"));
                                    } else {
                                        textViews[i].setVisibility(TextView.VISIBLE);
                                        textViews[i].setText(object.getString("file_name"));
                                        imgs_file_close[i].setVisibility(ImageView.GONE);
                                    }
                                    counter++;
                                }
                            }
                            //Toast.makeText(getApplicationContext(), success, Toast.LENGTH_SHORT).show();
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
                params.put("id", id);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    public void chooseFile() {
        /*
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICKFILE_RESULT_CODE);
        */
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MyAdoptionFormActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
            return;
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
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
                TextView[] textViews = {tv_file, tv_file2, tv_file3, tv_file4, tv_file5};
                ImageView[] imgs_file_close = {img_file_close, img_file_close2, img_file_close3, img_file_close4, img_file_close5};
                //one image
                flag = false;
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
                        //imagesEncodedList.add(displayName);
                    }
                    /*
                    for (int i = 0; i < bitmapList.size(); i++) {
                        textViews[i].setVisibility(TextView.VISIBLE);
                        textViews[i].setText(imagesEncodedList.get(i));
                        imgs_file_close[i].setVisibility(ImageView.VISIBLE);
                        Data dt = new Data(getStringImage(bitmapList.get(i)), imagesEncodedList.get(i));
                        imageList.add(dt);
                    }
                    */
                    //new image upload code
                    //img1[0] img2[1] img3[0] img4[0] img5[0]
                    if (textViews[0].getText().toString().matches("") && !textViews[1].getText().toString().matches("") && textViews[2].getText().toString().matches("") && textViews[3].getText().toString().matches("") && textViews[4].getText().toString().matches("")) {
                        textViews[0].setVisibility(TextView.VISIBLE);
                        textViews[0].setText(displayName);
                        imgs_file_close[0].setVisibility(ImageView.VISIBLE);
                        //img1[0] img2[1] img3[1] img4[1] img5[1]
                    } else if (textViews[0].getText().toString().matches("") && !textViews[1].getText().toString().matches("") && !textViews[2].getText().toString().matches("") && !textViews[3].getText().toString().matches("") && !textViews[4].getText().toString().matches("")) {
                        textViews[0].setVisibility(TextView.VISIBLE);
                        textViews[0].setText(displayName);
                        imgs_file_close[0].setVisibility(ImageView.VISIBLE);
                        //img1[0] img2[1] img3[1] img4[0] img5[0]
                    } else if (textViews[0].getText().toString().matches("") && !textViews[1].getText().toString().matches("") && !textViews[2].getText().toString().matches("") && !textViews[3].getText().toString().matches("") && textViews[4].getText().toString().matches("")) {
                        textViews[0].setVisibility(TextView.VISIBLE);
                        textViews[0].setText(displayName);
                        imgs_file_close[0].setVisibility(ImageView.VISIBLE);
                        //img1[0] img2[1] img3[1] img4[0] img5[0]
                    } else if (textViews[0].getText().toString().matches("") && !textViews[1].getText().toString().matches("") && !textViews[2].getText().toString().matches("") && textViews[3].getText().toString().matches("") && textViews[4].getText().toString().matches("")) {
                        textViews[0].setVisibility(TextView.VISIBLE);
                        textViews[0].setText(displayName);
                        imgs_file_close[0].setVisibility(ImageView.VISIBLE);
                        //img1[0] img2[0] img3[1] img4[1] img5[1]
                    } else if (textViews[0].getText().toString().matches("") && textViews[1].getText().toString().matches("") && !textViews[2].getText().toString().matches("") && !textViews[3].getText().toString().matches("") && !textViews[4].getText().toString().matches("")) {
                        textViews[1].setVisibility(TextView.VISIBLE);
                        textViews[1].setText(displayName);
                        imgs_file_close[1].setVisibility(ImageView.VISIBLE);
                        //img1[0] img2[0] img3[1] img4[1] img5[0]
                    } else if (textViews[0].getText().toString().matches("") && textViews[1].getText().toString().matches("") && !textViews[2].getText().toString().matches("") && !textViews[3].getText().toString().matches("") && textViews[4].getText().toString().matches("")) {
                        textViews[1].setVisibility(TextView.VISIBLE);
                        textViews[1].setText(displayName);
                        imgs_file_close[1].setVisibility(ImageView.VISIBLE);
                        //img1[1] img2[1] img3[0] img4[1] img5[1]
                    } else if (!textViews[0].getText().toString().matches("") && !textViews[1].getText().toString().matches("") && textViews[2].getText().toString().matches("")) {
                        textViews[2].setVisibility(TextView.VISIBLE);
                        textViews[2].setText(displayName);
                        imgs_file_close[2].setVisibility(ImageView.VISIBLE);
                        //img1[1] img2[1] img3[1] img4[0] img5[1]
                    } else if (!textViews[0].getText().toString().matches("") && !textViews[1].getText().toString().matches("") && !textViews[2].getText().toString().matches("") && textViews[3].getText().toString().matches("")) {
                        textViews[3].setVisibility(TextView.VISIBLE);
                        textViews[3].setText(displayName);
                        imgs_file_close[3].setVisibility(ImageView.VISIBLE);
                        //img1[1] img2[1] img3[1] img4[0] img5[1]
                    } else if (!textViews[0].getText().toString().matches("") && textViews[1].getText().toString().matches("") && !textViews[2].getText().toString().matches("") && textViews[3].getText().toString().matches("") && !textViews[4].getText().toString().matches("")) {
                        textViews[3].setVisibility(TextView.VISIBLE);
                        textViews[3].setText(displayName);
                        imgs_file_close[3].setVisibility(ImageView.VISIBLE);
                        //img1[1] img2[0] img3[0] img4[0] img5[0]
                    } else if (!textViews[0].getText().toString().matches("") && textViews[1].getText().toString().matches("") && !textViews[2].getText().toString().matches("") && !textViews[3].getText().toString().matches("") && !textViews[4].getText().toString().matches("")) {
                        textViews[1].setVisibility(TextView.VISIBLE);
                        textViews[1].setText(displayName);
                        imgs_file_close[1].setVisibility(ImageView.VISIBLE);
                    } else {
                        textViews[counter].setVisibility(TextView.VISIBLE);
                        textViews[counter].setText(displayName);
                        imgs_file_close[counter].setVisibility(ImageView.VISIBLE);
                    }
                    Data dt = new Data(getStringImage(compressImage(bitmap)), displayName);
                    imageList.add(dt);
                    newDataArray = gson.toJson(imageList);
                    counter++;
                    if (counter == 5) {
                        btnAttach.setEnabled(false);
                        btnAttach.setBackgroundColor(Color.LTGRAY);
                    }


                }
                //multiple-image
                else {
                    flag = true;
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        if (mClipData.getItemCount() > 5) {
                            Toast.makeText(getApplicationContext(), "Sorry you can only upload 5 images", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            String uriString = uri.toString();
                            File myFile = new File(uriString);
                            String path = myFile.getAbsolutePath();
                            displayName = null;

                            if (uriString.startsWith("content://")) {
                                Cursor cursor = null;
                                try {
                                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                    bitmapList.add(bitmap);
                                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                                    if (cursor != null && cursor.moveToFirst()) {
                                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                        imagesEncodedList.add(displayName);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    cursor.close();
                                }
                            } else if (uriString.startsWith("file://")) {
                                displayName = myFile.getName();
                                imagesEncodedList.add(displayName);
                            }
                        }
                        //TextView[] textViews = {tv_file, tv_file2, tv_file3, tv_file4, tv_file5};
                        //ImageView[] imgs_file_close = {img_file_close, img_file_close2, img_file_close3, img_file_close4, img_file_close5};
                        for (int i = 0; i < bitmapList.size(); i++) {
                            textViews[i].setVisibility(TextView.VISIBLE);
                            textViews[i].setText(imagesEncodedList.get(i));
                            imgs_file_close[i].setVisibility(ImageView.VISIBLE);
                            Data dt = new Data(getStringImage(bitmapList.get(i)), imagesEncodedList.get(i));
                            imageList.add(dt);
                        }
                        counter += mClipData.getItemCount();
                        newDataArray = gson.toJson(imageList);
                        if (counter == 5) {
                            btnAttach.setEnabled(false);
                            btnAttach.setBackgroundColor(Color.LTGRAY);
                        }
                        Toast.makeText(getApplicationContext(), "Size: " + counter, Toast.LENGTH_LONG).show();
                    }
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

    public File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public String getStringImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;
    }

    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//Compression quality, here 100 means no compression, the storage of compressed data to baos
        int options = 90;
        while (baos.toByteArray().length / 1024 > 400) {  //Loop if compressed picture is greater than 400kb, than to compression
            baos.reset();//Reset baos is empty baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//The compression options%, storing the compressed data to the baos
            options -= 10;//Every time reduced by 10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//The storage of compressed data in the baos to ByteArrayInputStream
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//The ByteArrayInputStream data generation
        return bitmap;
    }

    public void checkButton(View v) {
        int radio_id = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radio_id);
        if (radioButton.getText().toString().equals("No")) {
            have_yard = "0";
        } else {
            have_yard = "1";
        }
    }

    public void checkButton2(View v) {
        int radio_id2 = radioGroup.getCheckedRadioButtonId();
        radioButton2 = findViewById(radio_id2);
        if (radioButton2.getText().toString().equals("No")) {
            have_fenced = "0";
        } else {
            have_fenced = "1";
        }
    }

    public void checkButton3(View v) {
        int radio_id3 = radioGroup3.getCheckedRadioButtonId();
        radioButton3 = findViewById(radio_id3);
        if (radioButton3.getText().toString().equals("No")) {
            //Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
            have_vet = "0";
            LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.layout1);
            linearLayout.setVisibility(LinearLayout.GONE);
            Toast.makeText(getApplicationContext(), "Tae", Toast.LENGTH_SHORT).show();

            LinearLayout linearLayout2 = (LinearLayout) this.findViewById(R.id.layout2);
            linearLayout2.setVisibility(LinearLayout.GONE);
        } else {
            have_vet = "1";
            et_vet_name.getEditText().requestFocus();
            LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.layout1);
            linearLayout.setVisibility(LinearLayout.VISIBLE);

            LinearLayout linearLayout2 = (LinearLayout) this.findViewById(R.id.layout2);
            linearLayout2.setVisibility(LinearLayout.VISIBLE);
        }
    }

    public void checkButton4(View v) {
        int radio_id4 = radioGroup4.getCheckedRadioButtonId();
        radioButton4 = findViewById(radio_id4);
        if (radioButton4.getText().toString().equals("No")) {
            is_agreed = "0";
            et_pet_no_visit.getEditText().requestFocus();
            //Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
            LinearLayout linearLayout4 = (LinearLayout) this.findViewById(R.id.layout4);
            linearLayout4.setVisibility(LinearLayout.GONE);

            LinearLayout linearLayout3 = (LinearLayout) this.findViewById(R.id.layout3);
            linearLayout3.setVisibility(LinearLayout.VISIBLE);
        } else {
            is_agreed = "1";
            et_pet_yes_visit.getEditText().requestFocus();
            LinearLayout linearLayout3 = (LinearLayout) this.findViewById(R.id.layout3);
            linearLayout3.setVisibility(LinearLayout.GONE);

            LinearLayout linearLayout4 = (LinearLayout) this.findViewById(R.id.layout4);
            linearLayout4.setVisibility(LinearLayout.VISIBLE);
        }
    }


    public void retrieveType() {
        String url = "http://192.168.137.1:8080/IRO/Android/type.php";
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
                                    LiveType liveType = new LiveType(object.getInt("pet_live_type_id"), object.getString("pet_live_type_desc"));
                                    liveTypeArrayList.add(liveType);
                                }

                                for (int i = 0; i < liveTypeArrayList.size(); i++) {
                                    type.add(liveTypeArrayList.get(i).getType());
                                }


                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(MyAdoptionFormActivity.this, simple_spinner_item, type) {
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

    public boolean validateChildrenAge() {
        if (children_age.isEmpty()) {
            et_children_age.setError("This is required");
            return false;
        } else if (!children_age.matches("[a-zA-Z0-9.?#'#,/\\- ]*")) {
            et_children_age.setError("No special characters are allowed");
            return false;
        } else {
            et_children_age.setError(null);
            et_children_age.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validatePetReason() {
        if (reason.isEmpty()) {
            et_pet_reason.setError("This is required");
            return false;
        } else if (!reason.matches("[a-zA-Z0-9.?#'#,/\\- ]*")) {
            et_pet_reason.setError("No special characters are allowed");
            return false;
        } else {
            et_pet_reason.setError(null);
            et_pet_reason.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validatePetHouse() {
        if (pet_house.isEmpty()) {
            et_pet_house.setError("This is required");
            return false;
        } else if (!pet_house.matches("[a-zA-Z0-9.?#'#,/\\- ]*")) {
            et_pet_house.setError("No special characters are allowed");
            return false;
        } else {
            et_pet_house.setError(null);
            et_pet_house.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validatePetPast() {
        if (pet_past.isEmpty()) {
            et_pet_past.setError("This is required");
            return false;
        } else if (!pet_past.matches("[a-zA-Z0-9.?#'#,/\\- ]*")) {
            et_pet_past.setError("No special characters are allowed");
            return false;
        } else {
            et_pet_past.setError(null);
            et_pet_past.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validatePetDescribe() {
        if (pet_describe.isEmpty()) {
            et_pet_describe.setError("This is required");
            return false;
        } else if (!pet_describe.matches("[a-zA-Z0-9.?#'#,/\\- ]*")) {
            et_pet_describe.setError("No special characters are allowed");
            return false;
        } else {
            et_pet_describe.setError(null);
            et_pet_describe.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateRadioGroup() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            radio_no.setError("This is required");
            return false;
        } else {
            radio_no.setError(null);
            return true;
        }
    }

    public boolean validateRadioGroup2() {
        if (radioGroup2.getCheckedRadioButtonId() == -1) {
            radio_no2.setError("This is required");
            return false;
        } else {
            radio_no2.setError(null);
            return true;
        }
    }

    public boolean validateRadioGroup3() {
        if (radioGroup3.getCheckedRadioButtonId() == -1) {
            radio_no3.setError("This is required");
            return false;
        } else {
            radio_no3.setError(null);
            return true;
        }
    }

    public boolean validateRadioGroup4() {
        if (radioGroup4.getCheckedRadioButtonId() == -1) {
            radio_no4.setError("This is required");
            return false;
        } else {
            radio_no4.setError(null);
            return true;
        }
    }

    public boolean validatePetCircumstances() {
        if (pet_circumstances.isEmpty()) {
            et_pet_circumstances.setError("This is required");
            return false;
        } else if (!pet_circumstances.matches("[a-zA-Z0-9.?#'#,/\\- ]*")) {
            et_pet_circumstances.setError("No special characters are allowed");
            return false;
        } else {
            et_pet_circumstances.setError(null);
            et_pet_circumstances.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateVetName() {
        if (!vet_name.matches("[a-zA-Z0-9.?#'#,/\\- ]*")) {
            et_vet_name.setError("No special characters are allowed");
            return false;
        } else {
            et_vet_name.setError(null);
            et_vet_name.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateVetAddress() {
        if (!vet_address.matches("[a-zA-Z0-9.?#'#,/\\- ]*")) {
            et_vet_address.setError("No special characters are allowed");
            return false;
        } else {
            et_vet_address.setError(null);
            et_vet_address.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateAgree() {
        if (!pet_yes_visit.matches("[a-zA-Z0-9.?#'#,/\\- ]*")) {
            et_pet_yes_visit.setError("No special characters are allowed");
            return false;
        } else {
            et_pet_yes_visit.setError(null);
            et_pet_yes_visit.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateDisagree() {
        if (!pet_no_visit.matches("[a-zA-Z0-9.?#'#,/\\- ]*")) {
            et_pet_no_visit.setError("No special characters are allowed");
            return false;
        } else {
            et_pet_no_visit.setError(null);
            et_pet_no_visit.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateComments() {
        if (!comments.matches("[a-zA-Z0-9.?#'#,/\\- ]*")) {
            et_pet_comments.setError("No special characters are allowed");
            return false;
        } else {
            et_pet_comments.setError(null);
            et_pet_comments.setErrorEnabled(false);
            return true;
        }
    }

    public void save(final String id, final String id2, final String arrayString) {
        loading.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.GONE);
        String url = "http://192.168.137.1:8080/IRO/Android/edit_adoption_form.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoption_application.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", "Response: " + response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if (success.equals("1")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyAdoptionFormActivity.this);

                                builder.setTitle("Pet Adoption Application Form");
                                builder.setMessage("Successfully saved. Your application form has been updated.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        Intent intent = new Intent(getApplicationContext(), MyAdoptionActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                        //dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();

                            }
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
                params.put("userid", user_id);
                //params.put("petid", pet_id);
                params.put("docid", id);
                params.put("adoptionid", id2);
                params.put("age", children_age);
                params.put("reason", reason);
                params.put("typeid", typeid);
                params.put("current", pet_house);
                params.put("past_pet", pet_past);
                params.put("past_pet_details", pet_describe);
                params.put("have_yard", have_yard);
                params.put("is_fenced", have_fenced);
                params.put("have_vet", have_vet);
                params.put("vet_name", vet_name);
                params.put("vet_address", vet_address);
                params.put("circumstances", pet_circumstances);
                params.put("is_agreed", is_agreed);
                params.put("reason_disagree", pet_no_visit);
                params.put("home_visit_time", pet_yes_visit);
                params.put("comments", comments);
                params.put("date_submitted", date);
                params.put("array", arrayString);
                params.put("file1", file1);
                params.put("file2", file2);
                params.put("file3", file3);
                params.put("file4", file4);
                params.put("file5", file5);
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
