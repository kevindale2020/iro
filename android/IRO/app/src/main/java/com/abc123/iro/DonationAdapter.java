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

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DonationAdapter extends ArrayAdapter<Donations> {
    private LayoutInflater inflater;
    private List<Donations> donationsList;
    private Context mCtx;
    private ImageLoader imageLoader;
    private ImageView next;
    public static final String DONATION_ID = "com.abc123.iro.extra.DONATION_ID";

    public DonationAdapter(List<Donations> donationsList, Context mCtx) {
        super(mCtx, R.layout.custom_layout11, donationsList);
        this.mCtx = mCtx;
        this.donationsList = donationsList;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        inflater = LayoutInflater.from(mCtx);

        if (inflater == null) {
            inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_layout11, null, true);
        }

        //creating a view with our xml layout
        //final View listViewItem = inflater.inflate(R.layout.custom_layout, null, true);
        imageLoader = AppController.getmInstance().getmImageLoader();
        NetworkImageView imageView = convertView.findViewById(R.id.image_view);
        TextView tv_desc = convertView.findViewById(R.id.desc);
        TextView tv_amount = convertView.findViewById(R.id.amount);
        TextView tv_date = convertView.findViewById(R.id.date);
        TextView tv_status = convertView.findViewById(R.id.status);
        next = convertView.findViewById(R.id.icon_next);

        final Donations donations = donationsList.get(position);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String donation_id = String.valueOf(donations.getId());
                Intent intent = new Intent(getContext(), MyDonationFormActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(DONATION_ID, donation_id);
                mCtx.startActivity(intent);
            }
        });


        imageView.setImageUrl(donations.getImage(), imageLoader);
        tv_desc.setText(donations.getDesc());
        tv_amount.setText("₱"+String.valueOf(String.format("%.2f",donations.getAmount())));
        tv_date.setText(donations.getDate());
        if (donations.getStatus().equals("1")) {
            tv_status.setText("Verified");
        } else {
            tv_status.setText("Not Verified");
        }

        return convertView;
    }
}
