/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_pret_ouvrage;

import java.util.ArrayList;

/**
 * @author Thuy Dung
 */
public class Ouvrage {
    private int num_ouvrage;
    private String titre;
//    private int editeur;
    private String resume;
    private boolean disponible;
    private Pret pret = null;

    private String editeur;
    private int decompteDePret = 0;

    private ArrayList<Auteur> auteurList = new ArrayList<Auteur>();


    public Ouvrage(int num_ov, String t, String _editeur, String r, boolean d, ArrayList<Auteur> listeAuteur) {
        num_ouvrage = num_ov;
        titre = t;
        resume = r;
        disponible = d;
        editeur = _editeur;
        auteurList = listeAuteur;
    }

    public int getNumouvrage() {
        return num_ouvrage;
    }

    public void setNumouvrage(int num_ov) {
        this.num_ouvrage = num_ov;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String t) {
        this.titre = t;
    }

    public String getEditeur() {
        return editeur;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String r) {
        this.resume = r;
    }

    public boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(boolean d) {
        this.disponible = d;
    }

    public void getInfos() {
        System.out.println("------------");
        System.out.println("Id : " + num_ouvrage + "\n" +
                "Titre : " + titre + " " +
                "Resume: " + resume + " " +
                "Editeur: " + editeur);
    }

    public void setPret(Pret p) {
        pret = p;
    }

    public Pret getPret() {
        return pret;
    }

    public void incDecompte() {
        decompteDePret++;
    }

    public int getDecompte() {
        return decompteDePret;
    }

    public ArrayList<Auteur> getListeAuteur() {
        return auteurList;
    }
}
