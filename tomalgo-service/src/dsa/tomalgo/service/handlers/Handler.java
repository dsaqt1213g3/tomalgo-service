package dsa.tomalgo.service.handlers;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public abstract class Handler {
	protected DataSource dataSource = null;

	public Handler() {
		super();
		try {
			Context initContext = new InitialContext();
			Context context = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) context.lookup("jdbc/TomalgoDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public abstract void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException ;
}
