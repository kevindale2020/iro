package com.abc123.iro;

import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GetNearbyPlacesData extends AsyncTask<Object, String, String>{
    String googlePlaceData;
    GoogleMap googleMap;
    String url;
    double currentLatitude;
    double currentLongitude;

    @Override
    protected String doInBackground(Object... objects) {
        googleMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        currentLatitude = (Double) objects[2];
        currentLongitude = (Double) objects[3];


        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlaceData = downloadUrl.readURL(url);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlaceData;
    }

    @Override
    protected  void onPostExecute(String s) {
        try {
            JSONObject parentObject = new JSONObject(s);
            /*
            JSONArray resultArray = parentObject.getJSONArray("results");
            for(int i = 0; i<resultArray.length(); i++)
            {
                JSONObject jsonObject = resultArray.getJSONObject(i);
                JSONObject locationObj = jsonObject.getJSONObject("geometry").getJSONObject("location");

                String latitude = locationObj.getString("lat");
                String longitude = locationObj.getString("lng");

                JSONObject nameObj = resultArray.getJSONObject(i);

                String name = nameObj.getString("name");

                LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(name);
                markerOptions.position(latLng);

                googleMap.addMarker(markerOptions);
            }
            */
            JSONArray resultArray = parentObject.getJSONArray("data");
            for (int i = 0; i < resultArray.length(); i++) {
                //getting the json object of the particular index inside the array
                JSONObject jsonObject = resultArray.getJSONObject(i);
                String lat = jsonObject.getString("place_lat");
                String lng = jsonObject.getString("place_lng");
                float final_distance = distance(currentLatitude,currentLongitude,Double.parseDouble(lat),Double.parseDouble(lng));
                if(final_distance<1500){


                    LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.title(jsonObject.getString("place_name"));
                    markerOptions.position(latLng);

                    googleMap.addMarker(markerOptions);

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private float distance(double currentlatitude, double currentlongitude, double originLat, double originLon) {

        float[] results = new float[1];
        Location.distanceBetween(currentlatitude, currentlongitude, originLat, originLon, results);
        float distanceInMeters = results[0];

        return distanceInMeters;
    }
}