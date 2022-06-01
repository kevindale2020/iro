package com.abc123.iro;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GuidelineAdapter extends ArrayAdapter<Guideline> {
    private LayoutInflater inflater;
    private List<Guideline> guidelineList;
    private List<Guideline> arrayList;
    private Context mCtx;
    private ImageView next;
    public static final String GUIDELINE_ID = "com.abc123.iro.extra.COMMITTEE_ID";

    public GuidelineAdapter(List<Guideline> guidelineList, Context mctx) {
        super(mctx, R.layout.custom_layout6, guidelineList);
        this.mCtx = mctx;
        this.guidelineList = guidelineList;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(guidelineList);
    }

    @Override
    public int getCount() {
        return guidelineList.size();
    }

    @Nullable
    @Override
    public Guideline getItem(int position) {
        return guidelineList.get(position);
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
            convertView = inflater.inflate(R.layout.custom_layout6, null, true);
        }

        //creating a view with our xml layout
        //final View listViewItem = inflater.inflate(R.layout.custom_layout, null, true);
        TextView tv_guideline = convertView.findViewById(R.id.guideline);
        next = convertView.findViewById(R.id.icon_next);

        final Guideline guidelines = guidelineList.get(position);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guideline_id = String.valueOf(guidelines.getId());
                Intent intent = new Intent(getContext(), GuidelineDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(GUIDELINE_ID, guideline_id);
                mCtx.startActivity(intent);
            }
        });

        tv_guideline.setText(guidelines.getGuideline());


        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        guidelineList.clear();
        if (charText.length() == 0) {
            guidelineList.addAll(arrayList);
        } else {
            for (Guideline guidelines : arrayList) {
                if (guidelines.getGuideline().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
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
                    guidelineList.add(guidelines);
                }
            }
        }
        notifyDataSetChanged();
    }
}
