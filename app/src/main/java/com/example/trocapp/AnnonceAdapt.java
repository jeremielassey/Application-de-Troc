package com.example.trocapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Model.UserData;

public class AnnonceAdapt extends RecyclerView.Adapter<AnnonceAdapt.MyViewHolder> {

    private final List<MyItems> items;
    private final Context context;

    public AnnonceAdapt(List<MyItems> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ann_adapter_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyItems myitems = items.get(position);
        holder.nom_produit.setText(myitems.getNom_produit());
        holder.date_publication.setText(myitems.getDate_Ajout());
        Picasso.get().load(myitems.getImage()).into(holder.image);
        holder.user.setText(myitems.getNom_troqueur());
       // holder.hiddentext.setText(myitems.getHiddenText());

        // action sur le bouton troquer
        holder.troquer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Activity_view_all.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private final ImageView image;
        private final TextView nom_produit;
        private final TextView date_publication;
        private final TextView user;
       // private final TextView hiddentext;

        Button troquer;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            image =itemView.findViewById(R.id.image);
            nom_produit = itemView.findViewById(R.id.nom_produit);
            date_publication = itemView.findViewById(R.id.date);
            user=itemView.findViewById(R.id.user);
            troquer = itemView.findViewById(R.id.button_troquer);
            //hiddentext = itemView.findViewById(R.id.hiddenTextView);

        }
    }
}
