package application;

public class ValidationController {
	static String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
								"[a-zA-Z0-9_+&*-]+)*@" + 
								"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
								"A-Z]{2,7}$"; 
	
	static String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";  // at least one numeric, lowercase, uppercase character
	static String phoneNumberRegex = "^(^\\+62\\s?|^0)(\\d{3,4}-?){2}\\d{3,4}$";
	static String nameRegex = "^[A-Za-z]+([\\ A-Za-z]+)*";		// only alphabetic characters
	
	public static String validateEmail() {
		boolean isWrong = false;
		String email;
		
		do {
			if(isWrong == true) {
				System.out.println("Enter a valid email address!");
				App.sc.nextLine();
			}
			
			System.out.print("Email: ");
			email = App.sc.nextLine();
			
			if(!email.matches(emailRegex))
				isWrong = true;
			else
				isWrong = false;
			
		} while (isWrong == true);
	
		return email;
	}

//	public static String validateUsername() {
//		boolean isWrong = false;
//		String username;
//		
//		do {
//			if(isWrong == true) {
//				System.out.println("Enter a valid username!");
//				App.sc.nextLine();
//			}
//			
//			System.out.print("Username [5 - 15 characters]: ");
//			username = App.sc.nextLine();
//			
//			if(username.length() < 5 || username.length() > 15)
//				isWrong = true;
//			else
//				isWrong = false;
//			
//		} while (isWrong == true);
//		
//		return username;
//	}
	
	public static String validatePassword() {
		boolean isWrong = false;
		String password;
		
		do {
			if(isWrong == true) {
				System.out.println("Your password must at least one numeric, lowercase, and uppercase character!");
				App.sc.nextLine();
			}
			
			System.out.print("Password [minimum 8 characters] [at least one numeric, lowercase, uppercase character]: ");
			password = App.sc.nextLine();
			
			if(password.length() < 8 || !password.matches(passwordRegex))
				isWrong = true;
			else
				isWrong = false;
			
		} while (isWrong == true);
		
		return password;
	}
	
	
	public static String validateName() {
		boolean isWrong = false;
		String name;
		
		do {
			if(isWrong == true) {
				System.out.println("Enter a valid name!");
				App.sc.nextLine();
			}
			
			System.out.print("Name [7 - 25 characters]: ");
			name = App.sc.nextLine();
			
			if(name.length() < 7 || name.length() > 25 || !name.matches(nameRegex))
				isWrong = true;
			else
				isWrong = false;
			
		} while (isWrong == true);
		
		return name;
	}
	
	public static String validatePhoneNumber() {
		boolean isWrong = false;
		String phoneNumber;
		
		do {
			if(isWrong == true) {
				System.out.println("Enter a valid phone number!");
				App.sc.nextLine();
			}
			
			System.out.print("Phone Number [start with 0 or +62]: ");
			phoneNumber = App.sc.nextLine();
			
			if(!phoneNumber.matches(phoneNumberRegex))
				isWrong = true;
			else
				isWrong = false;
			
		} while (isWrong == true);
		
		return phoneNumber;
	}
	
	public static String validateAddress() {
		boolean isWrong = false;
		String address;
		
		do {
			if(isWrong == true) {
				System.out.println("Enter a valid address!");
				App.sc.nextLine();
			}
			
			System.out.print("Address [10 - 100 characters]: ");
			address = App.sc.nextLine();
			
			if(address.length() < 10 || address.length() > 100)
				isWrong = true;
			else
				isWrong = false;
			
		} while (isWrong == true);
		
		return address;
	}
}


