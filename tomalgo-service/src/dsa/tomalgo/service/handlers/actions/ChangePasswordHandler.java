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

public class ChangePasswordHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		// Getting parameters
		String username = (String) request.getParameter("username");
		String oldpassword = (String) request.getParameter("oldpassword");
		String newpassword = (String) request.getParameter("newpassword");
			
		if(username == null || oldpassword == null || newpassword == null)
			throw new HandlerException(401, "Missing parameter in " + this.getClass().getSimpleName());
		
		// Checking if username equals username of httpsession
		String sessionUsername = (String) request.getSession().getAttribute("username");
		
		if(!sessionUsername.equals(username)) {
			ServletMethod.sendResult("false", request, response);	
			return;
		}
		
		// Checking oldpassword equals newpassword
		Connection connection;
		Statement statement;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select password from user " +
					"where username='" + username + "';");
			
			if(resultSet.next()){
					if(!Util.toHexString(resultSet.getBytes("password")).equals(oldpassword)) {					
						ServletMethod.sendResult("false", request, response);
						return;
					}
					
					// Adding new password to database
					statement.execute("update user set password=0x" + newpassword + 
							" where username='" + username + "';");
					
					ServletMethod.sendResult("true", request, response);
			} else 
				ServletMethod.sendResult("false", request, response);
			
		} catch (SQLException e) {
			throw new HandlerException(401, "Database error: Can't operate with the database.");
		}
	}

}
