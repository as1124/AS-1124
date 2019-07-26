package com.as1124.spring.framework;

import org.springframework.context.annotation.Profile;

/**
 * {@link Profile}注解：其作用是为了部署单元能够适应多个运行环境；
 * 只有当Bean 的<code> Profile </code>与运行环境描述相匹配时, Bean才会被加载. 
 * 没有被 Profile 描述的Bean会始终被装配。
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class AboutSpringProfile {

}
