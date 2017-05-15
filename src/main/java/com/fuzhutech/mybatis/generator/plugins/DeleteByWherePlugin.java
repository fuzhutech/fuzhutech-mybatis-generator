package com.fuzhutech.mybatis.generator.plugins;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class DeleteByWherePlugin extends PluginAdapter {

  // 如果该方法返回false ，那么插件中的其他方法都不会再被调用
  // @Override
  public boolean validate(List<String> warnings) {
    return true;
  }

  @Override
  public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
      IntrospectedTable introspectedTable) {

    // rootInterfaze
    String enableRootInterfaze = properties.getProperty("rootInterfaze", "true");
    if (enableRootInterfaze.equals("true"))
      generateRootInterfaze(interfaze, introspectedTable);

    // selectByPageInfo
    String enableSelectByPageInfo = properties.getProperty("enableSelectByPageInfo", "true");
    if (enableSelectByPageInfo.equals("true"))
      generateSelectByPageInfo(interfaze, introspectedTable);

    // countByPageInfo
    String enableCountByPageInfo = properties.getProperty("enableCountByPageInfo", "true");
    if (enableCountByPageInfo.equals("true"))
      generateCountByPageInfo(interfaze, introspectedTable);

    // selectByWhere
    String enableSelectByWhere = properties.getProperty("enableSelectByWhere", "true");
    if (enableSelectByWhere.equals("true"))
      generateSelectByWhere(interfaze, introspectedTable);

    // deleteByWhere
    String enableDeleteByWhere = properties.getProperty("enableDeleteByWhere", "true");
    if (enableDeleteByWhere.equals("true"))
      generateDeleteByWhere(interfaze, introspectedTable);

    // batchInsert
    String enableBatchInsert = properties.getProperty("enableBatchInsert", "true");
    if (enableBatchInsert.equals("true"))
      generateBatchInsert(interfaze, introspectedTable);

    // batchDelete
    String enableBatchDelete = properties.getProperty("enableBatchDelete", "true");
    if (enableBatchDelete.equals("true"))
      generateBatchDelete(interfaze, introspectedTable);

    return true;
  }

  @Override
  public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

    XmlElement parentElement = document.getRootElement();

    // selectByPageInfo
    SelectByPageInfoElementGenerator(parentElement, introspectedTable);

    // countByPageInfo
    CountByPageInfoElementGenerator(parentElement, introspectedTable);

    // selectByWhere
    SelectByWhereElementGenerator(parentElement, introspectedTable);

    // deleteByWhere
    DeleteByWhereElementGenerator(parentElement, introspectedTable);

    // batchInsert
    BatchInsertElementGenerator(parentElement, introspectedTable);

    // batchDelete
    BatchDeleteElementGenerator(parentElement, introspectedTable);

    return super.sqlMapDocumentGenerated(document, introspectedTable);
  }

  private void generateRootInterfaze(Interface interfaze, IntrospectedTable introspectedTable) {
    // 获取实体类
    FullyQualifiedJavaType entityType =
        new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
    // import接口
    String rootInterface = "com.fuzhutech.common.dao.BaseMapper";
    interfaze.addImportedType(new FullyQualifiedJavaType(rootInterface));
    interfaze.addSuperInterface(
        new FullyQualifiedJavaType(rootInterface + "<" + entityType.getShortName() + ">"));
    // import实体类
    interfaze.addImportedType(entityType);
  }

  // List<?> selectByPageInfo(PageInfo pageInfo);
  private void generateSelectByPageInfo(Interface interfaze, IntrospectedTable introspectedTable) {

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

    if (context.getPlugins().clientSelectByExampleWithoutBLOBsMethodGenerated(method, interfaze,
        introspectedTable)) {
      interfaze.addImportedTypes(importedTypes);
      interfaze.addMethod(method);
    }

  }

  // int countByPageInfo(PageInfo pageInfo);
  private void generateCountByPageInfo(Interface interfaze, IntrospectedTable introspectedTable) {

    FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("com.fuzhutech.utils.PageInfo");

    Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
    importedTypes.add(fqjt);

    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(FullyQualifiedJavaType.getIntInstance());
    method.setName("countByPageInfo");
    method.addParameter(new Parameter(fqjt, "pageInfo")); //$NON-NLS-1$
    context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

    if (context.getPlugins().clientCountByExampleMethodGenerated(method, interfaze,
        introspectedTable)) {
      interfaze.addImportedTypes(importedTypes);
      interfaze.addMethod(method);
    }
  }

  // List<T> selectByWhere(T record);
  private void generateSelectByWhere(Interface interfaze, IntrospectedTable introspectedTable) {

    Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
    importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setName("selectByWhere");

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

    FullyQualifiedJavaType parameterType =
        new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
    importedTypes.add(parameterType);
    method.addParameter(new Parameter(parameterType, "record")); //$NON-NLS-1$

    context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

    if (context.getPlugins().clientSelectByExampleWithoutBLOBsMethodGenerated(method, interfaze,
        introspectedTable)) {
      interfaze.addImportedTypes(importedTypes);
      interfaze.addMethod(method);
    }

  }

  // deleteByWhere
  private void generateDeleteByWhere(Interface interfaze, IntrospectedTable introspectedTable) {

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

    if (context.getPlugins().clientDeleteByExampleMethodGenerated(method, interfaze,
        introspectedTable)) {
      interfaze.addImportedTypes(importedTypes);
      interfaze.addMethod(method);
    }

  }

  // int batchInsert(List<SelfAccount> list);
  private void generateBatchInsert(Interface interfaze, IntrospectedTable introspectedTable) {

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

    if (context.getPlugins().clientInsertMethodGenerated(method, interfaze, introspectedTable)) {
      interfaze.addImportedTypes(importedTypes);
      interfaze.addMethod(method);
    }
  }

  // void batchDelete(String[] ids);
  private void generateBatchDelete(Interface interfaze, IntrospectedTable introspectedTable) {

    Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

    Method method = new Method();
    method.setVisibility(JavaVisibility.PUBLIC);
    method.setReturnType(FullyQualifiedJavaType.getIntInstance());
    method.setName(introspectedTable.getDeleteByExampleStatementId());


    FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType("String[]");
    method.addParameter(new Parameter(parameterType, "ids")); //$NON-NLS-1$

    context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

    if (context.getPlugins().clientDeleteByExampleMethodGenerated(method, interfaze,
        introspectedTable)) {
      interfaze.addImportedTypes(importedTypes);
      interfaze.addMethod(method);
    }
  }

  // selectByPageInfo
  private void SelectByPageInfoElementGenerator(XmlElement parentElement,
      IntrospectedTable introspectedTable) {

    XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

    answer.addAttribute(new Attribute("id", "selectByPageInfo")); //$NON-NLS-1$

    if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
      answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
          introspectedTable.getResultMapWithBLOBsId()));
    } else {
      answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
          introspectedTable.getBaseResultMapId()));
    }

    answer.addAttribute(new Attribute("parameterType", "PageInfo")); //$NON-NLS-1$

    context.getCommentGenerator().addComment(answer);

    StringBuilder sb = new StringBuilder();
    sb.append("select "); //$NON-NLS-1$
    answer.addElement(new TextElement(sb.toString()));

    answer.addElement(getBaseColumnListElement(introspectedTable));
    if (introspectedTable.hasBLOBColumns()) {
      answer.addElement(new TextElement(",")); //$NON-NLS-1$
      answer.addElement(getBlobColumnListElement(introspectedTable));
    }

    sb.setLength(0);
    sb.append("from "); //$NON-NLS-1$
    sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
    sb.append("inner join (select id from ");
    sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
    sb.append(" order by id asc limit #{offset}, #{rows}) t2 using(id)");
    answer.addElement(new TextElement(sb.toString()));
    // [offset,] rows

    parentElement.addElement(answer);

  }

  // countByPageInfo
  private void CountByPageInfoElementGenerator(XmlElement parentElement,
      IntrospectedTable introspectedTable) {

    XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

    answer.addAttribute(new Attribute("id", "countByPageInfo")); //$NON-NLS-1$
    answer.addAttribute(new Attribute("resultType", "java.lang.Long")); //$NON-NLS-1$ //$NON-NLS-2$
    answer.addAttribute(new Attribute("parameterType", "PageInfo")); //$NON-NLS-1$

    context.getCommentGenerator().addComment(answer);

    StringBuilder sb = new StringBuilder();

    sb.append("select count(id) form "); //$NON-NLS-1$
    sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime()); // 数据库表名
    answer.addElement(new TextElement(sb.toString()));

    parentElement.addElement(answer);
  }

  private XmlElement getBaseColumnListElement(IntrospectedTable introspectedTable) {
    XmlElement answer = new XmlElement("include"); //$NON-NLS-1$
    answer.addAttribute(new Attribute("refid", //$NON-NLS-1$
        introspectedTable.getBaseColumnListId()));
    return answer;
  }

  private XmlElement getBlobColumnListElement(IntrospectedTable introspectedTable) {
    XmlElement answer = new XmlElement("include"); //$NON-NLS-1$
    answer.addAttribute(new Attribute("refid", //$NON-NLS-1$
        introspectedTable.getBlobColumnListId()));
    return answer;
  }

  // selectByWhere
  private void SelectByWhereElementGenerator(XmlElement parentElement,
      IntrospectedTable introspectedTable) {

    XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

    answer.addAttribute(new Attribute("id", "selectByWhere")); //$NON-NLS-1$
    if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
      answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
          introspectedTable.getResultMapWithBLOBsId()));
    } else {
      answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
          introspectedTable.getBaseResultMapId()));
    }

    String parameterType;

    if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
      parameterType = introspectedTable.getRecordWithBLOBsType();
    } else {
      parameterType = introspectedTable.getBaseRecordType();
    }

    answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
        parameterType));

    context.getCommentGenerator().addComment(answer);

    StringBuilder sb = new StringBuilder();

    sb.append("select "); //$NON-NLS-1$
    answer.addElement(new TextElement(sb.toString()));
    answer.addElement(getBaseColumnListElement(introspectedTable));

    if (introspectedTable.hasBLOBColumns()) {
      answer.addElement(new TextElement(",")); //$NON-NLS-1$
      answer.addElement(getBlobColumnListElement(introspectedTable));
    }

    sb.setLength(0);
    sb.append("from "); //$NON-NLS-1$
    sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
    answer.addElement(new TextElement(sb.toString()));

    XmlElement dynamicElement = new XmlElement("where"); //$NON-NLS-1$
    answer.addElement(dynamicElement);

    for (IntrospectedColumn introspectedColumn : ListUtilities
        .removeGeneratedAlwaysColumns(introspectedTable.getAllColumns())) {
      XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
      sb.setLength(0);
      sb.append(introspectedColumn.getJavaProperty());
      sb.append(" != null"); //$NON-NLS-1$
      isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
      dynamicElement.addElement(isNotNullElement);

      sb.setLength(0);
      sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
      sb.append(" = "); //$NON-NLS-1$
      sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
      sb.append(" and ");

      isNotNullElement.addElement(new TextElement(sb.toString()));
    }

    parentElement.addElement(answer);
  }

  // deleteByWhere
  private void DeleteByWhereElementGenerator(XmlElement parentElement,
      IntrospectedTable introspectedTable) {

    XmlElement answer = new XmlElement("delete"); //$NON-NLS-1$

    answer.addAttribute(new Attribute("id", "deleteByWhere")); //$NON-NLS-1$

    String parameterType;

    if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
      parameterType = introspectedTable.getRecordWithBLOBsType();
    } else {
      parameterType = introspectedTable.getBaseRecordType();
    }

    answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
        parameterType));

    context.getCommentGenerator().addComment(answer);

    StringBuilder sb = new StringBuilder();

    sb.append("delete from "); //$NON-NLS-1$
    sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime()); // 数据库表名
    answer.addElement(new TextElement(sb.toString()));

    XmlElement dynamicElement = new XmlElement("where"); //$NON-NLS-1$
    answer.addElement(dynamicElement);

    for (IntrospectedColumn introspectedColumn : ListUtilities
        .removeGeneratedAlwaysColumns(introspectedTable.getAllColumns())) {
      XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
      sb.setLength(0);
      sb.append(introspectedColumn.getJavaProperty());
      sb.append(" != null"); //$NON-NLS-1$
      isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
      dynamicElement.addElement(isNotNullElement);

      sb.setLength(0);
      sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
      sb.append(" = "); //$NON-NLS-1$
      sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
      sb.append(" and ");

      isNotNullElement.addElement(new TextElement(sb.toString()));
    }

    parentElement.addElement(answer);
  }

  // batchInsert
  private void BatchInsertElementGenerator(XmlElement parentElement,
      IntrospectedTable introspectedTable) {
    //
  }

  // batchDelete
  private void BatchDeleteElementGenerator(XmlElement parentElement,
      IntrospectedTable introspectedTable) {
    //
  }

}

