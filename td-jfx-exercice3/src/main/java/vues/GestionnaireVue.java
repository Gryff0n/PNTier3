package vues;

import controleur.Controleur;
import ordre.EcouteurOrdre;
import javafx.stage.Stage;
import ordre.LanceurOrdre;

import java.util.ArrayList;
import java.util.Collection;

public abstract class GestionnaireVue implements EcouteurOrdre, VueInteractive{
    private Stage stage;
    private Controleur controleur;
    private Collection<EcouteurOrdre> ecouteurOrdres = new ArrayList<>();
    private Collection<VueInteractive> vuesInteractives = new ArrayList<>();

    public GestionnaireVue(Stage stage) {
        this.stage = stage;
    }

    public void setAbonnement(LanceurOrdre lanceurOrdre) {
        ecouteurOrdres.forEach(e->e.setAbonnement(lanceurOrdre));
    }


    public void ajouterEcouteur(EcouteurOrdre ecouteurOrdre) {
        ecouteurOrdres.add(ecouteurOrdre);
    }

    public void ajouterVueInteractive(VueInteractive vueInteractive) {
        vuesInteractives.add(vueInteractive);
    }

    public Stage getStage() {
        return stage;
    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
        for(VueInteractive vue : vuesInteractives) {
            vue.setControleur(controleur);
        }
    }


}
