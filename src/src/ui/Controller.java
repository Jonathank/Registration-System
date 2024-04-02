package ui;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import database.DatabaseConnection;
import database.DatabaseOps;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Controller implements Initializable{
	
	@FXML
	private Button btnsave,btnexit,btnupdate,btndelete,btnclear;
	@FXML
	private TextField txtid,txtfname,txtlname,txtage;
	@FXML
	private ComboBox<String>combogender;
	@FXML
	private TableView<Student>studenttable;
	@FXML
	private TableColumn<Student,String>CID;
	@FXML
	private TableColumn<Student,String>CFN;
	@FXML
	private TableColumn<Student,String>CLN;
	@FXML
	private TableColumn<Student,String>CG;
	@FXML
	private TableColumn<Student,String>CAGE;
	

    Connection conn = DatabaseConnection.getConnection();
    PreparedStatement pstmt = null;
    Statement stmt = null;
    int myIndex;
    int age;
    
    ObservableList<Student> students = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      combogender.setItems(FXCollections.observableArrayList("MALE","FEMALE"));
     
        table();
    }   
    
    private static void showalert(AlertType alerttype,Window owner,String title,String message) {
		Alert alert = new Alert(alerttype);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}
	
	@FXML
    public void save(ActionEvent event) {
		if(txtid.getText().isEmpty()) {
			showalert(AlertType.ERROR,((Stage)txtid.getScene().getWindow()),"ERROR","PLEASE ENTER STUDENT ID NUMBER!");
			return;
		}
		
		if(txtfname.getText().isEmpty()) {
			showalert(AlertType.ERROR,((Stage)txtfname.getScene().getWindow()),"ERROR","PLEASE ENTER STUDENT FIRST NAME!");
			return;
		}
		if(txtlname.getText().isEmpty()) {
			showalert(AlertType.ERROR,((Stage)txtlname.getScene().getWindow()),"ERROR","PLEASE ENTER STUDENT LAST NAME!");
			return;
		}
		if(txtage.getText().isEmpty()) {
			showalert(AlertType.ERROR,((Stage)txtage.getScene().getWindow()),"ERROR","PLEASE ENTER STUDENT AGE!");
			return;
		}
		if(combogender.getValue() == null) {
			showalert(AlertType.ERROR,((Stage)txtid.getScene().getWindow()),"ERROR","PLEASE ENTER STUDENT GENDER!");
			return;
		}
           Student std = new Student(txtid.getText(),txtfname.getText(),txtlname.getText(),combogender.getValue(),Integer.parseInt(txtage.getText()));
           if(DatabaseOps.ifIDexist(std.getStdid())) {
   			/**
   			 * if id exists it shows alert message of error, duplicate id
   			 */
   			showalert(AlertType.INFORMATION,((Stage)txtid.getScene().getWindow()),"CHECK","DUPLICATE ID");
   		}
           
        try 
        {
        	String SQL = "INSERT INTO STUDENTS(STD_ID,FIRSTNAME,LASTNAME,GENDER,AGE) VALUES(?,?,?,?,?)";
            pstmt = conn.prepareStatement(SQL);
            
            pstmt.setString(1, std.getStdid());
            pstmt.setString(2, std.getFname());
            pstmt.setString(3, std.getLname());
            pstmt.setString(4, std.getGender());
            pstmt.setInt(5, std.getAge());
            
            
            pstmt.executeUpdate();
          
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Student Registation");
        alert.setHeaderText("Student Registation");
        alert.setContentText("Record Added!");

        alert.showAndWait();

            table();
            
        } 
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    
    public void table() {
           studenttable.getItems().clear();
      try 
       {
    	   String SQl = "SELECT * FROM STUDENTS";
           pstmt = conn.prepareStatement(SQl); 
           ResultSet rs = pstmt.executeQuery();
      {
        while (rs.next()) {
        
            
			try {
				Student st = new Student(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5));
				
				st.setStdid(rs.getString(1));
				st.setFname(rs.getString(2));
				st.setLname(rs.getString(3));
				st.setGender(rs.getString(4));
				st.setAge(rs.getInt(5));
				
			
            students.add(st);
       
    }catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 
                studenttable.setItems(students);
                CID.setCellValueFactory(new PropertyValueFactory<>("stdid"));
                CFN.setCellValueFactory(new PropertyValueFactory<>("fname"));
                CLN.setCellValueFactory(new PropertyValueFactory<>("lname"));
                CG.setCellValueFactory(new PropertyValueFactory<>("gender"));
                CAGE.setCellValueFactory(new PropertyValueFactory<>("age"));
       
      }
        }
                studenttable.setRowFactory(tv -> {
                	
             TableRow<Student> myRow = new TableRow<>();
             
             myRow.setOnMouseClicked (event ->   {
           
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                
                    myIndex =  studenttable.getSelectionModel().getSelectedIndex();
         
                  
                   txtid.setText(studenttable.getItems().get(myIndex).getStdid());
                   txtfname.setText(studenttable.getItems().get(myIndex).getFname());
                   txtlname.setText(studenttable.getItems().get(myIndex).getLname());
                   combogender.setValue(studenttable.getItems().get(myIndex).getGender());
                  txtage.setText(String.valueOf(studenttable.getItems().get(myIndex).getAge()));          
                     
                           
                }
             });
                return myRow;
                   });
    
       }catch (Exception e) {
   		// TODO Auto-generated catch block
   		e.printStackTrace();
   	}
      }

  //clear method
  	 @FXML
  	public void clear(ActionEvent event) {
  		txtid.setText("");
  		txtfname.setText("");
  		txtlname.setText("");
  		txtage.setText("");
  		combogender.setValue(null);
  	}
  	
  	//exit method
 	Stage stage;
 	public void exit(ActionEvent event) {
 		Alert alert = new Alert(AlertType.CONFIRMATION);
 		alert.setHeaderText("DO YOU WANT TO  EXIT BEFORE SAVING");
 		alert.setTitle("EXIT");
 		alert.setContentText("YOU ARE ABOUT TO EXIT!!! ");
 		
 		if(alert.showAndWait().get() == ButtonType.OK) {
 			stage = (Stage)btnexit.getScene().getWindow();
 			stage.close();
 		}
 	}
    
    @FXML
   public void Delete(ActionEvent event) {
    	
        myIndex = studenttable.getSelectionModel().getSelectedIndex();
           
       Student std = new Student(txtid.getText(),txtfname.getText(),txtlname.getText(),combogender.getValue(),Integer.parseInt(txtage.getText()));
        try 
        {
        	String SQL = "delete from students where std_id = ? ";
        	
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, std.getStdid());
            pstmt.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Student Registationn");
            alert.setHeaderText("Student Registation");
            alert.setContentText("Deletedd!");

            alert.showAndWait();
                  table();
        } 
        catch (SQLException ex)
        {
             ex.printStackTrace();
        }
    }

    @FXML
    public void Update(ActionEvent event) {
        
         myIndex = studenttable.getSelectionModel().getSelectedIndex();
         
           Student std = new Student(txtid.getText(),txtfname.getText(),txtlname.getText(),combogender.getValue(),Integer.parseInt(txtage.getText()));
        
        try 
        {
        	String SQL = "UPDATE students set firstname = ? ,lastname = ?,gender = ?,age = ? where std_id = ?";
            pstmt = conn.prepareStatement(SQL);
            
            pstmt.setString(1, std.getFname());
            pstmt.setString(2, std.getLname());
            pstmt.setString(3, std.getGender());
            pstmt.setInt(4, std.getAge());
            pstmt.setString(5,std.getStdid());
            
            pstmt.executeUpdate();
            
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Student Registationn");
        alert.setHeaderText("Student Registation");
        alert.setContentText("Updateddd!");

        alert.showAndWait();
                table();
        } 
        catch (SQLException ex)
        {
          ex.printStackTrace();
        }
    }

	
    
    
     
/**
 * note anyways
 * .getSelectedIndex()
 * Returns the integer value indicating the currently selected index in this model. If there are multiple items selected, this will return themost recent selection made. 

Note that the returned value is a snapshot in time - if you wish to observe the selection model for changes to the selected index, you canadd a ChangeListener as such: 

 SelectionModel sm = ...;
 InvalidationListener listener = ...;
 sm.selectedIndexProperty().addListener(listener);


 */

}
