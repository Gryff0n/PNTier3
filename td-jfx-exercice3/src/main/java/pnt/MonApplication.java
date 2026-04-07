package pnt;

import controleur.Controleur;
import facadeCreaFilm.FacadeScreenImpl;
import javafx.application.Application;
import javafx.stage.Stage;
import vues.GestionnaireVue;
import vues.GestionnaireVueImpl;

import java.io.IOException;

public class MonApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GestionnaireVue gestionnaireVue = new GestionnaireVueImpl(stage);
        Controleur controleur = new Controleur(new FacadeScreenImpl(),gestionnaireVue);
        controleur.run();
    }

    public static void main(String[] args) {
        launch();
    }
}
