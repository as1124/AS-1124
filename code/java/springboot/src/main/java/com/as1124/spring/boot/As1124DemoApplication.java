package com.as1124.spring.boot;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot 应用启动入口
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@SpringBootApplication
public class As1124DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext bootContext = SpringApplication.run(As1124DemoApplication.class, args);
		System.out.println(String.format("[As1124] 可用于停止应用 = %s # %s, displayName = %s", bootContext.getId(),
			bootContext.getApplicationName(), bootContext.getDisplayName()));
		// 关闭方式
		//1. bootContext.close();
		//2. SpringApplication.exit(bootContext, exitCodeGenerators)
		//3. POST请求访问： /actuator/shutdown
		int stopWay = 0;
		try (Scanner scanner = new Scanner(System.in)) {
			String str;
			while ((str = scanner.nextLine()) != null) {
				if ("quit".equals(str)) {
					break;
				} else if ("exit".equals(str)) {
					stopWay = 1;
					break;
				}
			}
		}
		System.out.println("[As1124]------即将通过控制台停止Spring Boot Application-------");
		if (stopWay == 1) {
			int exitCode = SpringApplication.exit(bootContext, () -> {
				// 处理判断程序是否异常退出，指定退出code
				System.out.println("[As1124]----exitCode => 124 -----");
				return 124;
			});
			System.exit(exitCode);
		} else {
			bootContext.close();
		}
	}

	@Bean
	public static BootApplicationLifecycleCallback lifecycleCallback() {
		return new BootApplicationLifecycleCallback();
	}

}
