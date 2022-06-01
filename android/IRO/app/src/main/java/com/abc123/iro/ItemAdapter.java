package com.abc123.iro;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
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

public class ItemAdapter extends ArrayAdapter<Items> {
    private LayoutInflater inflater;
    private List<Items> itemsList;
    private List<Items> arrayList;
    private Context mCtx;
    private ImageLoader imageLoader;
    private ImageView next;
    public static final String ITEM_ID = "com.abc123.iro.extra.ITEM_ID";

    public ItemAdapter(List<Items> itemsList, Context mCtx) {
        super(mCtx, R.layout.custom_layout5, itemsList);
        this.mCtx = mCtx;
        this.itemsList = itemsList;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(itemsList);
    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Nullable
    @Override
    public Items getItem(int position) {
        return itemsList.get(position);
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
            convertView = inflater.inflate(R.layout.custom_layout5, null, true);
        }

        //creating a view with our xml layout
        //final View listViewItem = inflater.inflate(R.layout.custom_layout, null, true);
        imageLoader = AppController.getmInstance().getmImageLoader();
        NetworkImageView imageView = convertView.findViewById(R.id.image_view);
        TextView tv_name = convertView.findViewById(R.id.name);
        TextView tv_category = convertView.findViewById(R.id.category);
        TextView tv_qty = convertView.findViewById(R.id.qty);
        TextView tv_price = convertView.findViewById(R.id.price);
        next = convertView.findViewById(R.id.icon_next);

        final Items items = itemsList.get(position);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item_id = String.valueOf(items.getId());
                Intent intent = new Intent(getContext(), ItemDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(ITEM_ID, item_id);
                mCtx.startActivity(intent);
            }
        });

        imageView.setImageUrl(items.getImage(), imageLoader);
        tv_name.setText(items.getName());
        tv_category.setText(items.getCategory());
        tv_qty.setText("Qty: "+items.getQty());
        tv_price.setText("₱"+String.valueOf(String.format("%.2f", items.getPrice())));


        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        itemsList.clear();
        if (charText.length() == 0) {
            itemsList.addAll(arrayList);
        } else {
            for (Items items : arrayList) {
                if (items.getName().toLowerCase(Locale.getDefault())
                        .contains(charText) || items.getCategory().toLowerCase(Locale.getDefault()).contains(charText)) {
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
                    itemsList.add(items);
                }
            }
        }
        notifyDataSetChanged();
    }
}
