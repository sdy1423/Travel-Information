package com.example.travelinfro;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TravelRecyclerAdapter extends RecyclerView.Adapter<TravelRecyclerAdapter.TravelViewHolder> {

    private ArrayList<TourDataItem> tourDataItems;
    private LayoutInflater mInflate;
    private Context mContext;

    public TravelRecyclerAdapter(ArrayList<TourDataItem> tourDataItems, Context context) {
        this.tourDataItems = tourDataItems;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public TravelRecyclerAdapter.TravelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.travel_recycler_item,parent,false);
        TravelViewHolder viewHolder = new TravelViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TravelRecyclerAdapter.TravelViewHolder holder, final int position) {
        holder.title.setText(tourDataItems.get(position).getTitle());
        holder.addr.setText(tourDataItems.get(position).getAddr1());
        String imageURL = tourDataItems.get(position).getFirstimage();
        Glide.with(mContext).load(imageURL).into(holder.picture);
        holder.btnOutline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,OutlineActivity.class);
                intent.putExtra("contenttypeid",tourDataItems.get(position).getContenttypeid());
                intent.putExtra("contentid",tourDataItems.get(position).getContentid());
                mContext.startActivity(intent);
            }
        });
        holder.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Send mapX",""+tourDataItems.get(position).getMapx());
                Log.e("Send mapY",""+tourDataItems.get(position).getMapy());
                Log.e("Send title",""+tourDataItems.get(position).getTitle());

                Intent intent = new Intent(mContext,MapActivity.class);
                intent.putExtra("mapx",tourDataItems.get(position).getMapx());
                intent.putExtra("mapy",tourDataItems.get(position).getMapy());
                intent.putExtra("title",tourDataItems.get(position).getTitle());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tourDataItems.size();
    }

    public static class TravelViewHolder extends RecyclerView.ViewHolder{
        public ImageView picture;
        public TextView title,addr;
        public Button btnOutline,btnMap;

        public TravelViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.travel_rc_item_pic);
            title = itemView.findViewById(R.id.travel_rc_item_title);
            addr = itemView.findViewById(R.id.travel_rc_item_add);
            btnOutline = itemView.findViewById(R.id.travel_rc_item_outline);
            btnMap = itemView.findViewById(R.id.travel_rc_item_map);
        }
    }
}
