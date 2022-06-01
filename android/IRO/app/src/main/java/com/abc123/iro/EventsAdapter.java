package com.abc123.iro;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EventsAdapter extends ArrayAdapter<Events> {

    private LayoutInflater inflater;
    private List<Events> eventList;
    private List<Events> arrayList;
    private Context mCtx;
    private ImageLoader imageLoader;

    public EventsAdapter(List<Events> eventList, Context mctx) {
        super(mctx, R.layout.custom_layout8, eventList);
        this.mCtx = mctx;
        this.eventList = eventList;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(eventList);
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Nullable
    @Override
    public Events getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        inflater = LayoutInflater.from(mCtx);

        if (inflater == null) {
            inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_layout8, null, true);
        }

        //creating a view with our xml layout
        //final View listViewItem = inflater.inflate(R.layout.custom_layout, null, true);
        imageLoader = AppController.getmInstance().getmImageLoader();
        NetworkImageView imageView = convertView.findViewById(R.id.image_view);
        NetworkImageView imageView2 = convertView.findViewById(R.id.image_view2);
        ImageView image_role = convertView.findViewById(R.id.image_role);
        TextView tv_name = convertView.findViewById(R.id.name);
        TextView tv_role = convertView.findViewById(R.id.role);
        TextView tv_date = convertView.findViewById(R.id.date);
        TextView tv_title = convertView.findViewById(R.id.title);
        TextView tv_desc = convertView.findViewById(R.id.desc);

        final Events events = eventList.get(position);


        imageView.setImageUrl(events.getUserImage(), imageLoader);
        imageView2.setImageUrl(events.getEventImage(), imageLoader);
        tv_name.setText(events.getName());
        tv_role.setText(events.getRole());
        tv_date.setText(events.getDate());
        tv_title.setText(events.getTitle());
        tv_desc.setText(events.getDesc());


        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        eventList.clear();
        if (charText.length() == 0) {
            eventList.addAll(arrayList);
        } else {
            for (Events events : arrayList) {
                if (events.getName().toLowerCase(Locale.getDefault())
                        .contains(charText) || events.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                        /*
                        if(pets.getStatus_id()==2) {
                            cardView.setVisibility(View.VISIBLE);
                            //next.setEnabled(false);
                            next.setClickable(false);
                        } else {
                            cardView.setVisibility(View.GONE);
                            //next.setEnabled(false);
                            next.setClickable(true);
                        }
                        */
                    eventList.add(events);
                }
            }
        }
        notifyDataSetChanged();
    }
}
