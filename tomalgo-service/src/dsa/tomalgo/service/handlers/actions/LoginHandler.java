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
import dsa.util.Util;

public class LoginHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;	
		
		// Getting parameters
		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
		if(username == null || password == null) 
			throw new HandlerException(400, "Missing parameter in " + this.getClass().getSimpleName());
		
		// Asking database
		String result;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select password,enterprise,enable from user " +
					"where username='" + username + "';");
			
			if(resultSet.next()){
				if(resultSet.getBoolean("enable")) {
					if(Util.toHexString(resultSet.getBytes("password")).equals(password)) {					
						request.getSession().setAttribute("username", username);				
						result = "{\"succeed\":true,\"enterprise\":" + resultSet.getBoolean("enterprise") + "}";
					} else 						
						result = "{\"succeed\":false,\"message\":\"Incorrect password.\"}";
				} else 			
					result = "{\"succeed\":false,\"message\":\"The account is not activated.\"}";

			} else 
				result = "{\"succeed\":false,\"message\":\"The account doesn't exist.\"}";
			
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
		
		// Sending JSON result
		ServletMethod.sendResult(result, request, response);
	}

}
