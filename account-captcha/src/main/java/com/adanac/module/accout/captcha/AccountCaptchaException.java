package com.adanac.module.accout.captcha;

import java.io.IOException;

/**
 * Created by song on 2016/9/17.
 */
public class AccountCaptchaException extends Exception {
    private String errCode;
    private String errMsg;
    public AccountCaptchaException(String msg) {
        super(msg);
    }

    public AccountCaptchaException(String errCode, String errMsg) {
        super();
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public AccountCaptchaException(String errCode, IOException e) {
        super(errCode,e);
    }
}
