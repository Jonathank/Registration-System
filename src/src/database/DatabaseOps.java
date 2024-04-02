package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;
import ui.Student;

public class DatabaseOps {
	static Connection conn = DatabaseConnection.getConnection();
	static PreparedStatement pstmt = null;
	private static final String query = "SELECT * FROM LOGIN WHERE username =? AND pass_word =?";
	
	static Parent root;
	static Stage stage;
	static Scene scene;
	
	
	
	public static boolean ifIDexist(String stdid) {
		try {
			
			String checkstmt = "SELECT COUNT(std_id) FROM students WHERE std_id = ?";
			pstmt = conn.prepareStatement(checkstmt);
			pstmt.setString(1, stdid);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int count = rs.getInt(1);
				return (count > 0);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validate(String username,String password) throws SQLException{
		try (
			
			 PreparedStatement pstmt = conn.prepareStatement(query)){
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				
				if((rs.getString(1).equals(username))&&(rs.getString(2).equals(password))) {
				 stage.getScene().getWindow();
				 stage.close();
					
					
			}else {
				
				showalert(AlertType.INFORMATION,stage.getScene().getWindow(),"WRONG PASSWORD OR USERNAME","CHECK CREDENTIALS");
			}
			}
		}catch(SQLException e) {
			e.printStackTrace();
			
		}
		return false;
	}
	
	private static void showalert(AlertType alerttype,Window owner,String title,String message) {
		Alert alert = new Alert(alerttype);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}
}
