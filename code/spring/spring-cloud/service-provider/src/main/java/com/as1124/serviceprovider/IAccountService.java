package com.as1124.serviceprovider;

import com.as1124.serviceprovider.entity.UserAccount;

/**
 * @author As-1124
 * @version 1.0
 * @since 2020/8/27 14:06
 */
public interface IAccountService {

    UserAccount getByPhoneNum(String phoneNum);

    boolean add(UserAccount account);

    UserAccount update(UserAccount account);
}
