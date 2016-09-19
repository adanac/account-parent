package com.adanac.module.account.captcha;

import com.adanac.module.accout.captcha.RandomGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by song on 2016/9/17.
 */
public class RandomGeneratorTest {
    @Test
    public  void testGetRandomString(){
        Set<String> randoms = new HashSet<>(100);
        for(int i = 0; i < randoms.size();i++){
            String random = RandomGenerator.getRandomString();
            Assert.assertFalse(randoms.contains(random));
            randoms.add(random);
        }
    }

}
