/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_pret_ouvrage;
import java.lang.reflect.Array;
import java.util.*;
/**
 *
 * @author Thuy Dung
 */
public class Auteur extends Personne {

    private int num_auteur;
    private String nationalite;
    private ArrayList<Ouvrage> listeOuvrage = new ArrayList<Ouvrage>();

    public Auteur(String n, String p, int num_au, String nat) {
        super(n,p);
        num_auteur = num_au;
        nationalite = nat;
    }
    public int getNumauteur() {
        return num_auteur;
    }

    public void setNumauteur(int num_au) {
        num_auteur = num_au;
    }
    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nat) {
        nationalite = nat;
    }
    public String getInfos() { 
        return (super.getInfos() + "\nNuméro d'auteur: " + getNumauteur() + "\nNationalité d'auteur : " + getNationalite());
    }

    public void addOuvrage(Ouvrage ov) {
        listeOuvrage.add(ov);
    }

    public ArrayList<Ouvrage> getOuvrages() {
        return listeOuvrage;
    }
}
    

