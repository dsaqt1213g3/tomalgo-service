package dsa.tomalgo.service.handlers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import dsa.util.Util;

public abstract class Handler {
	protected class ConfirmPasswordResult {
		Boolean succeed;
		Boolean enterprise;
		String message;
					
		public ConfirmPasswordResult(Boolean succeed, Boolean enterprise,
				String message) {
			super();
			this.succeed = succeed;
			this.enterprise = enterprise;
			this.message = message;
		}
		
		public Boolean getSucceed() {
			return succeed;
		}
		public Boolean getEnterprise() {
			return enterprise;
		}
		public String getMessage() {
			return message;
		}
		
		
	}
	
	
	protected DataSource dataSource = null;

	public Handler() {
		super();
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public abstract void process(HttpServletResponse response, HttpServletRequest request) throws HandlerException ;
	
	protected ConfirmPasswordResult confirmPassword(String username, String password, Connection connection, Statement statement) throws HandlerException {
		// Asking database
		ConfirmPasswordResult result;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select password,enterprise,enable from user " +
					"where username='" + username + "';");
			
			if(resultSet.next()){
				if(resultSet.getBoolean("enable")) {
					if(Util.toHexString(resultSet.getBytes("password")).equals(password)) {						
						result = new ConfirmPasswordResult(true, resultSet.getBoolean("enterprise"), null);
					} else 						
						result = new ConfirmPasswordResult(false, null, "Incorrect password.");
				} else 			
					result = new ConfirmPasswordResult(false, null, "The account is not activated.");

			} else 
				result = new ConfirmPasswordResult(false, null, "The account doesn't exist.");
			
		} catch (SQLException e) {
			throw new HandlerException(400, "Database error: " + e.getMessage());
		} finally {
			try {
				if(resultSet != null)
					resultSet.close();						
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
