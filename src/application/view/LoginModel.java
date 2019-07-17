package application.view;

import java.sql.*;

public class LoginModel
{
	Connection connection;
	public LoginModel()
	{
		connection=MyAppDatabaseConnection.LoginConnector();
		if(connection==null)
		{
			System.exit(1);
		}
	}
	
	public boolean isLoginValid(String user, String pass) throws SQLException
	{
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String query="select * from UserInfo where username=? and password=?";
		
		try
		{
			preparedStatement =connection.prepareStatement(query);
			preparedStatement.setString(1, user);
			preparedStatement.setString(2, pass);
			
			resultSet=preparedStatement.executeQuery();
			
			if(resultSet.next())
				return true;
			else
				return false;
		}
		catch(Exception e)
		{
			return false;
		}
		finally
		{
			preparedStatement.close();
			resultSet.close();
		}
	}	
}
