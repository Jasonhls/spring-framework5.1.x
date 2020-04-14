import com.cn.ioc.config.AppConfig;
import com.cn.ioc2.AppTwoConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Administrator
 * @create: 2020/3/17 7:34
 **/
public class IOCTest {

	@Test
	public void test(){
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	}

	@Test
	public void testTwo(){
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppTwoConfig.class);
		System.out.println(context.getBean("person"));
	}

	@Test
	public void testMap(){
		List<Sku> list = new ArrayList(1);
		list.add(new Sku("颜色","紫色"));
		Map<String,Student> map = new HashMap<>(2);
		List<Long> liOne = new ArrayList<>(1);
		liOne.add(123L);
		List<Long> liTwo = new ArrayList<>(1);
		liTwo.add(456L);
		map.put("紫色",new Student(liOne,false));
		map.put("红色",new Student(liTwo,false));
		A a = new A();
		a.setSkuName("颜色");
		a.setMap(map);
		List<A> colect = new ArrayList<>(1);
		colect.add(a);
		colect.stream().forEach(m ->{
				for(Map.Entry<String,Student> entry : m.getMap().entrySet()){
					String key = entry.getKey();
					Student value = entry.getValue();
					for (Sku sku : list){
						if(m.getSkuName().equals(sku.getSkuName()) && key.equals(sku.getSkuValue())){
							value.setShow(true);
						}
					}
				}
			}
		);
		System.out.println(colect.toString());
	}
}
