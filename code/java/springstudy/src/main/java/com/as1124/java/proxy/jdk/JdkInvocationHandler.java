package com.as1124.java.proxy.jdk;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.as1124.java.proxy.BankServiceImpl;
import com.as1124.java.proxy.IBankService;
import com.as1124.java.proxy.IBankStaff;

/**
 * JDK Standard Dynamic Proxy Way usage
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class JdkInvocationHandler implements InvocationHandler {

	private Object invokeTarget;

	public JdkInvocationHandler(Object target) {
		this.invokeTarget = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String methodName = method.getDeclaringClass().getName() + "#" + method.getName();
		System.out.println("\n[JdkInvocationHandler] brefore invoke = " + methodName);
		Object result = method.invoke(invokeTarget, args);
		System.out.println("[JdkInvocationHandler] finished " + methodName);
		return result;
	}

	public static void main(String[] args) {
		// 设置系统属性：保存动态生成的代理类；会生成在项目的根目录下 => com/sun/proxy/...
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		InvocationHandler proxyHandler = new JdkInvocationHandler(new BankServiceImpl());
		Class<?> proxyClass = Proxy.getProxyClass(Thread.currentThread().getContextClassLoader(), IBankStaff.class,
			IBankService.class);
		System.out.println("IBankService is proxy by = " + proxyClass.getName());
		System.out.println("Proxy can assign to IBankService = " + IBankService.class.isAssignableFrom(proxyClass));
		System.out.println("Proxy can assign to IBankStaff = " + IBankStaff.class.isAssignableFrom(proxyClass));
		try {
			Constructor<?> ctor = proxyClass.getConstructor(InvocationHandler.class);
			Object obj = ctor.newInstance(proxyHandler);
			IBankService service = (IBankService) obj;
			service.getBalance("As-1124");
			System.out.println("toString result = " + service.toString());

			// 异常：实际invoke的Target并没有实现 IBankStaff 接口; 但是代理定义时IBankStaff 接口又是优先声明
			// 故而在 InvocationHandler#invoke 时发现类型不匹配
			System.out.println("getName result = " + service.getName());
		} catch (ReflectiveOperationException | RuntimeException e) {
			e.printStackTrace();
		}
	}
}
