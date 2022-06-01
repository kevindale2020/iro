package com.abc123.iro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class PlaceAdapter extends ArrayAdapter<Places> {
    private LayoutInflater inflater;
    private List<Places> placesList;
    private Context mCtx;
    private double lat;
    private double lng;
    //public Button btnBack;
    private GoogleMap mMap;
    private Polyline currentPolyline;
    private MarkerOptions place1, place2;
    private List<MarkerOptions> markerOptionsList;
    private LinearLayout placeLayout;
    //public static final String CURRENT_LATITUDE = "com.abc123.googlemappractice.extra.CURRENT_LATITUDE";
    //public static final String CURRENT_LONGITUDE = "com.abc123.googlemappractice.extra.CURRENT_LONGITUDE";
    public static final String DESTINATION_LATITUDE = "com.abc123.googlemappractice.extra.DESTINATION_LATITUDE";
    public static final String DESTINATION_LONGITUDE = "com.abc123.googlemappractice.extra.DESTINATION_LONGITUDE";
    public static final String DESTINATION_NAME = "com.abc123.googlemappractice.extra.DESTINATION_NAME";
    ImageLoader imageLoader;
    public PlaceAdapter(List<Places> placesList, Context mctx, double lat, double lng, GoogleMap mMap, LinearLayout placeLayout) {
        super(mctx, R.layout.custom_layout, placesList);
        this.mCtx = mctx;
        this.placesList = placesList;
        this.lat = lat;
        this.lng = lng;
        this.mMap = mMap;
        this.placeLayout = placeLayout;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        inflater = LayoutInflater.from(mCtx);
        if(inflater==null) {
            inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView==null){
            convertView = inflater.inflate(R.layout.custom_layout, null, true);
        }

        //creating a view with our xml layout
        //final View listViewItem = inflater.inflate(R.layout.custom_layout, null, true);
        imageLoader = AppController.getmInstance().getmImageLoader();
        NetworkImageView imageView = convertView.findViewById(R.id.image_view);
        TextView name = convertView.findViewById(R.id.name);
        final TextView place = convertView.findViewById(R.id.place);
        TextView contact = convertView.findViewById(R.id.contact);
        ImageView icon_phone = convertView.findViewById(R.id.icon_phone);
        ImageView icon_location = convertView.findViewById(R.id.icon_location);

        final Places places = placesList.get(position);

        icon_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = places.getContact();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(intent);
            }
        });

        icon_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize the origin and destination
                LatLng place1 = new LatLng(lat,lng);//your current location
                LatLng place2 = new LatLng(places.getLat(),places.getLng());//location of selected place

                /*
                new FetchURL(mCtx)
                        .execute(getUrl(place1,place2,"driving"),"driving");

                mMap.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(places.getName());
                markerOptions.position(place2);
                mMap.addMarker(markerOptions);

                LatLngBounds.Builder builder = new LatLngBounds.Builder(); //add animation
                builder.include(place1);
                builder.include(place2);
                LatLngBounds bounds = builder.build();
                int width = mCtx.getResources().getDisplayMetrics().widthPixels;
                int height = mCtx.getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.30);
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,width,height,padding);
                mMap.animateCamera(cu);
                //mMap.addPolyline(new PolylineOptions().add(place1,place2).width(10).color(Color.RED));

                btnBack.setVisibility(Button.VISIBLE);
                placeLayout.setVisibility(LinearLayout.GONE);
                */
                Intent intent = new Intent(getContext(), MapsActivity2.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(DESTINATION_LATITUDE, places.getLat());
                intent.putExtra(DESTINATION_LONGITUDE, places.getLng());
                intent.putExtra(DESTINATION_NAME, places.getName());
                getContext().startActivity(intent);



            }
        });

        imageView.setImageUrl(places.getImage(),imageLoader);
        name.setText(places.getName());
        place.setText(places.getPlace());
        contact.setText(places.getContact());


        return convertView;
    }
}