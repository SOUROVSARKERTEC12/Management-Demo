package application.view;

import java.sql.*;

public class MyAppDatabaseConnection
{
	public static Connection LoginConnector()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			Connection conn=DriverManager.getConnection("jdbc:sqlite:G:\\Demo\\src\\Database\\MyAppDB.sqlite");
			return conn;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
}
