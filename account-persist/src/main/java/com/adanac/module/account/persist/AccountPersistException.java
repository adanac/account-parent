package com.adanac.module.account.persist;

import org.dom4j.DocumentException;

/**
 * Created by song on 2016/9/16.
 */
public class AccountPersistException extends RuntimeException{

    public AccountPersistException(String message) {
        super(message);
    }

    public AccountPersistException(String message, Throwable cause) {
        super(message, cause);
    }
}
