package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import lk.ijse.dep10.app.db.DBConnection;
import lk.ijse.dep10.app.model.Student;

import java.io.InputStream;
import java.sql.*;

public class ManageStudentController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnNewStudent;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<Student> tblStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;


    public void initialize() {

        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));


        btnDelete.setDisable(true);

        tblStudent.getSelectionModel().selectedItemProperty().addListener((value,previous,current) ->{
            btnDelete.setDisable(current == null);
            if (current == null) return;

            txtID.setText(current.getId());
            txtName.setText(current.getName());
            txtAddress.setText(current.getAddress());

            });

        loadStudents();


    }

    private void loadStudents() {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Student ");

            while (resultSet.next()){
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");

                Student student = new Student(id, name, address);

                tblStudent.getItems().add(student);
            }
            tblStudent.refresh();
            btnNewStudent.fire();


        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Failed load the student!").showAndWait();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        Student selectedStudent = tblStudent.getSelectionModel().getSelectedItem();

        if ( selectedStudent == null) {
            return;
        }

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from Student where id=?");

            preparedStatement.setString(1,txtID.getText());
            preparedStatement.executeUpdate();

            tblStudent.getItems().remove(selectedStudent);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to delete the student");
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnNewStudentOnAction(ActionEvent event) {
        generateID();

        txtName.clear();
        txtAddress.clear();
        tblStudent.getSelectionModel().clearSelection();

    }

    private void generateID() {
        String id = "";
        int size = tblStudent.getItems().size();
        if (size == 0) {
            txtID.setText("S001");
            return;
        }
        id = String.format("S%03d",(size+1));
        txtID.setText(id);

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        if (!isValidate()) {
            return;
        }


        Connection connection = DBConnection.getInstance().getConnection();
        Student selectedStudent = tblStudent.getSelectionModel().getSelectedItem();
        if (selectedStudent == null){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("insert into Student (id,name,address) values (?,?,?)");
                preparedStatement.setString(1,txtID.getText());
                preparedStatement.setString(2,txtName.getText());
                preparedStatement.setString(3,txtAddress.getText());

                preparedStatement.executeUpdate();

                Student student = new Student(txtID.getText(), txtName.getText(), txtAddress.getText());

                tblStudent.getItems().add(student);


            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,"Failed to save the customer").showAndWait();
                throw new RuntimeException(e);
            }
        }
        else {
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement("update Student set name = ?,address =? where id = ?");
                preparedStatement.setString(1,txtName.getText());
                preparedStatement.setString(2,txtAddress.getText());
                preparedStatement.setString(3,txtID.getText());

                preparedStatement.executeUpdate();

                selectedStudent.setName(txtName.getText());
                selectedStudent.setAddress(txtAddress.getText());

                tblStudent.refresh();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }




    }

    private boolean isValidate() {

        boolean isValid = true;

        String address = txtAddress.getText();
        String name = txtName.getText();


        if (!name.matches("[A-z ]+")){
            txtName.selectAll();
            txtName.requestFocus();
            isValid = false;
        }

        if (address.length() <=3 ){
            txtAddress.selectAll();
            txtAddress.requestFocus();
            isValid  = false;
        }


        return isValid;
    }

}
