package dsa.tomalgo.service.handlers;

public class HandlerInfo {
	private String name;
	private String handlerClass;
	private boolean auth;
	private boolean enterprise;

	public HandlerInfo(String name, String handlerClass, boolean auth, boolean enterprise) {
		super();
		this.name = name;
		this.handlerClass = handlerClass;
		this.auth = auth;
		this.enterprise = enterprise;
	}

	public String getName() {
		return name;
	}

	public String getHandlerClass() {
		return handlerClass;
	}

	public boolean needAuth() {
		return auth;
	}
	
	public boolean needEnterprise() {
		return enterprise;
	}

	@Override
	public String toString() {
		return "HandlerInfo [name=" + name + ", handlerClass=" + handlerClass
				+ ", auth=" + auth + ", enterprise=" + enterprise + "]";
	}	
}
