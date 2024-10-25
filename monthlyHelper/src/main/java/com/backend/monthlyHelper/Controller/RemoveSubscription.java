package com.backend.monthlyHelper.Controller;

import com.backend.monthlyHelper.Entity.SubscriptionEntity;
import com.backend.monthlyHelper.MonthlyHelperApplication;
import com.backend.monthlyHelper.SubscriptionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

@Component
@Log
public class RemoveSubscription {

    private final SubscriptionService subscriptionService;

    @Autowired
    public RemoveSubscription(SubscriptionService subscriptionService){
        this.subscriptionService = subscriptionService;
    }


    @FXML
    public ListView listview;

    @FXML
    public Button btnBackToStart;

    @FXML
    public Button btnRemoveSubscription;

    public List<SubscriptionEntity> subs;

    @FXML
    private void initialize(){
        subs = subscriptionService.getAllSubscriptions();
        ObservableList<SubscriptionEntity> items = FXCollections.observableList(subs);
        listview.setItems(items);

        listview.setCellFactory(param -> {
            return new ListCell<SubscriptionEntity>() {

                private final VBox vbox = new VBox();
                private final Label nameLabel = new Label();
                private final Label dateLabel = new Label();
                private final Label valueLabel = new Label();

                {
                    vbox.getChildren().addAll(nameLabel, dateLabel, valueLabel);
                }

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
    }

    @FXML
    private void goBackToStart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("startpage.fxml"));
        loader.setControllerFactory(MonthlyHelperApplication.context:: getBean);
        Parent root = loader.load();
        Scene startScene = new Scene(root);
        startScene.getStylesheets().add(getClass().getClassLoader().getResource("style.css").toExternalForm());
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(startScene);
    }

    @FXML
    private void removeSubscription(ActionEvent event) throws IOException {
        SubscriptionEntity item = (SubscriptionEntity) listview.getSelectionModel().getSelectedItem();
        subscriptionService.removeSubscription(item.getService());
        goBackToStart(event);
    }

}
