package com.as1124.serviceprovider.impl;

import com.as1124.serviceprovider.IAccountService;
import com.as1124.serviceprovider.entity.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author As-1124
 * @since 2020/8/27 14:08
 */
@Service
public class AccountServiceImpl implements IAccountService {

    static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public UserAccount getByPhoneNum(String phoneNum) {
        UserAccount account = new UserAccount();
        account.setPhoneNum(phoneNum);
        account.setName("Hey, 傻狗!!");
        return account;
    }

    @Override
    public boolean add(UserAccount account) {
        logger.info("[As-1124] new user created => " + account.toString());
        return true;
    }

    @Override
    public UserAccount update(UserAccount account) {
        account.setName("[info updated] " + account.getName());
        return account;
    }
}
