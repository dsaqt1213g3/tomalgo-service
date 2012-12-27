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
		// Getting parameters
		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
		if(username == null || password == null) 
			throw new HandlerException(400, "Missing parameter in " + this.getClass().getSimpleName());
		
		// Asking database
		String result;
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select password,enterprise,enable from user " +
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
		}
		
		// Sending JSON result
		ServletMethod.sendResult(result, request, response);
	}

}
