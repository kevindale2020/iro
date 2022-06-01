package com.abc123.iro;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String user_id;
    private String imageURL;
    private String imageURL2;
    private String fullname;
    private ImageView imageView2;
    private TextView tv_no_post;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View view;
    private NetworkImageView imageView;
    private SwipeRefreshLayout refreshLayout;
    private TextView tv_fullname;
    private ListView listView;
    private List<Events> eventsList;
    private EventsAdapter adapter;
    SessionManager sessionManager;
    private String image_url;
    ImageLoader imageLoader = AppController.getmInstance().getmImageLoader();
    private TextView tv_notification_badge;
    private int counter;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        //handler
        handler = new Handler();

        //for the session values
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.USER_ID);

        //for the sidenav
        drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //to set the navigation to control its components
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        view = navigationView.getHeaderView(0);
        imageView = view.findViewById(R.id.user_image);
        tv_fullname = view.findViewById(R.id.user_fullname);

        //listview and arraylist and refreshlayout
        eventsList = new ArrayList<>();
        listView = findViewById(R.id.list);
        adapter = new EventsAdapter(eventsList, EventsActivity.this);
        refreshLayout = findViewById(R.id.swipe_refresh_layout);

        //no post state
        imageView2 = findViewById(R.id.image_no_post);
        tv_no_post = findViewById(R.id.no_post);

        //bottom navigation
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(a);

                        break;

                    case R.id.navigation_locator:
                        Intent b = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(b);
                        break;

                    case R.id.navigation_adoption:
                        Intent c = new Intent(getApplicationContext(), AdoptionActivity.class);
                        startActivity(c);
                        break;

                    case R.id.navigation_shop:
                        Intent d = new Intent(getApplicationContext(), ItemActivity.class);
                        startActivity(d);
                        break;

                    case R.id.navigation_events:
                        Intent e = new Intent(getApplicationContext(), EventsActivity.class);
                        startActivity(e);
                        break;

                }
                return false;
            }
        });

        loadUserDetails(user_id);

        loadEvents();

        content();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                eventsList.clear();
                loadEvents();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adoption, menu);
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
        //notification
        final MenuItem menuItem1 = menu.findItem(R.id.notification);
        View actionView = MenuItemCompat.getActionView(menuItem1);
        tv_notification_badge = actionView.findViewById(R.id.notification_badge);
        setupBadge();
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem1);
            }
        });
        return true;
    }



    //update notification
    public void updateNotification2(final String userid) {
        String url = "http://192.168.137.1:8080/IRO/Android/update_notification2.php";
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
                return params;
            }
        };

        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    //count notifications
    public void countNotifications() {
        String url = "http://192.168.137.1:8080/IRO/Android/count_notification.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoption_details.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                counter = jsonObject.getInt("counter");
                            } else {
                                counter = 0;
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

    private void setupBadge() {

        if (tv_notification_badge!= null) {
            if (counter == 0) {
                if (tv_notification_badge.getVisibility() != View.GONE) {
                    tv_notification_badge.setVisibility(View.GONE);
                }
            } else {
                tv_notification_badge.setText(String.valueOf(Math.min(counter, 99)));
                if (tv_notification_badge.getVisibility() != View.VISIBLE) {
                    tv_notification_badge.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void content() {
        countNotifications();
        setupBadge();
        refresh(1000 * 1);
    }

    private void refresh(int i) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //pushNotification();
                //updateNotification(user_id, String.valueOf(notification_type_id));
                content();
            }

        };
        handler.postDelayed(runnable, i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                Intent intent = new Intent(EventsActivity.this, NotificationActivity.class);
                startActivity(intent);
                updateNotification2(user_id);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        loadUserDetails(user_id);
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        //loadUserDetails(user_id);
        //Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
        //startActivity(intent);
        //finish();
    }

    public void loadUserDetails(final String id) {
        String url = "http://192.168.137.1:8080/IRO/Android/users.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/users.php";
        final int time = (int) (System.currentTimeMillis());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                JSONArray resultArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < resultArray.length(); i++) {
                                    JSONObject object = resultArray.getJSONObject(i);
                                    if (object.getString("user_image").isEmpty()) {
                                        StringBuilder stringBuilder = new StringBuilder("http://192.168.137.1:8080/IRO/Android/images/users/");
                                        //StringBuilder stringBuilder = new StringBuilder("http://192.168.43.44:8080/IRO/Android/images/users/");
                                        stringBuilder.append("user_none.png");
                                        image_url = stringBuilder.toString();
                                        //Toast.makeText(getApplicationContext(), "user_none.png", Toast.LENGTH_SHORT).show();
                                    } else {
                                        StringBuilder stringBuilder = new StringBuilder("http://192.168.137.1:8080/IRO/Android/images/users/");
                                        //StringBuilder stringBuilder = new StringBuilder("http://192.168.43.44:8080/IRO/Android/images/users/");
                                        stringBuilder.append(object.getString("user_image"));
                                        stringBuilder.append("?timestamp=" + String.valueOf(time));
                                        image_url = stringBuilder.toString();
                                    }
                                    imageView.setImageUrl(image_url, imageLoader);
                                    tv_fullname.setText(object.getString("user_fname") + " " + object.getString("user_lname"));
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
        stringRequest.setShouldCache(false);
        //AppController.getmInstance().getmRequestQueue().getCache().remove(url);
        //AppController.getmInstance().getmRequestQueue().getCache().clear(url);
        AppController.getmInstance().addToRequestQueue(stringRequest);

        /*
        NetworkImageView imageView = view.findViewById(R.id.user_image);
        TextView tv_fullname = (TextView) view.findViewById(R.id.user_fullname);
        //TextView tv_email = (TextView) view.findViewById(R.id.user_email);
        imageView.setImageUrl(imageURL,imageLoader);
        tv_fullname.setText(fullname);
        tv_email.setText(email);
        */


    }

    //load reports
    public void loadEvents() {
        String url = "http://192.168.137.1:8080/IRO/Android/events.php";
        //String url = "http://192.168.43.44:8080/IRO/Android/adoptions.php";
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
                                    //userimage
                                    StringBuilder stringBuilder = new StringBuilder("http://192.168.137.1:8080/IRO/Android/images/users/");
                                    stringBuilder.append(object.getString("user_image"));
                                    imageURL = stringBuilder.toString();

                                    //reportimage
                                    StringBuilder stringBuilder2 = new StringBuilder("http://192.168.137.1:8080/IRO/Android/images/events/");
                                    stringBuilder2.append(object.getString("image"));
                                    imageURL2 = stringBuilder2.toString();

                                    //Pets pets = new Pets(object.getInt("pet_id"), imageURL, object.getString("pet_name"), object.getString("pet_type_desc"), object.getString("pet_age"), object.getString("date_added"), object.getInt("pet_status_id"));
                                    //petsList.add(pets);

                                    fullname = object.getString("user_fname")+" "+object.getString("user_lname");

                                    Events events = new Events(object.getInt("id"), imageURL, fullname, object.getString("role_desc"), object.getString("date_posted"), imageURL2, object.getString("title"), object.getString("desc"));
                                    eventsList.add(events);
                                }
                                adapter = new EventsAdapter(eventsList, EventsActivity.this);
                                listView.setAdapter(adapter);
                            } else {
                                imageView2.setVisibility(ImageView.VISIBLE);
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
                });
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    //side navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(EventsActivity.this);

                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to logout?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        sessionManager.logout();
                        finish();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                break;

            case R.id.nav_password:
                Intent a = new Intent(EventsActivity.this, ChangePasswordActivity.class);
                startActivity(a);
                break;

            case R.id.nav_adoption:
                Intent b = new Intent(EventsActivity.this, MyAdoptionActivity.class );
                startActivity(b);
                break;

            case R.id.nav_report1:
                Intent c = new Intent(EventsActivity.this, Report1Activity.class);
                startActivity(c);
                break;

            case R.id.nav_volunteer:
                Intent d = new Intent(EventsActivity.this, VolunteerActivity.class);
                startActivity(d);
                break;

            case R.id.nav_guidelines:
                Intent e = new Intent(EventsActivity.this, GuidelinesActivity.class);
                startActivity(e);
                break;

            case R.id.nav_my_reports:
                Intent f = new Intent(EventsActivity.this, MyReportActivity.class);
                startActivity(f);
                break;

            case R.id.nav_donation:
                Intent g = new Intent(EventsActivity.this, DonationActivity.class);
                startActivity(g);
                break;
        }
        return  true;
    }
}
