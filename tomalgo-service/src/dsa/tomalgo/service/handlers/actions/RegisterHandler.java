package dsa.tomalgo.service.handlers.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsa.tomalgo.service.ServletMethod;
import dsa.tomalgo.service.handlers.Handler;
import dsa.tomalgo.service.handlers.HandlerException;

public class RegisterHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		// Getting parameters
		String enterprise = request.getParameter("enterprise");
		if(enterprise == null)
			throw new HandlerException(400, "Missing parameter in " + this.getClass().getSimpleName());
		String result;
		if(enterprise.equals("1"))
			result = registerEnterprise(response, request);
		else
			result = registerUser(response, request);
		// Sending JSON result
		ServletMethod.sendResult(result, request, response);
	}

	private String registerUser(HttpServletResponse response, HttpServletRequest request) throws HandlerException { 
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String mail = request.getParameter("mail");
		String birth = request.getParameter("birth");

		if(username == null || password == null	 || mail == null || birth == null) 
			throw new HandlerException(400, "Missing parameter in " + this.getClass().getSimpleName());

		// Saving user into database
		String result;
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();

			statement.execute("INSERT INTO user VALUES(" +
					"null,'" + username + "',0x" + password + ",'" +
					mail + "', null,'" + birth + "','0','1');");			

			result = "\"Added into database.\"";	
		} catch (SQLException e) {
			throw new HandlerException(401, "Database error: Cant add the new entry.");
		}

		return result;
	}

	private String registerEnterprise(HttpServletResponse response, HttpServletRequest request) throws HandlerException { 
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String mail = request.getParameter("mail");
		String street = request.getParameter("street");
		if(username == null || password == null	 || mail == null || street == null ) 
			throw new HandlerException(400, "Missing parameter in " + this.getClass().getSimpleName());

		// Saving user into database
		String result;
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();

			statement.execute("INSERT INTO user VALUES(" +
					"null,'" + username + "',0x" + password + ",'" +
					mail + "','" + street +"',null,'1','0');");

			result = "\"Added into database.\"";		

		} catch (SQLException e) {
			throw new HandlerException(401, "Database error: " + e.getMessage());
		}
		return result;
	}


}
