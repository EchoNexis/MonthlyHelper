package com.backend.monthlyHelper.Controller;

import com.backend.monthlyHelper.Entity.SubscriptionEntity;
import com.backend.monthlyHelper.MonthlyHelperApplication;
import com.backend.monthlyHelper.SubscriptionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@Log
@Component
public class AddSubscription {

    private final SubscriptionService subscriptionService;

    @FXML
    public Label labelAddSubscriptionService;

    @Autowired
    public AddSubscription(SubscriptionService subscriptionService){
        this.subscriptionService = subscriptionService;
    }

    @FXML
    public Button btnBackToStart;

    @FXML
    public Button btnAddSubscription;

    @FXML
    public TextField inputService;

    @FXML
    public DatePicker inputCollectionDate;

    @FXML
    public TextField inputAmount;

    @FXML
    public Label labelService;

    @FXML
    public Label labelCollectionDate;

    @FXML
    public Label labelAmount;

    @FXML
    private void initialize(){
        if (subscriptionService.getItemToEdit()!=null){
            SubscriptionEntity item = subscriptionService.getItemToEdit();
            inputService.setText(item.getService());
            int amount = (int) (item.getAmount() * 100);
            inputAmount.setText(String.valueOf(amount));
            LocalDate date = LocalDate.now();
            inputCollectionDate.setValue(date);
        }
        inputAmount.setTextFormatter(new TextFormatter<String>(change -> {
            String text = change.getControlNewText();
            return text.matches("\\d*") ? change : null;
        }));
    }

    @FXML
    private void goBackToStart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("startpage.fxml"));
        loader.setControllerFactory(MonthlyHelperApplication.context::getBean);
        Parent startFXML = loader.load();
        Scene scene = new Scene(startFXML);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("style.css").toExternalForm());
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        subscriptionService.setItemToEdit(null);
    }

    @FXML
    private void addSubscription(ActionEvent event) throws IOException {
        String service = inputService.getText();
        String[] collection = (inputCollectionDate.getValue().toString()).split("-");
        String date = collection[2]+"."+collection[1];
        float amount = (float) Integer.parseInt(inputAmount.getText()) /100;
        if (subscriptionService.getItemToEdit()==null){
            if (subscriptionService.addSubscription(new SubscriptionEntity(service, date, amount))!=null){
                goBackToStart(event);
            }
        }
        else{
            subscriptionService.updateSubscription(new SubscriptionEntity(service, date, amount));
            goBackToStart(event);
        }
    }
}
