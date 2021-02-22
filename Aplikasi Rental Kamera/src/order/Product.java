package order;

abstract public class Product {
	
	private String cameraID;
	private String cameraBrand;
	private String cameraType;
	private String cameraName;
	private int cameraPrice = 0;
	protected int lendPrice = 0;
	
	public Product() {
		super();
	}

	public Product(String cameraID, String cameraBrand, String cameraType, String cameraName,
			int cameraPrice, int lendPrice) {
		super();
		this.cameraID = cameraID;
		this.cameraBrand = cameraBrand;
		this.cameraType = cameraType;
		this.cameraName = cameraName;
		this.cameraPrice = cameraPrice;
		this.lendPrice = lendPrice;
	}
	
	

	public String getCameraID() {
		return cameraID;
	}

	public void setCameraID(String cameraID) {
		this.cameraID = cameraID;
	}

	public String getCameraBrand() {
		return cameraBrand;
	}

	public void setCameraBrand(String cameraBrand) {
		this.cameraBrand = cameraBrand;
	}

	public String getCameraType() {
		return cameraType;
	}

	public void setCameraType(String cameraType) {
		this.cameraType = cameraType;
	}

	public String getCameraName() {
		return cameraName;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}

	public int getCameraPrice() {
		return cameraPrice;
	}

	public void setCameraPrice(int cameraPrice) {
		this.cameraPrice = cameraPrice;
	}

	public int getLendPrice() {
		return lendPrice;
	}

	public void setLendPrice(int lendPrice) {
		this.lendPrice = lendPrice;
	}
	
	abstract void generateIDNumber(int date, int month, int year);

}
