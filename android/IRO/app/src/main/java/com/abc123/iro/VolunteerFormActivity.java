package com.abc123.iro;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolunteerFormActivity extends AppCompatActivity {
    private static final int PICKFILE_RESULT_CODE = 1;
    private TextInputLayout et_name;
    private TextInputLayout et_nname;
    private TextInputLayout et_address;
    private TextInputLayout et_contact;
    private TextInputLayout et_email;
    private TextInputLayout et_occupation;
    private TextInputLayout et_date_transact;
    private TextInputLayout et_reason;
    private CheckBox checkBox;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBox5;
    private CheckBox checkBox6;
    private CheckBox checkBox7;
    private CheckBox checkBox8;
    private Button btnSave;
    private ProgressBar loading;
    private String user_id;
    private String name;
    private String nname;
    private String address;
    private String contact;
    private String email;
    private String occupation;
    private String is_adoption;
    private String is_transportation;
    private String is_shelter_clean_up;
    private String is_educational_campaign;
    private String is_animal_welfare;
    private String is_fostering;
    private String is_food_donation_drive;
    private String is_events;
    private String date_transact;
    private String reason;
    private String file1;
    private String file2;
    private String file3;
    private String file4;
    private String file5;
    private String doc_id;
    private int doc_status_id;
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
    private Button btnAttach;
    private String displayName;
    private String newDataArray;
    private Bitmap bitmap;
    private List<String> imagesEncodedList;
    private List<Bitmap> bitmapList;
    private List<Data> imageList;
    private int counter;
    private Boolean flag = false;
    private Gson gson;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_form);

        //custom toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //for the session values
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.USER_ID);
        name = user.get(sessionManager.FIRST_NAME) + " " + user.get(sessionManager.LAST_NAME);
        nname = user.get(sessionManager.NICKNAME);
        address = user.get(sessionManager.ADDRESS);
        contact = user.get(sessionManager.CONTACT);
        email = user.get(sessionManager.EMAIL);
        occupation = user.get(sessionManager.OCCUPATION);

        //setting the list
        gson = new GsonBuilder().setPrettyPrinting().create();
        imagesEncodedList = new ArrayList<>();
        bitmapList = new ArrayList<>();
        imageList = new ArrayList<>();
        counter = 0;
        file1 = "0";
        file2 = "0";
        file3 = "0";
        file4 = "0";
        file5 = "0";

        //getting the intent values
        Intent intent = getIntent();
        doc_id = intent.getStringExtra(MyAdoptionAdapter.DOC_ID);

        //setting the textview and close button icon
        tv_file_required = findViewById(R.id.tv_file_required);
        tv_file_header = findViewById(R.id.tv_file_header);
        tv_file = findViewById(R.id.file);
        tv_file2 = findViewById(R.id.file2);
        tv_file3 = findViewById(R.id.file3);
        tv_file4 = findViewById(R.id.file4);
        tv_file5 = findViewById(R.id.file5);
        img_file_close = findViewById(R.id.file_close);
        img_file_close2 = findViewById(R.id.file_close2);
        img_file_close3 = findViewById(R.id.file_close3);
        img_file_close4 = findViewById(R.id.file_close4);
        img_file_close5 = findViewById(R.id.file_close5);

        //edit text
        et_name = findViewById(R.id.name);
        et_nname = findViewById(R.id.nname);
        et_address = findViewById(R.id.address);
        et_contact = findViewById(R.id.contact);
        et_email = findViewById(R.id.email);
        et_occupation = findViewById(R.id.occupation);
        et_date_transact = findViewById(R.id.date_transact);
        et_reason = findViewById(R.id.reason);

        //checkbox
        checkBox = findViewById(R.id.checkbox);
        checkBox2 = findViewById(R.id.checkbox2);
        checkBox3 = findViewById(R.id.checkbox3);
        checkBox4 = findViewById(R.id.checkbox4);
        checkBox5 = findViewById(R.id.checkbox5);
        checkBox6 = findViewById(R.id.checkbox6);
        checkBox7 = findViewById(R.id.checkbox7);
        checkBox8 = findViewById(R.id.checkbox8);

        //button and progressbar
        btnSave = findViewById(R.id.btnSave);
        btnAttach = findViewById(R.id.btnAttach);
        loading = findViewById(R.id.loading);

        //setting the session value to edit text
        et_name.getEditText().setText(name);
        et_nname.getEditText().setText(nname);
        et_address.getEditText().setText(address);
        et_contact.getEditText().setText(contact);
        et_email.getEditText().setText(email);
        et_occupation.getEditText().setText(occupation);

        //disabling the edit text
        et_name.getEditText().setFocusable(false);
        et_name.getEditText().setClickable(false);
        et_nname.getEditText().setFocusable(false);
        et_nname.getEditText().setClickable(false);
        et_address.getEditText().setFocusable(false);
        et_address.getEditText().setClickable(false);
        et_contact.getEditText().setFocusable(false);
        et_contact.getEditText().setClickable(false);
        et_email.getEditText().setFocusable(false);
        et_email.getEditText().setClickable(false);
        et_occupation.getEditText().setFocusable(false);
        et_occupation.getEditText().setClickable(false);

        //load form and attachment
        loadForm(user_id);
        loadAttachment(doc_id);


        //deselect image
        final TextView[] textViews = {tv_file, tv_file2, tv_file3, tv_file4, tv_file5};
        img_file_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == true) {
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

        //button is clicked
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateCheckboxes() | counter == 0) {
                    tv_file_required.setVisibility(TextView.VISIBLE);
                    tv_file_required.setText("Image required.");
                    tv_file_required.setTextColor(Color.parseColor("#FFDD2C00"));
                    return;
                } else {
                    if (newDataArray != null && !newDataArray.isEmpty()) {
                        save(user_id, newDataArray, doc_id);
                    } else {
                        save(user_id, "0", doc_id);
                    }
                }
            }
        });

        //attach file
        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });
    }

    public void chooseFile() {
        /*
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICKFILE_RESULT_CODE);
        */
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
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

                            Data dt = new Data(getStringImage(compressImage(bitmapList.get(i))), imagesEncodedList.get(i));
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

    public void approved(String date) {
        LinearLayout layout_date = (LinearLayout) this.findViewById(R.id.layout_date);
        //LinearLayout layout_reason = (LinearLayout)this.findViewById(R.id.layout_reason);
        layout_date.setVisibility(LinearLayout.VISIBLE);
        //layout_reason.setVisibility(LinearLayout.VISIBLE);
        et_date_transact.getEditText().setClickable(false);
        et_date_transact.getEditText().setFocusable(false);
        et_date_transact.getEditText().setText(date);
        et_date_transact.setHint(getResources().getString(R.string.approved_date));
    }

    public void cancelled(String date, String reason_cancelled) {
        LinearLayout layout_date = (LinearLayout) this.findViewById(R.id.layout_date);
        LinearLayout layout_reason = (LinearLayout) this.findViewById(R.id.layout_reason);
        layout_date.setVisibility(LinearLayout.VISIBLE);
        layout_reason.setVisibility(LinearLayout.VISIBLE);
        et_date_transact.getEditText().setClickable(false);
        et_date_transact.getEditText().setFocusable(false);
        et_date_transact.getEditText().setText(date);
        et_date_transact.setHint(getResources().getString(R.string.cancelled_date));
        et_reason.getEditText().setClickable(false);
        et_reason.getEditText().setFocusable(false);
        et_reason.getEditText().setText(reason_cancelled);
        et_reason.setHint(getResources().getString(R.string.cancelled_reason));

    }

    public void rejected(String date, String reason_rejected) {
        LinearLayout layout_date = (LinearLayout) this.findViewById(R.id.layout_date);
        LinearLayout layout_reason = (LinearLayout) this.findViewById(R.id.layout_reason);
        layout_date.setVisibility(LinearLayout.VISIBLE);
        layout_reason.setVisibility(LinearLayout.VISIBLE);
        et_date_transact.getEditText().setClickable(false);
        et_date_transact.getEditText().setFocusable(false);
        et_date_transact.getEditText().setText(date);
        et_date_transact.setHint(getResources().getString(R.string.rejected_date));
        et_reason.getEditText().setClickable(false);
        et_reason.getEditText().setFocusable(false);
        et_reason.getEditText().setText(reason_rejected);
        et_reason.setHint(getResources().getString(R.string.rejected_reason));

    }

    public void disableForms() {
        LinearLayout layout_button = (LinearLayout) this.findViewById(R.id.layout_button);
        LinearLayout layout_attachment = (LinearLayout) this.findViewById(R.id.layout_attachment);
        LinearLayout layou_notice = (LinearLayout) this.findViewById(R.id.layout_notice);
        layout_button.setVisibility(LinearLayout.GONE);
        layout_attachment.setVisibility(LinearLayout.GONE);
        layou_notice.setVisibility(LinearLayout.GONE);
        tv_file_header.setVisibility(TextView.VISIBLE);
        checkBox.setClickable(false);
        checkBox2.setClickable(false);
        checkBox3.setClickable(false);
        checkBox4.setClickable(false);
        checkBox5.setClickable(false);
        checkBox6.setClickable(false);
        checkBox7.setClickable(false);
        checkBox8.setClickable(false);
    }

    public void loadForm(final String id) {
        String url = "http://192.168.137.1:8080/IRO/Android/volunteer_form.php";
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
                                    //check doc status id
                                    doc_status_id = object.getInt("doc_status_id");
                                    if (doc_status_id != 1) {
                                        disableForms();
                                    }
                                    if (doc_status_id == 2) {
                                        date_transact = object.getString("approved_date");
                                        approved(date_transact);
                                    }
                                    if (doc_status_id == 3) {
                                        date_transact = object.getString("rejected_date");
                                        reason = object.getString("rejected_reason");
                                        rejected(date_transact, reason);
                                    }
                                    if (doc_status_id == 4) {
                                        date_transact = object.getString("cancelled_date");
                                        reason = object.getString("cancelled_reason");
                                        cancelled(date_transact, reason);
                                    }
                                    //checkbox
                                    if (object.getString("is_adoption").equals("1")) {
                                        checkBox.setChecked(true);
                                    } else {
                                        checkBox.setChecked(false);
                                    }
                                    //checkbox2
                                    if (object.getString("is_transportation").equals("1")) {
                                        checkBox2.setChecked(true);
                                    } else {
                                        checkBox2.setChecked(false);
                                    }
                                    //checkbox3
                                    if (object.getString("is_shelter_clean_up").equals("1")) {
                                        checkBox3.setChecked(true);
                                    } else {
                                        checkBox3.setChecked(false);
                                    }
                                    //checkbox4
                                    if (object.getString("is_educational_campaign").equals("1")) {
                                        checkBox4.setChecked(true);
                                    } else {
                                        checkBox4.setChecked(false);
                                    }
                                    //checkbox5
                                    if (object.getString("is_animal_welfare").equals("1")) {
                                        checkBox5.setChecked(true);
                                    } else {
                                        checkBox5.setChecked(false);
                                    }
                                    //checkbox6
                                    if (object.getString("is_fostering").equals("1")) {
                                        checkBox6.setChecked(true);
                                    } else {
                                        checkBox6.setChecked(false);
                                    }
                                    //checkbox7
                                    if (object.getString("is_food_donation_drive").equals("1")) {
                                        checkBox7.setChecked(true);
                                    } else {
                                        checkBox7.setChecked(false);
                                    }
                                    //checkbox8
                                    if (object.getString("is_events").equals("1")) {
                                        checkBox8.setChecked(true);
                                    } else {
                                        checkBox8.setChecked(false);
                                    }
                                }
                            }
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
        String url = "http://192.168.137.1:8080/IRO/Android/volunteer_form_attachment.php";
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

    public void save(final String id, final String arrayString, final String id2) {
        loading.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.GONE);
        String url = "http://192.168.137.1:8080/IRO/Android/edit_volunteer.php";
        //checkbox input
        if (checkBox.isChecked()) {
            is_adoption = "1";
        } else {
            is_adoption = "0";
        }

        if (checkBox2.isChecked()) {
            is_transportation = "1";
        } else {
            is_transportation = "0";
        }

        if (checkBox3.isChecked()) {
            is_shelter_clean_up = "1";
        } else {
            is_shelter_clean_up = "0";
        }

        if (checkBox4.isChecked()) {
            is_educational_campaign = "1";
        } else {
            is_educational_campaign = "0";
        }
        if (checkBox5.isChecked()) {
            is_animal_welfare = "1";
        } else {
            is_animal_welfare = "0";
        }

        if (checkBox6.isChecked()) {
            is_fostering = "1";
        } else {
            is_fostering = "0";
        }

        if (checkBox7.isChecked()) {
            is_food_donation_drive = "1";
        } else {
            is_food_donation_drive = "0";
        }

        if (checkBox8.isChecked()) {
            is_events = "1";
        } else {
            is_events = "0";
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if (success.equals("1")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(VolunteerFormActivity.this);

                                builder.setTitle("Volunteer Application Form");
                                builder.setMessage("Successfully saved. Your application form has been updated.");

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        Intent intent = new Intent(getApplicationContext(), VolunteerActivity.class);
                                        startActivity(intent);
                                        //dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(VolunteerFormActivity.this);

                                builder.setTitle("Failed to save");
                                builder.setMessage(message);

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        //Intent intent = new Intent(getApplicationContext(), VolunteerActivity.class);
                                        //startActivity(intent);
                                        dialog.dismiss();
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
                params.put("userid", id);
                params.put("docid", id2);
                params.put("is_adoption", is_adoption);
                params.put("is_transportation", is_transportation);
                params.put("is_shelter_clean_up", is_shelter_clean_up);
                params.put("is_educational_campaign", is_educational_campaign);
                params.put("is_animal_welfare", is_animal_welfare);
                params.put("is_fostering", is_fostering);
                params.put("is_food_donation_drive", is_food_donation_drive);
                params.put("is_events", is_events);
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

    public boolean validateCheckboxes() {
        if (!checkBox.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked() && !checkBox4.isChecked() && !checkBox5.isChecked() && !checkBox6.isChecked() && !checkBox7.isChecked() && !checkBox8.isChecked()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(VolunteerFormActivity.this);

            builder.setTitle("Failed to submit");
            builder.setMessage("Please choose at least one committee.");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing but close the dialog
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
            loading.setVisibility(View.GONE);
            btnSave.setVisibility(View.VISIBLE);
            return false;
        } else {
            return true;
        }
    }
}
