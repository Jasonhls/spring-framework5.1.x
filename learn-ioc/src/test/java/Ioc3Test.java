import com.cn.ioc3.FactoryBeanService;
//import com.cn.ioc3.Ioc3Config;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description:
 * @author: helisen
 * @create: 2020-08-10 09:51
 **/
public class Ioc3Test {
	@Test
	public void test() {
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Ioc3Config.class);

//		FactoryBeanService bean = context.getBean(FactoryBeanService.class);
//		bean.testFactoryBean();
	}

	@Test
	public void test2() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		FactoryBeanService bean = context.getBean(FactoryBeanService.class);
		bean.testFactoryBean();
	}
}
