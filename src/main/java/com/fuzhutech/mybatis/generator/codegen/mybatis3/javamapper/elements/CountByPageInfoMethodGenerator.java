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
public class CountByPageInfoMethodGenerator extends AbstractJavaMapperMethodGenerator {

  public CountByPageInfoMethodGenerator(boolean isSimple) {
    super();
  }

  @Override
  public void addInterfaceElements(Interface interfaze) {
    FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("com.fuzhutech.utils.PageInfo");

    Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
    importedTypes.add(fqjt);

    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(FullyQualifiedJavaType.getIntInstance());
    method.setName("countByPageInfo");
    method.addParameter(new Parameter(fqjt, "pageInfo")); //$NON-NLS-1$
    context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

    addMapperAnnotations(interfaze, method);

    if (context.getPlugins().clientCountByExampleMethodGenerated(method, interfaze,
        introspectedTable)) {
      interfaze.addImportedTypes(importedTypes);
      interfaze.addMethod(method);
    }
  }

  public void addMapperAnnotations(Interface interfaze, Method method) {}
}
