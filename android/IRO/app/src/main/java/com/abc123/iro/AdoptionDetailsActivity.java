package com.abc123.iro;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
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

public class AdoptionDetailsActivity extends AppCompatActivity {
    public static final String PET_ID = "com.abc123.iro.extra.PET_ID";
    public static final String PET_NAME = "com.abc123.iro.extra.PET_NAME";
    public static final String PET_AGE = "com.abc123.iro.extra.PET_AGE";
    public static final String PET_GENDER = "com.abc123.iro.extra.PET_GENDER";
    private final static String TAG = AdoptionDetailsActivity.class.getSimpleName();
    private NetworkImageView imageView;
    private TextView tv_name;
    private TextView tv_breed;
    private TextView tv_gender;
    private TextView tv_age;
    private TextView tv_desc;
    private TextView tv_date;
    private TextView tv_acquired;
    private Button btnAdopt;
    private String id;
    private String imageURL;
    private String name;
    private String age;
    private String gender;
    private String acquired;
    ImageLoader imageLoader = AppController.getmInstance().getmImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_details);

        //setting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setting the widgets
        imageView = (NetworkImageView) findViewById(R.id.image_view);
        tv_name = findViewById(R.id.name);
        tv_breed = findViewById(R.id.breed);
        tv_gender = findViewById(R.id.gender);
        tv_age = findViewById(R.id.age);
        tv_acquired = findViewById(R.id.acquired);
        tv_desc = findViewById(R.id.desc);
        tv_date = findViewById(R.id.date);

        Intent intent = getIntent();
        //Toast.makeText(getApplicationContext(), intent.getStringExtra(PetAdapter.PET_ID), Toast.LENGTH_SHORT).show();
        id = intent.getStringExtra(PetAdapter.PET_ID);

        //load adoption details
        loadDetails(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adoption_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //action
                finish();
                break;

            case R.id.proceed:
                //action
                Intent a = new Intent(AdoptionDetailsActivity.this, AdoptionFormActivity.class);
                a.putExtra(PET_ID, id);
                a.putExtra(PET_NAME, name);
                a.putExtra(PET_AGE, age);
                a.putExtra(PET_GENDER, gender);
                startActivity(a);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadDetails(final String pet_id) {
        String url = "http://192.168.137.1:8080/IRO/Android/adoption_details.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoption_details.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString() + " dar amaw");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    StringBuilder stringBuilder = new StringBuilder("http://192.168.137.1:8080/IRO/Android/images/adoptions/");
                                    //StringBuilder stringBuilder = new StringBuilder("http://192.168.43.44:8080/IRO/Android/images/adoptions/");
                                    stringBuilder.append(object.getString("pet_image"));
                                    imageURL = stringBuilder.toString();
                                    imageView.setImageUrl(imageURL, imageLoader);
                                    name = object.getString("pet_name");
                                    age = object.getString("pet_age");
                                    gender = object.getString("pet_gender");
                                    tv_name.setText(object.getString("pet_name"));
                                    tv_breed.setText(object.getString("pet_type_desc"));
                                    tv_gender.setText(object.getString("pet_gender"));
                                    tv_age.setText(object.getString("pet_age"));
                                    tv_acquired.setText(object.getString("acquired_from"));
                                    tv_desc.setText(object.getString("pet_desc"));
                                    tv_date.setText(object.getString("date_added"));
                                    //Toast.makeText(getApplicationContext(), object.getString("pet_name"), Toast.LENGTH_SHORT).show();
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
                params.put("id", pet_id);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }
}
