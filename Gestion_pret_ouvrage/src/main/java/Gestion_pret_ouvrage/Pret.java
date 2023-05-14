/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_pret_ouvrage;

import java.util.Date;

/**
 * @author Thuy Dung
 */
public class Pret {
    private int pret;
    private Date date_emprunt;
    private Date date_retour;
    private Ouvrage ouv;
    private Abonne abo;
    private Bibliothecaire bt;

    private boolean rendu = false;

    public Pret(int pr, Date de, Date dr, Ouvrage _ouv, Abonne _abon, Bibliothecaire _bt) {
        pret = pr;
        date_emprunt = de;
        date_retour = dr;
        ouv = _ouv;
        abo = _abon;
        bt = _bt;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pr) {
        pret = pr;
    }

    public Date getDateEmprunt() {
        return date_emprunt;
    }

    public void setDateEmprunt(Date de) {
        this.date_emprunt = de;
    }

    public Date getDateRetour() {
        return date_retour;
    }

    public void setDateRetour(Date dr) {
        this.date_retour = dr;
    }

    public Ouvrage getOuvrage() {
        return ouv;
    }

    public Abonne getAbonne() {
        return abo;
    }

    public void setRendu() {
        rendu = true;
    }

    public boolean estRendu() {
        return rendu;
    }

    public void getInfos() {
        System.out.println("Id : " + pret);
        System.out.println("Date d'emprunt : " + date_emprunt);
        ouv.getInfos();
        abo.getInfos();
    }
}
