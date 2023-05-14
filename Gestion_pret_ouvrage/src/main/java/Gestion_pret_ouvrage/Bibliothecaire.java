/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_pret_ouvrage;

/**
 *
 * @author Thuy Dung
 */
public class Bibliothecaire extends Utilisateur {

    private int bibli;
    private String specialite;
    

    public Bibliothecaire(String n, String p, String log, String pw, int bb, String sp) {
        super(n,p,log,pw);
        bibli = bb;
        specialite = sp;
    }
    public int getBibli() {
        return bibli;
    }

    public void setBibli(int bb) {
        bibli = bb;
    }
    
    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String sp) {
        specialite = sp;
    }
    
    
    public String getInfos() { 
        return (super.getInfos() + " Numéro de bibliothécaire: " + getBibli() + " Spécialité : " + getSpecialite());
    }
}    
    

