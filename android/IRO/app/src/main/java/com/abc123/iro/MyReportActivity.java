package com.abc123.iro;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

public class MyReportActivity extends AppCompatActivity {
    private String user_id;
    private String imageURL;
    private String imageURL2;
    private String fullname;
    SessionManager sessionManager;
    private SwipeRefreshLayout refreshLayout;
    private List<Reports> reportsList;
    private ListView listView;
    private MyReportsAdapter adapter;
    private ImageView imageView;
    private TextView tv_no_post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_report);

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

        //listview and arraylist and refreshlayout
        reportsList = new ArrayList<>();
        listView = findViewById(R.id.list);
        adapter = new MyReportsAdapter(reportsList, MyReportActivity.this, user_id);
        refreshLayout = findViewById(R.id.swipe_refresh_layout);

        //load reports
        loadReports();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reportsList.clear();
                loadReports();
                refreshLayout.setRefreshing(false);
            }
        });
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

    //load reports
    public void loadReports() {
        String url = "http://192.168.137.1:8080/IRO/Android/my_reports.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoptions.php";
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
                                    //userimage
                                    if (object.getString("user_image").isEmpty()) {
                                        StringBuilder stringBuilder = new StringBuilder("http://192.168.137.1:8080/IRO/Android/images/users/");
                                        //StringBuilder stringBuilder = new StringBuilder("http://192.168.43.44:8080/IRO/Android/images/users/");
                                        stringBuilder.append("user_none.png");
                                        imageURL = stringBuilder.toString();
                                        //Toast.makeText(getApplicationContext(), "user_none.png", Toast.LENGTH_SHORT).show();
                                    } else {
                                        StringBuilder stringBuilder = new StringBuilder("http://192.168.137.1:8080/IRO/Android/images/users/");
                                        stringBuilder.append(object.getString("user_image"));
                                        imageURL = stringBuilder.toString();
                                    }

                                    //reportimage
                                    StringBuilder stringBuilder2 = new StringBuilder("http://192.168.137.1:8080/IRO/Android/images/attachments/");
                                    stringBuilder2.append(object.getString("file_name"));
                                    imageURL2 = stringBuilder2.toString();

                                    //Pets pets = new Pets(object.getInt("pet_id"), imageURL, object.getString("pet_name"), object.getString("pet_type_desc"), object.getString("pet_age"), object.getString("date_added"), object.getInt("pet_status_id"));
                                    //petsList.add(pets);

                                    fullname = object.getString("user_fname")+" "+object.getString("user_lname");

                                    Reports reports = new Reports(object.getInt("doc_id"), object.getInt("doc_status_id"), object.getInt("report_type_id"), object.getInt("user_id"), imageURL, fullname, object.getString("user_contact"), object.getString("date_reported"), object.getString("reporter_location"), imageURL2, object.getString("title"), object.getString("description"), object.getString("doc_status_desc"));
                                    reportsList.add(reports);
                                }
                                adapter = new MyReportsAdapter(reportsList, MyReportActivity.this, user_id);
                                listView.setAdapter(adapter);
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
                params.put("id", user_id);
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
