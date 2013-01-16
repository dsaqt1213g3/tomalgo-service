package dsa.tomalgo.model;

public enum AccountProfile {
	None,
	Client,
	Enterprise;
	
	public static AccountProfile fromString(String acountProfile) {
		switch (acountProfile) {
		case "Client":
			return Client;
		case "Enterprise":
			return Enterprise;
		default:
			return None;
		}
	}
}