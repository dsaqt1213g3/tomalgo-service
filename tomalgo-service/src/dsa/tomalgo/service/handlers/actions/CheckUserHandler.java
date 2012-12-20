package dsa.tomalgo.service.handlers.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsa.tomalgo.service.ServletMethod;
import dsa.tomalgo.service.handlers.Handler;
import dsa.tomalgo.service.handlers.HandlerException;

public class CheckUserHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {
		// Getting parameters
		String username = (String) request.getParameter("username");
		if(username == null)
			throw new HandlerException(401, "Missing parameter in " + this.getClass().getSimpleName());
		
		// Getting the session of the user		
		String lastUsername = (String) request.getSession().getAttribute("username");
		
		// Sending JSON result
		ServletMethod.sendResult(Boolean.toString(username.equals(lastUsername)), request, response);
	}

}
