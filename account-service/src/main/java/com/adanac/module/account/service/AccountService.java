package com.adanac.module.account.service;

/**
 * Created by song on 2016/9/17.
 */
public interface AccountService {
    String generateCaptchaKey() throws AccountServiceException;

    byte[] generateCaptchaImage(String captchaKey) throws AccountServiceException;

    void signUp(SignUpRequest signUpRequest) throws AccountServiceException;

    void activate(String activationNumber) throws AccountServiceException;

    void login(String id, String password) throws AccountServiceException;
}
