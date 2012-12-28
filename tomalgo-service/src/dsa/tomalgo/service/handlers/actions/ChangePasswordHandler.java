package dsa.tomalgo.service.handlers.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsa.tomalgo.service.ServletMethod;
import dsa.tomalgo.service.handlers.Handler;
import dsa.tomalgo.service.handlers.HandlerException;

public class ChangePasswordHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		Connection connection = null;
		Statement statement = null;
		
		// Getting parameters
		String username = (String) request.getSession().getAttribute("username");
		String oldpassword = (String) request.getParameter("oldpassword");
		String newpassword = (String) request.getParameter("newpassword");
			
		if(username == null || oldpassword == null || newpassword == null)
			throw new HandlerException(401, "Missing parameter in " + this.getClass().getSimpleName());
		
		// Checking oldpassword equals newpassword
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
							
			// Adding new password to database
			int updates = statement.executeUpdate("update user set password=0x" + newpassword + 
					" where username='" + username + "' and password=0x" + oldpassword + ";");
					
			ServletMethod.sendResult(Boolean.toString(updates == 1), request, response);			
		} catch (SQLException e) {
			throw new HandlerException(400, "Database error: " + e.getMessage());
		}  finally {
			try {
				if(statement != null)
					statement.close();
				
				if(connection != null)
					connection.close();						
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
