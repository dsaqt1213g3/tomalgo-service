package dsa.tomalgo.service.handlers;

public class HandlerInfo {
	private String name;
	private String handlerClass;
	private boolean auth;

	public HandlerInfo(String name, String handlerClass, boolean auth) {
		super();
		this.name = name;
		this.handlerClass = handlerClass;
		this.auth = auth;
	}

	public String getName() {
		return name;
	}

	public String getHandlerClass() {
		return handlerClass;
	}

	public boolean isAuth() {
		return auth;
	}
	
	@Override
	public String toString() {
		return "HandlerInfo [name=" + name + ", handlerClass=" + handlerClass
				+ ", auth=" + auth + "]";
	}
}
