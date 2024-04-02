package signup;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DatabaseConnection;
import database.DatabaseOps;
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

public class SignUpController {
	@FXML
	private Button btnlogin,btnsignup;
	@FXML
	private TextField txtusername;
	@FXML
	private PasswordField txtpassword,txtconfirm;
	@FXML
	private Label message;
	private Parent root;
	private Stage stage;
	private Scene scene;
	
	
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
	
	public void signup() {
		try {
			
			stage = (Stage)btnlogin.getScene().getWindow();
			//Window owner = btnlogin.getScene().getWindow();
			txtusername.getText();
			txtpassword.getText();
			txtconfirm.getText();
			if(txtusername.getText().isEmpty()) {
				showalert(Alert.AlertType.ERROR,stage,"Error","please enter your username");
				return;
			}
			if(txtpassword.getText().isEmpty()) {
				showalert(Alert.AlertType.ERROR,stage,"Error","please enter your password");
				return;
			}
			if(txtconfirm.getText().isEmpty()) {
				showalert(Alert.AlertType.ERROR,stage,"Error","please confirm your password");
				return;
			}
			
			Signup sign = new Signup(txtusername.getText(),txtpassword.getText(),txtconfirm.getText());
			
			 if(DatabaseOps.ifIDexist(sign.getUsername())) {
				 Alert alert = new Alert(AlertType.INFORMATION);
				 alert.setTitle("CHOOSE ANOTHER USERNAME");
				 alert.setContentText("USERNAME ALREADY EXISTS!");
				 alert.showAndWait();
				 
					//showalert(AlertType.INFORMATION,((Stage)txtusername.getScene().getWindow()),"CHOOSE ANOTHER USERNAME","USERNAME ALREADY EXITS");
		   		}
			 
			
			if(sign.getPassword().equals(sign.getConfirmp())) {
				if(txtpassword.getText().equals("") || txtconfirm.getText().equals("")) {
					message.setText("");
				}
				
			String SQL = "INSERT INTO LOGIN(USERNAME,PASSWORD) VALUES(?,?)";
			
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, sign.getUsername());
			pstmt.setString(2, sign.getPassword());
			pstmt.execute();
			
			  Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setTitle("CREATING ACCOUNT");
		        alert.setHeaderText("ACCOUNT REGISTRATION");
		        alert.setContentText("ACCOUNT CREATED SUCCESSFULLY!");

		        alert.showAndWait();

			
			}else {
				
				message.setText("ERROR: PLEASE CHECK PASSWORD");
				
		
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	public void closeStage() {
		stage = (Stage)(btnlogin.getScene().getWindow());
		stage.close();
	}
	
	public void login() throws IOException{
		closeStage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/login.fxml"));
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
	
	
	

}
