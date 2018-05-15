package com.acceval.core.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

public class CustomSpringPhysicalNamingStrategy extends SpringPhysicalNamingStrategy {

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		Identifier iden = apply(name, jdbcEnvironment);
		return new Identifier("tb_" + iden.getText(), iden.isQuoted());
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		Identifier iden = apply(name, jdbcEnvironment);
		return iden;
	}

	private Identifier apply(Identifier name, JdbcEnvironment jdbcEnvironment) {
		if (name == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder(name.getText().replace('.', '_'));
		for (int i = 1; i < builder.length() - 1; i++) {
			if (isUnderscoreRequired(builder.charAt(i - 1), builder.charAt(i), builder.charAt(i + 1))) {
				builder.insert(i++, '_');
			}
		}
		return getIdentifier(builder.toString(), name.isQuoted(), jdbcEnvironment);
	}

	private boolean isUnderscoreRequired(char before, char current, char after) {
		return (Character.isLowerCase(before) && Character.isUpperCase(current)
				&& Character.isLowerCase(after)) || (Character.isLowerCase(before) && 'I' == current && 'D' == after);
	}
}
