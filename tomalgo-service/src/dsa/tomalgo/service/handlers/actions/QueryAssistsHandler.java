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

public class QueryAssistsHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		// Getting parameters
		String username = (String) request.getSession().getAttribute("username");
		String password = (String) request.getParameter("password");
		String event = (String) request.getParameter("event");
		if(username == null || password == null || event == null) 
			throw new HandlerException(400, "Missing parameter in " + this.getClass().getSimpleName());
		
		// Asking database
		ConfirmPasswordResult confirmPassResult;
		int assists = 0;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			confirmPassResult = confirmPassword(username, password, connection, statement);
			if(!confirmPassResult.getSucceed())
				throw new HandlerException(401, "Auth needed.");
			
			resultSet = statement.executeQuery("select id from rl_event where event='" + event + "';");
			
			resultSet.last();
			assists = resultSet.getRow();
			
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
		ServletMethod.sendResult(Integer.toString(assists), request, response);
	}
	
}
