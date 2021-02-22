package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import order.Borrower;
import order.Provider;
import user.User;

public class FileController {
	private static File file = null;
	private static FileWriter fileWriter = null;
	private static Scanner sc = null;
	
	// User Data
	private static final String userDataPath = "Database/UserData.txt";
	private static String[] userData = new String[8];
	static int income = 0;
	static int expenditure = 0;
	
	// Provider Product Data
	private static final String providerProductDataPath = "Database/ProviderProductData.txt";
	static String providerID = null;
	static int cameraPrice = 0;
	static int lendPrice = 0;
	static int dayLendDue = 0;
	static int monthLendDue = 0;
	static int yearLendDue = 0;
	static int accessoriesLendPrice = 0;
	static String userName = null;
	static String borrowerUserName = null;
	static String cameraBrand = null;
	static String cameraType = null;
	static String cameraName = null;
	static String accessoriesName = null;
	static boolean lendStatus = false, accessoriesAvailable = false;
	private static String[] providerProductData = new String[14];
	
	
	// Borrower Product Data
	private static final String borrowerProductDataPath = "Database/BorrowerProductData.txt";
	static String cameraBrandBor;
	static String cameraTypeBor;
	static String cameraNameBor;
	static int cameraPriceBor;
	static int lendPriceBor;
	static String borUserName;
	static String providerUserName;
	static String orderID;
	static String cameraID;
	static boolean includeAccessories;
	static String accessoriesBorrowedName = null;
	static int dateBorrow;
	static int monthBorrow;
	static int yearBorrow;
	static int dateBorrowDue;
	static int monthBorrowDue;
	static int yearBorrowDue;
	static int totalBorrowDuration;
	static int totalBorrowPrice;
	static int totalDeposit; 
	static String paymentMethod;
	static boolean isPaid = false;
	static boolean isBorrowed;
	static boolean isExtended = false;
	static int totalExtendDuration;
	static int extendPrice;
	static int fineAmount = 0;
	private static String[] borrowerProductData = new String[28];
	
	
	// USER FILE
	public static void clearUserFile() {
		file = new File(userDataPath);

		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeUserFile(String email, String username, String password, String name, String phoneNumber, String address, int income, int expenditure) {
		file = new File(userDataPath);
		try {
			fileWriter = new FileWriter(file, true);
			fileWriter.write(email + '#' + username + '#' + password + '#' + name + '#' + phoneNumber + '#' + address + '#' + income + '#' + expenditure + '\n');
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void readUserFile() {
		file = new File(userDataPath);
		App.userList.clear();
		
		if(file.exists()) {
			try {
				sc = new Scanner(file);
				
				while(sc.hasNextLine()) {
					userData = sc.nextLine().split("#");
					
					income = Integer.parseInt(userData[6]);
					expenditure = Integer.parseInt(userData[7]);
					
					App.userList.add(new User(userData[0], userData[1], userData[2], userData[3], userData[4], userData[5], income, expenditure));
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// PROVIDER DATA FILE
	public static void clearProviderProductFile() {
		file = new File(providerProductDataPath);

		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeProviderProductFile(String providerID, String userName, String cameraBrand, String cameraType, String cameraName,
			int cameraPrice, int lendPrice, int dayLendDue, int monthLendDue, int yearLendDue, boolean lendStatus, String accessoriesName, 
			int accessoriesLendPrice, boolean accessoriesAvailable, String borrowerUsername) {
		file = new File(providerProductDataPath);
		try {
			fileWriter = new FileWriter(file, true);
			fileWriter.write(providerID + '#' + userName + '#' + cameraBrand + '#' + cameraType + '#' + cameraName + '#' + cameraPrice 
					 + '#' + lendPrice + '#' + dayLendDue + '#' + monthLendDue + '#' + yearLendDue + '#' + lendStatus + '#' + accessoriesName 
					 + '#' + accessoriesLendPrice + '#' + accessoriesAvailable +  '#' + borrowerUsername + '\n');
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void readProviderProductFile() {
		file = new File(providerProductDataPath);
		App.providerProductList.clear();
		
		if(file.exists()) {
			try {
				sc = new Scanner(file);
				
				while(sc.hasNextLine()) {
					providerProductData = sc.nextLine().split("#");
					
					providerID = providerProductData[0];
					userName = providerProductData[1];
					cameraBrand = providerProductData[2];
					cameraType = providerProductData[3];
					cameraName = providerProductData[4];
					cameraPrice = Integer.parseInt(providerProductData[5]);
					lendPrice = Integer.parseInt(providerProductData[6]);
					dayLendDue = Integer.parseInt(providerProductData[7]);
					monthLendDue = Integer.parseInt(providerProductData[8]);
					yearLendDue = Integer.parseInt(providerProductData[9]);
					borrowerUserName = providerProductData[10];
					
					if(providerProductData[10].equalsIgnoreCase("true")) 
						lendStatus = true;
					else 
						lendStatus = false;
					
					accessoriesName = providerProductData[11];
					accessoriesLendPrice = Integer.parseInt(providerProductData[12]);
					
					if(providerProductData[13].equalsIgnoreCase("true")) 
						accessoriesAvailable = true;
					else 
						accessoriesAvailable = false;
					
					
					
					App.providerProductList.add(new Provider(providerID, userName, cameraBrand, cameraType, cameraName, cameraPrice, lendPrice, 
							dayLendDue, monthLendDue, yearLendDue, lendStatus, accessoriesName, accessoriesLendPrice, accessoriesAvailable, borrowerUserName));
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	// BORROWER DATA FILE
		public static void clearBorrowerProductFile() {
			file = new File(borrowerProductDataPath);

			try {
				fileWriter = new FileWriter(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static void writeBorrowerProductFile(String cameraID, String cameraBrand, String cameraType, String cameraName, int cameraPrice, int accessoriesLendPrice, boolean isBorrowed, 
				String borrowerUserName, String providerUserName, String orderID, boolean includeAccessories, String accessoriesBorrowedName, int dateBorrow, int monthBorrow, int yearBorrow, 
				int dateBorrowDue, int monthBorrowDue, int yearBorrowDue, int totalBorrowDuration, int totalBorrowPrice, int totalDeposit, String paymentMethod, boolean isPaid, 
				boolean isExtended, int totalExtendDuration, int extendPrice, int fineAmount) {
			file = new File(borrowerProductDataPath);
			try {
				fileWriter = new FileWriter(file, true);
				fileWriter.write(cameraID + '#' + cameraBrand + '#' + cameraType + '#' + cameraName + '#' + cameraPrice + '#' + accessoriesLendPrice + '#' + isBorrowed + '#' + 
						borrowerUserName + '#' + providerUserName + '#' + orderID + '#' + includeAccessories + '#' + accessoriesBorrowedName + '#' + dateBorrow + '#' + monthBorrow + '#' + yearBorrow + '#' + 
						dateBorrowDue + '#' + monthBorrowDue + '#' + yearBorrowDue + '#' + totalBorrowDuration + '#' + totalBorrowPrice + '#' + totalDeposit + '#' + paymentMethod + '#' + isPaid + '#' + 
						isExtended + '#' + totalExtendDuration + '#' + extendPrice + '#' + fineAmount + '\n');
				fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void readBorrowerProductFile() {
			file = new File(borrowerProductDataPath);
			App.borrowerProductList.clear();
			
			if(file.exists()) {
				try {
					sc = new Scanner(file);
					
					while(sc.hasNextLine()) {
						borrowerProductData = sc.nextLine().split("#");
						
						cameraID = borrowerProductData[0]; 
						cameraBrandBor = borrowerProductData[1]; 
						cameraTypeBor = borrowerProductData[2]; 
						cameraNameBor = borrowerProductData[3]; 
						cameraPriceBor = Integer.parseInt(borrowerProductData[4]); 
						lendPriceBor = Integer.parseInt(borrowerProductData[5]); 
						
						if(borrowerProductData[6].equalsIgnoreCase("true")) 
							isBorrowed = true;
						else 
							isBorrowed = false;
						
						borUserName = borrowerProductData[7]; 
						providerUserName = borrowerProductData[8]; 
						orderID = borrowerProductData[9]; 
						
						if(borrowerProductData[10].equalsIgnoreCase("true")) 
							includeAccessories = true;
						else 
							includeAccessories = false;
						
						accessoriesBorrowedName = borrowerProductData[11]; 
						dateBorrow = Integer.parseInt(borrowerProductData[12]); 
						monthBorrow = Integer.parseInt(borrowerProductData[13]); 
						yearBorrow = Integer.parseInt(borrowerProductData[14]); 
						dateBorrowDue = Integer.parseInt(borrowerProductData[15]); 
						monthBorrowDue = Integer.parseInt(borrowerProductData[16]); 
						yearBorrowDue = Integer.parseInt(borrowerProductData[17]); 
						totalBorrowDuration = Integer.parseInt(borrowerProductData[18]); 
						totalBorrowPrice = Integer.parseInt(borrowerProductData[19]); 
						totalDeposit = Integer.parseInt(borrowerProductData[20]); 
						paymentMethod = borrowerProductData[21];
						
						if(borrowerProductData[22].equalsIgnoreCase("true")) 
							isPaid = true;
						else 
							isPaid = false;
						
						if(borrowerProductData[23].equalsIgnoreCase("true")) 
							isExtended = true;
						else 
							isExtended = false;
						
						totalExtendDuration = Integer.parseInt(borrowerProductData[24]); 
						extendPrice = Integer.parseInt(borrowerProductData[25]); 
						fineAmount = Integer.parseInt(borrowerProductData[26]);
			
						
						App.borrowerProductList.add(new Borrower(cameraID, cameraBrandBor, cameraTypeBor, cameraNameBor, cameraPriceBor,
								lendPriceBor, isBorrowed, borUserName, providerUserName, orderID,
								includeAccessories, accessoriesBorrowedName, dateBorrow, monthBorrow, yearBorrow,
								dateBorrowDue, monthBorrowDue, yearBorrowDue, totalBorrowDuration, totalBorrowPrice,
								totalDeposit, paymentMethod, isPaid, isExtended, totalExtendDuration,
								extendPrice, fineAmount));
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
}
