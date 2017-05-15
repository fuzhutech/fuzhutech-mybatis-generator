package com.fuzhutech.mybatis.generator.internal.types;

import java.sql.Types;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

public class JavaTypeResolverImpl extends JavaTypeResolverDefaultImpl {

  public JavaTypeResolverImpl() {
    super();
    typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT", //$NON-NLS-1$
        new FullyQualifiedJavaType(Integer.class.getName())));
  }

  @Override
  public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
    return super.calculateJavaType(introspectedColumn);
  }
}
