package product.service;

import java.util.Map;

public class DeleteRequest {
	private String userId;
	private int productNumber;
	private String password;
	public DeleteRequest(String userId, int no,
			String password) {
		super();
		this.userId = userId;
		this.productNumber = no;
		this.password = password;
	}
	
	public int getProductNumber() {
		return productNumber;
	}
	public String getPassword() {
		return password;
	}
	public String getUserId() {
		return userId;
	}

	public void validate(Map<String, Boolean> errors) {

		if (password == null || password.isEmpty()) {
			errors.put("password", true);
		}
	}
	
	
}
