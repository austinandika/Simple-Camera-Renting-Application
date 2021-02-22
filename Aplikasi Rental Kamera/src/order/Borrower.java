package order;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import application.App;

public class Borrower extends Product implements BorrowerAddOn {

	private String borrowerUserName;
	private String providerUserName;
	private String orderID;
	private boolean includeAccessories;
	private String accessoriesBorrowedName = null;
	private int dateBorrow;
	private int monthBorrow;
	private int yearBorrow;
	private int dateBorrowDue;
	private int monthBorrowDue;
	private int yearBorrowDue;
	private int totalBorrowDuration;
	private int totalBorrowPrice;
	private int totalDeposit; // 25% of the camera's price
	private String paymentMethod;
	private boolean isPaid = false;
	private boolean isBorrowed;
	

	private boolean isExtended = false;
	private int totalExtendDuration;
	private int extendPrice;
	
	private int fineAmount = 0;		// denda

	public Borrower() {
		super();
	}

	// FILE PROCESSING
	public Borrower(String cameraID, String cameraBrand, String cameraType, String cameraName, int cameraPrice,
			int lendPrice, boolean isBorrowed, String borrowerUserName, String providerUserName, String orderID,
			boolean includeAccessories, String accessoriesBorrowedName, int dateBorrow, int monthBorrow, int yearBorrow,
			int dateBorrowDue, int monthBorrowDue, int yearBorrowDue, int totalBorrowDuration, int totalBorrowPrice,
			int totalDeposit, String paymentMethod, boolean isPaid, boolean isExtended, int totalExtendDuration,
			int extendPrice, int fineAmount) {
		super(cameraID, cameraBrand, cameraType, cameraName, cameraPrice, lendPrice);
		this.borrowerUserName = borrowerUserName;
		this.providerUserName = providerUserName;
		this.orderID = orderID;
		this.includeAccessories = includeAccessories;
		this.accessoriesBorrowedName = accessoriesBorrowedName;
		this.dateBorrow = dateBorrow;
		this.monthBorrow = monthBorrow;
		this.yearBorrow = yearBorrow;
		this.dateBorrowDue = dateBorrowDue;
		this.monthBorrowDue = monthBorrowDue;
		this.yearBorrowDue = yearBorrowDue;
		this.totalBorrowDuration = totalBorrowDuration;
		this.totalBorrowPrice = totalBorrowPrice;
		this.totalDeposit = totalDeposit;
		this.paymentMethod = paymentMethod;
		this.isPaid = isPaid;
		this.isExtended = isExtended;
		this.totalExtendDuration = totalExtendDuration;
		this.extendPrice = extendPrice;
		this.isBorrowed = isBorrowed;
		this.fineAmount = fineAmount;
	}






	
	public int getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(int fineAmount) {
		this.fineAmount = fineAmount;
	}

	public int getExtendPrice() {
		return extendPrice;
	}

	public void setExtendPrice(int extendPrice) {
		this.extendPrice = extendPrice;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public boolean isExtended() {
		return isExtended;
	}

	public void setExtended(boolean isExtended) {
		this.isExtended = isExtended;
	}

	public String getBorrowerUserName() {
		return borrowerUserName;
	}

	public void setBorrowerUserName(String borrowerUserName) {
		this.borrowerUserName = borrowerUserName;
	}

	public String getProviderUserName() {
		return providerUserName;
	}

	public void setProviderUserName(String providerUserName) {
		this.providerUserName = providerUserName;
	}

	public boolean isIncludeAccessories() {
		return includeAccessories;
	}

	public void setIncludeAccessories(boolean includeAccessories) {
		this.includeAccessories = includeAccessories;
	}

	public int getTotalBorrowDuration() {
		return totalBorrowDuration;
	}

	public void setTotalBorrowDuration(int totalBorrowDuration) {
		this.totalBorrowDuration = totalBorrowDuration;
	}

	public int getTotalExtendDuration() {
		return totalExtendDuration;
	}

	public void setTotalExtendDuration(int totalExtendDuration) {
		this.totalExtendDuration = totalExtendDuration;
	}

	public int getDateBorrow() {
		return dateBorrow;
	}

	public void setDateBorrow(int dateBorrow) {
		this.dateBorrow = dateBorrow;
	}

	public int getMonthBorrow() {
		return monthBorrow;
	}

	public void setMonthBorrow(int monthBorrow) {
		this.monthBorrow = monthBorrow;
	}

	public int getYearBorrow() {
		return yearBorrow;
	}

	public void setYearBorrow(int yearBorrow) {
		this.yearBorrow = yearBorrow;
	}

	public int getDateBorrowDue() {
		return dateBorrowDue;
	}

	public void setDateBorrowDue(int dateBorrowDue) {
		this.dateBorrowDue = dateBorrowDue;
	}

	public int getMonthBorrowDue() {
		return monthBorrowDue;
	}

	public void setMonthBorrowDue(int monthBorrowDue) {
		this.monthBorrowDue = monthBorrowDue;
	}

	public int getYearBorrowDue() {
		return yearBorrowDue;
	}

	public void setYearBorrowDue(int yearBorrowDue) {
		this.yearBorrowDue = yearBorrowDue;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public int getTotalBorrowPrice() {
			return totalBorrowPrice;	
	}

	public void setTotalBorrowPrice(int totalBorrowPrice) {
			this.totalBorrowPrice = totalBorrowPrice;
	}

	public int getTotalDeposit() {
		return totalDeposit;
	}

	public void setTotalDeposit(int totalDeposit) {
		this.totalDeposit = totalDeposit;
	}

	public String getAccessoriesBorrowedName() {
		return accessoriesBorrowedName;
	}

	public void setAccessoriesBorrowedName(String accessoriesBorrowedName) {
		this.accessoriesBorrowedName = accessoriesBorrowedName;
	}

	public boolean isBorrowed() {
		return isBorrowed;
	}

	public void setBorrowed(boolean isBorrowed) {
		this.isBorrowed = isBorrowed;
	}


	public int getBorrowedCameraIndex(String cameraID) {
		int tempProvCameraIndex = -1;

		// getting the camera Index
		for (int i = 0; i < App.providerProductList.size(); i++) {
			if (App.providerProductList.get(i).getCameraID().equalsIgnoreCase(cameraID)) {
				if(App.providerProductList.get(i).isLended() == false) {
					tempProvCameraIndex = i;
					return tempProvCameraIndex;
				}
				else
					return -2;
			}
		}

		return -1;

	}

	@Override
	public void generateIDNumber(int borrowDate, int borrowMonth, int borrowYear) { // Borrower ID Number
		boolean isNewID = true;

		String orderNumber = null;
		do {
			orderNumber = "BOR/"
					+ String.format("%02d%02d%d/%04d", borrowDate, borrowMonth, borrowYear, App.random.nextInt(10000));

			// checking if there is same order number in the list
			for (Borrower borrowerList : App.borrowerProductList) {
				if (borrowerList.getCameraID().equals(orderNumber))
					isNewID = false;
			}

		} while (isNewID == false);

		setOrderID(orderNumber);
	}

	public void setBorrowedCameraData(int tempProvCameraIndex) {
		setProviderUserName(App.providerProductList.get(tempProvCameraIndex).getProviderUserName());
		setCameraID(App.providerProductList.get(tempProvCameraIndex).getCameraID());
		setCameraBrand(App.providerProductList.get(tempProvCameraIndex).getCameraBrand());
		setCameraType(App.providerProductList.get(tempProvCameraIndex).getCameraType());
		setCameraName(App.providerProductList.get(tempProvCameraIndex).getCameraName());
		setCameraPrice(App.providerProductList.get(tempProvCameraIndex).getCameraPrice());
		setLendPrice(App.providerProductList.get(tempProvCameraIndex).getLendPrice());
		setIncludeAccessories(App.providerProductList.get(tempProvCameraIndex).isAccessoriesAvailable());

		if (isIncludeAccessories()) {
			setAccessoriesBorrowedName(App.providerProductList.get(tempProvCameraIndex).getAccessoriesName());
		}

		// set borrower data
		setBorrowerUserName(App.userList.get(App.loginIndex).getUsername());

	}

	@Override
	public void setTotalBorrowDur(int borrowDate, int borrowMonth, int borrowYear, int borrowDateDue,
			int borrowMonthDue, int borrowYearDue) {
		LocalDate dateBefore = LocalDate.of(borrowYear, borrowMonth, borrowDate);
		LocalDate dateAfter = LocalDate.of(borrowYearDue, borrowMonthDue, borrowDateDue);

		int noOfDays = (int) ChronoUnit.DAYS.between(dateBefore, dateAfter);

		setDateBorrow(borrowDate);
		setMonthBorrow(borrowMonth);
		setYearBorrow(borrowYear);
		setDateBorrowDue(borrowDateDue);
		setMonthBorrowDue(borrowMonthDue);
		setYearBorrowDue(borrowYearDue);
		setTotalBorrowDuration(noOfDays);
	}

	@Override
	public void countTotalPrice() {
		int borrowDuration = getTotalBorrowDuration();
		int lendPrice = getLendPrice();
	
		setTotalBorrowPrice(lendPrice * borrowDuration);
	}

	@Override
	public void countDeposit() {
		int cameraPrice = getCameraPrice();
		int deposit = (int) (cameraPrice * (25 / 100.0f));
		
		setTotalDeposit(deposit);
	}

	public void confirmPayment(int tempProvCameraIndex, String paymentMethod, boolean isPaid) {
		// set status camera
		App.providerProductList.get(tempProvCameraIndex).setLended(true);
		App.providerProductList.get(tempProvCameraIndex).setBorrowerOrderID(getOrderID());
	
		// Add to arraylist
		App.borrowerProductList.add(new Borrower(getCameraID(), getCameraBrand(), getCameraType(), getCameraName(), getCameraPrice(), getLendPrice(), 
				true, getBorrowerUserName(), getProviderUserName(), getOrderID(), isIncludeAccessories(), getAccessoriesBorrowedName(), 
				getDateBorrow(), getMonthBorrow(), getYearBorrow(), getDateBorrowDue(), getMonthBorrowDue(), getYearBorrowDue(), 
				getTotalBorrowDuration(), getTotalBorrowPrice(), getTotalDeposit(), paymentMethod, isPaid, false, 0, 0, 0));

	}

	@Override
	public void extendBorrowDur(String orderID, int newBorrowDateDue, int newBorrowMonthDue, int newBorrowYearDue) {
		
		LocalDate dateBefore;
		LocalDate dateAfter;

		int extendDuration;

		for(int i=0;i<App.borrowerProductList.size();i++) {
			if(App.borrowerProductList.get(i).getOrderID().equals(orderID)) {
				// extend duration
				dateBefore = LocalDate.of(App.borrowerProductList.get(i).getYearBorrowDue(), App.borrowerProductList.get(i).getMonthBorrowDue(), 
						App.borrowerProductList.get(i).getDateBorrowDue());
				dateAfter = LocalDate.of(newBorrowYearDue, newBorrowMonthDue, newBorrowDateDue);
				extendDuration =  (int) ChronoUnit.DAYS.between(dateBefore, dateAfter);
				
				App.borrowerProductList.get(i).setExtended(true);
				App.borrowerProductList.get(i).setDateBorrowDue(newBorrowDateDue);
				App.borrowerProductList.get(i).setMonthBorrowDue(newBorrowMonthDue);
				App.borrowerProductList.get(i).setYearBorrowDue(newBorrowYearDue);
				App.borrowerProductList.get(i).setTotalExtendDuration(extendDuration);
				App.borrowerProductList.get(i).setExtendPrice(App.borrowerProductList.get(i).getLendPrice() * extendDuration);
			}
		}		
	}

	// MULTI THREADING
//	@Override
//	public void run() {
//		try {
//			System.out.println(Thread.currentThread().getId() + "is running");
//			countTotalPrice();
//			countDeposit();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//	}

}
