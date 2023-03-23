package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.scene.control.Alert;


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
    void lblEmployeesOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) lblEmployees.getScene().getWindow();
        stage.setScene(new Scene(new FXMLLoader(getClass().getResource("/view/EmployeeView.fxml")).load()));
        stage.centerOnScreen();
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
