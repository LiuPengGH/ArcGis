package cm.mileagePath;

public class Zfevent {

	private String objects;
	private String uniquecode;
	private String attribute;
	private String longitude;
	private String latitude;
	private String syntime;

	public Zfevent() {
		super();
	}
	public Zfevent(String objects,String uniquecode, String attribute, String longitude, String latitude,String syntime) {
		super();
		this.objects = objects;
		this.uniquecode = uniquecode;
		this.attribute = attribute;
		this.longitude = longitude;
		this.latitude = latitude;
		this.syntime = syntime;

	}

	public String getObjects() {
		return objects;
	}

	public void setObjects(String objects) {
		this.objects = objects;
	}

	public String getUniquecode() {
		return uniquecode;
	}

	public void setUniquecode(String uniquecode) {
		this.uniquecode = uniquecode;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getSyntime() {
		return syntime;
	}

	public void setSyntime(String syntime) {
		this.syntime = syntime;
	}
}
