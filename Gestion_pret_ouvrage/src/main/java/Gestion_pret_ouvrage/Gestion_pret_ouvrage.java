package Gestion_pret_ouvrage;

import java.util.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Thuy Dung
 */
public class Gestion_pret_ouvrage {

    private final ArrayList<Auteur> tabauteur;
    private final ArrayList<Ouvrage> tabouvrage;
    private final ArrayList<Pret> tabpret;
    private final ArrayList<Abonne> tababonne;
    private final ArrayList<Bibliothecaire> tabbiblio;

    public Gestion_pret_ouvrage() {
        tabauteur = new ArrayList<>();
        tabouvrage = new ArrayList<>();
        tabpret = new ArrayList<>();
        tababonne = new ArrayList<>();
        Bibliothecaire bibliothecaire1 = new Bibliothecaire("Dung", "Nguyen", "root", "root", 1000,
                "Senvoler");
        tabbiblio = new ArrayList<>();
        tabbiblio.add(bibliothecaire1);
    }

    Bibliothecaire bibliothecaire = null;
    Abonne abonne = null;

    public Auteur creerAuteur() {
        String n, pn, na;
        int num_auteur;
        Auteur a;

        System.out.println("==================");
        System.out.println("Donner le nom d'auteur :");
        n = Clavier.lireString();
        System.out.println("Donner le prénom d'auteur :");
        pn = Clavier.lireString();
        System.out.println("Donner le numéro de l'auteur :");
        num_auteur = Clavier.lireInt();
        System.out.println("Donner la nationalité de l'auteur :");
        na = Clavier.lireString();
        a = new Auteur(n, pn, num_auteur, na);

        tabauteur.add(a);

        return a;
    }

    public ArrayList<Auteur> rechercheAuteur(String nom) {
        ArrayList<Auteur> aList = new ArrayList<>();
        if (!tabauteur.isEmpty()) {
            for (Auteur a : tabauteur) {
                if (a.getNom().equalsIgnoreCase(nom)) {
                    aList.add(a);
                }
            }
        }
        return aList;
    }

    public void creerOuvrage() {
        String nom, t, r;
        boolean dispo;
        int num_ov, aId;
        Ouvrage ov;
        String editeur;
        ArrayList<Auteur> listeAuteur = new ArrayList<>();
        ArrayList<Auteur> aList;
        Auteur auteur = null;

        boolean correctAuteurChoisi = false;

        // Add auteur
        System.out.println("Nom d'auteur à rechercher");
        nom = Clavier.lireString();
        aList = rechercheAuteur(nom);
        if (aList.size() != 0) {
            System.out.println("Quel auteur voulez-vous ?");
            for (Auteur a : aList) {
                System.out.println(a.getInfos());
            }
            aId = Clavier.lireInt();
            for (Auteur a : aList) {
                if (a.getNumauteur() == aId) {
                    correctAuteurChoisi = true;
                    listeAuteur.add(a);
                    auteur = a;
                    break;
                }
            }

            if (!correctAuteurChoisi) {
                System.out.println("Auteur n'existe pas ! Il faut le créer !");
                Auteur a = creerAuteur();
                listeAuteur.add(a);
                auteur = a;
            }


        } else {
            System.out.println("Auteur n'existe pas ! Il faut le créer !");
            Auteur a = creerAuteur();
            listeAuteur.add(a);
            auteur = a;
        }
        System.out.println("Nom d'éditeur : ");
        editeur = Clavier.lireString();

        System.out.println("Donner le numéro d'ouvrage ");
        num_ov = Clavier.lireInt();
        System.out.println("Donner le titre d'ouvrage ");
        t = Clavier.lireString();
        System.out.println("Donner la resumé ");
        r = Clavier.lireString();
        System.out.println("Donner la disponibilité: True ou False? ");
        dispo = Clavier.lireBoolean();
        ov = new Ouvrage(num_ov, t, editeur, r, dispo, listeAuteur);
        tabouvrage.add(ov);
        auteur.addOuvrage(ov);
    }

    public void creerAbonne() {
        String n, p, login, motpasse, adresse;
        int num_abonne;
        Date date_abonnement, suspendu;
        Abonne ab;

        System.out.println("==================");
        System.out.println("Donner le nom d'abonne :");
        n = Clavier.lireString();
        System.out.println("Donner le prénom d'abonne :");
        p = Clavier.lireString();

        System.out.println("Login :");
        login = Clavier.lireString();
        System.out.println("Donner mot de passe :");
        motpasse = Clavier.lireString();

        System.out.println("Donner le numéro d'abonné :");
        num_abonne = Clavier.lireInt();
        System.out.println("Donner l'adresse de l'abonné :");
        adresse = Clavier.lireString();

        date_abonnement = new Date();
        suspendu = new Date();

        ab = new Abonne(n, p, login, motpasse, num_abonne, adresse, date_abonnement, suspendu);
        ab.getInfos();


        tababonne.add(ab);
        System.out.println(tababonne.size());
    }

    public void creerPret() {
        int ouvId, aboId, pretId;
        Date djour, dateRetour;
        djour = new Date();
        boolean ouvAvailable = false, aboAvailable = false;
        ArrayList<Ouvrage> availableOuvrages = new ArrayList<>();
        ArrayList<Abonne> availableAbonnes = new ArrayList<>();


        // check available ouvrage and avaiable abonne
        for (Ouvrage ouv : tabouvrage) {
            if (ouv.getDisponible()) {
                availableOuvrages.add(ouv);
                ouvAvailable = true;
            }
        }

        for (Abonne abo : tababonne) {
            if (abo.getSuspendu().compareTo(djour) != 1) {
                availableAbonnes.add(abo);
                aboAvailable = true;
            }
        }

        if (!ouvAvailable || !aboAvailable) {
            System.out.println("La creation de Pret n'est pas disponible en ce moment");
            return;
        }

        boolean correctOuvrageIdChosen = false;
        Ouvrage ouvrage = null;
        System.out.println("==================");
        for (Ouvrage o : availableOuvrages) {
            o.getInfos();
        }
        System.out.println("Id d'ouvrage :");
        ouvId = Clavier.lireInt();
        for (Ouvrage o : availableOuvrages) {
            if (o.getNumouvrage() == ouvId) {
                ouvrage = o;
                correctOuvrageIdChosen = true;
                o.setDisponible(false);
                break;
            }
        }

        while (!correctOuvrageIdChosen) {
            System.out.println("Id d'ouvrage incorrect !!! Choisissez a nouveau !");
            System.out.println("Id d'ouvrage :");
            ouvId = Clavier.lireInt();
            for (Ouvrage o : availableOuvrages) {
                if (o.getNumouvrage() == ouvId) {
                    ouvrage = o;
                    correctOuvrageIdChosen = true;
                    o.setDisponible(false);
                    break;
                }
            }
        }

        Abonne abonne = null;
        for (Abonne abo : availableAbonnes) {
            abo.getInfos();
        }

        boolean correctAboChosen = false;
        System.out.println("Id d'abonne :");
        aboId = Clavier.lireInt();
        for (Abonne abo : availableAbonnes) {
            if (abo.getAbonne() == aboId) {
                correctAboChosen = true;
                abonne = abo;
                break;
            }
        }

        while(!correctAboChosen) {
            System.out.println("Id d'abonne incorrect ! Merci de saisir a nouveau : ");
            System.out.println("Id d'abonne :");
            aboId = Clavier.lireInt();
            for (Abonne abo : availableAbonnes) {
                if (abo.getAbonne() == aboId) {
                    correctAboChosen = true;
                    abonne = abo;
                    break;
                }
            }
        }

        System.out.println("Id de Pret :");
        pretId = Clavier.lireInt();
        System.out.println("Date d'emprunt : " + djour);
        dateRetour = djour;

        Pret pret = new Pret(pretId, djour, dateRetour, ouvrage, abonne, bibliothecaire);
        tabpret.add(pret);
        ouvrage.setPret(pret);
        ouvrage.incDecompte();
        abonne.ajouterPret(pret);
    }

    private void RetournerOuvrage() {
        System.out.println("Id de prêt à retourner :");
        int pretId = Clavier.lireInt();
        Pret pret = null;
        for (Pret p : tabpret) {
            if (p.getPret() == pretId) {
                pret = p;
                break;
            }
        }
        if (pret == null) {
            System.out.println("Id incorrect !");
            return;
        }
        pret.getOuvrage().setDisponible(true);

        System.out.println("Ouvrage retourne !");

        Date aujourdhui = new Date();
        // retour en retard
        if (pret.getDateRetour().before(aujourdhui)) {
            Abonne abonne = pret.getAbonne();

            long diffInMillies = Math.abs(aujourdhui.getTime() - pret.getDateRetour().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            Calendar c = Calendar.getInstance();
            c.setTime(aujourdhui);
            c.add(Calendar.DATE, (int) diff);
            abonne.setSuspendu(c.getTime());
            System.out.println("Ouvrage retourne en retard ! Abonne suspendu !");
        }

        pret.setDateRetour(aujourdhui);

        pret.getAbonne().retirerPret(pret);


    }

    private void RechercherPretsEnRetard() {
        Date aujourdhui = new Date();
        ArrayList<Pret> pretsRetard = new ArrayList<>();
        boolean zeroPretEnRetard = true;
        for (Pret p : tabpret) {
            if (!p.estRendu()) {
                long diffInMillies = Math.abs(aujourdhui.getTime() - p.getDateEmprunt().getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if (diff > 15) {
                    pretsRetard.add(p);
                    zeroPretEnRetard = false;
                }
            }
        }
        for (Pret p : pretsRetard) {
            p.getOuvrage().getInfos();
            p.getAbonne().getInfos();
        }

        if (zeroPretEnRetard) {
            System.out.println("Il n'y a pas de pret en retard !");
        }
    }

    private void RechercherAbonne() {
        System.out.println("Id d'abonne à rechercher : ");
        int id = Clavier.lireInt();
        for (Abonne a : tababonne) {
            if (id == a.getAbonne()) {
                System.out.println(a.getInfos());
                break;
            }
        }
    }

    private void RechercherPretAPartirDOuvrage() {
        System.out.println("Id d'ouvrage de pret à rechercher : ");
        int ouvId = Clavier.lireInt();
        for (Ouvrage o : tabouvrage) {
            if (ouvId == o.getNumouvrage()) {
                if (!o.getDisponible()) {
                    o.getPret().getInfos();
                }
                break;
            }
        }
    }

    private void CompterDesPretOuvrage() {
        for (Ouvrage o : tabouvrage) {
            o.getInfos();
            System.out.println("Nombre de Pret : " + o.getDecompte());
        }
    }

    // Section : Abonne
    private void PretsEnCours() {
        for (Pret p : abonne.getPret()) {
            p.getInfos();
        }
    }

    private void ModifierMdp() {
        System.out.println("Ancien mot de passe : ");
        String ancien = Clavier.lireString();
        System.out.println("Nouveau mot de passe : ");
        String nouveau = Clavier.lireString();
        System.out.println("Nouveau mot de passe (à nouveau) : ");
        String nouveau1 = Clavier.lireString();
        if (!ancien.equals(abonne.getMotpasse())) {
            System.out.println("Ancien mot de passe incorrect !");
            return;
        }
        if (!nouveau.equals(nouveau1)) {
            System.out.println("Nouveau mot de passe non identique !");
            return;
        }
        abonne.setMotpasse(nouveau);
    }

    private void RechercherOuvrageAPartirDeTitreEtAuteur() {
        ArrayList<Ouvrage> listeOuvrage = new ArrayList<>();
        System.out.println("Titre d'ouvrage à rechercher : ");
        String titre = Clavier.lireString();
        for (Ouvrage o : tabouvrage) {
            if (o.getTitre().equals(titre)) {
//                o.getInfos();
                listeOuvrage.add(o);
            }
        }
        System.out.println("Nom d'auteur : ");
        String nomAuteur = Clavier.lireString();
        for (Ouvrage o : listeOuvrage) {
            for (Auteur a : o.getListeAuteur()) {
                if (a.getNom().equals(nomAuteur)) {
                    o.getInfos();
                }
            }
        }
    }


    private void RechercherOuvrageAPartirIdAuteur() {
        System.out.println("Id d'auteur : ");
        int idAuteur = Clavier.lireInt();
        for (Auteur a : tabauteur) {
            if (a.getNumauteur() == idAuteur) {
                for (Ouvrage o : a.getOuvrages()) {
                    o.getInfos();
                }
            }
            break;
        }
    }

    private void ListeAbonne() {
        for (Abonne a : tababonne) {
            System.out.println(a.getInfos());
        }
    }

    public void bibMenu() {

        int n;
        System.out.println("Que voulez vous faire ? ");
        System.out.println("1 - Créer un auteur ");
        System.out.println("2 - Créer un ouvrage ");
        System.out.println("3 - Créer un abonné ");
        System.out.println("4 - Créer un prêt ");
        System.out.println("5 - Retourner un ouvrage ");
        System.out.println("6 - Rechercher les prêts en retard ");
        System.out.println("7 - Rechercher un abonné à partir de son abonné et afficher ses caractéristiques ");
        System.out.println("8 - Rechercher un prêt à partir du numéro de l’ouvrage ");
        System.out.println("9 - Compter le nombre de prêts de chaque ouvrage ");
        System.out.println("10 - Login");
        n = Clavier.lireInt();
        switch (n) {
            case 1 -> {
                creerAuteur();
                bibMenu();
            }
            case 2 -> {
                creerOuvrage();
                bibMenu();
            }
            case 3 -> {
                creerAbonne();
                bibMenu();
            }
            case 4 -> {
                creerPret();
                bibMenu();
            }
            case 5 -> {
                RetournerOuvrage();
                bibMenu();
            }
            case 6 -> {
                RechercherPretsEnRetard();
                bibMenu();
            }
            case 7 -> {
                RechercherAbonne();
                bibMenu();
            }
            case 8 -> {
                RechercherPretAPartirDOuvrage();
                bibMenu();
            }
            case 9 -> {
                CompterDesPretOuvrage();
                bibMenu();
            }
            case 100 -> {
                ListeAbonne();
                bibMenu();
            }
            default -> selectUserType();
        }
    }

    private void aboMenu() {
        int n;
        System.out.println("Que voulez vous faire ? ");
        System.out.println("1 - Consultation de prêts en cours ");
        System.out.println("2 - Modifier mot de passe ");
        System.out.println("3 - Rechercher un ouvrage avec nom d'auteur et titre ");
        System.out.println("4 - Rechercher les ouvrages d'un auteur ");
        System.out.println("5 - Main menu ");
        n = Clavier.lireInt();
        switch (n) {
            case 1 -> {
                PretsEnCours();
                aboMenu();
            }
            case 2 -> {
                ModifierMdp();
                aboMenu();
            }
            case 3 -> {
                RechercherOuvrageAPartirDeTitreEtAuteur();
                aboMenu();
            }
            case 4 -> {
                RechercherOuvrageAPartirIdAuteur();
                aboMenu();
            }
            default -> selectUserType();
        }
    }

    private void selectUserType() {
        System.out.println("Vous êtes : ");
        System.out.println("1 - Bibliothécaire");
        System.out.println("2 - Abonné");
        System.out.println("3 - Quitter");
        int selection = Clavier.lireInt();
        if (selection == 1) {
            System.out.println("Login : ");
            String login = Clavier.lireString();
            System.out.println("Mot de passe : ");
            String mdp = Clavier.lireString();
            for (Bibliothecaire b : tabbiblio) {
                if (b.getLogin().equals(login) && b.getMotpasse().equals(mdp)) {
                    bibliothecaire = b;
                    bibMenu();
                    break;
                }
            }
            selectUserType();
        } else if (selection == 2) {
            if (tababonne.size() != 0) {
                System.out.println("Login : ");
                String login = Clavier.lireString();
                System.out.println("Mot de passe : ");
                String mdp = Clavier.lireString();
                for (Abonne a : tababonne) {
                    if (a.getLogin().equals(login) && a.getMotpasse().equals(mdp)) {
                        abonne = a;
                        aboMenu();
                        break;
                    }
                }
                selectUserType();
            } else
                selectUserType();
        } else {

            System.exit(0);
        }
    }

    public static void main(String[] args) {
        Gestion_pret_ouvrage gp = new Gestion_pret_ouvrage();
        gp.selectUserType();
    }

}
