package user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import application.App;

public class User {
	private String email;
	

	private String username; // auto generate
	private String password;
	private String name;
	private String phoneNumber;
	private String address;
	
	private int income;
	private int expenditure;

	
	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public int getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(int expenditure) {
		this.expenditure = expenditure;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User(String email, String password, String name, String phoneNumber, String address, int income, int expenditure) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.income = income;
		this.expenditure = expenditure;
		generateUsername();
		generateHashPassword();
	}
	
	// FILE PROCESSING
	public User(String email, String username, String password, String name, String phoneNumber, String address, int income, int expenditure) {
		super();
		this.email = email;
		this.username = username;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.income = income;
		this.expenditure = expenditure;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}
	
	private void generateHashPassword() {
		try {
			setPassword(hashPassword(this.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generateUsername() {
		String randomUsername;
		Random randomNumber = new Random();
		String randomChar = this.getName().toUpperCase().replaceAll("\\s+","").substring(0, 5);	// no white space, name index 0 - 4
		boolean isNewUsername = true;
		
		do {
			randomUsername = String.format("%s%03d", randomChar, randomNumber.nextInt(1000));
			
			// checking if there is same username in the list
			for (User user : App.userList) {
				if(user.getUsername().equals(randomUsername)) {
					isNewUsername = false;
					break;
				}
			}
			
		} while (isNewUsername == false);		
		
		setUsername(randomUsername);
	}
	
	public static String hashPassword(String password) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] b = md.digest();
		StringBuffer sb = new StringBuffer();
		for(byte b1 : b) {
			sb.append(Integer.toHexString(b1 & 0xff).toString());
		}
		return sb.toString();
	}
	
	public int validatePassword(String email, String password) {
		boolean emailValid = false;
		boolean passwordValid = false;
		String hashedPassword = null;
		int tempIndex = 0;
		for(int i = 0; i < App.userList.size(); i++) {
			if(email.equalsIgnoreCase(App.userList.get(i).getEmail())) {
				tempIndex = i;
				emailValid = true;
			}
		}
		
		if(emailValid == true) {
			try {
				hashedPassword = hashPassword(password);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
			if(hashedPassword.equals(App.userList.get(tempIndex).getPassword())) {
				passwordValid = true;
			}
		}
		
		if(emailValid == true && passwordValid == true)
			return tempIndex;
		else
			return -1;
	}
}
