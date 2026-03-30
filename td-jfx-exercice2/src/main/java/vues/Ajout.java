package vues;

import controleur.Controleur;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modeleCreaFilm.GenreFilm;

import java.io.IOException;

public class Ajout extends Vue implements VueInteractive {
    private Controleur controleur;

    public Parent getTop() {
        return mainVbox;
    }
    @FXML
    VBox mainVbox;
    @FXML
    TextField titre;
    @FXML
    TextField realisateur;
    @FXML
    ComboBox genreBox;



    public static Ajout creerVue(Controleur controleur, Stage stage)  {
        FXMLLoader fxmlLoader = new FXMLLoader(Ajout.class.getResource("ajout.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Probleme de construction de vue Formulaire");
        }
        Ajout vue = fxmlLoader.getController();
        vue.setControleur(controleur);
        vue.setStage(stage);
        vue.chargerGenres();
        vue.setScene(new Scene(vue.getTop()));
        return vue;

    }

    public void gotomenu(ActionEvent actionEvent) {
        controleur.gotoMenu();
    }

    public void chargerGenres() {
        ObservableList<GenreFilm> liste = FXCollections.observableArrayList(controleur.getGenres());
        genreBox.setItems(liste);
        genreBox.getSelectionModel().selectFirst();
    }

    @Override
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }


    public void creerFilm(ActionEvent actionEvent) {
        GenreFilm genreSelectionne = (GenreFilm) genreBox.getValue();
        controleur.creerFilm(titre.getText(), genreSelectionne.toString(), realisateur.getText());
    }

    public void viderChamps() {
        titre.setText("");
        genreBox.getSelectionModel().selectFirst();
        realisateur.setText("");
    }

    public void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR,message);
        alert.setTitle(titre);
        alert.showAndWait();

    }
}
