package login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;


public class LoginController {
	@FXML
	private Button btnlogin,btnsignin;
	@FXML
	private TextField txtusername;
	@FXML
	private Label message;
	@FXML
	private PasswordField txtpassword;
	private Parent root;
	private Scene scene;
	private Stage stage;
	
	Connection conn = DatabaseConnection.getConnection();
	
	
	
	private static void showalert(AlertType alerttype,Window owner,String title,String message) {
		Alert alert = new Alert(alerttype);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}
	
	public static void infoBox(String infoMessage,String headerText,String Title) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(infoMessage);
		alert.setHeaderText(headerText);
		alert.setTitle(Title);
		alert.showAndWait();
	}
	
	
	public void login(ActionEvent event) throws IOException {
		
	try {
		
		stage = (Stage)btnlogin.getScene().getWindow();
		//Window owner = btnlogin.getScene().getWindow();
		txtusername.getText();
		txtpassword.getText();
		if(txtusername.getText().isEmpty()) {
			showalert(Alert.AlertType.ERROR,stage,"form Error","please enter your username");
			return;
		}
		if(txtpassword.getText().isEmpty()) {
			showalert(Alert.AlertType.ERROR,stage,"form Error","please enter your password");
			return;
		}
		Login login = new Login(txtusername.getText(),txtpassword.getText());
		String query = "SELECT * FROM LOGIN WHERE username =? AND password =?";
		
		 PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, login.getUsername());
				pstmt.setString(2, login.getPassword());
				pstmt.execute();
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
				
					if((!rs.getString(1).equals(login.getUsername()))&&(!rs.getString(2).equals(login.getPassword()))) {
						message.setStyle("-fx-text-fill : red");
						stage = (Stage)message.getScene().getWindow();
						message.setText("WRONG USERNAME OR PASSWORD");
				}
					closeStage();
					loadregpage();
					
				}
				
		 
	}catch(SQLException e) {
		e.printStackTrace();
	}
		
	
	}
	
	public void closeStage() {
		stage = (Stage)(message.getScene().getWindow());
		stage.close();
	}
	
	
public void loadregpage() throws IOException{
	FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/student.fxml"));
	 root = loader.load();
	 //stage= (Stage)((Node)event.getSource()).getScene().getWindow();
	scene = new Scene(root);
	stage = new Stage();
	stage.setScene(scene);
	stage.setOnCloseRequest(event ->{
		
		event.consume();
		exit(stage);
	});
	stage.setResizable(false);
	stage.show();
	
	
	}
	
	
	public void exit(Stage stage) {
 		Alert alert = new Alert(AlertType.CONFIRMATION);
 		alert.setHeaderText("DO YOU WANT TO  EXIT ");
 		alert.setTitle("EXIT");
 		alert.setContentText("YOU ARE ABOUT TO EXIT!!! ");
 		
 		if(alert.showAndWait().get() == ButtonType.OK) {
 			
 			stage.close();
 		}
 	}
	
	public void signin() throws IOException {
		closeStage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/signup/signup.fxml"));
		 root = loader.load();
		 //stage= (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage = new Stage();
		stage.setScene(scene);
		stage.setOnCloseRequest(event ->{
			
			event.consume();
			exit(stage);
		});
		stage.setResizable(false);
		stage.show();
		
	}
	
	

}
