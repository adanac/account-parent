package com.adanac.module.accout.captcha;

import java.util.List;

/**
 * Created by song on 2016/9/17.
 */
public interface AccountCaptchaService {
    /**
     * 生成随机的验证码主键
     * @return
     * @throws AccountCaptchaException
     */
    String generateCaptchaKey() throws AccountCaptchaException;

    /**
     * 生成验证码图片
     * @param captchaKey
     * @return
     * @throws AccountCaptchaException
     */
    byte[] generateCaptchaImage(String captchaKey) throws AccountCaptchaException;

    boolean validateCaptcha(String captchaKey, String captchaValue) throws AccountCaptchaException;


    /**预定义验证码图片的内容
     * y
     * @return
     */
    List<String> getPreDefinedTexts();

    void setPreDefinedTexts(List<String> preDefinedTexts);
}