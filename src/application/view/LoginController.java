package application.view;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController
{
	LoginModel loginModel=new LoginModel();
	
    @FXML
	Button signInButton;
    
    @FXML
    TextField usernameBox;
    
    @FXML
    PasswordField passwordBox;
    
    @FXML
    Text errorReport;

	@FXML
	void goSignIn() throws IOException
	{
		try
		{
			if(loginModel.isLoginValid(usernameBox.getText(), passwordBox.getText())==true)
			{
				Main.showHome();
			}
			else
			{
				errorReport.setText("Error! Invalid Username or Password.");
			}
		} catch (SQLException e)
		{
			errorReport.setText("Error! Invalid Username or Password.");
			e.printStackTrace();
		}
	}
}
