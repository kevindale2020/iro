package com.abc123.iro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Frag5 extends Fragment {
    private String imageURL;
    private String user_id;
    private ImageView imageView;
    private TextView tv_no_post;
    private List<Donations> donationsList;
    private ListView listView;
    private DonationAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    SessionManager sessionManager;
    ImageLoader imageLoader = AppController.getmInstance().getmImageLoader();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag5_layout, container, false);

        //for the session values
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = user.get(sessionManager.USER_ID);

        //listview and arraylist and refreshlayout
        donationsList = new ArrayList<Donations>();
        listView = view.findViewById(R.id.list);
        adapter = new DonationAdapter(donationsList, getContext());
        refreshLayout = view.findViewById(R.id.swipe_refresh_layout);

        //no post state
        imageView = view.findViewById(R.id.image_no_post);
        tv_no_post = view.findViewById(R.id.no_post);

        //loadMyDonations
        loadMyDonations(user_id);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                donationsList.clear();
                loadMyDonations(user_id);
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public void loadMyDonations(final String id) {
        String url = "http://192.168.137.1:8080/IRO/Android/my_donations.php";
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
                                    StringBuilder stringBuilder = new StringBuilder("http://192.168.137.1:8080/IRO/Android/images/payment methods/");
                                    //StringBuilder stringBuilder = new StringBuilder("http://192.168.43.44:8080/IRO/Android/images/adoptions/");
                                    if(object.getInt("donation_type_id")==2) {
                                        stringBuilder.append("paypal.jpg");
                                    } else if(object.getInt("donation_type_id")==3) {
                                        stringBuilder.append("china bank.jpg");
                                    } else if(object.getInt("donation_type_id")==4) {
                                        stringBuilder.append("western union.jpg");
                                    } else if(object.getInt("donation_type_id")==5) {
                                        stringBuilder.append("LBC.jpg");
                                    } else if(object.getInt("donation_type_id")==6) {
                                        stringBuilder.append("palawan.jpg");
                                    } else  if(object.getInt("donation_type_id")==7) {
                                        stringBuilder.append("cebuana.jpg");
                                    } else {
                                        stringBuilder.append("mlhuillier.jpg");
                                    }
                                    imageURL = stringBuilder.toString();
                                    Donations donations = new Donations(object.getInt("donation_id"), imageURL, object.getString("donation_type_desc"), object.getDouble("amount"), object.getString("date"), object.getString("is_verified"));
                                    donationsList.add(donations);
                                }
                                adapter = new DonationAdapter(donationsList, getContext());
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                imageView.setVisibility(ImageView.VISIBLE);
                                tv_no_post.setVisibility(TextView.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getContext(), "Failed" + error.toString(), Toast.LENGTH_SHORT).show();
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
