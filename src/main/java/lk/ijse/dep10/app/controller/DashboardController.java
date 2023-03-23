package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
        Stage stage = (Stage) lblCustomers.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/ManageCustomerView.fxml"))));
            stage.setTitle("Manage Customers");
            stage.centerOnScreen();

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "ManageCustomerView.fxml file or controller class not found").showAndWait();
            System.exit(1);
            throw new RuntimeException(e);
        }

    }

    @FXML
    void lblEmployeesOnAction(ActionEvent event) {

    }

    @FXML
    void lblStudentsOnAction(ActionEvent event) {

    }

    @FXML
    void lblSubscribersOnAction(ActionEvent event) {

    }

    @FXML
    void lblTeachersOnAction(ActionEvent event) {

    }

}
