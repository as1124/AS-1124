package com.as1124.spring.persistence;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public final class IPersistenceConstants {

	private IPersistenceConstants() {
		// default constructor
	}

	public static final String JDBC_JAVA = "jdbc-ds-java-injection";

	public static final String JDBC_XML = "jdbc-ds-xml-injection";

	public static final String JNDI_JAVA = "jndi-ds-java-injection";

	public static final String JNDI_XML = "jndi-ds-xml-factory";

	public static final String DBCP_JAVA = "dbcp-ds-java-injection";

	public static final String DBCP_XML = "dbcp-ds-xml-injection";

	public static final String C3P0_XML = "c3p0-ds-xml-injection";

}
