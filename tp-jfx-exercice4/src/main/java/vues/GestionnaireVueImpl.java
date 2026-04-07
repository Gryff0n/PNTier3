package vues;

import javafx.stage.Stage;
import ordre.LanceurOrdre;
import ordre.TypeOrdre;

public class GestionnaireVueImpl extends GestionnaireVue {

    private Ajout ajout;
    private TousLesJeux tousLesJeux;


    public GestionnaireVueImpl(Stage stage) {
        super(stage);
        ajout = Ajout.creerVue(this);
        tousLesJeux = TousLesJeux.creerVue(this);
    }


    @Override
    public void setAbonnement(LanceurOrdre lanceurOrdre) {
        lanceurOrdre.abonnement(this, TypeOrdre.SHOWAJOUT, TypeOrdre.SHOWJEUX);
        super.setAbonnement(lanceurOrdre);
    }

    @Override
    public void traiter(TypeOrdre typeOrdre) {
        switch (typeOrdre) {
            case SHOWAJOUT:
                getStage().setScene(ajout.getScene());
                break;
            case SHOWJEUX:
                getStage().setScene(tousLesJeux.getScene());
                break;
            default:
                break;
        }
        getStage().show();
    }

}
