package vues;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modeleCreaFilm.Film;

import java.io.IOException;
import java.util.Collection;

public class Ajout extends Vue implements VueInteractive {

    @FXML
    VBox mainVBox;

    @FXML
    TextField titre;

    @FXML
    TextField genre;

    @FXML
    TextField realisateur;

    private Controleur controleur;

    public static Ajout creerVue(Controleur controleur, Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(Ajout.class.getResource("ajout.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Problème de construction de vue de formulaire");
        }
        Ajout vue = fxmlLoader.getController();
        vue.setControleur(controleur);
        vue.setStage(stage);
        vue.setScene(new Scene(vue.mainVBox, 640, 480));
        return vue;
    }

    @Override
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    public void creerFilm(ActionEvent actionEvent) {
        controleur.creerFilm(titre.getText(), genre.getText(), realisateur.getText());
    }


    public void afficherErreur(String erreurDeFilm, String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR, s);
        alert.setTitle(erreurDeFilm);
        alert.showAndWait();
    }

    @FXML
    public void gotoMenu() {
        controleur.gotoMenu();
    }
    
    public void viderChamps() {
        titre.setText("");
        genre.setText("");
        realisateur.setText("");
    }
}
