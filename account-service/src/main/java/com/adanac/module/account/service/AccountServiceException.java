package com.adanac.module.account.service;

/**
 * Created by song on 2016/9/17.
 */
public class AccountServiceException extends Exception {
    public AccountServiceException(String message) {
        super(message);
    }

    public AccountServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
