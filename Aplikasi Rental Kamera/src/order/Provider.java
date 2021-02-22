package order;

import application.App;

public class Provider extends Product implements AdditionalRequest{
	
	private String providerUserName;
	private String borrowerOrderID;
	private int dateLendDue;
	private int monthLendDue;
	private int yearLendDue;
	private String accessoriesName = null;
	private int accessoriesLendPrice = 0;
	private boolean accessoriesAvailable = false;
	private boolean isLended;
	
	public Provider(String cameraID, String providerUserName, String cameraBrand, String cameraType, String cameraName,
			int cameraPrice, int lendPrice, int dateLendDue, int monthLendDue, int yearLendDue, boolean isLended,
			String accessoriesName, int accessoriesLendPrice, boolean accessoriesAvailable) {
		super(cameraID, cameraBrand, cameraType, cameraName, cameraPrice, lendPrice);
		this.providerUserName = providerUserName;
		this.accessoriesName = accessoriesName;
		this.accessoriesLendPrice = accessoriesLendPrice;
		this.accessoriesAvailable = accessoriesAvailable;
		this.dateLendDue = dateLendDue;
		this.monthLendDue = monthLendDue;
		this.yearLendDue = yearLendDue;
		this.isLended = isLended;
		generateIDNumber(dateLendDue, monthLendDue, yearLendDue);
		addReq(accessoriesLendPrice);
	}

	public Provider(String cameraID, String providerUserName, String cameraBrand, String cameraType, String cameraName,
			int cameraPrice, int lendPrice, int dateLendDue, int monthLendDue, int yearLendDue, boolean isLended) {
		super(cameraID, cameraBrand, cameraType, cameraName, cameraPrice, lendPrice);
		this.providerUserName = providerUserName;
		this.dateLendDue = dateLendDue;
		this.monthLendDue = monthLendDue;
		this.yearLendDue = yearLendDue;
		this.accessoriesAvailable = false;
		this.isLended = isLended;
		generateIDNumber(dateLendDue, monthLendDue, yearLendDue);
	}
	
	
	// File Processing
	public Provider(String cameraID, String providerUserName, String cameraBrand, String cameraType, String cameraName,
			int cameraPrice, int lendPrice, int dateLendDue, int monthLendDue, int yearLendDue, boolean isLended,
			String accessoriesName, int accessoriesLendPrice, boolean accessoriesAvailable, String borrowerOrderID) {
		super(cameraID, cameraBrand, cameraType, cameraName, cameraPrice, lendPrice);
		this.providerUserName = providerUserName;
		this.accessoriesName = accessoriesName;
		this.accessoriesLendPrice = accessoriesLendPrice;
		this.accessoriesAvailable = accessoriesAvailable;
		this.dateLendDue = dateLendDue;
		this.monthLendDue = monthLendDue;
		this.yearLendDue = yearLendDue;
		this.borrowerOrderID = borrowerOrderID;
		this.isLended = isLended;
	}

	
	public boolean isLended() {
		return isLended;
	}

	public void setLended(boolean isLended) {
		this.isLended = isLended;
	}

	public String getBorrowerOrderID() {
		return borrowerOrderID;
	}

	public void setBorrowerOrderID(String borrowerUserName) {
		this.borrowerOrderID = borrowerUserName;
	}

	public String getProviderUserName() {
		return providerUserName;
	}

	public void setProviderUserName(String providerUserName) {
		this.providerUserName = providerUserName;
	}

	public int getDateLendDue() {
		return dateLendDue;
	}

	public void setDateLendDue(int dateLendDue) {
		this.dateLendDue = dateLendDue;
	}

	public int getMonthLendDue() {
		return monthLendDue;
	}

	public void setMonthLendDue(int monthLendDue) {
		this.monthLendDue = monthLendDue;
	}

	public int getYearLendDue() {
		return yearLendDue;
	}

	public void setYearLendDue(int yearLendDue) {
		this.yearLendDue = yearLendDue;
	}

	public String getAccessoriesName() {
		return accessoriesName;
	}

	public void setAccessoriesName(String accessoriesName) {
		this.accessoriesName = accessoriesName;
	}

	public int getAccessoriesLendPrice() {
		return accessoriesLendPrice;
	}

	public void setAccessoriesLendPrice(int accessoriesLend) {
		this.accessoriesLendPrice = accessoriesLend;
	}

	public boolean isAccessoriesAvailable() {
		return accessoriesAvailable;
	}

	public void setAccessoriesAvailable(boolean accessoriesAvailable) {
		this.accessoriesAvailable = accessoriesAvailable;
	}

	//from interface
	public void addReq(int accessoriesLendPrice) {
		this.setLendPrice(this.lendPrice + accessoriesLendPrice);
	}
	
	@Override
	void generateIDNumber(int dateLendDue, int monthLendDue, int yearLendDue) {
		boolean isNewID = true;
		
		String orderNumber = null;
		do {
			orderNumber = "PRO/" + String.format("%02d%02d%d/%04d", dateLendDue, monthLendDue, yearLendDue, App.random.nextInt(10000));
			
			// checking if there is same order number in the list
			for (Provider providerList : App.providerProductList) {
				if(providerList.getCameraID().equals(orderNumber))
					isNewID = false;
			}
			
		} while (isNewID == false);	
		
		setCameraID(orderNumber);
	}

	// accessories Available
	public static void lendCamera(String userName, String cameraBrand, String cameraType, String cameraName,
			int cameraPrice, int lendPrice, int dateLendDue, int monthLendDue, int yearLendDue, boolean isLended, String accessoriesName, int accessoriesLendPrice, boolean accessoriesAvailable) {		
		App.providerProductList.add(new Provider(null, App.userList.get(App.loginIndex).getUsername(), cameraBrand, 
				cameraType, cameraName, cameraPrice, lendPrice, dateLendDue, monthLendDue, yearLendDue, isLended, 
				accessoriesName, accessoriesLendPrice, accessoriesAvailable));	
		
		System.out.println("New camera added!");
	}
	
	// No accessories Available
	public static void lendCamera(String userName, String cameraBrand, String cameraType, String cameraName,
			int cameraPrice, int lendPrice, int dateLendDue, int monthLendDue, int yearLendDue, boolean isLended) {
		App.providerProductList.add(new Provider(null, App.userList.get(App.loginIndex).getUsername(), cameraBrand, 
				cameraType, cameraName, cameraPrice, lendPrice, dateLendDue, monthLendDue, yearLendDue, isLended));	
		
		System.out.println("New camera added!");
	}
	
	public static void changeLendPrice(int index, int newLendPrice) {
		App.providerProductList.get(index).setLendPrice(newLendPrice);
	}

	public static void cancelLend(int index) {
		App.providerProductList.remove(index);
	}
}
