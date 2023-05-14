/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_pret_ouvrage;
import java.util.*;
/**
 *
 * @author Thuy Dung
 */
public class Utilisateur extends Personne {

    private String login;
    private String motpasse;

    public Utilisateur(String n, String p, String log, String pw) {
        super(n,p);
        login = log;
        motpasse = pw;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String log) {
        login = log;
    }
    public String getMotpasse() {
        return motpasse;
    }

    public void setMotpasse(String pw) {
        motpasse = pw;
    }
    public String getInfos() { 
        return (super.getInfos() + " Login: " + getLogin() + " Mot de passe : " + getMotpasse());
    }
}    
    

