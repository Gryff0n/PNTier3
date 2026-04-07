package controleur;

import facadeLudotheque.CategorieNotFoundException;
import facadeLudotheque.FacadeLudotheque;
import facadeLudotheque.InformationManquanteException;
import facadeLudotheque.JeuDejaExistant;
import modeleLudotheque.CategorieJeu;
import modeleLudotheque.Jeu;
import ordre.EcouteurOrdre;
import ordre.LanceurOrdre;
import ordre.TypeOrdre;
import vues.GestionnaireVue;

import java.util.*;

public class Controleur implements LanceurOrdre {
    private Map<TypeOrdre, Collection<EcouteurOrdre>> abonnes;
    private GestionnaireVue gestionnaireVue;
    private FacadeLudotheque facadeScreen;
    private String categorieActuelle;

    public Controleur(FacadeLudotheque facadeScreen, GestionnaireVue gestionnaireVue) {
        this.facadeScreen = facadeScreen;
        this.gestionnaireVue = gestionnaireVue;
        abonnes = new HashMap<>();
        for(TypeOrdre typeOrdre : TypeOrdre.values()) {
            abonnes.put(typeOrdre, new ArrayList<>());
        }
        gestionnaireVue.setControleur(this);
        gestionnaireVue.setAbonnement(this);
    }



    public void run() {
        fireOrdre(TypeOrdre.CHARGERCATEGORIES);
        fireOrdre(TypeOrdre.SHOWAJOUT);
    }

    public void showAjout() {
        fireOrdre(TypeOrdre.SHOWAJOUT);
    }
    public void showConsulter() {
        fireOrdre(TypeOrdre.SHOWJEUX);
    }


    public void gotoConsulter() {
        fireOrdre(TypeOrdre.CHARGERJEUX);
        showConsulter();
    }

    public void gotoAjout() {
        fireOrdre(TypeOrdre.SHOWAJOUT);
        showAjout();
    }

    public void creerJeu(String titre, CategorieJeu categorieJeu, String realisateur)  {
        if (Objects.isNull(titre)||Objects.isNull(categorieJeu)||Objects.isNull(realisateur)||titre.equals("")||realisateur.equals("")){
            fireOrdre(TypeOrdre.ERREURCHAMPSVIDE);
            showAjout();
        }
        else {
            try {
                facadeScreen.ajoutJeu(titre, String.valueOf(categorieJeu), realisateur);
                fireOrdre(TypeOrdre.VIDERCHAMPS);
                categorieActuelle = String.valueOf(categorieJeu);
                gotoConsulter();
            } catch (CategorieNotFoundException e) {
                fireOrdre(TypeOrdre.ERREURCATEGORIEINCONNU);
                fireOrdre(TypeOrdre.VIDERCHAMPS);
                showAjout();
            } catch (InformationManquanteException e) {
                fireOrdre(TypeOrdre.ERREURCHAMPSVIDE);
                showAjout();
            } catch (JeuDejaExistant e) {
                fireOrdre(TypeOrdre.ERREURTITREEXISTANT);
                fireOrdre(TypeOrdre.VIDERCHAMPS);
                showAjout();
            }
        }
}

    public Collection<CategorieJeu> getCategories() {
        return this.facadeScreen.getAllsCategorie();
    }

    public Collection<Jeu> getLesJeux() {
        try {
            return facadeScreen.getJeuxDuneCategorie(categorieActuelle);
        } catch (CategorieNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fireOrdre(TypeOrdre ordre) {
        for(EcouteurOrdre ecouteurOrdre : abonnes.get(ordre)) {
            ecouteurOrdre.traiter(ordre);
        }
    }

    @Override
    public void abonnement(EcouteurOrdre e, TypeOrdre... types) {
        for(TypeOrdre typeOrdre : types) {
            abonnes.get(typeOrdre).add(e);
        }
    }
}
