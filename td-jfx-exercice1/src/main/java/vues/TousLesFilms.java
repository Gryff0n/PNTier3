package vues;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modeleCreaFilm.Film;

import java.io.IOException;
import java.util.Collection;

public class TousLesFilms extends Vue implements VueInteractive {

    @FXML
    TextArea lesFilms;
    @FXML
    VBox mainVBox;
    @FXML



    private Controleur controleur;

    public static TousLesFilms creerVue(Controleur controleur, Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(TousLesFilms.class.getResource("tousLesFilms.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Problème de construction de vue de formulaire");
        }
        TousLesFilms vue = fxmlLoader.getController();
        vue.setControleur(controleur);
        vue.setStage(stage);
        vue.setScene(new Scene(vue.mainVBox));
        return vue;
    }

    @Override
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    public void gotoMenu() {
        controleur.gotoMenu();
    }

    public void show() {
        Collection<Film> films = this.controleur.getFilms();
        StringBuilder s = new StringBuilder();
        for (Film f : films) {
            s.append(f.getTitre()).append(" ").append(f.getRealisateur()).append("\n");
        }
        this.lesFilms.setText(s.toString());
        super.show();
    }
}
