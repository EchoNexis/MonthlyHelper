package com.backend.monthlyHelper.Controller;

import com.backend.monthlyHelper.Entity.SubscriptionEntity;
import com.backend.monthlyHelper.MonthlyHelperApplication;
import com.backend.monthlyHelper.SubscriptionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Log
@Component
public class Startpage {

    private final SubscriptionService subscriptionService;

    @Autowired
    public Startpage(SubscriptionService subscriptionService){
        this.subscriptionService = subscriptionService;
    }

    private int activeList;

    @FXML
    public Button btnEditSubscription;

    @FXML
    public Label labelToPayAmount;

    @FXML
    public Label labelPayedAmount;

    
    @FXML
    public Button btnRemoveSubscription;

    @FXML
    public ListView listviewPayed;

    @FXML
    public ListView listviewToPay;

    @FXML
    public Button btnAddSubscription;

    @FXML
    public Label labelPayed;

    @FXML
    public Label labelToPay;

    @FXML
    private void goToAddScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("AddSubscription.fxml"));
        loader.setControllerFactory(MonthlyHelperApplication.context::getBean);
        Parent addSubscription = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene addSubscriptionScene = new Scene(addSubscription);
        stage.setScene(addSubscriptionScene);
        stage.show();
    }

    @FXML
    private void goToEditScene(ActionEvent event) throws IOException {
        subscriptionService.setItemToEdit(getActiveItem());
        goToAddScene(event);
    }

    @FXML
    private void removeSubscription(ActionEvent event) throws IOException {
        SubscriptionEntity item = getActiveItem();
        subscriptionService.removeSubscription(item.getService());
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("startpage.fxml"));
        loader.setControllerFactory(MonthlyHelperApplication.context::getBean);
        Parent root = loader.load();
        Scene start = new Scene(root);
        stage.setScene(start);
    }

    @FXML
    public void initialize(){
        listviewPayed.setOnMouseClicked(mouseEvent -> {
            this.activeList = 1;
        });
        listviewToPay.setOnMouseClicked(mouseEvent -> {
            this.activeList = 0;
        });
        List<SubscriptionEntity> subsPayed = subscriptionService.getSubscriptionPayed();
        log.info(subsPayed.toString());
        ObservableList<SubscriptionEntity> subListPayed = FXCollections.observableList(subsPayed);
        listviewPayed.setItems(subListPayed);

        List<SubscriptionEntity> subsToPay = subscriptionService.getSubscriptionToPay();
        ObservableList<SubscriptionEntity> subListToPay = FXCollections.observableList(subsToPay);
        listviewToPay.setItems(subListToPay);

        listviewToPay.setCellFactory(param -> {
            return new ListCell<SubscriptionEntity>() {
                private VBox vbox = new VBox();
                private Label nameLabel = new Label();
                private Label dateLabel = new Label();
                private Label valueLabel = new Label();
                {vbox.getChildren().addAll(nameLabel, dateLabel, valueLabel);}
                @Override
                protected void updateItem(SubscriptionEntity item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        nameLabel.setText("service: " + item.getService());
                        dateLabel.setText("date: " + item.getDate());
                        valueLabel.setText("amount: " + item.getAmount());
                        setGraphic(vbox); // Setze die VBox als grafische Darstellung
                    }
                }
            };
        });
        float sum = 0;
        for(SubscriptionEntity sub: subsToPay){
            sum += sub.getAmount();
        }
        labelToPayAmount.setText("to pay: " + sum);

        listviewPayed.setCellFactory(param -> {
            return new ListCell<SubscriptionEntity>() {
                private VBox vbox = new VBox();
                private Label nameLabel = new Label();
                private Label dateLabel = new Label();
                private Label valueLabel = new Label();
                {vbox.getChildren().addAll(nameLabel, dateLabel, valueLabel);}
                @Override
                protected void updateItem(SubscriptionEntity item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        nameLabel.setText("Name: " + item.getService());
                        dateLabel.setText("Datum: " + item.getDate());
                        valueLabel.setText("Wert: " + item.getAmount());
                        setGraphic(vbox); // Setze die VBox als grafische Darstellung
                    }
                }
            };
        });

        sum = 0;
        for(SubscriptionEntity sub:subsPayed){
            sum +=  sub.getAmount();
        }
        labelPayedAmount.setText("already payed: " + sum);
    }

    public SubscriptionEntity getActiveItem(){
        return (SubscriptionEntity) (activeList==1 ? listviewPayed.getSelectionModel().getSelectedItem() :
                listviewToPay.getSelectionModel().getSelectedItem());
    }



}
