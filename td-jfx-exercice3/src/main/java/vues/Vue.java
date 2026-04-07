package vues;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Vue {
    private Scene scene;



    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }


}
