package lk.ijse.dep10.app.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.dep10.app.db.DBConnection;
import lk.ijse.dep10.app.util.Subscriber;

import java.sql.*;

public class ManageSubscribersViewController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnNewSubscriber;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<Subscriber> tblSubscribers;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;
    public void initialize(){
        btnDelete.setDisable(true);
        Platform.runLater(() -> {
            btnNewSubscriber.fire();
        });
        loadAllSubscribers();

    }

    private void loadAllSubscribers() {
        try {
            Connection connection= DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            String sql="select * from Subscriber";
            ResultSet rst = stm.executeQuery(sql);
            while(rst.next()){
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");
                tblSubscribers.getItems().add(new Subscriber(id,name,address));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Subscriber selectedSubscriber = tblSubscribers.getSelectionModel().getSelectedItem();
        Connection connection=DBConnection.getInstance().getConnection();
        try {
            PreparedStatement stm = connection.prepareStatement("delete from Subscriber where id=?");
            stm.setString(1,selectedSubscriber.getId());
            stm.executeUpdate();
            tblSubscribers.getItems().remove(selectedSubscriber);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnNewSubscriberOnAction(ActionEvent event) {
        generateNewId();
        txtName.clear();
        txtAddress.clear();
        txtName.requestFocus();


    }

    private String generateNewId() {
        ObservableList<Subscriber> subscriberList = tblSubscribers.getItems();
        if(subscriberList.isEmpty()) return "DEP10-sub-001";
        String lastId = subscriberList.get(subscriberList.size() - 1).
                getId().substring(10);
        String newId=String.format("DEP10-sub-%03s",Integer.parseInt(lastId));
        return  newId;
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

}
