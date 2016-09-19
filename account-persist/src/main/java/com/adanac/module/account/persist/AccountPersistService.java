package com.adanac.module.account.persist;

/**
 * Created by song on 2016/9/16.
 */
public interface AccountPersistService {

    Account createAccount(Account account) throws AccountPersistException;
    Account readAccount(String id) throws AccountPersistException;
    Account updateAccount(Account account) throws AccountPersistException;
    void deleteAccount(String id) throws AccountPersistException;
}
