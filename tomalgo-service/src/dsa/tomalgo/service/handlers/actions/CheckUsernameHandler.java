package dsa.tomalgo.service.handlers.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsa.tomalgo.model.JSONResult;
import dsa.tomalgo.service.ServletMethod;
import dsa.tomalgo.service.handlers.Handler;
import dsa.tomalgo.service.handlers.HandlerException;

public class CheckUsernameHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		// Getting parameters
		String username = (String) request.getParameter("username");
		if(username == null)
			throw new HandlerException(400, "Missing parameter in " + this.getClass().getSimpleName());
		
		// Asking database
		JSONResult result;
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select username from user " +
					"where username='" + username + "';");
			
			result = new JSONResult("OK", Boolean.toString(resultSet.next()));
			
		} catch (SQLException e) {
			throw new HandlerException(401, "Database error: " + e.getMessage());
		}
		
		ServletMethod.sendResult(result, request, response);
	}

}
