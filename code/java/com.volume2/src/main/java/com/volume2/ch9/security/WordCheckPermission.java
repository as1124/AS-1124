package com.volume2.ch9.security;

import java.security.Permission;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

/**
 * A permission that checks for bad words
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class WordCheckPermission extends Permission {

	private static final long serialVersionUID = -2940383590801329236L;

	public static void main(String[] args) {
		System.setProperty("java.security.policy", "WordCheck.policy");
		System.setSecurityManager(new SecurityManager());

		SecurityManager secManager = System.getSecurityManager();
		if (secManager == null) {
			System.out.println("虚拟机没有配置安全项");
			return;
		}

		try (Scanner consoleIn = new Scanner(System.in)) {
			String line = "";
			while ((line = consoleIn.nextLine()) != null) {
				if ("quit".equals(line)) {
					break;
				}
				WordCheckPermission p = new WordCheckPermission(line, "avoid");
				try {
					secManager.checkPermission(p);
					System.out.println("内容合法");
				} catch (SecurityException e) {
					System.out.println("!!!!!!!!!不合法内容!!!!!!!!!!!");
				}
			}
			if (consoleIn != null) {
				consoleIn.close();
			}
		}

	}

	private String action;

	public WordCheckPermission(String name, String anAction) {
		super(name);
		this.action = anAction;
	}

	/**
	 * @param permission 待检查权限项
	 * 
	 * @return true：检查通过, false：安全检查没通过
	 */
	@Override
	public boolean implies(Permission permission) {
		if (permission instanceof WordCheckPermission) {
			WordCheckPermission p = (WordCheckPermission) permission;
			if ("avoid".equals(this.action) && "avoid".equals(p.getActions())) {
				Iterator<String> inputWords = p.badWords().iterator();
				Set<String> avoidWords = badWords();
				while (inputWords.hasNext()) {
					if (avoidWords.contains(inputWords.next())) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && WordCheckPermission.class.isAssignableFrom(obj.getClass())) {
			WordCheckPermission p = (WordCheckPermission) obj;
			if (this.action.equals(p.getActions()) && getName().equals(p.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), action);
	}

	@Override
	public String getActions() {
		return this.action;
	}

	public Set<String> badWords() {
		Set<String> words = new HashSet<String>();
		String[] array = getName().split(",");
		for (String str : array) {
			words.add(str);
		}
		return words;
	}

}
