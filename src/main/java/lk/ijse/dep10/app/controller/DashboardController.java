package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.PublicKey;

public class DashboardController {
    public Button lblCustomers;
    public Button lblEmployees;
    public Button lblStudents;
    public Button lblSubscribers;
    public Button lblTeachers;

    @FXML
    void lblCustomersOnAction(ActionEvent event) {

    }

    @FXML
    void lblEmployeesOnAction(ActionEvent event) {

    }

    @FXML
    void lblStudentsOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/ManageStudent.fxml"));
        AnchorPane root = fxmlLoader.load();

        Stage stage = (Stage) lblCustomers.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
        stage.setTitle("Manage Student Scene");
        stage.centerOnScreen();



    }

    @FXML
    void lblSubscribersOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/ManageSubscribersView.fxml"));
        AnchorPane root = fxmlLoader.load();

        Stage stage = (Stage) lblCustomers.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
        stage.setTitle("Manage Student Scene");
        stage.centerOnScreen();

    }

    @FXML
    void lblTeachersOnAction(ActionEvent event) {

    }

}
