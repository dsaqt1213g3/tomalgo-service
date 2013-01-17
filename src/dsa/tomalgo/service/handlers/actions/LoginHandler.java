package dsa.tomalgo.service.handlers.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsa.tomalgo.service.ServletMethod;
import dsa.tomalgo.service.handlers.Handler;
import dsa.tomalgo.service.handlers.HandlerException;

public class LoginHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		Connection connection = null;
		Statement statement = null;
		
		// Getting parameters
		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
		if(username == null || password == null) 
			throw new HandlerException(400, "Missing parameter in " + this.getClass().getSimpleName());
		
		// Asking database
		ConfirmPasswordResult confirmPassResult;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			confirmPassResult = confirmPassword(username, password, connection, statement);			
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
		String result;
		if(confirmPassResult.getSucceed()) {
			request.getSession().setAttribute("username", username);
			result = "{\"succeed\":true,\"enterprise\":"+ confirmPassResult.getEnterprise() +",\"mail\":\"" +confirmPassResult.getMail()+"\"}";
		}
		else
			result = "{\"succeed\":false,\"message\":\""+ confirmPassResult.getMessage() +"\"}";
		ServletMethod.sendResult(result, request, response);
	}

}
