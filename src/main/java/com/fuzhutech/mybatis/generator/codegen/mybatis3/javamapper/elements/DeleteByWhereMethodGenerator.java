package com.fuzhutech.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

/**
 * 
 * @author fuzhu
 * 
 */
public class DeleteByWhereMethodGenerator extends AbstractJavaMapperMethodGenerator {

  public DeleteByWhereMethodGenerator(boolean isSimple) {
    super();
  }

  @Override
  public void addInterfaceElements(Interface interfaze) {
    Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(FullyQualifiedJavaType.getIntInstance());
    method.setName("deleteByWhere");


    FullyQualifiedJavaType parameterType =
        new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
    importedTypes.add(parameterType);
    method.addParameter(new Parameter(parameterType, "record")); //$NON-NLS-1$

    context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

    addMapperAnnotations(interfaze, method);

    if (context.getPlugins().clientDeleteByExampleMethodGenerated(method, interfaze,
        introspectedTable)) {
      interfaze.addImportedTypes(importedTypes);
      interfaze.addMethod(method);
    }
  }

  public void addMapperAnnotations(Interface interfaze, Method method) {}
}
