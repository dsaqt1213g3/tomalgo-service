package dsa.tomalgo.service;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dsa.tomalgo.service.handlers.HandlerException;
import dsa.tomalgo.service.handlers.HandlerFactory;
import dsa.tomalgo.service.handlers.HandlerInfo;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter("/AuthFilter")
public class AuthFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		// Getting action parameter
		String action = (String) request.getParameter("action");
		if(action == null) {
			ServletMethod.sendError("Missing parameter.", 401,
					(HttpServletRequest) request, (HttpServletResponse) response);
			return;
		}
		
		// Getting handler info
		HandlerInfo hInfo;
		try {
			hInfo = HandlerFactory.newInstance().getInfo(action);
		} catch (HandlerException e) {
			ServletMethod.sendError(e.getMessage(), e.getCode(), 
					(HttpServletRequest) request, (HttpServletResponse) response);
			return;
		}
		
		// If auth needed, checking httpSessions
		if(hInfo.isAuth() && 
				((HttpServletRequest) request).getSession().getAttribute("username") == null) {
			ServletMethod.sendError("Auth needed.", 401,
					(HttpServletRequest) request, (HttpServletResponse) response);
			return;
		}
		
		System.out.println("filtro pasado");
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	
	}

}
