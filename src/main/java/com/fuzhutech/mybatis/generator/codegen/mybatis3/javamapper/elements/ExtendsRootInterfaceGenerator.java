package com.fuzhutech.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

/**
 * 
 * @author fuzhu
 * 
 */
public class ExtendsRootInterfaceGenerator extends AbstractJavaMapperMethodGenerator {

  public ExtendsRootInterfaceGenerator(boolean isSimple) {
    super();
  }

  @Override
  public void addInterfaceElements(Interface interfaze) {
    Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

    // 实体类
    FullyQualifiedJavaType entityType =
        new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
    importedTypes.add(entityType);

    // 接口
    String rootInterface = "com.fuzhutech.common.dao.BaseMapper";
    FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
    importedTypes.add(fqjt);

    interfaze.addImportedTypes(importedTypes);
    interfaze.addSuperInterface(
        new FullyQualifiedJavaType(rootInterface + "<" + entityType.getShortName() + ">"));

  }

  public void addMapperAnnotations(Interface interfaze, Method method) {}
}
