package com.volume2.ch10.script;

import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.swing.JFrame;

import com.java.core.log.JavaCoreLogger;

/**
 * 脚本语言代码与java交互示例
 *
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
public class ScriptEngineCase {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			ScriptEngineManager manager = new ScriptEngineManager();
			String language = "";
			if (args.length == 0) {
				System.out.println("Available factories: ");
				for (ScriptEngineFactory factory : manager.getEngineFactories()) {
					System.out.println(factory.getEngineName());
				}
				language = "js";
			} else {
				language = args[0];
			}

			ScriptEngine engine = manager.getEngineByName(language);
			if (engine == null) {
				System.err.println("No engine for " + language);
				System.exit(1);
			}

			try {
				JFrame frame = ButtonFrame.class.newInstance();
				InputStream in = frame.getClass().getResourceAsStream("init." + language);
				if (in != null) {
					engine.eval(new InputStreamReader(in));
				}
				getComponentBindings(frame, engine);

				Properties events = new Properties();
				in = frame.getClass().getResourceAsStream(language + ".properties");
				events.load(in);

				Iterator<Object> it = events.keySet().iterator();
				while (it.hasNext()) {
					Object e = it.next();
					String[] s = e.toString().split("\\.");
					addListener(s[0], s[1], events.get(e).toString(), engine);
				}
				frame.setTitle("ScriptTests");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			} catch (Exception e) {
				JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
			}
		});
	}

	/**
	 * Gathers all named components in a container.
	 * @param c the component
	 * @param engine 
	 */
	private static void getComponentBindings(Component c, ScriptEngine engine) {
		String name = c.getName();
		if (name != null) {
			engine.put(name, c);
		}
		if (c instanceof Container) {
			for (Component child : ((Container) c).getComponents()) {
				getComponentBindings(child, engine);
			}
		}
	}

	/**
	 * Adds a listener to an object whose listener method executes a script.
	 * 
	 * @param beanName the name of the bean to which the listener should be added
	 * @param eventName the name of the listener type, such as "action" or "change"
	 * @param scriptCode the script code to be executed
	 * @param engine the engine that executes the code
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private static void addListener(String beanName, String eventName, final String scriptCode, ScriptEngine engine)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Object bean = engine.get(beanName);
		EventSetDescriptor descriptor = getEventSetDescriptor(bean, eventName);
		if (descriptor == null)
			return;
		else {
			descriptor.getAddListenerMethod().invoke(bean,
				Proxy.newProxyInstance(null, new Class[] { descriptor.getListenerType() }, new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						engine.eval(scriptCode);
						return null;
					}
				}));
		}
	}

	private static EventSetDescriptor getEventSetDescriptor(Object bean, String eventName)
			throws IntrospectionException {
		for (EventSetDescriptor descriptor : Introspector.getBeanInfo(bean.getClass()).getEventSetDescriptors()) {
			if (descriptor.getName().equals(eventName))
				return descriptor;
		}
		return null;
	}
}
