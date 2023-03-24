package lk.ijse.dep10.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lk.ijse.dep10.app.db.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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

        Connection connection = DBConnection.getInstance().getConnection();
        System.out.println(connection);

        generateTables();

    }
    private void generateTables(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            stm.execute(readDBScript());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Cannot generate tables, try again").showAndWait();
            throw new RuntimeException(e);
        }
    }
    private String readDBScript(){
        InputStream is = getClass().getResourceAsStream("/schema.sql");
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            String line;
            StringBuilder dbScriptBuilder = new StringBuilder();
            while((line  = br.readLine()) != null){
                dbScriptBuilder.append(line);
            }
            return dbScriptBuilder.toString();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
