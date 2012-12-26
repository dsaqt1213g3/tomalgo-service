package dsa.tomalgo.service;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsa.tomalgo.model.JSONResult;

public class ServletMethod {
	
	public static void sendError(String error, int code, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setAttribute("result", new JSONResult("KO", "\"" +  code + "- " + error + "\"").toJSON());
			response.sendError(code);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendResult(JSONResult result, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("result", result.toJSON());
		RequestDispatcher rd = request.getRequestDispatcher("/result.jsp");		
		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}