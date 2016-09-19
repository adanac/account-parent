package com.adanac.module.accout.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.InitializingBean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by song on 2016/9/17.
 */
public class AccountCaptchaServiceImpl implements AccountCaptchaService, InitializingBean {
    private DefaultKaptcha producer;

    private Map<String, String> captchaMap = new HashMap<>();
    private List<String> preDefinedTexts;
    private int textCount = 0;

    @Override
    public String generateCaptchaKey() throws AccountCaptchaException {
        String key = RandomGenerator.getRandomString();
        String value = getCaptchaText();
        captchaMap.put(key, value);
        return key;
    }

    private String getCaptchaText() {
        if (preDefinedTexts != null && !preDefinedTexts.isEmpty()) {
            String text = preDefinedTexts.get(textCount);
            textCount = (textCount + 1) % preDefinedTexts.size();
            return text;
        } else {
            return producer.createText();
        }
    }

    @Override
    public byte[] generateCaptchaImage(String captchaKey) throws AccountCaptchaException {
        String text = captchaMap.get(captchaKey);
        if (text == null) {
            throw new AccountCaptchaException("CaptchaKey %s is not found", captchaKey);
        }
        BufferedImage image = producer.createImage(text);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", out);
        } catch (IOException e) {
            throw new AccountCaptchaException("Failed to write captcha stream", e);
        }
        return out.toByteArray();
    }

    @Override
    public boolean validateCaptcha(String captchaKey, String captchaValue) throws AccountCaptchaException {
        String text = captchaMap.get(captchaKey);
        if (text == null) {
            throw new AccountCaptchaException("CaptchaKey %s is not found!", captchaKey);
        }
        if (text.equals(captchaValue)) {
            captchaMap.remove(captchaKey);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> getPreDefinedTexts() {
        return preDefinedTexts;
    }

    @Override
    public void setPreDefinedTexts(List<String> preDefinedTexts) {
        this.preDefinedTexts = preDefinedTexts;
    }

    /**
     * 初始化图片验证码生成器
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        producer = new DefaultKaptcha();
        producer.setConfig(new Config(new Properties()));
    }
}
