package com.adanac.module.account.persist;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

/**
 * Created by song on 2016/9/16.
 */
public class AccountPersistServiceTest {
    private AccountPersistService accountPersistService;

    @org.junit.Before
    public void prepare(){
        File dataFile  = new File("target/test-classes/persist-data.xml");
        if (dataFile.exists()){
            dataFile.delete();
        }

        ApplicationContext ctx = new ClassPathXmlApplicationContext("account-persist.xml");
        accountPersistService = (AccountPersistService) ctx.getBean("accountPersistService");
        Account account = new Account();
        account.setId("allen");
        account.setName("AllenLoo");
        account.setEmail("allen@sina.com");
        account.setPassword("allen123");
        account.setActivated(true);
        accountPersistService.createAccount(account);
    }

    @Test
    public void testReadAccout(){
        Account account = accountPersistService.readAccount("allen");
        assertNotNull(account);
        assertEquals("allen",account.getId());
        assertEquals("AllenLoo",account.getName());
        assertEquals("allen@sina.com",account.getEmail());
        assertEquals("allen123",account.getPassword());
        assertTrue(account.isActivated());
    }

}
