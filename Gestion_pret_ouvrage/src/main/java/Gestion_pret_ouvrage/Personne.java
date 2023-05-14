package Gestion_pret_ouvrage;




public class Personne {

    private String nom;
    private String prenom;
   

    public Personne(String n, String p) {
        nom = n;
        prenom = p;
        
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String n) {
        nom = n;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String n) {
        prenom = n;
    }
public String getInfos() { 
        return ("========abonne========\nNom: " + getNom() + "\nPrenom :" + getPrenom());
    }
}