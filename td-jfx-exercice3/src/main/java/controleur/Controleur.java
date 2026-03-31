package controleur;

import facadeCreaFilm.FacadeScreen;
import facadeCreaFilm.GenreNotFoundException;
import facadeCreaFilm.NomFilmDejaExistantException;
import javafx.stage.Stage;
import modeleCreaFilm.Film;
import modeleCreaFilm.GenreFilm;
import ordre.EcouteurOrdre;
import ordre.LanceurOrdre;
import ordre.TypeOrdre;
import vues.*;

import java.util.*;

public class Controleur implements LanceurOrdre {
    private Map<TypeOrdre, Collection<EcouteurOrdre>> abonnes;
    private GestionnaireVue gestionnaireVue;
    private FacadeScreen facadeScreen;

    public Controleur(FacadeScreen facadeScreen, GestionnaireVue gestionnaireVue) {
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
        fireOrdre(TypeOrdre.CHARGERGENRE);
        fireOrdre(TypeOrdre.SHOWMENU);
    }

    public void showAjout() {
        fireOrdre(TypeOrdre.SHOWAJOUT);
    }
    public void showConsulter() {
        fireOrdre(TypeOrdre.SHOWFILMS);
    }
    public void showMenu() {
        fireOrdre(TypeOrdre.SHOWMENU);
    }


    public void gotoConsulter() {
        fireOrdre(TypeOrdre.CHARGERFILMS);
        showConsulter();
    }

    public void gotoAjout() {
        fireOrdre(TypeOrdre.SHOWAJOUT);
        showAjout();
    }

    public void gotoMenu() {
        fireOrdre(TypeOrdre.CHARGERGENRE);
        showMenu();
    }

    public void creerFilm(String titre, GenreFilm genre, String realisateur)  {
        if (Objects.isNull(titre)||Objects.isNull(genre)||Objects.isNull(realisateur)||titre.equals("")||realisateur.equals("")){
            fireOrdre(TypeOrdre.ERREURCHAMPSVIDE);
            showAjout();
        }
        else {
            try {
                facadeScreen.creerFilm(titre, realisateur, genre);
                fireOrdre(TypeOrdre.VIDERCHAMPS);
                gotoMenu();

            } catch (GenreNotFoundException e) {
                fireOrdre(TypeOrdre.ERREURGENREINCONNU);
                fireOrdre(TypeOrdre.VIDERCHAMPS);
                showAjout();
            } catch (NomFilmDejaExistantException e) {
                fireOrdre(TypeOrdre.ERREURTITREEXISTANT);
                fireOrdre(TypeOrdre.VIDERCHAMPS);
                showAjout();
            }
        }
}

    public Collection<GenreFilm> getGenres() {
        return this.facadeScreen.getAllGenres();
    }

    public Collection<Film> getLesFilms() {
        return  facadeScreen.getAllFilms();
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
