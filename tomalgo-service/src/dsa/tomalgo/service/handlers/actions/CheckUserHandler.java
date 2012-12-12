package dsa.tomalgo.service.handlers.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsa.tomalgo.model.JSONResult;
import dsa.tomalgo.service.handlers.Handler;
import dsa.tomalgo.service.handlers.HandlerException;
import dsa.tomalgo.service.servlets.ServletMethod;

public class CheckUserHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		// Getting parameters
		String username = (String) request.getParameter("username");
		if(username == null)
			throw new HandlerException(401, "Missing parameter in " + this.getClass().getSimpleName());
		
		// Getting the session of the user
		
		String UserSession = (String) request.getSession().getAttribute(username);
		
		// Sending JSON result
		JSONResult result;
		if(UserSession == null) 
			result = new JSONResult("OK", "false");	
		else if(UserSession.equals("Connected"))
			result = new JSONResult("OK", "true");
		else
			result = new JSONResult("OK", "false");
		
		ServletMethod.sendResult(result, request, response);
	}

}
