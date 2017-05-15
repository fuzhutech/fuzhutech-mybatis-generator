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
public class BatchInsertMethodGenerator extends AbstractJavaMapperMethodGenerator {

  public BatchInsertMethodGenerator(boolean isSimple) {
    super();
  }

  @Override
  public void addInterfaceElements(Interface interfaze) {
    Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
    Method method = new Method();

    method.setReturnType(FullyQualifiedJavaType.getIntInstance());
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setName(introspectedTable.getInsertStatementId());

    importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

    FullyQualifiedJavaType parameterType = FullyQualifiedJavaType.getNewListInstance();
    FullyQualifiedJavaType listType;
    if (introspectedTable.getRules().generateBaseRecordClass()) {
      listType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
    } else if (introspectedTable.getRules().generatePrimaryKeyClass()) {
      listType = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
    } else {
      throw new RuntimeException("RuntimeError.12"); //$NON-NLS-1$
    }

    importedTypes.add(listType);
    parameterType.addTypeArgument(listType);

    importedTypes.add(parameterType);
    method.addParameter(new Parameter(parameterType, "list")); //$NON-NLS-1$

    context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

    addMapperAnnotations(interfaze, method);

    if (context.getPlugins().clientInsertMethodGenerated(method, interfaze, introspectedTable)) {
      interfaze.addImportedTypes(importedTypes);
      interfaze.addMethod(method);
    }
  }

  public void addMapperAnnotations(Interface interfaze, Method method) {}
}
