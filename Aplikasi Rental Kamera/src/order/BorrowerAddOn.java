package order;

public interface BorrowerAddOn {

	void countTotalPrice();
	
	void countDeposit();

	void setTotalBorrowDur(int borrowDate, int borrowMonth, int borrowYear, int borrowDateDue, int borrowMonthDue,
			int borrowYearDue);

	void generateIDNumber(int borrowDate, int borrowMonth, int borrowYear);

	void extendBorrowDur(String orderID, int newBorrowDateDue, int newBorrowMonthDue, int newBorrowYearDue);
}
