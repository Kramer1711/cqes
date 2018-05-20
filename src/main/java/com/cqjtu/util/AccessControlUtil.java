package com.cqjtu.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class AccessControlUtil {
	/**
	 * 检测Uri是否是不需要登录就能够访问的地址
	 * 
	 * @param filePath
	 *            xml文件路径
	 * @param uri
	 *            访问地址
	 * @return 是否不需要登录
	 */
	public static boolean isIgnoreUri(String filePath, String uri) {
		if (uri == null) {
			return false;
		}
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(new File(filePath));
			Element root = document.getRootElement();
			List<Element> list = root.elements();
			// 遍历
			for (Element element : list) {
				// 判断Uri是否以设定的忽略路径为开头
				if ("start".equals(element.getName())) {
					Iterator<Node> iterator = element.nodeIterator();
					while (iterator.hasNext()) {
						Node node = iterator.next();
						if (node instanceof Element && "header".equals(((Element) node).getName())) {
							Element childElement = (Element) node;
							if (uri.startsWith(childElement.getText()) == true) {
								return true;
							}
						}
					}
				}
				// 判断Uri是否与忽略路径完全符合
				else if ("equal".equals(element.getName())) {
					Iterator<Node> iterator = element.nodeIterator();
					while (iterator.hasNext()) {
						Node node = iterator.next();
						if (node instanceof Element && "complete".equals(((Element) node).getName())) {
							Element childElement = (Element) node;
							if (childElement.getText().equals(uri) == true) {
								return true;
							}
						}
					}
				}
			}
			// 如果不属于忽略范围，返回false
			return false;
		} catch (DocumentException e) {
			System.out.println("xml文件处理出错!");
			e.printStackTrace();
			return false;
		}
	}
}
