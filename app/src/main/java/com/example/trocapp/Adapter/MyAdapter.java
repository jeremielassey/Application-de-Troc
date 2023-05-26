package com.example.trocapp.Adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trocapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import Model.Produit;

public class MyAdapter  extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<Produit> dataList;
    public MyAdapter(Context context, List<Produit> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_choice_item, parent, false);
        return new MyViewHolder(view);
    }


    int selectedItemPosition;

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //Glide.with(context).load(dataList.get(position).getImage()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getNom_produit());
        holder.recDesc.setText(dataList.get(position).getDescription());
        Picasso.get().load(dataList.get(position).getImage()).into(holder.recImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItemPosition = position;
                notifyDataSetChanged();
            }

        });

        if(selectedItemPosition == position)
            holder.recCard.setBackgroundColor(Color.parseColor("#DC746C"));
            //String produitId = dataList.get(position).


    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void searchDataList(ArrayList<Produit> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}


class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView recImage;
    TextView recTitle, recDesc, recLang;
    CardView recCard;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recDesc = itemView.findViewById(R.id.recDesc);
        //recLang = itemView.findViewById(R.id.recLang);
        recTitle = itemView.findViewById(R.id.recTitle);
    }
}
