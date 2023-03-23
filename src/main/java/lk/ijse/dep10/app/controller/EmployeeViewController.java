package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.app.db.DBConnection;
import lk.ijse.dep10.app.model.Employee;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeViewController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnNewEmployee;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<Employee> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    ArrayList<Employee> employeeList = new ArrayList<>();

    public void initialize(){

        tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblEmployee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Employee");
            while (resultSet.next()){
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");

                Employee employee = new Employee(id, name, address);
                employeeList.add(employee);
            }
            tblEmployee.getItems().addAll(employeeList);
            tblEmployee.refresh();

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"failed to load Employees").show();
        }

        btnDelete.setDisable(true);
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observableValue, employee, current) -> {
            btnDelete.setDisable(current == null);

            if(current==null)return;

            txtId.setText(current.getId());
            txtName.setText(current.getName());
            txtAddress.setText(current.getAddress());
        });
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Employee selectedItem = tblEmployee.getSelectionModel().getSelectedItem();

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Employee WHERE id=?");
            statement.setString(1,selectedItem.getId());
            statement.executeUpdate();

            tblEmployee.getItems().remove(tblEmployee.getSelectionModel().getSelectedItem());
            tblEmployee.refresh();
            if(tblEmployee.getItems().isEmpty()){
                tblEmployee.getSelectionModel().clearSelection();
                btnNewEmployee.fire();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"failed to delete employee");
        }

    }

    @FXML
    void btnNewEmployeeOnAction(ActionEvent event) {
        txtAddress.clear();
        txtId.clear();
        txtName.clear();
        txtName.requestFocus();

        btnDelete.setDisable(true);

        tblEmployee.getSelectionModel().clearSelection();
    }

    private String generateId() {
        Employee employee = employeeList.get(employeeList.size() - 1);
        String id = employee.getId().substring(1);
        return (String.format("E%03d",Integer.parseInt(id)+1));
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if(!isValidEmployee())return;

        String id = generateId();
        String name = txtName.getText();
        String address = txtAddress.getText();

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement statement;
            if (tblEmployee.getSelectionModel().getSelectedItem() == null) {
                statement = connection.prepareStatement("INSERT INTO Employee (id,name,address) VALUES (?,?,?)");
                statement.setString(1, id);
                statement.setString(2, name);
                statement.setString(3, address);
                statement.executeUpdate();

                Employee employee = new Employee(id, name, address);
                tblEmployee.getItems().add(employee);
            }
            else {
                id = txtId.getText();
                statement = connection.prepareStatement("UPDATE Employee SET name=?, address=? WHERE id=?");
                statement.setString(1, name);
                statement.setString(2, address);
                statement.setString(3, id);
                statement.executeUpdate();

                Employee employee = new Employee(id, name, address);
                tblEmployee.getItems().set(tblEmployee.getSelectionModel().getSelectedIndex(),employee);
            }

            tblEmployee.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"failed to save employee");
        }

        btnNewEmployee.fire();

    }

    private boolean isValidEmployee() {
        boolean isValid = true;
        String name = txtName.getText();
        String address = txtAddress.getText();

        if(!address.matches("[a-zA-Z ]+")){
            txtAddress.requestFocus();
            txtAddress.selectAll();
            isValid =false;
        }
        if(!name.matches("[a-zA-Z ]+")){
            txtName.requestFocus();
            txtName.selectAll();
            isValid =false;
        }
        return isValid;
    }

}
