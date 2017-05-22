package com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

/**
 * 
 * @author fuzhu
 * 
 */
public class SelectByPageInfoElementGenerator extends AbstractXmlElementGenerator {

  public SelectByPageInfoElementGenerator(boolean isSimple) {
    super();
  }

  @Override
  public void addElements(XmlElement parentElement) {

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

    answer.addElement(getBaseColumnListElement());
    if (introspectedTable.hasBLOBColumns()) {
      answer.addElement(new TextElement(",")); //$NON-NLS-1$
      answer.addElement(getBlobColumnListElement());
    }

    sb.setLength(0);
    sb.append("from "); //$NON-NLS-1$
    sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
    sb.append(" inner join (select id from ");
    sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
    sb.append(" order by id asc limit #{offset}, #{rows}) t2 using(id)");
    answer.addElement(new TextElement(sb.toString()));
    // [offset,] rows

    parentElement.addElement(answer);
  }
}
