package member.model;

import java.util.Date;

public class Member {
	
	private String id;
	private String password;
	private String name;
	private String phone;
	private String email;
	private Date regDate;
	
	public Member(String id, String password, String name, String phone, String email, Date regDate) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.regDate = regDate;
	}
	
	/*
	@Override
	public String toString() {
		return "Member [id=" + id + ", password=" + password + ", name=" + name + ", phone=" + phone + ", email=" + email + ", regDate=" + regDate + "]";
	}
	*/
	
	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}
	
	public String getEmail() {
		return email;
	}

	public Date getRegDate() {
		return regDate;
	}

	public boolean matchPassword(String pwd) {
		return password.contentEquals(pwd);
	}
	
	public void changePassword(String newPwd) {
		this.password = newPwd;
	}

}
