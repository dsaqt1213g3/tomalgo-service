package dsa.tomalgo.service.handlers.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsa.tomalgo.model.JSONResult;
import dsa.tomalgo.service.handlers.Handler;
import dsa.tomalgo.service.handlers.HandlerException;
import dsa.tomalgo.service.servlets.ServletMethod;
import dsa.util.Util;

public class LoginHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		// Getting parameters
		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
		if(username == null || password == null) 
			throw new HandlerException(401, "Missing parameter in " + this.getClass().getSimpleName());
		
		// Asking database
		JSONResult result;
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select password from user " +
					"where username='" + username + "';");
			
			if(resultSet.next() && Util.toHexString(resultSet.getBytes("password")).equals(password)){	
				request.getSession().setAttribute(username, "Connected");				
				result = new JSONResult("OK", "{LoginOK}");
			} else {
				result = new JSONResult("OK", "{LoginKO}");
			}
		} catch (SQLException e) {
			throw new HandlerException(401, "Database error");
		}
		
		// Sending JSON result
		ServletMethod.sendResult(result, request, response);
	}

}
