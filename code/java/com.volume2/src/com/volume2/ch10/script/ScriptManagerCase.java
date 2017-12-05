package com.volume2.ch10.script;

import java.util.List;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

/**
 * 脚本引擎初探<br/>
 * 从JDK 8开始，<code>Nashorn</code> 取代 <code>Rhino</code> 成为Java的嵌入式JavaScript引擎。
 * Nashorn完全支持ECMAScript 5.1规范以及一些扩展。它使用基于JSR 292的新语言特性，其中包含在JDK 7中引入的invokedynamic，
 * 将JavaScript编译成Java字节码。
 * 与先前的Rhino实现相比，这带来了2到10倍的性能提升，虽然它仍然比Chrome和Node.js中的V8引擎要差一些。
 *
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
public class ScriptManagerCase {

	public static void main(String[] args) {
		ScriptEngineManager scriptManager = new ScriptEngineManager();
		List<ScriptEngineFactory> engineFactories = scriptManager.getEngineFactories();
		for (ScriptEngineFactory factory : engineFactories) {
			System.out.print("[" + factory.getEngineName() + "-" + factory.getEngineVersion() + "] ");
			System.out.print(factory.getLanguageName() + "=" + factory.getLanguageVersion() + ",");
			System.out.println("extensions=" + factory.getExtensions());
		}
	}

}
