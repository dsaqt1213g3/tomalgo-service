package dsa.tomalgo.service.handlers.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsa.tomalgo.model.JSONResult;
import dsa.tomalgo.service.ServletMethod;
import dsa.tomalgo.service.handlers.Handler;
import dsa.tomalgo.service.handlers.HandlerException;

public class LogoutHandler extends Handler {

	@Override
	public void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException {		
		// Getting the session of the user
		request.getSession().removeAttribute("username");
		
		// Sending JSON result		
		ServletMethod.sendResult(new JSONResult("OK", "Logout successful."), request, response);
	}

}