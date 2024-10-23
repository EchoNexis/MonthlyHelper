package com.backend.monthlyHelper;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class MonthlyHelperApplication extends Application{

	public static ApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(MonthlyHelperApplication.class, args);
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("startpage.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		stage.setResizable(false);
	}
}
