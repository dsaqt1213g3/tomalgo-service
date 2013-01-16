package dsa.tomalgo.service.handlers.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsa.tomalgo.service.ServletMethod;
import dsa.tomalgo.service.handlers.Handler;
import dsa.tomalgo.service.handlers.HandlerException;

public class DeclineHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		Connection connection = null;
		Statement statement = null;
		
		// Getting parameters
		String username = (String) request.getSession().getAttribute("username");
		String password = (String) request.getParameter("password");
		if(username == null || password == null) 
			throw new HandlerException(400, "Missing parameter in " + this.getClass().getSimpleName());
		
		// Asking database
		int updates;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
						
			updates = statement.executeUpdate("update user set enable=0 " +
					"where username='" + username + "' and password=0x" + password + ";");		
				
		} catch (SQLException e) {
			throw new HandlerException(400, "Database error: " + e.getMessage());
		} finally {
			try {
				if(statement != null)
					statement.close();
				
				if(connection != null)
					connection.close();						
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
		// Sending JSON result	
		if(updates == 1) {
			request.getSession().removeAttribute("username");
			ServletMethod.sendResult("true", request, response);
		}
		else 
			ServletMethod.sendResult("false", request, response);
	}
	
}
