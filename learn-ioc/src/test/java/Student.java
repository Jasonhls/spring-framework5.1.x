import java.util.List;

/**
 * @description:
 * @author: Administrator
 * @create: 2020/3/25 17:32
 **/
public class Student {
	private List<Long> goodsIds;
	private Boolean isShow;

	public Student() {
	}

	public Student(List<Long> goodsIds, Boolean isShow) {
		this.goodsIds = goodsIds;
		this.isShow = isShow;
	}

	public List<Long> getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(List<Long> goodsIds) {
		this.goodsIds = goodsIds;
	}

	public Boolean getShow() {
		return isShow;
	}

	public void setShow(Boolean show) {
		isShow = show;
	}


}
