package com.example.trocapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.FileDescriptor;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import Model.Produit;

public class AjoutProduit extends AppCompatActivity {

    Button add;
    //EditText nom_produit;
    TextInputEditText description_produit;
    TextInputEditText nom_produit;
    ChipGroup cat_choice;
    List <String> choix_cat= new ArrayList<String>();
    ImageView image_produit;


    DateTimeFormatter currentTime;
    String date_UP,image;
    Button camera ,gallerie;
    Uri image_uri;

    Produit produit;






    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_produit);

        // recuperation des reference sur les element de la vu


        add = findViewById(R.id.btn_add_product);
        nom_produit = findViewById(R.id.nom_produit);
        description_produit = findViewById(R.id.description_produit);
        cat_choice =findViewById(R.id.chip);
        image_produit = findViewById(R.id.ib_load_photo);
        camera = findViewById(R.id.camera);
        gallerie = findViewById(R.id.gallery);

/*
        //adapter pour inflate les categories dans le fichier xml situe dans les ressources
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_produits, android.R.layout.simple_spinner_item);

        //choix de la maniere dont la liste sera afficher
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
*/

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargementImage();
                // recuperation des valeurs

                String nom_pro = nom_produit.getText().toString();
                String description = description_produit.getText().toString();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    currentTime  = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDateTime date = LocalDateTime.now();
                    date_UP = currentTime.format(date);
                }
                String image_ = "";

                produit = new Produit();
                produit.setNom_produit(nom_pro);
                produit.setDescription(description);
                produit.setDate_Ajout(date_UP);
                produit.setCategorie(choix_cat.toString());



                if(nom_pro.isEmpty() || description.isEmpty()){
                    Toast.makeText(AjoutProduit.this, "veuillez saisir toutes les champs", Toast.LENGTH_SHORT).show();
                }else{

                    //FirebaseDatabase.getInstance().getReference().child("Produits").push().setValue(produit);
                    Toast.makeText(AjoutProduit.this, "produit ajouter", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cat_choice.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {

                //Chip chip = (Chip)group.getChildAt(i);
                for(int i=0;i<group.getChildCount();i++){
                    Chip chip = (Chip)group.getChildAt(i);
                    if(chip.isChecked()){
                        choix_cat.add(chip.getText().toString());
                    }

                }
            }
        });



        // gestion du choix de l'image a partir de la gallerie

        ActivityResultLauncher<Intent> gallerieActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            image_uri = result.getData().getData();
                            image_produit.setImageURI(image_uri);
                        }

                    }
                });

        gallerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photo = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                gallerieActivityResultLauncher.launch(photo);

            }
        });


    }

    private  void updateProduit(String url){
        produit.setImage(url);

        FirebaseDatabase.getInstance().getReference("Produits/").push().setValue(produit);

    }
        private  void chargementImage(){
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Chargement...");
            dialog.show();

            FirebaseStorage.getInstance().getReference("image/"+ UUID.randomUUID().toString()).putFile(image_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.isSuccessful()){
                                    //image = task.getResult().toString();
                                    updateProduit(task.getResult().toString());
                                }
                            }
                        });
                        Toast.makeText(AjoutProduit.this, "image telechargee", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AjoutProduit.this, "Impossible de charger l'image", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();

                }
            }) ;
        }


}
