package com.abc123.iro;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.MenuItem;
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

import java.util.HashMap;
import java.util.Map;

public class GuidelineDetailsActivity extends AppCompatActivity {
    private String user_id;
    private String guideline_id;
    private String text;
    private TextView tv_text;
    private TextView tv_guideline;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideline_details);

        //custom toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setting the textview
        tv_text = findViewById(R.id.text);
        tv_guideline = findViewById(R.id.guideline);

        //for the session values
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.USER_ID);

        //intent values
        Intent intent = getIntent();
        guideline_id = intent.getStringExtra(GuidelineAdapter.GUIDELINE_ID);

        loadDetails(guideline_id);
    }

    public void loadDetails(final String id) {
        String url = "http://192.168.137.1:8080/IRO/Android/guideline_details.php";
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
                                    tv_guideline.setText(object.getString("guideline"));
                                    //tv_text.setText(object.getString("details"));
                                    text = object.getString("details");
                                    if(object.getInt("guideline_id")==1) {
                                        //SpannableString ss = new SpannableString(text);
                                        //StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                                        //ss.setSpan(boldSpan, 816, 831, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        SpannableStringBuilder builder = new SpannableStringBuilder();
                                        SpannableString spanString = new SpannableString(text);
                                        spanString.setSpan(new StyleSpan(Typeface.BOLD), 816, 835, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        spanString.setSpan(new StyleSpan(Typeface.BOLD), 917, 967, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        spanString.setSpan(new StyleSpan(Typeface.BOLD), 1457, 1473, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        spanString.setSpan(new StyleSpan(Typeface.BOLD), 1481, 1530, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        builder.append(spanString);
                                        tv_text.setText(builder);
                                    } else {
                                        tv_text.setText(text);
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
