package dsa.tomalgo.service.handlers.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsa.tomalgo.model.JSONResult;
import dsa.tomalgo.service.handlers.Handler;
import dsa.tomalgo.service.handlers.HandlerException;
import dsa.tomalgo.service.servlets.ServletMethod;

public class RegisterHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		// Getting parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String mail = request.getParameter("mail");
		String birth = request.getParameter("birth");
		String enterprise = request.getParameter("enterprise");
		if(username == null || password == null	 || mail == null || enterprise == null) 
			throw new HandlerException(401, "Missing parameter in " + this.getClass().getSimpleName());
		
		// Saving user into database
		JSONResult result;
		try {
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			if(enterprise.equals("1"))
				statement.execute("INSERT INTO user VALUES(" +
						"null,'" + username + "',0x" + password + ",'" +
						mail + "',null,'1','0');");
			else
				statement.execute("INSERT INTO user VALUES(" +
						"null,'" + username + "',0x" + password + ",'" +
						mail + "','" + birth + "','0','1');");			
					
			result = new JSONResult("OK", "Added into database.");			
		} catch (SQLException e) {
			throw new HandlerException(401, "Database error: Cant add the new entry.");
		}
		
		// Sending JSON result
		ServletMethod.sendResult(result, request, response);
	}

}
