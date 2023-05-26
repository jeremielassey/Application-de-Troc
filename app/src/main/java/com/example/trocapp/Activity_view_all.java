
package com.example.trocapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.*;

import com.example.trocapp.Adapter.MyAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Model.Produit;
import Model.TrocProduit;

public class Activity_view_all extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    List<Produit> productList;
    MyAdapter adapter;
    ValueEventListener eventListener;
    FloatingActionButton choisir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        choisir = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Activity_view_all.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_view_all.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        productList = new ArrayList<>();
        //adapter = new MyAdapter(Activity_view_all.this, productList);
        //recyclerView.setAdapter(adapter);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot itemSnapshot : snapshot.child("Produits").getChildren()) {
                    if (itemSnapshot.getKey().equals(userId)) {
                        for(DataSnapshot elmnts: itemSnapshot.getChildren()){
                            Produit produit = new Produit();
                            produit.setImage(elmnts.child("image").getValue(String.class));
                            produit.setNom_produit(elmnts.child("nom_produit").getValue(String.class));
                            produit.setDescription(elmnts.child("description").getValue(String.class));
                            System.out.println(produit.getNom_produit());
                            productList.add(produit);}
                    }
                }
                recyclerView.setAdapter(new MyAdapter(Activity_view_all.this,productList));
                //adapter.notifyDataSetChanged();
                dialog.dismiss();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MyFragment", "Error getting data", error .toException());
            }
        });

        // handler du choix
      choisir.setOnClickListener(new View.OnClickListener() {
           @Override
               public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Activity_view_all.this);

                    alertDialog.setTitle("are sure");
                    alertDialog.setMessage("Confirm your choice")
                            .setCancelable(false)
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(Activity_view_all.this, "Request send", Toast.LENGTH_SHORT).show();

                                   // TrocProduit TrocData = new TrocProduit();
                                  //  TrocData.user1= userId;
                                    //TrocData.produitId1 =
                                }
                            });
               alertDialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(Activity_view_all.this, "Cancelling", Toast.LENGTH_SHORT).show();

                                }
                            });

               AlertDialog dialog = alertDialog.create();
               dialog.show();
               }
       });
    }
}