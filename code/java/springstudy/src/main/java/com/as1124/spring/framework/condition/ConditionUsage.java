package com.as1124.spring.framework.condition;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;

import com.as1124.spring.framework.IMedia;

/**
 * Spring 条件化装配Bean, {@link Condition}的用法
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class ConditionUsage implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Environment springEnv = context.getEnvironment();
		springEnv.getDefaultProfiles();
		BeanDefinitionRegistry beanRegistry = context.getRegistry();
		if (beanRegistry.isAlias("puShu") || beanRegistry.isBeanNameInUse("puShu")) {
			System.out.println("PuShuDisc 加载了");
		} else {
			System.out.println("没有 PuShuDisc-Bean");
		}

		ConfigurableBeanFactory beanFactory = context.getBeanFactory();
		try {
			Object obj = beanFactory.getBean("puShu");
			if (obj == null) {
				obj = beanFactory.getBean(IMedia.class);
			}
			System.out.println("IMedia-Bean = " + obj.getClass().getName());
		} catch (Exception e) {
			System.out.println("Bean Factory 还没有创建IMedia-Bean!!");
		}

		if (metadata instanceof AnnotationMetadata) {
			System.out.println("当前准备Bean== " + ((AnnotationMetadata) metadata).getClassName());
		} else if (metadata instanceof MethodMetadata) {
			System.out.println("当前准备Bean== " + ((MethodMetadata) metadata).getReturnTypeName());
		}

		return true;
	}

}
