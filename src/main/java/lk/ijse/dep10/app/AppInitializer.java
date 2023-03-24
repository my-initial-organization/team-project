package lk.ijse.dep10.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.dep10.app.db.DBConnection;

import java.io.IOException;
import java.sql.Connection;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/DashboardController.fxml"))));
        primaryStage.setTitle("Dashboard");
        primaryStage.show();
        primaryStage.centerOnScreen();

    }
}
