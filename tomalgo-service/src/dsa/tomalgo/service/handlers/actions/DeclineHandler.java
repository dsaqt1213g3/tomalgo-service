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
		// Getting parameters
		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
		if(username == null || password == null) 
			throw new HandlerException(400, "Missing parameter in " + this.getClass().getSimpleName());
		
		// Asking database
		int updates;
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
						
			updates = statement.executeUpdate("update user set enable=0 " +
					"where username='" + username + "' and password=0x" + password + ";");		
				
		} catch (SQLException e) {
			throw new HandlerException(400, "Database error: " + e.getMessage());
		}
			
		// Sending JSON result	
		ServletMethod.sendResult(Boolean.toString(updates == 1), request, response);
	}
	
}
