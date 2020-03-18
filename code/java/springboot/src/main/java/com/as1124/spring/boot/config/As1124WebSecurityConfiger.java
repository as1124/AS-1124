package com.as1124.spring.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.AbstractPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.as1124.spring.boot.model.Author;

/**
 * Spring Web Security 配置类；演示用户自定义的配置将覆盖boot的自动配置
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Profile("dev")
@Configuration
@EnableWebSecurity
public class As1124WebSecurityConfiger extends WebSecurityConfigurerAdapter {

	private PasswordEncoder passwordEncoder = new AbstractPasswordEncoder() {

		@Override
		public boolean matches(CharSequence rawPassword, String encodedPassword) {
			String decodeStr = new String(Hex.decode(encodedPassword));
			return decodeStr.equals(rawPassword);
		}

		@Override
		protected byte[] encode(CharSequence rawPassword, byte[] salt) {
			return rawPassword.toString().getBytes();
		}
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 要求所有请求需要登录人员有 'EDIT' 角色
		// 设置登录页面路径
		http.authorizeRequests().antMatchers("/").access("hasRole('EDIT')").antMatchers("/**").permitAll().and()
				.formLogin().loginPage("/login").failureUrl("/login?error=true");
		super.configure(http);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 自定义控制登录用户信息
		UserDetailsService userService = (String username) -> new Author("root",
				new String(Hex.encode("root".getBytes())));
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}
}
