package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardUI extends Application {

    private Stage primaryStage;
    private static Scene dashboardScene;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        SceneController.setStage(primaryStage);
        showDashboard();
    }

    public void showDashboard() {

        Button bookBtn = new Button("Manage Books");
        Button studentBtn = new Button("Manage Students");
        Button loanBtn = new Button("Manage Loans");

        bookBtn.getStyleClass().add("dashboard-button");
        studentBtn.getStyleClass().add("dashboard-button");
        loanBtn.getStyleClass().add("dashboard-button");

        bookBtn.setOnAction(e -> primaryStage.setScene(BookUI.getScene()));
        studentBtn.setOnAction(e -> primaryStage.setScene(StudentUI.getScene()));
        loanBtn.setOnAction(e -> primaryStage.setScene(LoanUI.getScene()));

        VBox buttonBox = new VBox(15, bookBtn, studentBtn, loanBtn);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        VBox layout = new VBox(30, buttonBox);
        layout.setPadding(new Insets(250, 50, 50, 50));
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setStyle("-fx-background-image: url('/pics/dashboard.png');"
                + "-fx-background-size: cover;"
                + "-fx-background-repeat: no-repeat;"
                + "-fx-background-position: center center;");

        Scene scene = new Scene(layout, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        dashboardScene = scene;

        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Scene getDashboardScene() {
        return dashboardScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}