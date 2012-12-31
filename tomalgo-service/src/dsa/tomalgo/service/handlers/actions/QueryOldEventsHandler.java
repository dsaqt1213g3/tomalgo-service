package dsa.tomalgo.service.handlers.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsa.tomalgo.model.Event;
import dsa.tomalgo.service.ServletMethod;
import dsa.tomalgo.service.handlers.Handler;
import dsa.tomalgo.service.handlers.HandlerException;

public class QueryOldEventsHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		Connection connection = null;
		Statement statement = null;
		
		// Getting parameters
		String username = (String) request.getSession().getAttribute("username");
		String password = request.getParameter("password");
		if(username == null || password == null)
			throw new HandlerException(400, "Missing parameter in " + this.getClass().getSimpleName());
		
		ConfirmPasswordResult confirmPassResult;		
		try {
			connection = dataSource.getConnection();			
			statement = connection.createStatement();
			
			confirmPassResult = confirmPassword(username, password, connection, statement);
			if(!confirmPassResult.getSucceed())
				throw new HandlerException(401, "Auth needed.");
						
			String result = Event.toJSONVector(Event.fromDB(statement.executeQuery(
						"SELECT event.id,user.username,event.text,event.inidate,event.enddate,event.promo " +
								"FROM user,rl_tag,event " +
								"WHERE user.id=rl_tag.user AND user.id=event.enterprise AND user.enterprise=true " +
								"AND now() < event.enddate AND rl_tag.tag IN ( " +
									"SELECT tag " +
									"FROM user,rl_tag " +
									"WHERE rl_tag.user=user.id AND user.username='" + username + "'" +
								");")));			

			// Sending JSON result
			ServletMethod.sendResult(result, request, response);
			
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
	}
	
}
