package dsa.tomalgo.service.handlers.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsa.tomalgo.service.ServletMethod;
import dsa.tomalgo.service.handlers.Handler;
import dsa.tomalgo.service.handlers.HandlerException;

public class SendEventHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		Connection connection = null;
		Statement statement = null;	
		
		// Getting parameters
		String username = (String) request.getSession().getAttribute("username");
		String text = request.getParameter("text");
		String inidate = request.getParameter("inidate");
		String enddate = request.getParameter("enddate");
		String isevent = request.getParameter("isevent");
		if(username == null || text == null || inidate == null || enddate == null || isevent == null)
			throw new HandlerException(400, "Missing parameter in " + this.getClass().getSimpleName());
				
		try {
			connection = dataSource.getConnection();			
			statement = connection.createStatement();
			statement.execute( "insert into event(enterprise,text,inidate,enddate,promo) " +
					"select user.id,'"+ text + "','" + inidate + "','" + enddate + "'," + isevent +
					" from user where user.username='" + username + "';");						
		} catch (SQLException e1) {			
			throw new HandlerException(400, "Database error: " + e1.getMessage());
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
		ServletMethod.sendResult("\"Succeed\"", request, response);
	}
	
}
