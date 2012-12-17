package dsa.tomalgo.service.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public abstract class Handler {
	protected DataSource dataSource = null;

	public Handler() {
		super();
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public abstract void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException ;
}
