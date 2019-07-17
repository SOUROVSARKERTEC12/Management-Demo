package application.view;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomeController implements Initializable
{
	Connection connection=MyAppDatabaseConnection.LoginConnector();
	ObservableList<User> data=FXCollections.observableArrayList();
	FilteredList<User> filteredData=new FilteredList<>(data,e->true);
	
	PreparedStatement preparedStatement=null;
	ResultSet rs=null;
	
	@FXML
	private Button signOut;
	
	@FXML
	TableView<User> table;
	
	@FXML
	private TableColumn<?, ?> nameCol;

	@FXML
	private TableColumn<?, ?> usernameCol;

	@FXML
	private TableColumn<?, ?> passwordCol;
	
	@FXML
	Button addnewBtn;
	
	@FXML Button updateBtn;
	
	@FXML Button deleteBtn;
	
	@FXML TextField searchBox;
	
	@FXML TextField usernameBox;
	
	@FXML TextField nameBox;
	
	@FXML TextField passwordBox;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
		usernameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
		passwordCol.setCellValueFactory(new PropertyValueFactory<>("Password"));
		loadDatabaseData();
	}
	
	public void loadDatabaseData()
	{
		String query="select * from UserInfo";
		
		try
		{
			nameBox.clear();
			usernameBox.clear();
			passwordBox.clear();		
			data.clear();

			preparedStatement=connection.prepareStatement(query);
			rs=preparedStatement.executeQuery();
			
			while(rs.next())
			{
				data.add(new User(
						rs.getString("Name"),
						rs.getString("Username"),
						rs.getString("Password")
						));
				table.setItems(data);
			}
			preparedStatement.close();
			rs.close();
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	
	@FXML
	public void AddNewUser() throws SQLException
	{
		String name=nameBox.getText();
		String user=usernameBox.getText();
		String pass=passwordBox.getText();

		String query="INSERT INTO UserInfo (Name, Username, Password) VALUES(?,?,?)";
		
		preparedStatement=null;
		
		try
		{
			preparedStatement=connection.prepareStatement(query);
			
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, user);
			preparedStatement.setString(3, pass);
			
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			preparedStatement.execute();
			preparedStatement.close();
		}
		
		loadDatabaseData();
		Main.showInformationAlertBox("New User '"+name+"' Added Successfully!");
	}

	static String tempUsername;
	@FXML
	public void showOnClick()
	{
		try
		{
			User user=(User)table.getSelectionModel().getSelectedItem();
			String query="select * from UserInfo";
			preparedStatement=connection.prepareStatement(query);
			
			tempUsername=user.getUsername();
			nameBox.setText(user.getName());
			usernameBox.setText(user.getUsername());
			passwordBox.setText(user.getPassword());
			
			preparedStatement.close();
			rs.close();
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@FXML
	public void deleteUser()
	{
		String name = null;
		try
		{
			User user=(User)table.getSelectionModel().getSelectedItem();
			String query="delete from UserInfo where Username=?";
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, user.getUsername());
			name=user.getName();
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			rs.close();
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
		loadDatabaseData();
		Main.showInformationAlertBox("User '"+name+"' Deleted Successfully!");

	}
	
	@FXML
	public void UpdateUser()
	{
		String query="update UserInfo set Name=?, Username=?,Password=? where Username='"+tempUsername+"'";

		try
		{
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1, nameBox.getText());
			preparedStatement.setString(2, usernameBox.getText());
			preparedStatement.setString(3, passwordBox.getText());
			preparedStatement.execute();
			preparedStatement.close();
			Main.showInformationAlertBox("User '"+nameBox.getText()+"' Updated Successfully!");	
			loadDatabaseData();
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@FXML
	public void searchUser()
	{
		searchBox.textProperty().addListener((observableValue,oldValue,newValue)->{
			filteredData.setPredicate((Predicate<? super User>)user->{
				if(newValue==null||newValue.isEmpty()){
					return true;
				}
				String lowerCaseFilter=newValue.toLowerCase();
				if(user.getName().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(user.getUsername().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				return false;
			});
		});
		SortedList<User> sortedData=new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(table.comparatorProperty());
		table.setItems(sortedData);
	}

	
	@FXML
	void goSignOut() throws IOException
	{
		Main.showLogin();
	}

}
