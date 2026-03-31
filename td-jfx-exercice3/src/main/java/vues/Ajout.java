package vues;

import controleur.Controleur;
import javafx.collections.FXCollections;
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
import ordre.EcouteurOrdre;
import ordre.LanceurOrdre;
import ordre.TypeOrdre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Ajout extends Vue implements VueInteractive, EcouteurOrdre {
    private Controleur controleur;

    @FXML
    VBox mainVbox;

    @FXML
    TextField titre;
    @FXML
    ComboBox<GenreFilm> genre;
    @FXML
    TextField realisateur;

    public Parent getTop() {
        return mainVbox;
    }


    public static Ajout creerVue(GestionnaireVue gestionnaireVue)  {
        FXMLLoader fxmlLoader = new FXMLLoader(Ajout.class.getResource("ajout.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Probleme de construction de vue Formulaire");
        }

        Ajout vue = fxmlLoader.getController();
        gestionnaireVue.ajouterVueInteractive(vue);
        gestionnaireVue.ajouterEcouteur(vue);
        return vue;
    }

    public void gotomenu(ActionEvent actionEvent) {
        controleur.gotoMenu();
    }

    @Override
    public void setControleur(Controleur controleur) {

        this.controleur = controleur;

    }

    public void creerFilm(ActionEvent actionEvent) {
        controleur.creerFilm(titre.getText(), genre.getSelectionModel().getSelectedItem(), realisateur.getText());
    }

    public void viderChamps() {
        titre.setText("");
        genre.getSelectionModel().clearSelection();
        realisateur.setText("");
    }

    private void chargerGenre() {
        this.genre.setItems(FXCollections.observableList(new ArrayList<>(controleur.getGenres())));
    }

    public void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR,message);
        alert.setTitle(titre);
        alert.showAndWait();

    }

    @Override
    public void setAbonnement(LanceurOrdre lanceurOrdre) {
        lanceurOrdre.abonnement(this, TypeOrdre.CHARGERGENRE, TypeOrdre.ERREURCHAMPSVIDE, TypeOrdre.ERREURTITREEXISTANT, TypeOrdre.ERREURGENREINCONNU, TypeOrdre.VIDERCHAMPS);
    }

    @Override
    public void traiter(TypeOrdre typeOrdre) {
        switch (typeOrdre) {
            case CHARGERGENRE:
                chargerGenre();
                break;
            case ERREURCHAMPSVIDE:
                afficherErreur("Erreur de film", "Un ou plusieurs champs sont vides !");
                break;
            case ERREURGENREINCONNU:
                afficherErreur("Erreur de genre", "Genre Inconnu !");
                break;
            case ERREURTITREEXISTANT:
                afficherErreur("Erreur de film", "Ce titre existe déja !");
                break;
            case VIDERCHAMPS:
                viderChamps();
                break;
            default:
                break;
        }
    }
}
