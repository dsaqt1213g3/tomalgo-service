package dsa.tomalgo.model;

import com.google.gson.Gson;

public class JSONResult {
	private String status;
	private String result;

	public JSONResult(String status, String result) {
		super();
		this.status = status;
		this.result = result;
	}
	
	public String getStatus() {
		return status;
	}
	public String getResult() {
		return result;
	}
	
	public String toJSON() {
		return new Gson().toJson(this);
	}

	public static JSONResult fromJSON(String json) {
		return new Gson().fromJson(json, JSONResult.class);
	}

	@Override
	public String toString() {
		return "JSONResult [status=" + status + ", result=" + result + "]";
	}	
}
