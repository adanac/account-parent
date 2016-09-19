package com.adanac.module.account.captcha;

import com.adanac.module.accout.captcha.AccountCaptchaException;
import com.adanac.module.accout.captcha.AccountCaptchaService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 2016/9/17.
 */
public class AccountCaptchaServiceTest {
    private AccountCaptchaService service;

    @Before
    public void prepare() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("account-captcha.xml");
        service = (AccountCaptchaService) ac.getBean("accountCaptchaService");

    }
    @Test
    public void testGeneratorCaptcha() throws AccountCaptchaException {
        String captchaKey = service.generateCaptchaKey();
        Assert.assertNotNull(captchaKey);

        byte[] captchaImage = service.generateCaptchaImage(captchaKey);
        Assert.assertTrue(captchaImage.length>0);

        File image = new File("target/"+captchaKey+".jpg");
        OutputStream out = null;
        try {
            out = new FileOutputStream(image);
            out.write(captchaImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out!=null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Assert.assertTrue(image.exists()&&image.length()>0);
    }

    @Test
    public void testValidateCaptchaCorrect() throws AccountCaptchaException {
        List<String> preDefinedTexts = new ArrayList<>();
        preDefinedTexts.add("123456");
        preDefinedTexts.add("abcdef");
        service.setPreDefinedTexts(preDefinedTexts);
        String key = service.generateCaptchaKey();
        service.generateCaptchaImage(key
        );
        Assert.assertTrue(service.validateCaptcha(key,"123456"));

        key = service.generateCaptchaKey();
        service.generateCaptchaImage(key);
        Assert.assertTrue(service.validateCaptcha(key,"abcdef"));

    }

    @Test
    public void testValidateCaptchaIncorrect() throws AccountCaptchaException {
        List<String> preDefinedTexts = new ArrayList<>();
        preDefinedTexts.add("12345");
        service.setPreDefinedTexts(preDefinedTexts);
        String key = service.generateCaptchaKey();
        service.generateCaptchaImage(key);
        Assert.assertFalse(service.validateCaptcha(key,"67890"));
    }
}
