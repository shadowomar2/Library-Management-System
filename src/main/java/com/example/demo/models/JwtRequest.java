package com.example.demo.models;

 
import java.io.Serializable;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	private String usernameoremail;
	private String password;
	
	//need default constructor for JSON Parsing
	public JwtRequest()
	{
		
	}

	public JwtRequest(String usernameoremail, String password) {
		this.setUsernameoremail(usernameoremail);
		this.setPassword(password);
	}

 
	public String getUsernameoremail() {
		return usernameoremail;
	}

	public void setUsernameoremail(String usernameoremail) {
		this.usernameoremail = usernameoremail;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}