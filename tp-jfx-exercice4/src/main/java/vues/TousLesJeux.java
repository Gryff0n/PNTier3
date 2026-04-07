package vues;

import controleur.Controleur;
import facadeLudotheque.CategorieNotFoundException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import modeleLudotheque.Jeu;
import ordre.EcouteurOrdre;
import ordre.LanceurOrdre;
import ordre.TypeOrdre;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class TousLesJeux extends Vue implements VueInteractive, EcouteurOrdre {
    private Controleur controleur;

    @FXML
    VBox mainVbox;

    @FXML
    ListView<Jeu> lesJeux;

    public Parent getTop() {
        return mainVbox;
    }

    public static TousLesJeux creerVue(GestionnaireVue gestionnaireVue) {
        FXMLLoader fxmlLoader = new FXMLLoader(TousLesJeux.class.getResource("tousLesJeux.fxml"));
        try {
            fxmlLoader.load();
            TousLesJeux vue = fxmlLoader.getController();
            vue.setScene(new Scene(vue.getTop()));
            gestionnaireVue.ajouterVueInteractive(vue);
            gestionnaireVue.ajouterEcouteur(vue);
            return vue;
        } catch (IOException e) {
            throw new RuntimeException("Problème de chargement du FXML tousLesJeux.fxml", e);
        }
    }


    public void gotoajout(ActionEvent actionEvent) {
        controleur.gotoAjout();
    }


    @Override
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }



    public void chargerJeux() {
        Collection<Jeu> jeux = controleur.getLesJeux();
        this.lesJeux.setItems(FXCollections.observableList(new ArrayList<>(jeux)));
        this.lesJeux.setCellFactory(param -> new ListCell<Jeu>() {
            @Override
            protected void updateItem(Jeu item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else if (item == null) {
                    setText("No body");
                } else {
                    setText(item.getNomJeu() + " (" + item.getCategorieJeu() + ", " + item.getResume() + ")");
                }
            }
        });
    }

    @Override
    public void setAbonnement(LanceurOrdre lanceurOrdre) {
        lanceurOrdre.abonnement(this, TypeOrdre.CHARGERJEUX);
    }

    @Override
    public void traiter(TypeOrdre typeOrdre) {
        if(typeOrdre == TypeOrdre.CHARGERJEUX) {
            chargerJeux();
        }
    }
}
