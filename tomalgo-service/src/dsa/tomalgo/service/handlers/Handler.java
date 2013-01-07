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
		String mail;
					
		public ConfirmPasswordResult(Boolean succeed, Boolean enterprise,
				String message, String mail) {
			super();
			this.succeed = succeed;
			this.enterprise = enterprise;
			this.message = message;
			this.mail = mail;
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
		
		public String getMail() {
			return mail;
		}

		@Override
		public String toString() {
			return "ConfirmPasswordResult [succeed=" + succeed
					+ ", enterprise=" + enterprise + ", message=" + message
					+ ", mail=" + mail + "]";
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
	
	protected ConfirmPasswordResult confirmPassword(String username, String password, Connection connection, Statement statement) throws HandlerException, SQLException {
		// Asking database
		ConfirmPasswordResult result;
		ResultSet resultSet = null;
		
		connection = dataSource.getConnection();
		statement = connection.createStatement();
		resultSet = statement.executeQuery("select password,mail,enterprise,enable from user " +
				"where username='" + username + "';");
	
		if(resultSet.next()){
			if(resultSet.getBoolean("enable")) {
				if(Util.toHexString(resultSet.getBytes("password")).equals(password)) {						
					result = new ConfirmPasswordResult(true, resultSet.getBoolean("enterprise"), null, resultSet.getString("mail"));
				} else 						
					result = new ConfirmPasswordResult(false, null, "Incorrect password.", null);
			} else 			
				result = new ConfirmPasswordResult(false, null, "The account is not activated.", null);

		} else 
			result = new ConfirmPasswordResult(false, null, "The account doesn't exist.", null);
			
		return result;
	}
	
	protected Boolean isEnterprise(String username, Connection connection , Statement statement) throws SQLException, HandlerException {
		boolean enterprise;	
		
		ResultSet resultSet = statement.executeQuery("select enterprise from user " +
				"where username='" + username + "';");		
		if(resultSet.next())
			enterprise = resultSet.getBoolean("enterprise");
		else 
			throw new HandlerException(401, "Auth needed.");	
		
		resultSet.close();
		
		return enterprise;		
	}
}
