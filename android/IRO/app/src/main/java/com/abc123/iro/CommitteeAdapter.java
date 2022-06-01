package com.abc123.iro;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.widget.ArrayAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.load.model.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommitteeAdapter extends ArrayAdapter<Committee> {
    private LayoutInflater inflater;
    private List<Committee> committeeList;
    private List<Committee> arrayList;
    private Context mCtx;
    private ImageView next;
    public static final String COMMITTEE_ID = "com.abc123.iro.extra.COMMITTEE_ID";

    public CommitteeAdapter(List<Committee> committeeList, Context mctx) {
        super(mctx, R.layout.custom_layout4, committeeList);
        this.mCtx = mctx;
        this.committeeList = committeeList;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(committeeList);
    }

    @Override
    public int getCount() {
        return committeeList.size();
    }

    @Nullable
    @Override
    public Committee getItem(int position) {
        return committeeList.get(position);
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
            convertView = inflater.inflate(R.layout.custom_layout4, null, true);
        }

        //creating a view with our xml layout
        //final View listViewItem = inflater.inflate(R.layout.custom_layout, null, true);
        TextView tv_committee = convertView.findViewById(R.id.committee);
        next = convertView.findViewById(R.id.icon_next);

        final Committee committees = committeeList.get(position);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String committee_id = String.valueOf(committees.getId());
                Intent intent = new Intent(getContext(), CommitteeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(COMMITTEE_ID, committee_id);
                mCtx.startActivity(intent);
            }
        });

        tv_committee.setText(committees.getCommittee());


        return convertView;
    }
}
