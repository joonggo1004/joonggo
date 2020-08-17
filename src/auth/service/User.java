package auth.service;

public class User {
	
	private String id;
	private String name;
	private boolean emailChecked;
	
	public User(String id, String name, boolean emailChecked) {
		this.id = id;
		this.name = name;
		this.emailChecked = emailChecked;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public boolean isEmailChecked() {
		return emailChecked;
	}	

}
