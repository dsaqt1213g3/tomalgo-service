package dsa.tomalgo.service.handlers.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsa.tomalgo.service.ServletMethod;
import dsa.tomalgo.service.handlers.Handler;
import dsa.tomalgo.service.handlers.HandlerException;

public class CheckUsernameHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;	
		
		// Getting parameters
		String username = (String) request.getParameter("username");
		if(username == null)
			throw new HandlerException(400, "Missing parameter in " + this.getClass().getSimpleName());
		
		// Asking database
		String result;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select username from user " +
					"where username='" + username + "';");
			
			result = Boolean.toString(resultSet.next());
			
		} catch (SQLException e) {
			throw new HandlerException(400, "Database error: " + e.getMessage());
		} finally {
			try {
				if(resultSet != null)
					resultSet.close();
				
				if(statement != null)
					statement.close();
				
				if(connection != null)
					connection.close();						
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		ServletMethod.sendResult(result, request, response);
	}

}
