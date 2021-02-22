package application;

public class LinearSearch implements Runnable{

	private int startIndex;
	private int endIndex;
	private int option;
	private String searchValue;
	public static int searchIndex = -1;

	public LinearSearch(int startIndex, int endIndex, String searchValue, int option){
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.searchValue = searchValue;
		searchIndex = -1;
		this.option = option;
		
		Thread thread = new Thread(this);
		thread.start();
	}

	
	@Override
	public void run() {
		if(option == 1) {
			for (int i = startIndex; i < endIndex; i++) {
				if(App.providerProductList.get(i).getCameraID().equalsIgnoreCase(searchValue)) {
					searchIndex = i;
				}
			}
		}
		if(option == 2) {
			for (int i = startIndex; i < endIndex; i++) {
				if(App.borrowerProductList.get(i).getOrderID().equalsIgnoreCase(searchValue)) {
					searchIndex = i;
				}
			}
		}
		
//		System.out.println("Search is successful ");
	}
	
}
