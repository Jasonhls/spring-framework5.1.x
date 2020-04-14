import java.util.Map;

/**
 * @description:
 * @author: Administrator
 * @create: 2020/3/25 17:42
 **/
public class A {

	private String skuName;
	private Map<String,Student> map;

	public A() {
	}

	public A(String skuName, Map<String, Student> map) {
		this.skuName = skuName;
		this.map = map;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Map<String, Student> getMap() {
		return map;
	}

	public void setMap(Map<String, Student> map) {
		this.map = map;
	}


}
