package com.abc123.iro;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {
    private String user_id;
    private List<Notifications> notificationsList;
    private ListView listView;
    private NotificationAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private ImageView imageView;
    private TextView tv_no_post;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //setting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //for the session values
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.USER_ID);

        //no post state
        imageView = findViewById(R.id.image_no_post);
        tv_no_post = findViewById(R.id.no_post);

        //listview and arraylist
        notificationsList = new ArrayList<>();
        listView = findViewById(R.id.list);
        refreshLayout = findViewById(R.id.swipe_refresh_layout);

        loadNotifications();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notificationsList.clear();
                loadNotifications();
                refreshLayout.setRefreshing(false);
            }
        });

        //onClickListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Notifications notifications = notificationsList.get(i);
                if (notifications.getType() == 1) {
                    Intent intent = new Intent(NotificationActivity.this, MyAdoptionActivity.class);
                    startActivity(intent);
                } else if (notifications.getType() == 2) {
                    Intent intent = new Intent(NotificationActivity.this, EventsActivity.class);
                    startActivity(intent);
                } else if (notifications.getType() == 3) {
                    Intent intent = new Intent(NotificationActivity.this, VolunteerActivity.class);
                    startActivity(intent);
                } else if (notifications.getType() == 4 || notifications.getType() == 5 || notifications.getType() == 6) {
                    Intent intent = new Intent(NotificationActivity.this, MyReportActivity.class);
                    startActivity(intent);
                } else if (notifications.getType() == 7) {
                    Intent intent = new Intent(NotificationActivity.this, DonationActivity.class);
                    startActivity(intent);
                } else {
                    //nothing happens
                    return;
                }
            }
        });


    }

    public void loadNotifications() {
        String url = "http://192.168.137.1:8080/IRO/Android/notifications.php";
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
                                    Notifications notifications = new Notifications(object.getInt("notification_id"), object.getInt("notification_type_id"), object.getString("subject"), object.getString("content"), object.getString("date"));
                                    notificationsList.add(notifications);
                                }
                                adapter = new NotificationAdapter(notificationsList, NotificationActivity.this, user_id);
                                listView.setAdapter(adapter);
                            } else {
                                imageView.setVisibility(ImageView.VISIBLE);
                                tv_no_post.setVisibility(TextView.VISIBLE);
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
                params.put("id", user_id);
                return params;
            }
        };
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //action
                finish();
                break;

            case R.id.notification:
                Intent intent = new Intent(NotificationActivity.this, NotificationActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
