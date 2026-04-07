package vues;

import javafx.stage.Stage;
import ordre.LanceurOrdre;
import ordre.TypeOrdre;
import controleur.Controleur;

public class GestionnaireVueImpl extends GestionnaireVue {

    private Menu menu;
    private Ajout ajout;
    private TousLesFilms tousLesFilms;


    public GestionnaireVueImpl(Stage stage) {
        super(stage);
        menu = Menu.creerVue(this);
        ajout = Ajout.creerVue(this);
        tousLesFilms = TousLesFilms.creerVue(this);
    }


    @Override
    public void setAbonnement(LanceurOrdre lanceurOrdre) {
        lanceurOrdre.abonnement(this, TypeOrdre.SHOWMENU, TypeOrdre.SHOWAJOUT, TypeOrdre.SHOWFILMS);
        super.setAbonnement(lanceurOrdre);
    }

    @Override
    public void traiter(TypeOrdre typeOrdre) {
        switch (typeOrdre) {
            case SHOWAJOUT:
                getStage().setScene(ajout.getScene());
                break;
            case SHOWFILMS:
                getStage().setScene(tousLesFilms.getScene());
                break;
            case SHOWMENU:
                getStage().setScene(menu.getScene());
                break;
            default:
                break;
        }
        getStage().show();
    }

}
