package com.abc123.iro;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdoptionActivity extends AppCompatActivity {
    private String user_id;
    private int intent_id;
    private String imageURL;
    private List<MyAdoptions> myAdoptionsList;
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;
    private MyAdoptionAdapter adapter;
    private ImageView imageView;
    private TextView tv_no_post;
    ImageLoader imageLoader = AppController.getmInstance().getmImageLoader();
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_adoption);

        /*
        //get intent values
        Intent intent = getIntent();
        intent_id = intent.getIntExtra(HomeActivity.TYPE_ID,0);
        */

        //for the session values
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.USER_ID);

        //no post state
        imageView = findViewById(R.id.image_no_post);
        tv_no_post = findViewById(R.id.no_post);

        //custom toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setting listview and arraylist and refreshlayout
        myAdoptionsList = new ArrayList<>();
        listView = findViewById(R.id.list);
        adapter = new MyAdoptionAdapter(myAdoptionsList, MyAdoptionActivity.this);
        refreshLayout = findViewById(R.id.swipe_refresh_layout);

        //update notification
        //updateNotification(user_id,String.valueOf(intent_id));

        //load my adoption
        loadMyAdoptions(user_id);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myAdoptionsList.clear();
                loadMyAdoptions(user_id);
                refreshLayout.setRefreshing(false);
            }
        });

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

    //search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)) {
                    adapter.filter("");
                    listView.clearTextFilter();
                } else {
                    adapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }
    /*
    public void updateNotification(final String userid, final String typeid) {
        String url = "http://192.168.137.1:8080/IRO/Android/update_notification.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/my_adoptions.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                Log.d("Result","Success: "+success);
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
                params.put("userid", userid);
                params.put("typeid", typeid);
                return params;
            }
        };

        AppController.getmInstance().addToRequestQueue(stringRequest);
    }
    */

    public void loadMyAdoptions(final String id) {
        String url = "http://192.168.137.1:8080/IRO/Android/my_adoptions.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/my_adoptions.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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
                                    StringBuilder stringBuilder = new StringBuilder("http://192.168.137.1:8080/IRO/Android/images/adoptions/");
                                    //StringBuilder stringBuilder = new StringBuilder("http://192.168.43.44:8080/IRO/Android/images/adoptions/");
                                    stringBuilder.append(object.getString("pet_image"));
                                    imageURL = stringBuilder.toString();
                                    MyAdoptions myAdoptions = new MyAdoptions(object.getInt("user_id"), object.getInt("doc_id"), object.getInt("doc_status_id"), object.getInt("pet_status_id"), imageURL, object.getString("pet_name"), object.getString("pet_type_desc"), object.getString("pet_age"), object.getString("doc_status_desc"));
                                    myAdoptionsList.add(myAdoptions);
                                }
                                adapter = new MyAdoptionAdapter(myAdoptionsList, MyAdoptionActivity.this);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                imageView.setVisibility(ImageView.VISIBLE);
                                tv_no_post.setVisibility(TextView.VISIBLE);
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

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        //loadUserDetails(user_id);
        myAdoptionsList.clear();
        loadMyAdoptions(user_id);
    }
}
