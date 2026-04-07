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
import modeleLudotheque.CategorieJeu;
import ordre.EcouteurOrdre;
import ordre.LanceurOrdre;
import ordre.TypeOrdre;

import java.io.IOException;
import java.util.ArrayList;

public class Ajout extends Vue implements VueInteractive, EcouteurOrdre {
    private Controleur controleur;

    @FXML
    VBox mainVbox;

    @FXML
    TextField titre;
    @FXML
    ComboBox<CategorieJeu> categories;
    @FXML
    TextField description;

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
        vue.setScene(new Scene(vue.getTop()));
        gestionnaireVue.ajouterVueInteractive(vue);
        gestionnaireVue.ajouterEcouteur(vue);
        return vue;
    }

    @Override
    public void setControleur(Controleur controleur) {

        this.controleur = controleur;

    }

    public void creerJeu(ActionEvent actionEvent) {
        controleur.creerJeu(titre.getText(), categories.getSelectionModel().getSelectedItem(), description.getText());
    }

    public void viderChamps() {
        titre.setText("");
        categories.getSelectionModel().clearSelection();
        description.setText("");
    }

    private void chargerCategories() {
        this.categories.setItems(FXCollections.observableList(new ArrayList<>(controleur.getCategories())));
    }

    public void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR,message);
        alert.setTitle(titre);
        alert.showAndWait();

    }

    @Override
    public void setAbonnement(LanceurOrdre lanceurOrdre) {
        lanceurOrdre.abonnement(this, TypeOrdre.CHARGERCATEGORIES, TypeOrdre.ERREURCHAMPSVIDE, TypeOrdre.ERREURTITREEXISTANT, TypeOrdre.ERREURCATEGORIEINCONNU, TypeOrdre.VIDERCHAMPS);
    }

    @Override
    public void traiter(TypeOrdre typeOrdre) {
        switch (typeOrdre) {
            case CHARGERCATEGORIES:
                chargerCategories();
                break;
            case ERREURCHAMPSVIDE:
                afficherErreur("Erreur de film", "Un ou plusieurs champs sont vides !");
                break;
            case ERREURCATEGORIEINCONNU:
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
