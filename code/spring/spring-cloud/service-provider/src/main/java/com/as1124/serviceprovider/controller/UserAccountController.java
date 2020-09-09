package com.as1124.serviceprovider.controller;

import com.as1124.serviceprovider.IAccountService;
import com.as1124.serviceprovider.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author As-1124
 * @since 2020/8/28 16:28
 */
@RestController
@RequestMapping(path = "/user")
public class UserAccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping("/info/{phoneNum}")
    public UserAccount get(@PathVariable("phoneNum") String phoneNum) {
        return accountService.getByPhoneNum(phoneNum);
    }

    @PostMapping("/add")
    public boolean createUser(UserAccount account) {
        return accountService.add(account);
    }

    @PostMapping("/update")
    public UserAccount updateUser(UserAccount newAccountInfo) {
        return accountService.update(newAccountInfo);
    }
}
