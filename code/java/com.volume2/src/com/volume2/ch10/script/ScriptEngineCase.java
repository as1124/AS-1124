package com.volume2.ch10.script;

import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JFrame;

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

			String frameClassName = args.length < 2 ? "buttons1.ButtonFrame" : args[1];

			try {
				JFrame frame = (JFrame) Class.forName(frameClassName).newInstance();
				InputStream in = frame.getClass().getResourceAsStream("init." + language);
				if (in != null) {
					engine.eval(new InputStreamReader(in));
				}
				getComponentBindings(frame, engine);

				Properties events = new Properties();
				in = frame.getClass().getResourceAsStream(language + ".properties");
				events.load(in);

				for (Object e : events.keySet()) {
					String[] s = e.toString().split("\\.");
					addListener(s[0], s[1], events.get(e).toString(), engine);
				}
				frame.setTitle("ScriptTests");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			} catch (ScriptException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
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
		if (c.getClass().isInstance(java.awt.Container.class)) {
			for (Component child : ((Container) c).getComponents()) {
				getComponentBindings(child, engine);
			}
		}
	}

	private static void addListener(String beanName, String eventName, String scriptCode, ScriptEngine engine) {
		Object bean = engine.get(beanName);
		EventSetDescriptor descriptor = getEventSetDescriptor(bean, eventName);
		if (descriptor == null)
			return;
		else {
			//			descriptor.getAddListenerMethod().invoke(bean, Proxy.newProxyInstance(null, new Class[] {}, (Object a, b, c)->{}));
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
