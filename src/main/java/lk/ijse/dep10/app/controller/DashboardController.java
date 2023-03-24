package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    void lblStudentsOnAction(ActionEvent event) {

    }

    @FXML
    void lblSubscribersOnAction(ActionEvent event) {

    }

    @FXML
    void lblTeachersOnAction(ActionEvent event) throws IOException {
        URL fxmlFile = getClass().getResource("/view/ManageTeacherController.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile);
        AnchorPane root = fxmlLoader.load();

        Stage manageTeacherStage = new Stage();
        Scene manageTeacherScene = new Scene(root);
        manageTeacherStage.setScene(manageTeacherScene);
        manageTeacherStage.show();
        manageTeacherStage.setTitle("Manage Teacher");
        manageTeacherStage.centerOnScreen();
    }

}
