package com.abc123.iro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class ItemDetailsActivity extends AppCompatActivity {
    private NetworkImageView imageView;
    private TextView tv_name;
    private TextView tv_category;
    private TextView tv_desc;
    private TextView tv_qty;
    private TextView tv_price;
    private String item_id;
    private String imageURL;
    ImageLoader imageLoader = AppController.getmInstance().getmImageLoader();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        //setting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setting the widgets
        imageView = (NetworkImageView) findViewById(R.id.image_view);
        tv_name = findViewById(R.id.name);
        tv_category = findViewById(R.id.category);
        tv_desc = findViewById(R.id.desc);
        tv_qty = findViewById(R.id.qty);
        tv_price = findViewById(R.id.price);

        Intent intent = getIntent();
        item_id = intent.getStringExtra(ItemAdapter.ITEM_ID);

        loadDetails(item_id);
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

    public void loadDetails(final String id) {
        String url = "http://192.168.137.1:8080/IRO/Android/item_details.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoption_details.php";
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
                                    StringBuilder stringBuilder = new StringBuilder("http://192.168.137.1:8080/IRO/Android/images/items/");
                                    //StringBuilder stringBuilder = new StringBuilder("http://192.168.43.44:8080/IRO/Android/images/adoptions/");
                                    stringBuilder.append(object.getString("item_image"));
                                    imageURL = stringBuilder.toString();
                                    imageView.setImageUrl(imageURL, imageLoader);
                                    tv_name.setText(object.getString("item_name"));
                                    tv_category.setText(object.getString("item_category_desc"));
                                    tv_desc.setText(object.getString("item_desc"));
                                    tv_qty.setText(String.valueOf(object.getInt("item_qty")));
                                    tv_price.setText("₱"+String.valueOf(String.format("%.2f", object.getDouble("item_price"))));
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
                params.put("id", id);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }
}
