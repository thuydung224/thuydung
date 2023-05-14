/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_pret_ouvrage;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Thuy Dung
 */
public class Abonne extends Utilisateur {

    private int abonne;
    private String adresse;
    private Date date_abonnement;
    private Date suspendu;
    private ArrayList<Pret> listePrets;

    public Abonne(String n, String p, String log, String pw, int ab, String ad, Date da, Date s) {
        super(n,p,log,pw);
        abonne = ab;
        adresse = ad;
        date_abonnement = da;
        suspendu = s;
        listePrets = new ArrayList<Pret>();
    }
    public int getAbonne() {
        return abonne;
    }

    public void setAbonne(int ab) {
        abonne = ab;
    }
    
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String ad) {
        adresse = ad;
    }
    
    public Date getDateAbonne() {
        return date_abonnement;
    }
    public void setDateAbonne(Date da) {
        date_abonnement = da;}
    
    public Date getSuspendu() {
        return suspendu;
    }
    public void setSuspendu(Date s) {
        suspendu = s;}
    
    public String getInfos() { 
        return (super.getInfos() + " Numéro d'abonné: " + getAbonne() + " Adresse : " + getAdresse() + " Date abonnement : " + getDateAbonne() + " Suspendu jusqu'au : " + getSuspendu());
    }

    public void ajouterPret(Pret pret) {
        listePrets.add(pret);
    }

    public void retirerPret(Pret pret) {
        listePrets.remove(pret);
    }

    public ArrayList<Pret> getPret() {
        return listePrets;
    }
}
    
