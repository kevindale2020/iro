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
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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

import static android.app.Activity.RESULT_OK;

public class Frag2 extends Fragment {
    private static final int PICKFILE_RESULT_CODE = 1;
    private TextInputLayout et_name;
    private TextInputLayout et_nname;
    private TextInputLayout et_address;
    private TextInputLayout et_contact;
    private TextInputLayout et_email;
    private TextInputLayout et_occupation;
    private CheckBox checkBox;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBox5;
    private CheckBox checkBox6;
    private CheckBox checkBox7;
    private CheckBox checkBox8;
    private TextView tv_file_required;
    private TextView tv_file;
    private TextView tv_file2;
    private TextView tv_file3;
    private TextView tv_file4;
    private TextView tv_file5;
    private ImageView img_file_close;
    private ImageView img_file_close2;
    private ImageView img_file_close3;
    private ImageView img_file_close4;
    private ImageView img_file_close5;
    private Button btnAttach;
    private Button btnSubmit;
    private ProgressBar loading;
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag2_layout, container, false);

        //session values
        sessionManager = new SessionManager(getContext());
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

        //setting the textview and close button icon
        tv_file_required = view.findViewById(R.id.tv_file_required);
        tv_file = view.findViewById(R.id.file);
        tv_file2 = view.findViewById(R.id.file2);
        tv_file3 = view.findViewById(R.id.file3);
        tv_file4 = view.findViewById(R.id.file4);
        tv_file5 = view.findViewById(R.id.file5);
        img_file_close = view.findViewById(R.id.file_close);
        img_file_close2 = view.findViewById(R.id.file_close2);
        img_file_close3 = view.findViewById(R.id.file_close3);
        img_file_close4 = view.findViewById(R.id.file_close4);
        img_file_close5 = view.findViewById(R.id.file_close5);

        //edit text
        et_name = view.findViewById(R.id.name);
        et_nname = view.findViewById(R.id.nname);
        et_address = view.findViewById(R.id.address);
        et_contact = view.findViewById(R.id.contact);
        et_email = view.findViewById(R.id.email);
        et_occupation = view.findViewById(R.id.occupation);

        //checkbox
        checkBox = view.findViewById(R.id.checkbox);
        checkBox2 = view.findViewById(R.id.checkbox2);
        checkBox3 = view.findViewById(R.id.checkbox3);
        checkBox4 = view.findViewById(R.id.checkbox4);
        checkBox5 = view.findViewById(R.id.checkbox5);
        checkBox6 = view.findViewById(R.id.checkbox6);
        checkBox7 = view.findViewById(R.id.checkbox7);
        checkBox8 = view.findViewById(R.id.checkbox8);

        //button and progressbar
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnAttach = view.findViewById(R.id.btnAttach);
        loading = view.findViewById(R.id.loading);

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

        //deselect image
        final TextView[] textViews = {tv_file, tv_file2, tv_file3, tv_file4, tv_file5};

        img_file_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == true) {
                    counter--;
                    textViews[0].setText(null);
                    textViews[0].setVisibility(TextView.GONE);
                    img_file_close.setVisibility(ImageView.GONE);
                    bitmapList.clear();
                    imageList.set(0, null);
                    newDataArray = gson.toJson(imageList);
                    Toast.makeText(getContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    if (counter < 5) {
                        btnAttach.setEnabled(true);
                        btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    }
                } else {
                    counter--;
                    textViews[0].setText(null);
                    textViews[0].setVisibility(TextView.GONE);
                    img_file_close.setVisibility(ImageView.GONE);
                    imageList.set(0, null);
                    bitmap.recycle();
                    newDataArray = gson.toJson(imageList);
                    Toast.makeText(getContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    if (counter < 5) {
                        btnAttach.setEnabled(true);
                        btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    }
                }
            }
        });
        img_file_close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == true) {
                    counter--;
                    textViews[1].setText(null);
                    textViews[1].setVisibility(TextView.GONE);
                    img_file_close2.setVisibility(ImageView.GONE);
                    bitmapList.clear();
                    imageList.set(1, null);
                    newDataArray = gson.toJson(imageList);
                    Toast.makeText(getContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    if (counter < 5) {
                        btnAttach.setEnabled(true);
                        btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    }
                } else {
                    counter--;
                    textViews[1].setText(null);
                    textViews[1].setVisibility(TextView.GONE);
                    img_file_close2.setVisibility(ImageView.GONE);
                    imageList.set(1, null);
                    bitmap.recycle();
                    newDataArray = gson.toJson(imageList);
                    Toast.makeText(getContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    if (counter < 5) {
                        btnAttach.setEnabled(true);
                        btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    }
                }
            }
        });
        img_file_close3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == true) {
                    counter--;
                    textViews[2].setText(null);
                    textViews[2].setVisibility(TextView.GONE);
                    img_file_close3.setVisibility(ImageView.GONE);
                    bitmapList.clear();
                    imageList.set(2, null);
                    newDataArray = gson.toJson(imageList);
                    Toast.makeText(getContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    if (counter < 5) {
                        btnAttach.setEnabled(true);
                        btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    }
                } else {
                    counter--;
                    textViews[2].setText(null);
                    textViews[2].setVisibility(TextView.GONE);
                    img_file_close3.setVisibility(ImageView.GONE);
                    imageList.set(2, null);
                    bitmap.recycle();
                    newDataArray = gson.toJson(imageList);
                    Toast.makeText(getContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    if (counter < 5) {
                        btnAttach.setEnabled(true);
                        btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    }
                }
            }
        });
        img_file_close4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == true) {
                    counter--;
                    textViews[3].setText(null);
                    textViews[3].setVisibility(TextView.GONE);
                    img_file_close4.setVisibility(ImageView.GONE);
                    bitmapList.clear();
                    imageList.set(3, null);
                    newDataArray = gson.toJson(imageList);
                    Toast.makeText(getContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    if (counter < 5) {
                        btnAttach.setEnabled(true);
                        btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    }
                } else {
                    counter--;
                    textViews[3].setText(null);
                    textViews[3].setVisibility(TextView.GONE);
                    img_file_close4.setVisibility(ImageView.GONE);
                    imageList.set(3, null);
                    bitmap.recycle();
                    newDataArray = gson.toJson(imageList);
                    Toast.makeText(getContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    if (counter < 5) {
                        btnAttach.setEnabled(true);
                        btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    }
                }
            }
        });
        img_file_close5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == true) {
                    counter--;
                    textViews[4].setText(null);
                    textViews[4].setVisibility(TextView.GONE);
                    img_file_close5.setVisibility(ImageView.GONE);
                    bitmapList.clear();
                    imageList.set(4, null);
                    newDataArray = gson.toJson(imageList);
                    Toast.makeText(getContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    if (counter < 5) {
                        btnAttach.setEnabled(true);
                        btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    }
                } else {
                    counter--;
                    textViews[4].setText(null);
                    textViews[4].setVisibility(TextView.GONE);
                    img_file_close5.setVisibility(ImageView.GONE);
                    imageList.set(4, null);
                    bitmap.recycle();
                    newDataArray = gson.toJson(imageList);
                    Toast.makeText(getContext(), "Counter: " + counter, Toast.LENGTH_SHORT).show();
                    if (counter < 5) {
                        btnAttach.setEnabled(true);
                        btnAttach.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    }
                }
            }
        });

        //button is clicked
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateCheckboxes() | counter == 0) {
                    tv_file_required.setVisibility(TextView.VISIBLE);
                    tv_file_required.setText("Image required.");
                    tv_file_required.setTextColor(Color.parseColor("#FFDD2C00"));
                    return;
                }
                submit();
            }
        });

        //attach file
        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });

        return view;
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
                            Toast.makeText(getContext(), "Sorry you can only upload 5 images", Toast.LENGTH_SHORT).show();
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
                                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                                    bitmapList.add(bitmap);
                                    cursor = getContext().getContentResolver().query(uri, null, null, null, null);
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
                        Toast.makeText(getContext(), "Size: " + counter, Toast.LENGTH_LONG).show();
                    }
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

    public void submit() {
        loading.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);
        String url = "http://192.168.137.1:8080/IRO/Android/volunteer_application.php";
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle("Volunteer Application Form");
                                builder.setMessage("Successfully submitted. Your application form is now pending for approval.");

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

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                builder.setTitle("Failed to submit");
                                builder.setMessage(message);

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do nothing but close the dialog
                                        Intent intent = new Intent(getContext(), VolunteerActivity.class);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                                loading.setVisibility(View.GONE);
                                btnSubmit.setVisibility(View.VISIBLE);
                            }
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
                params.put("userid", user_id);
                params.put("is_adoption", is_adoption);
                params.put("is_transportation", is_transportation);
                params.put("is_shelter_clean_up", is_shelter_clean_up);
                params.put("is_educational_campaign", is_educational_campaign);
                params.put("is_animal_welfare", is_animal_welfare);
                params.put("is_fostering", is_fostering);
                params.put("is_food_donation_drive", is_food_donation_drive);
                params.put("is_events", is_events);
                params.put("array", newDataArray);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    public boolean validateCheckboxes() {
        if (!checkBox.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked() && !checkBox4.isChecked() && !checkBox5.isChecked() && !checkBox6.isChecked() && !checkBox7.isChecked() && !checkBox8.isChecked()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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
            btnSubmit.setVisibility(View.VISIBLE);
            return false;
        } else {
            return true;
        }
    }

}
