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

public class PetAdapter extends ArrayAdapter<Pets> {
    private LayoutInflater inflater;
    private List<Pets> petsList;
    private List<Pets> arrayList;
    private Context mCtx;
    private ImageLoader imageLoader;
    private ImageView next;
    private CardView cardView;
    public static final String PET_ID = "com.abc123.iro.extra.PET_ID";

    public PetAdapter(List<Pets> petsList, Context mctx) {
        super(mctx, R.layout.custom_layout2, petsList);
        this.mCtx = mctx;
        this.petsList = petsList;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(petsList);
    }

    @Override
    public int getCount() {
        return petsList.size();
    }

    @Nullable
    @Override
    public Pets getItem(int position) {
        return petsList.get(position);
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
            convertView = inflater.inflate(R.layout.custom_layout2, null, true);
        }

        //creating a view with our xml layout
        //final View listViewItem = inflater.inflate(R.layout.custom_layout, null, true);
        imageLoader = AppController.getmInstance().getmImageLoader();
        NetworkImageView imageView = convertView.findViewById(R.id.image_view);
        TextView tv_name = convertView.findViewById(R.id.name);
        TextView tv_breed = convertView.findViewById(R.id.breed);
        TextView tv_age = convertView.findViewById(R.id.age);
        TextView tv_date = convertView.findViewById(R.id.date);
        next = convertView.findViewById(R.id.icon_next);
        cardView = convertView.findViewById(R.id.image_adopted_cover);

        final Pets pets = petsList.get(position);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pet_id = String.valueOf(pets.getId());
                Intent intent = new Intent(getContext(), AdoptionDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(PET_ID, pet_id);
                mCtx.startActivity(intent);
            }
        });
        if (pets.getStatus_id() == 2) {
            cardView.setVisibility(View.VISIBLE);
            //next.setEnabled(false);
            next.setClickable(false);
        } else {
            cardView.setVisibility(View.GONE);
            //next.setEnabled(false);
            next.setClickable(true);
        }
        imageView.setImageUrl(pets.getImage(), imageLoader);
        tv_name.setText(pets.getName());
        tv_breed.setText(pets.getBreed());
        tv_age.setText(pets.getAge());
        tv_date.setText(pets.getDate());


        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        petsList.clear();
        if (charText.length() == 0) {
            petsList.addAll(arrayList);
        } else {
            for (Pets pets : arrayList) {
                if (pets.getName().toLowerCase(Locale.getDefault())
                        .contains(charText) || pets.getBreed().toLowerCase(Locale.getDefault()).contains(charText)) {
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
                        petsList.add(pets);
                }
            }
        }
        notifyDataSetChanged();
    }
}
