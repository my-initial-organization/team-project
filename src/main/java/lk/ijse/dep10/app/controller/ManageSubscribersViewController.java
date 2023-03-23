package lk.ijse.dep10.app.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.dep10.app.util.Subscriber;

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

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnNewSubscriberOnAction(ActionEvent event) {
        generateNewId();
        txtName.clear();
        txtAddress.clear();


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
