package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.app.db.DBConnection;
import lk.ijse.dep10.app.model.Customer;

import java.io.*;
import java.sql.*;

public class ManageCustomerViewController {
    public Button btnDelete;
    public Button btnNewCustomer;
    public Button btnSave;
    public TableView<Customer> tblCustomers;
    public TextField txtAddress;
    public TextField txtId;
    public TextField txtName;
    private Connection connection;

    public void initialize() {
        connection = DBConnection.getInstance().getConnection();

        /*Column Mapping*/
        tblCustomers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        loadAllCustomers();

        /*Table selection listener*/
        tblCustomers.getSelectionModel().selectedItemProperty().addListener((value, previous, current) -> {
            txtId.setDisable(false);
            txtName.setDisable(false);
            txtAddress.setDisable(false);

            btnDelete.setDisable(current == null);
            if (current ==null) return;

            txtName.getStyleClass().remove("invalid");
            txtAddress.getStyleClass().remove("invalid");

            txtId.setText(current.getId());
            txtName.setText(current.getName());
            txtAddress.setText(current.getAddress());
        });

    }

    private void loadAllCustomers() {
        try {
            Statement statement = connection.createStatement();
            ResultSet rst = statement.executeQuery("SELECT * FROM Customer");
            while (rst.next()) {
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");
                tblCustomers.getItems().add(new Customer(id, name, address));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database data did not fetch, Try again").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {
        txtId.setDisable(false);
        txtName.setDisable(false);
        txtAddress.setDisable(false);

        txtId.setText(generateId());

        txtName.getStyleClass().remove("invalid");
        txtAddress.getStyleClass().remove("invalid");

        txtName.clear();
        txtAddress.clear();
        txtName.requestFocus();

        tblCustomers.getSelectionModel().clearSelection();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();
        Customer customer = tblCustomers.getSelectionModel().getSelectedItem();
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Customer WHERE id=?");
            statement.setString(1, id);
            statement.executeUpdate();
            tblCustomers.getItems().remove(customer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (!isDataValid()) return;

        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            if (tblCustomers.getSelectionModel().isEmpty()) {
                Customer customer = new Customer(id, name, address);

                PreparedStatement statement = connection.prepareStatement("INSERT INTO Customer (id, name, address) VALUES (?,?,?)");
                statement.setString(1, id);
                statement.setString(2, name);
                statement.setString(3, address);
                statement.executeUpdate();

                tblCustomers.getItems().add(customer);
            } else {
                Customer customer = tblCustomers.getSelectionModel().getSelectedItem();
                PreparedStatement statement = connection.prepareStatement("UPDATE Customer SET name=?, address=? WHERE id=?");
                statement.setString(1, name);
                statement.setString(2, address);
                statement.setString(3, id);
                statement.executeUpdate();
                customer.setName(name);
                customer.setAddress(address);
                tblCustomers.refresh();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean isDataValid() {
        txtName.getStyleClass().remove("invalid");
        txtAddress.getStyleClass().remove("invalid");

        boolean dataValid = true;

        if (!txtAddress.getText().matches("^[A-z0-9/ ]{5,}$")) {
            txtAddress.requestFocus();
            txtAddress.selectAll();
            txtAddress.getStyleClass().add("invalid");
            dataValid = false;
        }

        if (!txtName.getText().matches("^[A-z ]{3,}$")) {
            txtName.requestFocus();
            txtName.selectAll();
            txtName.getStyleClass().add("invalid");
            dataValid = false;
        }
        return dataValid;
    }

    private String generateId() {
        if (tblCustomers.getItems().size() == 0) return "C001";
        Customer lastCustomer = tblCustomers.getItems().get(tblCustomers.getItems().size() - 1);
        int lastId = Integer.parseInt(lastCustomer.getId().substring(1));
        String id = String.format("C%03d", (lastId + 1));
        return id;
    }

//    private void generateTables() {
//        try {
//            Statement statement = connection.createStatement();
//            InputStream is = getClass().getResourceAsStream("/schema.sql");
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            String dbScript= "";
//            String line = "";
//            while ((line = br.readLine()) != null) {
//                dbScript += line + "\n";
//            }
//            System.out.println(dbScript);
//
//
//
//        } catch (SQLException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
