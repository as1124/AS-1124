package com.volume2.ch9.security;

import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

/**
 * This program authenticates a user via a custom login and then 
 * executes the SysPropAction with the user's privileges.
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class LoginByOSUser {

	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		try {
			LoginContext context = new LoginContext("");
			context.login();
			System.out.println("认证成功！！！");
			Subject subject = context.getSubject();
			PrivilegedAction<String> action = new PrivilegedAction<String>() {

				@Override
				public String run() {
					return System.getProperty("user.home");
				}
			};
			String result = Subject.doAsPrivileged(subject, action, null);
			System.out.println(result);
			context.logout();
		} catch (LoginException ex) {
			ex.printStackTrace();
		}
	}

}
