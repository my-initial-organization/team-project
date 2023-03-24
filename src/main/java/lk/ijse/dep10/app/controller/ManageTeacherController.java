package lk.ijse.dep10.app.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.app.db.DBConnection;
import lk.ijse.dep10.app.model.Teacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;

public class ManageTeacherController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnNewTeacher;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<Teacher> tblTeachers;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;
    public void initialize(){
        Platform.runLater(btnNewTeacher::fire);

        tblTeachers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblTeachers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblTeachers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        loadAllTeachers();

        tblTeachers.getSelectionModel().selectedItemProperty().addListener((value,previous,current) -> {
            if (current == null){
                return;
            }
            txtID.setText(current.getId());
            txtName.setText(current.getName());
            txtAddress.setText(current.getAddress());

            btnDelete.setDisable(false);
        });
    }
    private void loadAllTeachers(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Teacher");
            while (rst.next()){
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");

                Teacher teacher = new Teacher(id, name, address);
                tblTeachers.getItems().add(teacher);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Unable to load teachers, try again...!").showAndWait();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Teacher selectedTeacher = tblTeachers.getSelectionModel().getSelectedItem();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmTeacher = connection.prepareStatement("DELETE FROM Teacher WHERE id = ?");
            stmTeacher.setString(1, txtID.getText());

            stmTeacher.executeUpdate();
            connection.commit();

            tblTeachers.getItems().remove(selectedTeacher);
            if (tblTeachers.getItems().size() == 0){
                btnNewTeacher.fire();
            }
        } catch (Throwable e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            new Alert(Alert.AlertType.ERROR,"Unable to delete, try again...!").showAndWait();
            throw new RuntimeException(e);
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnNewTeacherOnAction(ActionEvent event) {
        txtID.setText(generateNewID());
        txtName.clear();
        txtAddress.clear();
        tblTeachers.getSelectionModel().clearSelection();
    }
    private String generateNewID(){
        String id = "";
        ObservableList<Teacher> teachersList = tblTeachers.getItems();
        if (teachersList.size() == 0){
            id = "T001";
        }
        else {
            String studentId = teachersList.get(teachersList.size() - 1).getId();
            int newId = Integer.parseInt(studentId.substring(1)) + 1;
            id = String.format("T%03d",newId);
        }
        return id;
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (!isDataValid()) return;

        Teacher selectedTeacher = tblTeachers.getSelectionModel().getSelectedItem();
        Connection connection = DBConnection.getInstance().getConnection();
        if (selectedTeacher == null){
            try {
                connection.setAutoCommit(false);
                PreparedStatement stmTeacher = connection.prepareStatement("INSERT INTO Teacher (id,name,address) VALUES (?,?,?)");
                stmTeacher.setString(1,txtID.getText());
                stmTeacher.setString(2,txtName.getText());
                stmTeacher.setString(3, txtAddress.getText());

                stmTeacher.executeUpdate();
                connection.commit();

                Teacher teacher = new Teacher(txtID.getText(), txtName.getText(), txtAddress.getText());
                tblTeachers.getItems().add(teacher);
                btnNewTeacher.fire();
            } catch (Throwable e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                new Alert(Alert.AlertType.ERROR,"Unable to save the teacher, try again...!").showAndWait();
                throw new RuntimeException(e);
            }
            finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else if (selectedTeacher != null){
            try {
                connection.setAutoCommit(false);
                PreparedStatement stmTeacher = connection.prepareStatement("UPDATE Teacher SET name = ?,address = ? WHERE id = ?");
                stmTeacher.setString(1, txtName.getText());
                stmTeacher.setString(2, txtAddress.getText());
                stmTeacher.setString(3, txtID.getText());

                stmTeacher.executeUpdate();
                connection.commit();

                selectedTeacher.setId(txtID.getText());
                selectedTeacher.setName(txtName.getText());
                selectedTeacher.setAddress(txtAddress.getText());
                tblTeachers.refresh();
                btnNewTeacher.fire();
            } catch (Throwable e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                new Alert(Alert.AlertType.ERROR,"Unable to update, try again...!").showAndWait();
                throw new RuntimeException(e);
            }
            finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private boolean isDataValid(){
        boolean dataValid = true;
        if (txtAddress.getText().strip().length() < 3){
            dataValid = false;
            txtAddress.requestFocus();
            txtAddress.selectAll();
        }
        if (!txtName.getText().strip().matches("[A-z ]{3,}")){
            dataValid = false;
            txtName.requestFocus();
            txtName.selectAll();
        }
        return dataValid;
    }

}

