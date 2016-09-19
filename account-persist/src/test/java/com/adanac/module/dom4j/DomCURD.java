package com.adanac.module.dom4j;

/**
 * Created by song on 2016/9/17.
 */
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @use 使用Dom4j对指定标签增加内容
 * @author Bird
 *
 */
public class DomCURD{
    @SuppressWarnings("unchecked")
    public static void add() throws Exception{
        SAXReader reader = new SAXReader();
        Document document = reader.read("d://book.xml");

        Element book = document.getRootElement().element("书");
        List list = book.elements();

        Element price = DocumentHelper.createElement("售价");
        price.setText("999元");
        list.add(2, price);

        OutputFormat former =OutputFormat.createPrettyPrint();//设置格式化输出器
        former.setEncoding("UTF-8");

        XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream("d://book.xml"),"UTF-8"),former);
        writer.write(document);
        writer.close();
    }


    public static void delete() throws Exception{
        SAXReader reader = new SAXReader();
        Document document = reader.read("d://book.xml");

        Element price = document.getRootElement().element("书").element("售价");
        price.getParent().remove(price);

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");

        XMLWriter writer = new XMLWriter(new FileOutputStream("d://book.xml"),format);
        writer.write(document);
        writer.close();
    }

    public static void update() throws Exception{
        SAXReader reader = new SAXReader();
        Document document = reader.read("d://book.xml");

        Element price = document.getRootElement().element("书").element("售价");
        price.setText("10086元");

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");

        XMLWriter writer = new XMLWriter(new FileOutputStream("d://book.xml"),format);
        writer.write(document);
        writer.close();
    }

    public static void main(String[] args) throws Exception{
		add();
//		delete();
//        update();
    }
}
