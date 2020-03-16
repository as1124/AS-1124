package com.as1124.java.proxy.cglib;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.as1124.java.proxy.BankServiceImpl;
import com.as1124.java.proxy.IBankService;
import com.as1124.java.proxy.IBankStaff;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class CGlibProxyEnhancer implements MethodInterceptor {

	private Object invokeTarget;

	public CGlibProxyEnhancer(Object target) {
		this.invokeTarget = target;
	}

	@Override
	public Object intercept(Object proxy, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
		String methodName = method.getDeclaringClass().getName() + "#" + method.getName();
		System.out.println("\n[CGlibProxyEnhancer] brefore invoke = " + methodName);
		Object result = method.invoke(invokeTarget, arguments);
		System.out.println("[CGlibProxyEnhancer] finished " + methodName);
		return result;
	}

	public static void main(String[] args) {
		IBankService invokeTarget = new BankServiceImpl();
		Enhancer enhancer = new Enhancer();
		enhancer.setInterfaces(new Class[] { IBankStaff.class, IBankService.class });
		//		enhancer.setSuperclass(IBankService.class);
		enhancer.setCallback(new CGlibProxyEnhancer(invokeTarget));
		Object proxyInstance = enhancer.create();
		Class<?> proxyClass = proxyInstance.getClass();
		System.out.println("IBankService is proxy by = " + proxyClass.getName());
		System.out.println("Proxy can assign to IBankService = " + IBankService.class.isAssignableFrom(proxyClass));
		System.out.println("Proxy can assign to IBankStaff = " + IBankStaff.class.isAssignableFrom(proxyClass));

		IBankService service = (IBankService) proxyInstance;
		service.getBalance("As-1124");
		System.out.println("toString result = " + service.toString());

		// 异常：实际invoke的Target并没有实现 IBankStaff 接口
		System.out.println("getName result = " + service.getName());
	}

}
