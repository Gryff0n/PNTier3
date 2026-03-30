package vues;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.*;

public class Menu extends Vue implements VueInteractive {
    private Controleur controleur;

    public static Menu creerVue(Controleur controleur, Stage stage) {
        Menu menu = new Menu();
        menu.setControleur(controleur);
        menu.setStage(stage);
        menu.initialiserVue();
        return menu;

    }

    private void initialiserVue() {

        Label titre = new Label("Films");
        titre.setAlignment(Pos.CENTER);
        titre.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        titre.setFont(Font.font(42));

        Button consulter = new Button("Consulter");
        consulter.setAlignment(Pos.CENTER);
        consulter.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        consulter.setFont(Font.font(22));

        //inner anonymous class
        consulter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controleur.gotoConsulter();
            }
        });

        Button ajouter = new Button("Ajouter");
        ajouter.setAlignment(Pos.CENTER);
        ajouter.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        ajouter.setFont(Font.font(22));
        //lambda expression
        ajouter.setOnAction(actionEvent -> controleur.gotoAjout());

        VBox vbox = new VBox();
        vbox.getChildren().addAll(consulter, ajouter);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        vbox.setSpacing(40);

        BorderPane bordure = new BorderPane();
        bordure.setCenter(vbox);
        bordure.setTop(titre);

        setScene(new Scene(bordure,640,480));
    }

    @Override
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }


}
