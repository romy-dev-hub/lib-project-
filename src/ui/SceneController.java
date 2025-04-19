package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

    private static Stage primaryStage;

    public static void setStage(Stage stage){
        primaryStage = stage;
    }

    public static void switchScene(Scene scene){
        primaryStage.setScene(scene);
    }

    public static void switchSceneFromFXML(String fxmlPath){
        try {
            Parent root = FXMLLoader.load(SceneController.class.getResource(fxmlPath));
            primaryStage.setScene(new Scene(root));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
