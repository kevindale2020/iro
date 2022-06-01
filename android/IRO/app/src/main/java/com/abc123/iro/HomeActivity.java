package com.abc123.iro;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.abc123.iro.AppController.CHANNEL_1_ID;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TYPE_ID = "com.abc123.iro.extra.TYPE_ID";
    private NotificationManagerCompat notificationManager;
    private TextView mTextMessage;
    private TextView tv_notification_badge;
    private DrawerLayout drawer;
    private View view;
    private NavigationView navigationView;
    private String imageURL;
    private String imageURL2;
    private String fname;
    private String lname;
    private String fullname;
    private String email;
    private String path;
    private String subject;
    private String content;
    private int notification_type_id;
    private int counter;
    private ImageView btnEdit;
    private String user_id;
    private String image_url;
    private NetworkImageView imageView;
    private TextView tv_fullname;
    private SwipeRefreshLayout refreshLayout;
    private List<Reports> reportsList;
    private ListView listView;
    private ReportsAdapter adapter;
    private ImageView image_no_post;
    private TextView tv_no_post;
    private static final String TAG = HomeActivity.class.getSimpleName();
    SessionManager sessionManager;
    Handler handler;
    ImageLoader imageLoader = AppController.getmInstance().getmImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*
        //random integer
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        */
        //handler
        handler = new Handler();
        //for the session values
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.USER_ID);
        //Toast.makeText(getApplicationContext(), user_id, Toast.LENGTH_SHORT).show();

        //for the sidenav and toolbar
        drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        //String username = user.get(sessionManager.USERNAME);
        //Toast.makeText(getApplicationContext(), "Welcome "+fname, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), fullname, Toast.LENGTH_SHORT).show();

        //notification
        notificationManager = NotificationManagerCompat.from(this);
        content();

        //listview and arraylist and refreshlayout
        reportsList = new ArrayList<>();
        listView = findViewById(R.id.list);
        refreshLayout = findViewById(R.id.swipe_refresh_layout);
        adapter = new ReportsAdapter(reportsList, HomeActivity.this, user_id);

        image_no_post = findViewById(R.id.image_no_post);
        tv_no_post = findViewById(R.id.no_post);

        btnEdit = view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                //recreate();
            }
        });

        //bottom navigation
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
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
        getMenuInflater().inflate(R.menu.adoption, menu);
        //search
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
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
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                Intent intent = new Intent(HomeActivity.this, NotificationActivity.class);
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
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        loadUserDetails(user_id);
        //Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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
                        Log.i(TAG, response.toString());
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
                            //e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
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

    public void checkStatus(final String id) {
        String url = "http://192.168.137.1:8080/IRO/Android/check_status.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                JSONArray resultArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < resultArray.length(); i++) {
                                    JSONObject object = resultArray.getJSONObject(i);
                                    if(object.getInt("is_active")==1) {

                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

                                        builder.setTitle("Message");
                                        builder.setMessage("Your account has been temporarily blocked. You will be logged out");

                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int which) {
                                                // Do nothing but close the dialog
                                                sessionManager.logout();
                                                finish();
                                                dialog.dismiss();
                                            }
                                        });

                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            //e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
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
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    public void pushNotification() {
        String url = "http://192.168.137.1:8080/IRO/Android/notification.php";
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
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    //Toast.makeText(getApplicationContext(), "Subject: "+obj.getString("subject")+"\n"+"Content: "+obj.getString("content"), Toast.LENGTH_SHORT).show();
                                    //Notifications notifications = new Notifications(obj.getInt("id"), obj.getString("subject"), obj.getString("content"));
                                    //list.add(notifications);
                                    //subject = obj.getString("subject");
                                    //content = obj.getString("content");
                                    subject = obj.getString("subject");
                                    content = obj.getString("content");
                                    notification_type_id = obj.getInt("notification_type_id");
                                    sendNotification(subject, content, notification_type_id);
                                }
                                /*
                                for (int i = 0; i < list.size(); i++) {

                                    Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                                            .setSmallIcon(R.drawable.ic_looks_one_black_24dp)
                                            .setContentTitle(list.get(i).getSubject())
                                            .setContentText(list.get(i).getContent())
                                            .setContentIntent(pendingIntent)
                                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                            .build();
                                    notificationManager.notify(1, notification);

                                    //Toast.makeText(getApplicationContext(), "Hi", Toast.LENGTH_SHORT).show();
                                }
                                */

                            }
                        } catch (JSONException e) {
                            //e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(user_id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void content() {
        pushNotification();
        countNotifications();
        setupBadge();
        updateNotification(user_id, String.valueOf(notification_type_id));
        //checkStatus(user_id);
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

    public void sendNotification(String subject, String content, int typeid) {
        if (typeid == 1) {
            //random integer
            //int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
            Intent resultIntent = new Intent(getApplicationContext(), MyAdoptionActivity.class);
            //resultIntent.putExtra(USER_ID, userid);
            //resultIntent.putExtra(TYPE_ID, typeid);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_hnet_com_image)
                    .setContentTitle(subject)
                    .setContentText(content)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();
            //int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
            notificationManager.notify(1, notification);
        } else if (typeid == 2) {
            //random integer
            //int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
            Intent resultIntent = new Intent(getApplicationContext(), EventsActivity.class);
            //resultIntent.putExtra(USER_ID, userid);
            //resultIntent.putExtra(TYPE_ID, typeid);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_hnet_com_image)
                    .setContentTitle(subject)
                    .setContentText(content)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();
            notificationManager.notify(2, notification);
        } else if (typeid == 3) {
            //random integer
            //int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
            Intent resultIntent = new Intent(getApplicationContext(), VolunteerActivity.class);
            //resultIntent.putExtra(USER_ID, userid);
            //resultIntent.putExtra(TYPE_ID, typeid);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_hnet_com_image)
                    .setContentTitle(subject)
                    .setContentText(content)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();
            notificationManager.notify(3, notification);
        } else if (typeid == 4 || typeid == 5 || typeid == 6) {
            //random integer
            //int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
            Intent resultIntent = new Intent(getApplicationContext(), MyReportActivity.class);
            //resultIntent.putExtra(USER_ID, userid);
            //resultIntent.putExtra(TYPE_ID, typeid);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_hnet_com_image)
                    .setContentTitle(subject)
                    .setContentText(content)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();
            notificationManager.notify(4, notification);
        } else if (typeid == 7) {
            //random integer
            //int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
            Intent resultIntent = new Intent(getApplicationContext(), DonationActivity.class);
            //resultIntent.putExtra(USER_ID, userid);
            //resultIntent.putExtra(TYPE_ID, typeid);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_hnet_com_image)
                    .setContentTitle(subject)
                    .setContentText(content)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();
            notificationManager.notify(5, notification);
        } else {
            //random integer
            //int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
            Intent resultIntent = new Intent(getApplicationContext(), NotificationActivity.class);
            //resultIntent.putExtra(USER_ID, userid);
            //resultIntent.putExtra(TYPE_ID, typeid);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_hnet_com_image)
                    .setContentTitle(subject)
                    .setContentText(content)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();
            notificationManager.notify(6, notification);
        }
        //Intent resultIntent = new Intent(getApplicationContext(), Main2Activity.class);
        //resultIntent.putExtra(USER_ID, userid);
        //resultIntent.putExtra(TYPE_ID, typeid);
        //resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    //update notification
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
                                Log.d("Result", "Success: " + success);
                            }
                        } catch (JSONException e) {
                            //e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
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
                                Log.d("Result", "Success: " + success);
                            }
                        } catch (JSONException e) {
                            //e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error.printStackTrace();
                       // Toast.makeText(getApplicationContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
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
                            //e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
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

        if (tv_notification_badge != null) {
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

    //load reports
    public void loadReports() {
        String url = "http://192.168.137.1:8080/IRO/Android/reports.php";
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

                                    fullname = object.getString("user_fname") + " " + object.getString("user_lname");

                                    Reports reports = new Reports(object.getInt("doc_id"), object.getInt("doc_status_id"), object.getInt("report_type_id"), object.getInt("user_id"), imageURL, fullname, object.getString("user_contact"), object.getString("date_reported"), object.getString("reporter_location"), imageURL2, object.getString("title"), object.getString("description"), object.getString("doc_status_desc"));
                                    reportsList.add(reports);
                                }
                                adapter = new ReportsAdapter(reportsList, HomeActivity.this, user_id);
                                listView.setAdapter(adapter);
                                image_no_post.setVisibility(ImageView.GONE);
                                tv_no_post.setVisibility(TextView.GONE);
                                adapter.notifyDataSetChanged();
                            } else {
                                image_no_post.setVisibility(ImageView.VISIBLE);
                                tv_no_post.setVisibility(TextView.VISIBLE);
                            }
                        } catch (JSONException e) {
                            //e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        AppController.getmInstance().addToRequestQueue(stringRequest);
    }

    //side navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

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
                Intent a = new Intent(HomeActivity.this, ChangePasswordActivity.class);
                startActivity(a);
                break;

            case R.id.nav_adoption:
                Intent b = new Intent(HomeActivity.this, MyAdoptionActivity.class);
                startActivity(b);
                break;

            case R.id.nav_report1:
                Intent c = new Intent(HomeActivity.this, Report1Activity.class);
                startActivity(c);
                break;

            case R.id.nav_volunteer:
                Intent d = new Intent(HomeActivity.this, VolunteerActivity.class);
                startActivity(d);
                break;

            case R.id.nav_guidelines:
                Intent e = new Intent(HomeActivity.this, GuidelinesActivity.class);
                startActivity(e);
                break;

            case R.id.nav_my_reports:
                Intent f = new Intent(HomeActivity.this, MyReportActivity.class);
                startActivity(f);
                break;

            case R.id.nav_donation:
                Intent g = new Intent(HomeActivity.this, DonationActivity.class);
                startActivity(g);
                break;
        }
        return false;
    }

}
