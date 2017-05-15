package com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

public class CountByPageInfoElementGenerator extends AbstractXmlElementGenerator {

  public CountByPageInfoElementGenerator(boolean isSimple) {
    super();
  }

  @Override
  public void addElements(XmlElement parentElement) {
    XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

    answer.addAttribute(new Attribute("id", "countByPageInfo")); //$NON-NLS-1$
    answer.addAttribute(new Attribute("resultType", "java.lang.Integer")); //$NON-NLS-1$ //$NON-NLS-2$
    answer.addAttribute(new Attribute("parameterType", "PageInfo")); //$NON-NLS-1$

    context.getCommentGenerator().addComment(answer);

    StringBuilder sb = new StringBuilder();

    sb.append("select count(id) count from "); //$NON-NLS-1$
    sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime()); // 数据库表名
    answer.addElement(new TextElement(sb.toString()));

    parentElement.addElement(answer);
  }
}
