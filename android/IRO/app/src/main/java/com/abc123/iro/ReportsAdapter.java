package com.abc123.iro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class ReportsAdapter extends ArrayAdapter<Reports> {
    private LayoutInflater inflater;
    private List<Reports> reportsList;
    private List<Reports> arrayList;
    private Context mCtx;
    private ImageLoader imageLoader;
    private String userid;

    public ReportsAdapter(List<Reports> reportsList, Context mctx, String userid) {
        super(mctx, R.layout.custom_layout7, reportsList);
        this.mCtx = mctx;
        this.reportsList = reportsList;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(reportsList);
        this.userid = userid;
    }

    @Override
    public int getCount() {
        return reportsList.size();
    }

    @Nullable
    @Override
    public Reports getItem(int position) {
        return reportsList.get(position);
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
            convertView = inflater.inflate(R.layout.custom_layout7, null, true);
        }

        //creating a view with our xml layout
        //final View listViewItem = inflater.inflate(R.layout.custom_layout, null, true);
        imageLoader = AppController.getmInstance().getmImageLoader();
        NetworkImageView imageView = convertView.findViewById(R.id.image_view);
        NetworkImageView imageView2 = convertView.findViewById(R.id.image_view2);
        TextView tv_name = convertView.findViewById(R.id.name);
        TextView tv_date = convertView.findViewById(R.id.date);
        TextView tv_location = convertView.findViewById(R.id.location);
        TextView tv_title = convertView.findViewById(R.id.title);
        TextView tv_desc = convertView.findViewById(R.id.desc);
        ImageView icon_call = convertView.findViewById(R.id.icon_call);

        final Reports reports = reportsList.get(position);

        if(!String.valueOf(reports.getId4()).equals(userid)) {
            icon_call.setVisibility(ImageView.VISIBLE);
        } else {
            icon_call.setVisibility(ImageView.GONE);
        }

        icon_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = reports.getContact();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(intent);
            }
        });

        imageView.setImageUrl(reports.getUserImage(), imageLoader);
        imageView2.setImageUrl(reports.getReportImage(), imageLoader);
        tv_name.setText(reports.getName());
        tv_date.setText(reports.getDate());
        tv_location.setText(reports.getLocation());
        tv_title.setText(reports.getTitle());
        tv_desc.setText(reports.getDesc());


        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        reportsList.clear();
        if (charText.length() == 0) {
            reportsList.addAll(arrayList);
        } else {
            for (Reports reports : arrayList) {
                if (reports.getName().toLowerCase(Locale.getDefault())
                        .contains(charText) || reports.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
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
                    reportsList.add(reports);
                }
            }
        }
        notifyDataSetChanged();
    }
}
