package com.as1124.spring.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.AbstractPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.as1124.spring.boot.IDataAction;
import com.as1124.spring.boot.model.Author;

/**
 * Spring Web Security 配置类
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Configuration
@EnableWebSecurity
public class As1124WebSecurityConfiger extends WebSecurityConfigurerAdapter {

	private PasswordEncoder passwordEncoder = new AbstractPasswordEncoder() {

		public boolean matches(CharSequence rawPassword, String encodedPassword) {
			String decodeStr = new String(Hex.decode(encodedPassword));
			return decodeStr.equals(rawPassword);
		};

		@Override
		protected byte[] encode(CharSequence rawPassword, byte[] salt) {
			return rawPassword.toString().getBytes();
		}
	};

	@Autowired
	private IDataAction<Author> authorAction;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 要求所有请求需要登录人员有 'EDIT' 角色
		//		http.authorizeRequests().antMatchers("/").access("hasRole('EDIT')").antMatchers("/**").permitAll().and();
		// 设置登录页面路径
		//				.formLogin().loginPage("/login").failureUrl("/login?error=true");
		super.configure(http);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserDetailsService userService = (String username) -> {
			// 自定义控制登录用户信息
			Author author = authorAction.findOne(1);
			if (author != null) {
				String encodePwd = new String(Hex.encode("root".getBytes()));
				author.setPassword(encodePwd);
				return author;
			} else {
				return null;
			}
		};

		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}
}
