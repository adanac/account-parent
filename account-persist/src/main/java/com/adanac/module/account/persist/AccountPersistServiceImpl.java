package com.adanac.module.account.persist;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by song on 2016/9/16.
 * 通过操作XML实现账户的持久化
 */
public class AccountPersistServiceImpl implements AccountPersistService {

    private static final String ELEMENT_ROOT = "account_persist";
    private static final String ELEMENT_ACCOUNTS = "accounts";
    private static final String ELEMENT_ACCOUNT = "account";
    private static final String ELEMENT_ACCOUNT_ID = "id";
    private static final String ELEMENT_ACCOUNT_NAME = "name";
    private static final String ELEMENT_ACCOUNT_EMAIL = "email";
    private static final String ELEMENT_ACCOUNT_PASSWORD = "password";
    private static final String ELEMENT_ACCOUNT_ACTIVATED = "activated";

    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    private SAXReader reader = new SAXReader();

    private Document readDocument() throws AccountPersistException {
        File dataFile = new File(file);
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            Document doc = DocumentFactory.getInstance().createDocument();
            Element rootEle = doc.addElement(ELEMENT_ROOT);
            writeDocument(doc);
        }
        try {
            return reader.read(new File(file));
        } catch (DocumentException e) {
            throw new AccountPersistException("unable to read persist data xml", e);
        }

    }

    private void writeDocument(Document doc) throws AccountPersistException {
        Writer out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            XMLWriter writer = new XMLWriter(out, OutputFormat.createPrettyPrint());
            writer.write(doc);
        } catch (IOException e) {
            throw new AccountPersistException("unable to write persist data xml", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                throw new AccountPersistException("unable to close persist data xml", e);
            }
        }
    }

    @Override
    public Account createAccount(Account account) throws AccountPersistException {
        Document document = readDocument();
        Element rootElement = document.getRootElement();
        Element accountsElement = rootElement.addElement(ELEMENT_ACCOUNTS);
        Element accountElement = accountsElement.addElement(ELEMENT_ACCOUNT);
        Element element1 = accountElement.addElement(ELEMENT_ACCOUNT_ID);
        Element element2 = accountElement.addElement(ELEMENT_ACCOUNT_NAME);
        Element element3 = accountElement.addElement(ELEMENT_ACCOUNT_EMAIL);
        Element element4 = accountElement.addElement(ELEMENT_ACCOUNT_PASSWORD);
        Element element5 = accountElement.addElement(ELEMENT_ACCOUNT_ACTIVATED);
        element1.addText(account.getId());
        element2.addText(account.getName());
        element3.addText(account.getEmail());
        element4.addText(account.getPassword());
        element5.addText(true == account.isActivated() ? "true" : "false");
        writeDocument(document);
        return account;
    }

    @Override
    public Account readAccount(String id) throws AccountPersistException {
        Document doc = readDocument();
        Element accountsEle = doc.getRootElement().element(ELEMENT_ACCOUNTS);
        listNodes(accountsEle);

        Iterator<Element> iterator = accountsEle.elementIterator();
        while (iterator.hasNext()) {
            Element e = iterator.next();
            if (ELEMENT_ACCOUNT.equals(e.getName())) {
                return buildAccount(e);
            }
        }
        return null;
    }

    //遍历当前节点下的所有节点
    public void listNodes(Element node) {
        System.out.println("当前节点的名称：" + node.getName());
        //首先获取当前节点的所有属性节点
        List<Attribute> list = node.attributes();
        //遍历属性节点
        for (Attribute attribute : list) {
            System.out.println("属性" + attribute.getName() + ":" + attribute.getValue());
        }
        //如果当前节点内容不为空，则输出
        if (!(node.getTextTrim().equals(""))) {
            System.out.println(node.getName() + "：" + node.getText());
        }
        //同时迭代当前节点下面的所有子节点
        //使用递归
        Iterator<Element> iterator = node.elementIterator();
        while (iterator.hasNext()) {
            Element e = iterator.next();
            listNodes(e);
        }
    }

    private Account buildAccount(Element accountEle) {
        Account account = new Account();
        account.setId(accountEle.elementText(ELEMENT_ACCOUNT_ID));
        account.setName(accountEle.elementText(ELEMENT_ACCOUNT_NAME));
        account.setEmail(accountEle.elementText(ELEMENT_ACCOUNT_EMAIL));
        account.setPassword(accountEle.elementText(ELEMENT_ACCOUNT_PASSWORD));
        account.setActivated("true".equals(accountEle.elementText(ELEMENT_ACCOUNT_ACTIVATED)) ? true : false);
        return account;
    }

    @Override
    public Account updateAccount(Account account) throws AccountPersistException {
        if (readAccount(account.getId()) != null) {
            deleteAccount(account.getId());

            return createAccount(account);
        }

        return null;
    }

    @Override
    public void deleteAccount(String id) throws AccountPersistException {
        Document doc = readDocument();

        Element accountsEle = doc.getRootElement().element(ELEMENT_ACCOUNTS);

        for (Element accountEle : (List<Element>) accountsEle.elements()) {
            if (accountEle.elementText(ELEMENT_ACCOUNT_ID).equals(id)) {
                accountEle.detach();

                writeDocument(doc);

                return;
            }
        }
    }


}
