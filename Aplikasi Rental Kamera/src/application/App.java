package application;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import order.Borrower;
import order.Provider;
import user.User;

public class App {
	public static Scanner sc = new Scanner(System.in);
	public static Random random = new Random();
	
	// Variables (App)
	String inputDate;
	String[] splitDate = new String[3];
	int todayDate;
	int todayMonth;
	int todayYear;
	boolean validChoice;
	String dateRegex = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";
	
	
	int choice;
	String choiceString;
	public static int loginIndex = -1;
	public static ArrayList<User> userList = new ArrayList<User>();
	
	boolean isLogin = false;
	boolean isExit = false;
	
	// Variables (Provider)
	int providerChoice;
	String cameraBrand;
	String cameraType;
	String cameraName;
	int cameraPrice;
	int lendPrice;	//per day
	int maxLendDuration;
	boolean isLended;
	String providerChoice2;
	String accessoriesName;
	boolean accessoriesAvailable;
	int accessoriesLendPrice;
	String cameraID;
	int newLendPrice;
	public static ArrayList<Provider> providerProductList = new ArrayList<>();
	
	// Variables (Borrower)
	int borrowerChoice;
	public int tempProvCameraIndex = -1;
	boolean isTrueDate = false;
	public static ArrayList<Borrower> borrowerProductList = new ArrayList<>();
	
	// temporary date
	int tempDate, tempMonth, tempYear;
	String inputTempDate;
	String[] splitTempDate = new String[3];
	
    // Return date
	int returnDate, returnMonth, returnYear;
	String inputreturnDate;
	String[] splitreturnDate = new String[3];
	
	int tempIndex = -1;
	
	public App() {
		validChoice = false;
		// Read file data
		FileController.readUserFile();
		FileController.readProviderProductFile();
		FileController.readBorrowerProductFile();
		
		do {
			// Creating header
			headerGenerator();
			System.out.println("1. Login");
			System.out.println("2. Create a New Account");
			System.out.println("3. Exit");
			System.out.print("Choose >> ");
			try {
				choice = sc.nextInt();
			} catch (Exception e) {
				
			}
			
			if(choice < 1 || choice > 3) {
				System.out.println("Please enter a valid menu!");
				sc.nextLine();
				validChoice = false;
			}
			else
				validChoice = true;
			
			sc.nextLine();
			
			switch (choice) {
			case 1:
				System.out.println("[LOG IN]");
				login();
				
				if(isLogin) {
					do {
						headerGenerator();
						welcomePage();
					} while (isLogin);
				}
				break;
	
			case 2:
				System.out.println("[SIGN UP]");
				signup();
				break;
			
			
			case 3:
				System.out.println("[EXIT APPLICATION]");
				exit();
				break;
			}
		
		} while (isExit == false);	
	}
	
	// MENU
	void signup() {
		String email;
		String password;
		String name;
		String phoneNumber;
		String address;
		boolean isRegistered = false;
	
		email = ValidationController.validateEmail();
		
		for (User user : userList) {				// checking user already registered or no
			if(user.getEmail().equalsIgnoreCase(email)) {	
				System.out.println("Email has already registered. Continue to login.");
				sc.nextLine();
				isRegistered = true;
				break;
			}
		}
		
		if(isRegistered == false) {
			password = ValidationController.validatePassword();
			name = ValidationController.validateName();
			phoneNumber = ValidationController.validatePhoneNumber();
			address = ValidationController.validateAddress();
			
			userList.add(new User(email, password, name, phoneNumber, address, 0, 0));
			
			// Replace file with the new data
			updateFile();
			
			System.out.println("You have registered, please login!");
			sc.nextLine();
		}
		
	}
	
	void login() {
		String email;
		String password;

		email = ValidationController.validateEmail();
		password = ValidationController.validatePassword();
			
		User user = new User();
		if(user.validatePassword(email, password) == -1) {
			isLogin = false;
			System.out.println("Invalid username or password !");
			sc.nextLine();
		}
		else {
			loginIndex = user.validatePassword(email, password);
			
			// PROCESSING LOGIN
			isLogin = true;
			processSign("You are logging in");
			
		}	
	}
	
	void exit() {
		do {
			System.out.print("Are you sure want to exit? [yes / no]: ");
			choiceString = sc.nextLine();
		} while (!choiceString.equalsIgnoreCase("yes") && !choiceString.equalsIgnoreCase("no"));
		
		if(choiceString.equalsIgnoreCase("yes")) {
			processSign("You are exiting");
			
			// Replace file with the new data
			updateFile();
			
			
			
			isExit = true;
		}
		else 
			isExit = false;
	}
	
	void welcomePage() {
		choice = 0;
		validChoice = false;
			
			System.out.format("Hello Mr/Mrs %s.\n\n", userList.get(loginIndex).getName());
			
			System.out.println("[MAIN MENU]");
			System.out.println("Please select the menus:");
			System.out.println("1. See my profile");
			System.out.println("2. Login as camera provider");
			System.out.println("3. Login as camera borrower");
			System.out.println("4. Log out");
			System.out.print("Choice >> ");
			
			try {
				choice = sc.nextInt();
			} catch (Exception e) {
				
			}
			
			if(choice < 1 || choice > 4) {
				System.out.println("Please enter a valid menu!");
				sc.nextLine();
				validChoice = false;
			}
			else
				validChoice = true;
			
			sc.nextLine();
			
			switch (choice) {
			case 1:
				System.out.println();
				headerGenerator();
				System.out.println("[MAIN MENU -> MY PROFILE]");
				System.out.println("Your Profile");
				System.out.println("============");
				System.out.println("Name: " + userList.get(loginIndex).getName());
				System.out.println("Email: " + userList.get(loginIndex).getEmail());
				System.out.println("Username: " + userList.get(loginIndex).getUsername());
				System.out.println("Phone Number: " + userList.get(loginIndex).getPhoneNumber());
				System.out.println("Address: " + userList.get(loginIndex).getAddress());
				System.out.println("============");
				System.out.println();
				System.out.println("My Finished Order Income     : " + convertCurrency(userList.get(loginIndex).getIncome()));
				System.out.println("My Finished Order Expenditure: " + convertCurrency(userList.get(loginIndex).getExpenditure()));
				System.out.println();
				System.out.println("Press enter to continue...");
				sc.nextLine();
				break;

			case 2:
				headerGenerator();
				cameraProviderMenu();
				
				break;
				
			case 3:
				headerGenerator();
				cameraBorrowerMenu();
				break;
				
			case 4:				// logout
				loginIndex = -1;
				isLogin = false;
				processSign("Logout");
				break;
			}	
	}
	
	void cameraBorrowerMenu() {
		String orderID;
		String cameraID;
		do {
			
			System.out.println();
			headerGenerator();
			System.out.println("[MAIN MENU -> CAMERA BORROWER PAGE]");
			System.out.println("Please select the menus:");
			System.out.println("1. Borrow Camera");
			System.out.println("2. View Borrowed Camera");
			System.out.println("3. Extend Borrowing Time");
			System.out.println("4. Return Camera");
			System.out.println("5. Back to Main Menu");
			System.out.printf(">> ");
			
			try {
				borrowerChoice = sc.nextInt();
			} catch (Exception e) {
				
			}
			
			if(borrowerChoice < 1 || borrowerChoice > 5) {
				System.out.println("Please enter a valid menu!");
				sc.nextLine();
			}
			
			sc.nextLine();
			
			switch (borrowerChoice) {
			case 1:
				headerGenerator();
				setCurrentDate();
				
				System.out.println("[MAIN MENU -> CAMERA BORROWER PAGE -> BORROW CAMERA]");
				displayAllCameraList();
				
				System.out.print("Enter Camera's ID: PRO/");
				cameraID = "PRO/" + sc.nextLine();
			
				borrowCamera(cameraID);
					
				System.out.println("Press enter to continue...");
				sc.nextLine();
					
				break;

			case 2:
				headerGenerator();
				System.out.println("[MAIN MENU -> CAMERA BORROWER PAGE -> VIEW BORROWED CAMERA]");
				
				displayBorrowerList();
				
				System.out.println("Press enter to continue...");
				sc.nextLine();
				break;
				
			case 3:
				headerGenerator();
				setCurrentDate();
				
				System.out.println("[MAIN MENU -> CAMERA BORROWER PAGE -> EXTEND BORROWING TIME]");
				
				displayBorrowerList();
				
				System.out.printf("Enter orderID : BOR/");
				orderID = "BOR/" + sc.nextLine();
				
				extendBorrowTime(orderID);
				
				// Replace file with the new data
				updateFile();
				
				sc.nextLine();
				break;
				
			case 4:
				headerGenerator();
				System.out.println("[MAIN MENU -> CAMERA BORROWER PAGE -> RETURN CAMERA]");
				
				setCurrentDate();
				
				displayBorrowerList();
				
				System.out.printf("enter orderID : BOR/");
				orderID = "BOR/" + sc.nextLine();
				
				returnCamera(orderID);
				
				// Replace file with the new data
				updateFile();
				
				
				System.out.println();
				System.out.println("Press enter to continue...");
				sc.nextLine();
				
				break;
				
			case 5:
				// Replace file with the new data
				updateFile();
				
				processSign("Back to Main Menu");
				break;
			}
			
		} while(borrowerChoice != 5);
	}
	
	void cameraProviderMenu() {
		do {
			
			System.out.println();
			headerGenerator();
			System.out.println("[MAIN MENU -> CAMERA PROVIDER PAGE]");
			System.out.println("Please select the menus:");
			System.out.println("1. Lend Camera");
			System.out.println("2. View Lended Camera");
			System.out.println("3. Cancel Lend");
			System.out.println("4. Edit Total Lend Price");
			System.out.println("5. Back to Main Menu");
			
			try {
				System.out.print("Choice >> ");
				providerChoice = sc.nextInt();
			} catch (Exception e) {
				
			}
			sc.nextLine();
			
			
			if(providerChoice < 1 || providerChoice > 5) {
				System.out.println("Please enter a valid menu!");
				sc.nextLine();
			}
			
			switch(providerChoice) {
			case 1:
				headerGenerator();
				System.out.println("[MAIN MENU -> CAMERA PROVIDER PAGE -> LEND CAMERA]");
				
				lendCamera();
				
				// Replace file with the new data
				updateFile();
				System.out.println();
				System.out.println("Press enter to continue...");
				sc.nextLine();
				break;
				
			case 2:
				headerGenerator();
				System.out.println("[MAIN MENU -> CAMERA PROVIDER PAGE -> VIEW LENDED CAMERA]");
				//System.out.println(providerProductList.size());		//NB: for debug only
				displayProviderList();
				System.out.println();
				System.out.println("Press enter to continue...");
				sc.nextLine();
				break;
				
			case 3:
				headerGenerator();
				System.out.println("[MAIN MENU -> CAMERA PROVIDER PAGE -> CANCEL LEND]");
				displayProviderList();
				
				System.out.print("Enter Camera's ID: PRO/");
				cameraID = "PRO/" + sc.nextLine();
				cancelLend(cameraID);
				
				// Replace file with the new data
				updateFile();
				System.out.println();
				System.out.println("Press enter to continue...");
				sc.nextLine();
				break;
				
			case 4:
				headerGenerator();
				System.out.println("[MAIN MENU -> CAMERA PROVIDER PAGE -> EDIT LEND PRICE]");
				displayProviderList();
				System.out.print("Enter Camera's ID: PRO/");
				cameraID = "PRO/" + sc.nextLine();
				System.out.print("Enter new lend price [per day]: ");
				newLendPrice = sc.nextInt();
				sc.nextLine();
				
				changeLendPrice(cameraID, newLendPrice);
				
				// Replace file with the new data
				updateFile();
				System.out.println();
				System.out.println("Press enter to continue...");
				sc.nextLine();
				break;
				
			case 5:
				FileController.clearProviderProductFile();
				// Replace file with the new data
				updateFile();
				processSign("Back to Main Menu");
			}
			
		} while (providerChoice != 5);
	}
	
	
	// SUPPORTING PROVIDER MENU
	void displayProviderList() {
		boolean isExist = false;	// product of the lender is exist or not
		String lendStatus;
		System.out.println();
		System.out.println("+==============================================================================================================================================================================================================================+");
		System.out.println("|    Camera's ID    |    Brand   |  Type  |            Name           |    Camera's Price    | Camera's Lend Price / Day | Accessories Name |  Accessories Price  | Total Lend Price / Day |   Lend Due Date   | Borrowed Status |");
		System.out.println("+==============================================================================================================================================================================================================================+");
		
		
		for(int i = 0; i < providerProductList.size(); i++) {
			if(providerProductList.get(i).getProviderUserName().equals(userList.get(loginIndex).getUsername())) {
				isExist = true;
				lendStatus = null;
				
				// Set Lend Status Value
				if(providerProductList.get(i).isLended() == false)
					lendStatus = "Available";
				else
					lendStatus = "Borrowed";
				
				// Set lend due date
				String tempDueDate = String.format("%02d %s %d", providerProductList.get(i).getDateLendDue(), 
						convertMonth(providerProductList.get(i).getMonthLendDue()), 
						providerProductList.get(i).getYearLendDue());
				
				// Set price format
				String cameraPrice = convertCurrency(providerProductList.get(i).getCameraPrice());
				String lendPrice = convertCurrency(providerProductList.get(i).getLendPrice() - providerProductList.get(i).getAccessoriesLendPrice());
				String accessoriesLendPrice = convertCurrency(providerProductList.get(i).getAccessoriesLendPrice());
				String totalLendPrice = convertCurrency(providerProductList.get(i).getLendPrice());
				
				if(providerProductList.get(i).getCameraType().equals("DSLR")) {	
					if(providerProductList.get(i).isAccessoriesAvailable() == true) {
						System.out.printf("| %-17s | %-10s | %-6s | %-25s | %-20s | %-25s | %-16s | %-19s | %-22s | %-17s | %-15s |\n", 
								providerProductList.get(i).getCameraID(), 
								providerProductList.get(i).getCameraBrand(), 
								providerProductList.get(i).getCameraType(), 
								providerProductList.get(i).getCameraName(), 
								cameraPrice,
								lendPrice,
								providerProductList.get(i).getAccessoriesName(), 
								accessoriesLendPrice,
								totalLendPrice,
								tempDueDate,
								lendStatus
								);
					}else {
						System.out.printf("| %-17s | %-10s | %-6s | %-25s | %-20s | %-25s |        -         |          -          | %-22s | %-17s | %-15s | \n", 
								providerProductList.get(i).getCameraID(), 
								providerProductList.get(i).getCameraBrand(), 
								providerProductList.get(i).getCameraType(), 
								providerProductList.get(i).getCameraName(), 
								cameraPrice, 
								lendPrice,
								
								totalLendPrice,
								tempDueDate,
								lendStatus
								);
					}
				}else {
					System.out.printf("| %-17s | %-10s | %-6s | %-25s | %-20s | %-25s |        -         |          -          | %-22s | %-17s | %-15s | \n", 
							providerProductList.get(i).getCameraID(), 
							providerProductList.get(i).getCameraBrand(), 
							providerProductList.get(i).getCameraType(), 
							providerProductList.get(i).getCameraName(), 
							cameraPrice, 
							lendPrice, 
							
							totalLendPrice,
							tempDueDate,
							lendStatus
							);	
				}
			}
		}
		
		
		if(isExist == false) 
			System.out.println("No data to be displayed.");

		System.out.println("+==============================================================================================================================================================================================================================+");
		
	}
	
	void lendCamera() {
		System.out.print("Enter camera's brand: ");
		cameraBrand = sc.nextLine();
		do {
			System.out.print("Enter camera's type [DSLR / Pocket]: ");
			cameraType = sc.nextLine();
		}while(!(cameraType.equals("DSLR") || cameraType.equals("Pocket")));
		
		System.out.print("Enter camera's name: ");
		cameraName = sc.nextLine();
		System.out.print("Enter camera's price: Rp ");
		cameraPrice = sc.nextInt();
		System.out.print("Enter lend price [per day]: Rp ");
		lendPrice = sc.nextInt();
		
		sc.nextLine();
		setTempDate("Input Lend Due Date [dd/mm/yyyy]: ");
		
		isLended = false;
		if(cameraType.equals("DSLR")) {
			do {
				System.out.print("Any accessories available to be lend [yes / no]: ");
				providerChoice2 = sc.nextLine();
			}while(!(providerChoice2.equals("yes") || providerChoice2.equals("no")));
			
			if(providerChoice2.equals("yes")) {
				accessoriesAvailable = true;
				System.out.print("Enter accessories name: ");
				accessoriesName = sc.nextLine();
				System.out.print("Enter accessories lend price: Rp ");
				accessoriesLendPrice = sc.nextInt();
				sc.nextLine();
				Provider.lendCamera(userList.get(loginIndex).getUsername(), cameraBrand, cameraType, cameraName, 
						cameraPrice, lendPrice, tempDate, tempMonth, tempYear, isLended, accessoriesName, accessoriesLendPrice, 
						accessoriesAvailable);
			
			}else {
				accessoriesAvailable = false;
				Provider.lendCamera(userList.get(loginIndex).getUsername(), cameraBrand, cameraType, 
						cameraName, cameraPrice, lendPrice, tempDate, tempMonth, tempYear, isLended);
			}
		}
		else {
			Provider.lendCamera(userList.get(loginIndex).getUsername(), cameraBrand, cameraType, 
					cameraName, cameraPrice, lendPrice, tempDate, tempMonth, tempYear, isLended);
		}
	}
	
	void changeLendPrice(String cameraID, int newLendPrice) {
		boolean isChangedPrice = false;
		boolean isAvailable = true;

		
		// MULTI THREADING
		LinearSearch ls1 = new LinearSearch(0, (providerProductList.size()/2), cameraID, 1);
		LinearSearch ls2 = new LinearSearch(providerProductList.size()/2, providerProductList.size(), cameraID, 1);
		Thread t1 = new Thread(ls1);
		Thread t2 = new Thread(ls2);
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//------------------//
	
		if(LinearSearch.searchIndex == -1) {
			isChangedPrice = false;
		}
		else if(providerProductList.get(LinearSearch.searchIndex).isLended() == false) {
			Provider.changeLendPrice(LinearSearch.searchIndex, newLendPrice);
			isChangedPrice = true;
		}
		else
			isAvailable = false;
		
		if (isAvailable == false) 
			System.out.println("You cannot change the product while status of the product is Lended");
		if(isChangedPrice == true && isAvailable == true)
			System.out.println("The price has been updated.");
		else if(isChangedPrice == false && isAvailable == true)
			System.out.println("Item not found or you have entered the wrong camera's ID.");
	}
	
	void cancelLend(String cameraID) {
		boolean isAvailable = true;
		boolean isCanceled = false;
		
		// MULTI THREADING
				LinearSearch ls1 = new LinearSearch(0, (providerProductList.size()/2), cameraID, 1);
				LinearSearch ls2 = new LinearSearch(providerProductList.size()/2, providerProductList.size(), cameraID, 1);
				Thread t1 = new Thread(ls1);
				Thread t2 = new Thread(ls2);
				
				t1.start();
				t2.start();
				
				try {
					t1.join();
					t2.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//------------------//
		
			
				
		if(LinearSearch.searchIndex == -1) {
			isCanceled = false;
		}
		else if(providerProductList.get(LinearSearch.searchIndex).isLended() == false) {
			Provider.cancelLend(LinearSearch.searchIndex);
			isCanceled = true;
			isAvailable = true;
		}
		else
			isAvailable = false;
		
		if (isAvailable == false) 
			System.out.println("You cannot change the product while status of the product is Lended");
		if(isCanceled == true && isAvailable == true)
			System.out.println("An item has been cancelled.");
		else if(isCanceled == false && isAvailable == true)
			System.out.println("Item not found or you have entered the wrong camera's ID.");
	}
	
	
	// SUPPORTING BORROWER MENU
	void displayBorrowerList() {
		boolean isExist = false;	// product of the lender is exist or not
		String borrowStatus;
		
		System.out.println();
		System.out.println("+=========================================================================================================================================================================================================================================================================================================================================================================+");
		System.out.println("|     Order ID      |    Camera's ID    |    Brand   |  Type  |            Name           |    Camera's Price    | Include Accessories | Accessories Name |    Lend Price / Day    |              Borrow Period             | Borrow Duration |  Total Borrow Price  | Extend Duration |     Extend Price     |      Deposit      |     Total Payment     | Borrow Status |");
		System.out.println("+=========================================================================================================================================================================================================================================================================================================================================================================+");
		//							1					2					3			4			5							6						7					8					9						10										11				12						12.0				12.1				13					14						15
		for(int i=0;i<borrowerProductList.size();i++) {
			if(borrowerProductList.get(i).getBorrowerUserName().equals(userList.get(loginIndex).getUsername())) {
				isExist = true;
				
				// Set Lend Status Value
				if(borrowerProductList.get(i).isBorrowed() == true) {
					borrowStatus = "Borrowed";
				}
				else {
					borrowStatus ="Returned";
				}	
				
				// Set borrow period
				String borrowPeriod = String.format("%02d %s %d - %02d %s %d", 
						borrowerProductList.get(i).getDateBorrow(), 
						convertMonth(borrowerProductList.get(i).getMonthBorrow()), 
						borrowerProductList.get(i).getYearBorrow(),
						borrowerProductList.get(i).getDateBorrowDue(),
						convertMonth(borrowerProductList.get(i).getMonthBorrowDue()),
						borrowerProductList.get(i).getYearBorrowDue());
				
				// Set price format
				String borrowCameraPrice = convertCurrency(borrowerProductList.get(i).getCameraPrice());
				String borrowTotalLendPrice = convertCurrency(borrowerProductList.get(i).getLendPrice());
				String totalBorrowPrice = convertCurrency(borrowerProductList.get(i).getLendPrice() * borrowerProductList.get(i).getTotalBorrowDuration());
				String deposit = convertCurrency(borrowerProductList.get(i).getTotalDeposit());
				String extendPrice = convertCurrency(borrowerProductList.get(i).getExtendPrice());
				String totalPayment = convertCurrency((borrowerProductList.get(i).getLendPrice() * borrowerProductList.get(i).getTotalBorrowDuration()) + 
						borrowerProductList.get(i).getTotalDeposit() + borrowerProductList.get(i).getExtendPrice());
				
				
				if(borrowerProductList.get(i).getCameraType().equals("DSLR")) {	
					if(borrowerProductList.get(i).isIncludeAccessories() == true) {
						System.out.printf("| %-17s | %-17s | %-10s | %-6s | %-25s | %-20s | %-19s | %-16s | %-22s | %-38s | %-15s | %-20s | %-15s | %-20s | %-17s | %-21s | %-13s |\n", 
						//						1		2		3	4			5		6		7		8		9		10		11		12	12.0	12.1		13		14		15
								borrowerProductList.get(i).getOrderID(),			// 1
								borrowerProductList.get(i).getCameraID(), 			// 2
								borrowerProductList.get(i).getCameraBrand(), 		// 3 
								borrowerProductList.get(i).getCameraType(), 		// 4
								borrowerProductList.get(i).getCameraName(), 		// 5
								borrowCameraPrice,									// 6
								"Yes",												// 7
								borrowerProductList.get(i).getAccessoriesBorrowedName(), 	// 8
								borrowTotalLendPrice,								// 9
								borrowPeriod,										// 10
								borrowerProductList.get(i).getTotalBorrowDuration() + " day(s)",	// 11
								totalBorrowPrice,											// 12
								borrowerProductList.get(i).getTotalExtendDuration() + " day(s)",	// 12.0 
								extendPrice,										// 12.1
								deposit,											// 13
								totalPayment,										// 14
								borrowStatus										// 15
								);
					}else {
						System.out.printf("| %-17s | %-17s | %-10s | %-6s | %-25s | %-20s | %-19s |        -         | %-22s | %-38s | %-15s | %-20s | %-15s | %-20s | %-17s | %-21s | %-13s |\n", 
								//				1		2		3	4			5		6		7		  	8			9		10		11		12		12.0	12.1	13		14		15
										borrowerProductList.get(i).getOrderID(),			// 1
										borrowerProductList.get(i).getCameraID(), 			// 2
										borrowerProductList.get(i).getCameraBrand(), 		// 3 
										borrowerProductList.get(i).getCameraType(), 		// 4
										borrowerProductList.get(i).getCameraName(), 		// 5
										borrowCameraPrice,									// 6
										"No",												// 7
										 	// 8
										borrowTotalLendPrice,								// 9
										borrowPeriod,										// 10
										borrowerProductList.get(i).getTotalBorrowDuration() + " day(s)",	// 11
										totalBorrowPrice,											// 12
										borrowerProductList.get(i).getTotalExtendDuration() + " day(s)",	// 12.0 
										extendPrice,										// 12.1
										deposit,											// 13
										totalPayment,										// 14
										borrowStatus										// 15
										);
					}
				}else {
					System.out.printf("| %-17s | %-17s | %-10s | %-6s | %-25s | %-20s | %-19s |        -         | %-22s | %-38s | %-15s | %-20s | %-15s | %-20s | %-17s | %-21s | %-13s |\n", 
							//				1		2		3	4			5		6		7		  	8			9		10		11		12		12.0 	12.1 	13		14		15
									borrowerProductList.get(i).getOrderID(),			// 1
									borrowerProductList.get(i).getCameraID(), 			// 2
									borrowerProductList.get(i).getCameraBrand(), 		// 3 
									borrowerProductList.get(i).getCameraType(), 		// 4
									borrowerProductList.get(i).getCameraName(), 		// 5
									borrowCameraPrice,									// 6
									"No",												// 7
									 	// 8
									borrowTotalLendPrice,								// 9
									borrowPeriod,										// 10
									borrowerProductList.get(i).getTotalBorrowDuration() + " day(s)",	// 11
									totalBorrowPrice,											// 12
									borrowerProductList.get(i).getTotalExtendDuration() + " day(s)",	// 12.0 
									extendPrice,										// 12.1
									deposit,											// 13
									totalPayment,										// 14
									borrowStatus										// 15
									);
				}
			}
		}
		if(isExist == false) 
			System.out.println("No data to be displayed.");


		System.out.println("+=========================================================================================================================================================================================================================================================================================================================================================================+");
	}
	
	void displayAllCameraList() {
		boolean isExist = false;	// product of the lender is exist or not
		String lendStatus;
		String includeAccessories;
		System.out.println();
		System.out.println("+====================================================================================================================================================================================================+");
		System.out.println("|    Camera's ID    |    Brand   |  Type  |            Name           |    Camera's Price    | Include Accessories | Accessories Name |    Lend Price / Day    |   Lended Until    | Borrowed Status |");
		System.out.println("+====================================================================================================================================================================================================+");
		//						1					2			3					4							5					6					7						8					9					10
		
		for(int i = 0; i < providerProductList.size(); i++) {
			// validation cannot order borrower own camera
			if(userList.get(loginIndex).getUsername().equalsIgnoreCase(providerProductList.get(i).getProviderUserName()) == false) {
				isExist = true;
				lendStatus = null;
				
				// Set Accessories Availability
				if(providerProductList.get(i).isAccessoriesAvailable())
					includeAccessories = "Yes";
				else
					includeAccessories = "No";
					
				
				// Set Lend Status Value
				if(providerProductList.get(i).isLended() == false)
					lendStatus = "Available";
				else
					lendStatus = "Borrowed";
				
				// Set lend due date
				String tempDueDate = String.format("%02d %s %d", providerProductList.get(i).getDateLendDue(), 
						convertMonth(providerProductList.get(i).getMonthLendDue()), 
						providerProductList.get(i).getYearLendDue());
				
				// Set price format
				String cameraPrice = convertCurrency(providerProductList.get(i).getCameraPrice());
				String totalLendPrice = convertCurrency(providerProductList.get(i).getLendPrice());
				
				if(providerProductList.get(i).getCameraType().equals("DSLR")) {	
					if(providerProductList.get(i).isAccessoriesAvailable() == true) {
						//						1		2		3	4		5		6		7		8		9		10	
						System.out.printf("| %-17s | %-10s | %-6s | %-25s | %-20s | %-19s | %-16s | %-22s | %-17s | %-15s |\n", 
								providerProductList.get(i).getCameraID(), 
								providerProductList.get(i).getCameraBrand(), 
								providerProductList.get(i).getCameraType(), 
								providerProductList.get(i).getCameraName(), 
								cameraPrice,
								includeAccessories,
								providerProductList.get(i).getAccessoriesName(), 
								totalLendPrice,
								tempDueDate,
								lendStatus
								);
					}else {
						System.out.printf("| %-17s | %-10s | %-6s | %-25s | %-20s | %-19s |        -         | %-22s | %-17s | %-15s |\n", 
								providerProductList.get(i).getCameraID(), 
								providerProductList.get(i).getCameraBrand(), 
								providerProductList.get(i).getCameraType(), 
								providerProductList.get(i).getCameraName(), 
								cameraPrice,
								includeAccessories,
								
								totalLendPrice,
								tempDueDate,
								lendStatus
								);
					}
				}else {
						System.out.printf("| %-17s | %-10s | %-6s | %-25s | %-20s | %-19s |        -         | %-22s | %-17s | %-15s |\n", 
								providerProductList.get(i).getCameraID(), 
								providerProductList.get(i).getCameraBrand(), 
								providerProductList.get(i).getCameraType(), 
								providerProductList.get(i).getCameraName(), 
								cameraPrice,
								includeAccessories,
								
								totalLendPrice,
								tempDueDate,
								lendStatus
								);
				}
			}
		}
		
		
		if(isExist == false) 
			System.out.println("No camera to be borrowed.");

		System.out.println("+====================================================================================================================================================================================================+");
		
	}

	void borrowCamera(String cameraID) {
		int choiceBorrowCamera = 0;
		Borrower borrower = new Borrower();
		tempProvCameraIndex = borrower.getBorrowedCameraIndex(cameraID);
		if(tempProvCameraIndex == -1) {
			System.out.println("Camera does not exist");
			sc.nextLine();
		}
		else if(tempProvCameraIndex == -2) {
			System.out.println("Camera is being borrowed");
			sc.nextLine();
		}
		else {
			int tempCompareDate = 0;
			int tempCompareDueDate = 0;

			do {
				setTempDate("Enter the camera return date [dd/mm/yyyy]: ");
				tempCompareDate = compareDate(todayDate, todayMonth, todayYear, tempDate, tempMonth, tempYear);
				tempCompareDueDate = compareDate(tempDate, tempMonth, tempYear, 
						providerProductList.get(tempProvCameraIndex).getDateLendDue(), providerProductList.get(tempProvCameraIndex).getMonthLendDue(), 
						providerProductList.get(tempProvCameraIndex).getYearLendDue());
				
				if(tempCompareDate < 0)
					System.out.println("Return's date must be greater than today's date.");
				if(tempCompareDueDate < 0)
					System.out.println("Return's date must be smaller than maximum lend due date.");
			} while (tempCompareDate < 0 || tempCompareDueDate < 0);
			
			
			borrower.generateIDNumber(todayDate, todayMonth, todayYear);
			borrower.setBorrowedCameraData(tempProvCameraIndex);
			borrower.setTotalBorrowDur(todayDate, todayMonth, todayYear, tempDate, tempMonth, tempYear);
			borrower.countTotalPrice();
			borrower.countDeposit();
			
			System.out.println("Order Detail");
			System.out.println("============");
			System.out.format("Order ID : %s\n", borrower.getOrderID());
			System.out.format("Camera's ID : %s\n", borrower.getCameraID());
			System.out.format("Camera's Brand : %s\n", borrower.getCameraBrand());
			System.out.format("Camera's Type : %s\n", borrower.getCameraType());
			System.out.format("Camera's Name : %s\n", borrower.getCameraName());
			System.out.format("Camera's Price : %s\n", convertCurrency(borrower.getCameraPrice()));
			if(borrower.isIncludeAccessories()) {
				System.out.format("Including Accessories : Yes\n");
				System.out.format("Accessories Name : %s\n", borrower.getAccessoriesBorrowedName());
			}
			else {
				System.out.format("Including Accessories : No\n");
			}
			System.out.format("Borrow time : %d %s %d - %d %s %d\n", borrower.getDateBorrow(), convertMonth(borrower.getMonthBorrow()), borrower.getYearBorrow(),
																		borrower.getDateBorrowDue(), convertMonth(borrower.getMonthBorrowDue()), borrower.getYearBorrowDue());
			System.out.format("Borrow Price / Day : %s\n", convertCurrency(borrower.getLendPrice()));
			System.out.format("Borrow Duration : %d day(s)\n", borrower.getTotalBorrowDuration());
			System.out.format("Total Borrow Price : %s\n", convertCurrency(borrower.getTotalBorrowPrice()));
			System.out.format("Deposit (25 percents of camera's price) : %s\n", convertCurrency(borrower.getTotalDeposit()));
			System.out.println();
			System.out.format("TOTAL PAYMENT : %s\n", convertCurrency(borrower.getTotalBorrowPrice() + borrower.getTotalDeposit()));
			System.out.println("============");
			System.out.println();
			System.out.println("Please transfer to BCA 292.520.158.8 a.n. PT Rentalcamera.com");
			
			do {
				System.out.println("1. Confirm Payment");
				System.out.println("2. Cancel Order");
				System.out.print("Choice >> ");
				
				try {
					choiceBorrowCamera = sc.nextInt();
				} catch (Exception e) {
					System.out.println("Please enter a valid menu!");
					sc.nextLine();
				}
				
				sc.nextLine();
				
				switch (choiceBorrowCamera) {
				case 1:
					borrower.confirmPayment(tempProvCameraIndex, "Transfer", true);
					processSign("Processing your order");
					break;

				case 2:
					processSign("Removing your order");
					break;
				}
				
				if(choiceBorrowCamera < 1 || choiceBorrowCamera > 2)
					System.out.println("Please enter the valid choice");
			} while (choiceBorrowCamera < 1 || choiceBorrowCamera > 2);
			
			// Replace file with the new data
			updateFile();
			
		}
	}
	
	void extendBorrowTime(String orderID) {
		Borrower borrower = new Borrower();
		int tempMaxDueDate = 0, tempMaxDueMonth = 0, tempMaxDueYear = 0;
		boolean isExtended = false;
		
			// Get maximum lend date
			for(int j = 0; j < providerProductList.size(); j++) {
				if(providerProductList.get(j).getBorrowerOrderID().equalsIgnoreCase(orderID)) {
					tempMaxDueDate = providerProductList.get(j).getDateLendDue();
					tempMaxDueMonth = providerProductList.get(j).getMonthLendDue();
					tempMaxDueYear = providerProductList.get(j).getYearLendDue();
					
					System.out.format("Maximum borrow date : %d %s %d\n", providerProductList.get(j).getDateLendDue(), 
							convertMonth(providerProductList.get(j).getMonthLendDue()), providerProductList.get(j).getYearLendDue());
					break;
				}
			}
			

			// MULTI THREADING
					LinearSearch ls1 = new LinearSearch(0, (borrowerProductList.size()/2), orderID,2);
					LinearSearch ls2 = new LinearSearch(borrowerProductList.size()/2, borrowerProductList.size(), orderID,2);
					Thread t1 = new Thread(ls1);
					Thread t2 = new Thread(ls2);
					
					t1.start();
					t2.start();
					
					try {
						t1.join();
						t2.join();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//------------------//
			
				
					
			if(LinearSearch.searchIndex == -1) {
				isExtended = false;
			}
			else {
				int i = LinearSearch.searchIndex;
				isExtended = true;
				int tempCompareDate;
				int tempCompareToday;
				int tempCompareBorrowDue;
				
				do {
					setTempDate("Enter the extended date [dd/mm/yyyy]: ");
					tempCompareDate = compareDate(tempDate, tempMonth, tempYear, tempMaxDueDate, tempMaxDueMonth, tempMaxDueYear);
					tempCompareToday = compareDate(todayDate, todayMonth, todayYear, tempDate, tempMonth, tempYear);
					tempCompareBorrowDue = compareDate(borrowerProductList.get(i).getDateBorrowDue(), borrowerProductList.get(i).getMonthBorrowDue(), 
							borrowerProductList.get(i).getYearBorrowDue(), tempDate, tempMonth, tempYear);
					
					if(tempCompareDate < 0) {
						System.out.println("Extend date cannot be greater than camera's maximum lend date");
					}
					if(tempCompareToday < 0) {
						System.out.println("Extend date must be greater than today date");
					}
					if(tempCompareBorrowDue < 0) {
						System.out.println("Extend date must be greater than borrow due date");
					}
				}while(tempCompareDate < 0 || tempCompareToday < 0 || tempCompareBorrowDue < 0);
				
				// Count extend duration
				int extendDuration = compareDate(borrowerProductList.get(i).getDateBorrowDue(), borrowerProductList.get(i).getMonthBorrowDue(),
						borrowerProductList.get(i).getYearBorrowDue(), tempDate, tempMonth, tempYear);
				
				System.out.println("Order Detail");
				System.out.println("============");
				System.out.format("Order ID : %s\n", borrowerProductList.get(i).getOrderID());
				System.out.format("Camera's ID : %s\n", borrowerProductList.get(i).getCameraID());
				System.out.format("Camera's Brand : %s\n", borrowerProductList.get(i).getCameraBrand());
				System.out.format("Camera's Type : %s\n", borrowerProductList.get(i).getCameraType());
				System.out.format("Camera's Name : %s\n", borrowerProductList.get(i).getCameraName());
				System.out.println();
				
				System.out.format("Adjusted Borrow Time : %d %s %d - %d %s %d\n",borrowerProductList.get(i).getDateBorrowDue(), convertMonth(borrowerProductList.get(i).getMonthBorrowDue()), borrowerProductList.get(i).getYearBorrowDue(),
						tempDate, convertMonth(tempMonth), tempYear);
				System.out.format("Extend Borrow Duration : %d day(s)\n", extendDuration);
				System.out.format("Borrow Price / Day : %s\n", convertCurrency(borrowerProductList.get(i).getLendPrice()));
				System.out.format("Total Extend Borrow Price : %s\n", convertCurrency(borrowerProductList.get(i).getLendPrice() * extendDuration));
				System.out.println();
				System.out.format("TOTAL : %s\n", convertCurrency(borrowerProductList.get(i).getLendPrice() * extendDuration));
				System.out.println("============");
				
				System.out.println("Please transfer to BCA 292.520.158.8 a.n. PT Rentalcamera.com");
				System.out.println("---Press enter to confirm your payment---");
				sc.nextLine();
				
				borrower.extendBorrowDur(orderID, tempDate, tempMonth, tempYear);
				
				processSign("Processing your order");
			
			}	
		
	
		if(isExtended == true)
			System.out.println("Order has been extended.");
		else
			System.out.println("Item not found or you have entered the wrong Order ID.");
		
		System.out.println();
		System.out.println("Press enter to continue...");
	}
	
	void returnCamera(String orderID) {
		int fineAmount = 0;
		int deposit = 0;
		int daysOfLate = 0;
		boolean isReturned = false;
		boolean alreadyReturned = false;
		
		// MULTI THREADING
		LinearSearch ls1 = new LinearSearch(0, (borrowerProductList.size()/2), orderID,2);
		LinearSearch ls2 = new LinearSearch(borrowerProductList.size()/2, borrowerProductList.size(), orderID,2);
		Thread t1 = new Thread(ls1);
		Thread t2 = new Thread(ls2);
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//------------------//

	
		
		if(LinearSearch.searchIndex == -1) {
			isReturned = false;
		}
		else {
			int i = LinearSearch.searchIndex;
				if(borrowerProductList.get(i).isBorrowed() == false) {
					alreadyReturned = true;
				}
				else {
				
					for(int j=0; j<providerProductList.size(); j++) {
						if(providerProductList.get(j).getBorrowerOrderID().equalsIgnoreCase(orderID)) {
							tempProvCameraIndex = j;
						}
					}
					
					// when no gap between today date and due borrow date
					if(compareDate(todayDate, todayMonth, todayYear, borrowerProductList.get(i).getDateBorrowDue(), 
							borrowerProductList.get(i).getMonthBorrowDue(), borrowerProductList.get(i).getYearBorrowDue()) == 0) {
						
						
						System.out.println("Deposit to be returned: " + convertCurrency(borrowerProductList.get(i).getTotalDeposit()));
						processSign("Returning Camera...");
						providerProductList.get(tempProvCameraIndex).setLended(false);
						borrowerProductList.get(i).setBorrowed(false);
						isReturned = true;
						
						// Set user expenditure (borrower) and income (provider)
						userList.get(loginIndex).setExpenditure(userList.get(loginIndex).getExpenditure() + borrowerProductList.get(i).getTotalBorrowPrice() + 
								borrowerProductList.get(i).getExtendPrice() + borrowerProductList.get(i).getFineAmount());
						for (int j = 0; j < userList.size(); j++) {
							if(userList.get(j).getUsername().equalsIgnoreCase(borrowerProductList.get(i).getProviderUserName())) {
								userList.get(j).setIncome(userList.get(j).getIncome() + fineAmount );
							}
						}
					
					// when late return the camera
					}else if(compareDate(todayDate, todayMonth, todayYear, borrowerProductList.get(i).getDateBorrowDue(), 
								borrowerProductList.get(i).getMonthBorrowDue(), borrowerProductList.get(i).getYearBorrowDue()) < 0) {
						
						
						daysOfLate = compareDate(todayDate, todayMonth, todayYear, borrowerProductList.get(i).getDateBorrowDue(), 
								borrowerProductList.get(i).getMonthBorrowDue(), borrowerProductList.get(i).getYearBorrowDue());
						
						System.out.println("Late duration: " + daysOfLate + " day(s)");
					
						
						int lendPrice = borrowerProductList.get(i).getLendPrice();
						fineAmount = (int) ((-1) * lendPrice * (120 / 100.0f) * daysOfLate);
						deposit = borrowerProductList.get(i).getTotalDeposit() - fineAmount;
						
						 System.out.println("Fines for late return of the camera: " + convertCurrency(fineAmount));
						 System.out.println("Deposit to be returned: " + convertCurrency(deposit));
						 processSign("Returning Camera...");
						 borrowerProductList.get(i).setFineAmount(fineAmount);
						 providerProductList.get(tempProvCameraIndex).setLended(false);
						 borrowerProductList.get(i).setBorrowed(false);
						 isReturned = true;
						 
						// Set user expenditure (borrower) and income (provider)
							userList.get(loginIndex).setExpenditure(userList.get(loginIndex).getExpenditure() + borrowerProductList.get(i).getTotalBorrowPrice() + 
									borrowerProductList.get(i).getExtendPrice() + borrowerProductList.get(i).getFineAmount());
							for (int j = 0; j < userList.size(); j++) {
								if(userList.get(j).getUsername().equalsIgnoreCase(borrowerProductList.get(i).getProviderUserName())) {
									userList.get(j).setIncome(userList.get(j).getIncome() + fineAmount );
								}
							}
					}
					
					// when too soon return the camera
					else {
						System.out.println("Deposit to be returned: " + convertCurrency(borrowerProductList.get(i).getTotalDeposit()));
						processSign("Returning Camera...");
						providerProductList.get(tempProvCameraIndex).setLended(false);
						borrowerProductList.get(i).setBorrowed(false);
						isReturned = true;
						
						// Set user expenditure (borrower) and income (provider)
						userList.get(loginIndex).setExpenditure(userList.get(loginIndex).getExpenditure() + borrowerProductList.get(i).getTotalBorrowPrice() + 
								borrowerProductList.get(i).getExtendPrice() + borrowerProductList.get(i).getFineAmount());
						for (int j = 0; j < userList.size(); j++) {
							if(userList.get(j).getUsername().equalsIgnoreCase(borrowerProductList.get(i).getProviderUserName())) {
								userList.get(j).setIncome(userList.get(j).getIncome() + fineAmount );
							}
						}
						
					}
				}
			
	}
	
	if(alreadyReturned == true) 
		System.out.println("You have returned that camera before.");
	if(isReturned == true && alreadyReturned == false)
		System.out.println("Camera has been returned.");
	else if(isReturned == false && alreadyReturned == false)
		System.out.println("Item not found or you have entered the wrong Order ID.");
	
	}

	void setReturnDate(String text) {
		boolean isDateFormat = false;
		
		do {
			System.out.print(text);
			inputreturnDate = sc.nextLine();
			
			if(inputreturnDate.matches(dateRegex)) {
				splitreturnDate = inputreturnDate.split("/");
				
				returnDate = Integer.parseInt(splitreturnDate[0]);
				returnMonth = Integer.parseInt(splitreturnDate[1]);
				returnYear = Integer.parseInt(splitreturnDate[2]);
				
				if(returnYear >= 2000 && returnYear <= 2025) {
					if(returnMonth <= 12 && returnMonth >= 1) {
						if(returnDate <= 31 && returnDate >= 1) {
							if(returnMonth == 2 && returnYear % 4 == 0) { // kabisat
								if(returnDate >= 1 && returnDate <= 29)
									isDateFormat = true;
							}
							if(returnMonth == 2 && returnYear % 4 != 0) {
								if(returnDate >= 1 && returnDate <= 28)
									isDateFormat = true;
							}
							if(returnMonth == 1 || returnMonth == 3 || returnMonth == 5 || returnMonth == 7 || 
									returnMonth == 8 || returnMonth == 10 || returnMonth == 12) {
								if(returnDate >= 1 && returnDate <= 31)
									isDateFormat = true;
							}
							else {
								if(returnDate >= 1 && returnDate <= 30)
									isDateFormat = true;
							}
						}
					}
				}
				else
					isDateFormat = false;
			}
			else {
				System.out.println("Enter a valid date's format!");
				isDateFormat = false;
				sc.nextLine();
			}
				
		} while (isDateFormat == false);
	}
	
	
	// SUPPORTING ALL
	
	void updateFile() {
		FileController.clearUserFile();
		for (User user : userList) {
			FileController.writeUserFile(user.getEmail(), user.getUsername(), user.getPassword(), user.getName(), user.getPhoneNumber(), 
					user.getAddress(), user.getIncome(), user.getExpenditure());
		}
		
		FileController.clearProviderProductFile();
		for (Provider provider : providerProductList) {
			FileController.writeProviderProductFile(provider.getCameraID(), provider.getProviderUserName(), provider.getCameraBrand(), 
					provider.getCameraType(), provider.getCameraName(), provider.getCameraPrice(), provider.getLendPrice(), 
					provider.getDateLendDue(), provider.getMonthLendDue(), provider.getYearLendDue(), 
					provider.isLended(), provider.getAccessoriesName(), provider.getAccessoriesLendPrice(),
					provider.isAccessoriesAvailable(), provider.getBorrowerOrderID());
		}
		
		FileController.clearBorrowerProductFile();
		for (Borrower borrower : borrowerProductList) {
			FileController.writeBorrowerProductFile(borrower.getCameraID(), borrower.getCameraBrand(), borrower.getCameraType(), borrower.getCameraType(), 
					borrower.getCameraPrice(), borrower.getLendPrice(), borrower.isBorrowed(), borrower.getBorrowerUserName(), borrower.getProviderUserName(), 
					borrower.getOrderID(), borrower.isIncludeAccessories(), borrower.getAccessoriesBorrowedName(), borrower.getDateBorrow(), 
					borrower.getMonthBorrow(), borrower.getYearBorrow(), borrower.getDateBorrowDue(), borrower.getMonthBorrowDue(), borrower.getYearBorrowDue(), 
					borrower.getTotalBorrowDuration(), borrower.getTotalBorrowPrice(), borrower.getTotalDeposit(), borrower.getPaymentMethod(), 
					borrower.isPaid(), borrower.isExtended(), borrower.getTotalExtendDuration(), borrower.getExtendPrice(), borrower.getFineAmount());
		}
		
	}
	
	int compareDate(int dateBefore, int monthBefore, int yearBefore, int dateAfter, int monthAfter, int yearAfter) {
		LocalDate DateBefore = LocalDate.of(yearBefore, monthBefore, dateBefore);
		LocalDate DateAfter = LocalDate.of(yearBefore, monthAfter, dateAfter);
		
		long result = ChronoUnit.DAYS.between(DateBefore, DateAfter);

		return (int)result;
	}
	
	void setCurrentDate() {
		boolean isDateFormat = false;
		
		do {
			System.out.println("* For simulation only");
			System.out.print("Input today's date [dd/mm/yyyy]: ");
			inputDate = sc.nextLine();
			
			if(inputDate.matches(dateRegex)) {
				splitDate = inputDate.split("/");
				
				todayDate = Integer.parseInt(splitDate[0]);
				todayMonth = Integer.parseInt(splitDate[1]);
				todayYear = Integer.parseInt(splitDate[2]);
				
				if(todayYear >= 2000 && todayYear <= 2025) {
					if(todayMonth <= 12 && todayMonth >= 1) {
						if(todayDate <= 31 && todayDate >= 1) {
							if(todayMonth == 2 && todayYear % 4 == 0) { // kabisat
								if(todayDate >= 1 && todayDate <= 29)
									isDateFormat = true;
							}
							if(todayMonth == 2 && todayYear % 4 != 0) {
								if(todayDate >= 1 && todayDate <= 28)
									isDateFormat = true;
							}
							if(todayMonth == 1 || todayMonth == 3 || todayMonth == 5 || todayMonth == 7 || 
									todayMonth == 8 || todayMonth == 10 || todayMonth == 12) {
								if(todayDate >= 1 && todayDate <= 31)
									isDateFormat = true;
							}
							else {
								if(todayDate >= 1 && todayDate <= 30)
									isDateFormat = true;
							}
						}
					}
				}
				else
					isDateFormat = false;
			}
			else {
				System.out.println("Enter a valid date's format!");
				isDateFormat = false;
				sc.nextLine();
			}
				
		} while (isDateFormat == false);
	}
	
	void showCurrentDate() {
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] month = dfs.getMonths();
		
		System.out.println("* For simulation only");
		System.out.format("[%d - %s - %d]\n\n", todayDate, month[todayMonth-1], todayYear);
	}

	void setTempDate(String text) {
		boolean isDateFormat = false;
		
		do {
			System.out.print(text);
			inputTempDate = sc.nextLine();
			
			if(inputTempDate.matches(dateRegex)) {
				splitTempDate = inputTempDate.split("/");
				
				tempDate = Integer.parseInt(splitTempDate[0]);
				tempMonth = Integer.parseInt(splitTempDate[1]);
				tempYear = Integer.parseInt(splitTempDate[2]);
				
				if(tempYear >= 2000 && tempYear <= 2025) {
					if(tempMonth <= 12 && tempMonth >= 1) {
						if(tempDate <= 31 && tempDate >= 1) {
							if(tempMonth == 2 && tempYear % 4 == 0) { // kabisat
								if(tempDate >= 1 && tempDate <= 29)
									isDateFormat = true;
							}
							if(tempMonth == 2 && tempYear % 4 != 0) {
								if(tempDate >= 1 && tempDate <= 28)
									isDateFormat = true;
							}
							if(tempMonth == 1 || tempMonth == 3 || tempMonth == 5 || tempMonth == 7 || 
									tempMonth == 8 || tempMonth == 10 || tempMonth == 12) {
								if(tempDate >= 1 && tempDate <= 31)
									isDateFormat = true;
							}
							else {
								if(tempDate >= 1 && tempDate <= 30)
									isDateFormat = true;
							}
						}
					}
				}
				else
					isDateFormat = false;
			}
			else {
				System.out.println("Enter a valid date's format!");
				isDateFormat = false;
				sc.nextLine();
			}
				
		} while (isDateFormat == false);
	}
	
	private String convertMonth(int tempMonth) {
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] tempStringMonth = dfs.getMonths();
		
		return tempStringMonth[tempMonth-1];
	}
	
	
	private String convertCurrency(int moneyValue) {
		DecimalFormat IDExchangeRate = (DecimalFormat) DecimalFormat.getCurrencyInstance();
		DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
		
		formatRp.setCurrencySymbol("Rp. ");
		formatRp.setMonetaryDecimalSeparator(',');
		formatRp.setGroupingSeparator('.');
		
		IDExchangeRate.setDecimalFormatSymbols(formatRp);
		
		return IDExchangeRate.format(moneyValue);	
	}
	
	void processSign(String temp) {
		System.out.format("\n%s", temp);
		try {
			for (int i = 0; i < 3; i++) {
				System.out.print(".");
				TimeUnit.MILLISECONDS.sleep(500);
			}	
		} catch (InterruptedException e) {
			
		}
		
		System.out.println();
	}
	
	void headerGenerator() {
		for(int clear = 0; clear < 50; clear++)
		  {
		     System.out.println();
		  }
		System.out.println("  _____               _          _  _  __                                                            \r\n" + 
				" |  __ \\             | |        | || |/ /                                                            \r\n" + 
				" | |__) | ___  _ __  | |_  __ _ | || ' /  __ _  _ __ ___    ___  _ __  __ _     ___  ___   _ __ ___  \r\n" + 
				" |  _  / / _ \\| '_ \\ | __|/ _` || ||  <  / _` || '_ ` _ \\  / _ \\| '__|/ _` |   / __|/ _ \\ | '_ ` _ \\ \r\n" + 
				" | | \\ \\|  __/| | | || |_| (_| || || . \\| (_| || | | | | ||  __/| |  | (_| | _| (__| (_) || | | | | |\r\n" + 
				" |_|  \\_\\\\___||_| |_| \\__|\\__,_||_||_|\\_\\\\__,_||_| |_| |_| \\___||_|   \\__,_|(_)\\___|\\___/ |_| |_| |_|\r\n" + 
				"                                                                                                     ");
	}
	
	public static void main(String[] args) {
		new App();
	}

}
