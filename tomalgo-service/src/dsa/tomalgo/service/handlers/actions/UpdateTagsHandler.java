package dsa.tomalgo.service.handlers.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dsa.tomalgo.service.ServletMethod;
import dsa.tomalgo.service.handlers.Handler;
import dsa.tomalgo.service.handlers.HandlerException;

public class UpdateTagsHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		Boolean enterprise;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;	
		
		// Getting parameters
		String username = (String) request.getSession().getAttribute("username");
		String tags = request.getParameter("tags");
		if(username == null || tags == null)
			throw new HandlerException(400, "Missing parameter in " + this.getClass().getSimpleName());
		
		try {
			connection = dataSource.getConnection();			
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select enterprise from user " +
					"where username='" + username + "';");
			if(resultSet.next())
				enterprise = resultSet.getBoolean("enterprise");
			else {
				ServletMethod.sendResult("false", request, response);
				return;
			}
				
		} catch (SQLException e1) {
			try {
				if(statement != null)
					statement.close();
				
				if(connection != null)
					connection.close();		
			} catch (SQLException e2) {	}
			throw new HandlerException(400, "Database error: " + e1.getMessage());
		} finally {
			try {								
				if(resultSet != null)
					resultSet.close();						
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		String[] tag = new Gson().fromJson(tags, String[].class);		
		if(enterprise && tag.length > 3) {
			ServletMethod.sendResult("false", request, response);
			return;
		}
		
		// Removing old tags
		try {			
			statement.execute("delete from rl_tag " +
         					  "where exists ( " +
         					  	 "select user.id from user " +
         					  	 "where user.username='" + username+ "' and user.id=rl_tag.user);");			
		} catch (SQLException e) {
			try {
				if(statement != null)
					statement.close();
				
				if(connection != null)
					connection.close();		
			} catch (SQLException e2) {	}
			throw new HandlerException(400, "Database error: Cant add the new entry.");
		}
				
			
		// Saving rl_tag into database
		try {			
			for( String t : tag)
				statement.execute("insert into rl_tag(user,tag) " +
						          "select user.id,tag.id " +
						          "from user,tag " +
						          "where user.username='" + username +"' and tag.name='" + t + "';");			
		} catch (SQLException e) {
			throw new HandlerException(400, "Database error: Cant add the new entry.");
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
		ServletMethod.sendResult("true", request, response);
	}
	
}
