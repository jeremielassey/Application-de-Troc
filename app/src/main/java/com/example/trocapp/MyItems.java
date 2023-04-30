package com.example.trocapp;

public class MyItems {
    private final String categorie, description, image, nom_produit, nom_troqueur;
    private final String date_Ajout;

    public MyItems(String categorie, String description, String image, String nom_produit,  String date_Ajout, String nom_troqueur) {
        this.categorie = categorie;
        this.description = description;
        this.image = image;
        this.nom_produit = nom_produit;
        this.nom_troqueur = nom_troqueur;
        this.date_Ajout = date_Ajout;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public String getDate_Ajout() {
        return date_Ajout;
    }

    public String getNom_troqueur() {
        return nom_troqueur;
    }
}
