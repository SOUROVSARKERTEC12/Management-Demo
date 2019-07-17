package application.view;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application
{
	public static Stage stage;
	private static BorderPane mainLayout;
	@Override
	public void start(Stage primaryStage) throws IOException
	{
		Main.stage=primaryStage;
		Main.stage.setTitle("My Application");
		stage.setMaximized(true);
		Main.showMain();
		Main.showLogin();
	}
	
	public static void showMain() throws IOException
	{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(Main.class.getResource("MainView.fxml"));
		mainLayout=loader.load();
		Scene scene=new Scene(mainLayout);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void showLogin() throws IOException
	{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(Main.class.getResource("Login.fxml"));
		BorderPane mainItems=loader.load();
		mainLayout.setCenter(mainItems);
	}
	
	public static void showHome() throws IOException
	{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(Main.class.getResource("Home.fxml"));
		BorderPane mainItems=loader.load();
		mainLayout.setCenter(mainItems);
	}
	 public static void showInformationAlertBox(String info){
		 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		 alert.setContentText(info);
		 alert.showAndWait();
	 }
	public static void main(String[] args) {
		launch(args);
	}
}
