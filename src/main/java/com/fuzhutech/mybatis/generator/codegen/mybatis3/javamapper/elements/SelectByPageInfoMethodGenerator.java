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
public class SelectByPageInfoMethodGenerator extends AbstractJavaMapperMethodGenerator {

  public SelectByPageInfoMethodGenerator(boolean isSimple) {
    super();
  }

  @Override
  public void addInterfaceElements(Interface interfaze) {
    Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
    FullyQualifiedJavaType type = new FullyQualifiedJavaType("com.fuzhutech.utils.PageInfo");
    importedTypes.add(type);
    importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

    Method method = new Method();

    method.setVisibility(JavaVisibility.PUBLIC);

    FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
    FullyQualifiedJavaType listType;
    if (introspectedTable.getRules().generateBaseRecordClass()) {
      listType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
    } else if (introspectedTable.getRules().generatePrimaryKeyClass()) {
      listType = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
    } else {
      throw new RuntimeException("RuntimeError.12"); //$NON-NLS-1$
    }

    importedTypes.add(listType);
    returnType.addTypeArgument(listType);
    method.setReturnType(returnType);

    method.setName("selectByPageInfo");

    method.addParameter(new Parameter(type, "pageInfo")); //$NON-NLS-1$

    context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

    addMapperAnnotations(interfaze, method);

    if (context.getPlugins().clientSelectByExampleWithoutBLOBsMethodGenerated(method, interfaze,
        introspectedTable)) {
      interfaze.addImportedTypes(importedTypes);
      interfaze.addMethod(method);
    }

  }

  public void addMapperAnnotations(Interface interfaze, Method method) {}
}
