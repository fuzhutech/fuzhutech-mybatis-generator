package com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * 
 * @author fuzhu
 * 
 */
public class DeleteByWhereElementGenerator extends AbstractXmlElementGenerator {

  public DeleteByWhereElementGenerator(boolean isSimple) {
    super();
  }

  @Override
  public void addElements(XmlElement parentElement) {
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
}
