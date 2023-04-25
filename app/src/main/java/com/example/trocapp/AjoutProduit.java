package com.example.trocapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AjoutProduit extends AppCompatActivity {

    Button add;
    EditText nom_produit;
    EditText description_produit;
    Spinner spinner;

    Date currentTime;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_produit);

        add = findViewById(R.id.btn_add_product);
        nom_produit = findViewById(R.id.product_title);
        description_produit = findViewById(R.id.product_description);
        spinner = findViewById(R.id.spinner_category); //liste dropdow des categories

        //adapter pour inflate les categories dans le fichier xml situe dans les ressources
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_produits, android.R.layout.simple_spinner_item);

        //choix de la maniere dont la liste sera afficher
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // recuperation des valeurs
                String nom_pro = nom_produit.getText().toString();
                String description = description_produit.getText().toString();
                String categorie = spinner.getSelectedItem().toString();
                currentTime = Calendar.getInstance().getTime();

                // stockage dans le hash map
                HashMap<String,Object> produits = new HashMap<>();
                produits.put("nom_produit",nom_pro);
                produits.put("description_produit",description);
                produits.put("categorie",categorie);
                produits.put("Date",currentTime.getTime());


                if(nom_pro.isEmpty() || description.isEmpty()){
                    Toast.makeText(AjoutProduit.this, "veuillez saisir toutes les champs", Toast.LENGTH_SHORT).show();
                }else{

                    FirebaseDatabase.getInstance().getReference().child("Produits").push().child("produit").updateChildren(produits);
                    Toast.makeText(AjoutProduit.this, "product ajouter", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}