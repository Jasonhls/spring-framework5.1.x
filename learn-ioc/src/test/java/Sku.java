/**
 * @description:
 * @author: Administrator
 * @create: 2020/3/25 17:35
 **/
public class Sku {
	private String skuName;
	private String skuValue;

	public Sku() {
	}

	public Sku(String skuName, String skuValue) {
		this.skuName = skuName;
		this.skuValue = skuValue;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuValue() {
		return skuValue;
	}

	public void setSkuValue(String skuValue) {
		this.skuValue = skuValue;
	}


}
