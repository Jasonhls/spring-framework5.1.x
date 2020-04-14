package com.cn.spi;

import java.util.ServiceLoader;

/**
 * @description:
 * SPI机制：
 * 1.当服务提供者提供了接口的一种具体实现后，在工程中的META-INF/services目录下创建一个以"接口全限定名"为命名的文件，
 * 内容为实现类的全限定名；
 * 2.接口实现类所在的工程 classpath中；
 * 3.主程序通过java.util.ServiceLoader动态装载实现模块，它通过扫描META-INF/services目录下的配置文件找到实现类的全限定名
 * 4.SPI的实现类必须携带一个不带参数的构造方法
 * @author: Administrator
 * @create: 2020/3/27 19:09
 **/
public class MainClass {

	public static void main(String[] args) {
		ServiceLoader<IParseDoc> iParseDocs = ServiceLoader.load(IParseDoc.class);
		for (IParseDoc iParseDoc : iParseDocs){
			iParseDoc.parse();
		}
	}
}
