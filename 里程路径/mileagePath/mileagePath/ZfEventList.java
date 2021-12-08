package cm.mileagePath;

public class ZfEventList {
	private String version;
	private String timestamp;
	//private String table;
	private String List;
	private String sign;
	//private String Type;
	public ZfEventList() {
		super();
	}
	public ZfEventList(String version, String timestamp, String table, String list, String sign, String type) {
		super();
		this.version = version;
		this.timestamp = timestamp;
		//this.table = table;
		List = list;
		this.sign = sign;
		//Type = type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
//	public String getTable() {
//		return table;
//	}
//	public void setTable(String table) {
//		this.table = table;
//	}
	public String getList() {
		return List;
	}
	public void setList(String list) {
		List = list;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
//	public String getType() {
//		return Type;
//	}
//	public void setType(String type) {
//		Type = type;
//	}
	
	
	
}
