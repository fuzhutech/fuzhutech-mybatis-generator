package com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper.elements.BatchDeleteElementGenerator;
import com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper.elements.BatchInsertElementGenerator;
import com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper.elements.CountByPageInfoElementGenerator;
import com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper.elements.DeleteByWhereElementGenerator;
import com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectByPageInfoElementGenerator;
import com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SelectByWhereElementGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SimpleSelectAllElementGenerator;

public class XmlGenerator
    extends org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator {
  public XmlGenerator() {
    super();
  }

  protected XmlElement getSqlMapElement() {
    FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
    progressCallback.startTask(getString("Progress.12", table.toString())); //$NON-NLS-1$
    XmlElement answer = new XmlElement("mapper"); //$NON-NLS-1$
    String namespace = introspectedTable.getMyBatis3SqlMapNamespace();
    answer.addAttribute(new Attribute("namespace", //$NON-NLS-1$
        namespace));

    context.getCommentGenerator().addRootComment(answer);

    addResultMapWithoutBLOBsElement(answer);
    addResultMapWithBLOBsElement(answer);
    addExampleWhereClauseElement(answer);
    addMyBatis3UpdateByExampleWhereClauseElement(answer);
    addBaseColumnListElement(answer);
    addBlobColumnListElement(answer);
    addSelectByExampleWithBLOBsElement(answer);
    addSelectByExampleWithoutBLOBsElement(answer);
    addSelectByPrimaryKeyElement(answer);
    addDeleteByPrimaryKeyElement(answer);
    addDeleteByExampleElement(answer);
    addInsertElement(answer);
    addInsertSelectiveElement(answer);
    addCountByExampleElement(answer);
    addUpdateByExampleSelectiveElement(answer);
    addUpdateByExampleWithBLOBsElement(answer);
    addUpdateByExampleWithoutBLOBsElement(answer);
    addUpdateByPrimaryKeySelectiveElement(answer);
    addUpdateByPrimaryKeyWithBLOBsElement(answer);
    addUpdateByPrimaryKeyWithoutBLOBsElement(answer);

    // 将SelectAll打开
    addSelectAllElement(answer);

    // 增加自定义的方法
    addSelectByPageInfoElement(answer);
    addCountByPageInfoElement(answer);
    addSelectByWhereElement(answer);
    addDeleteByWhereElement(answer);
    addBatchInsertElement(answer);
    addBatchDeleteElement(answer);

    return answer;
  }

  protected void addSelectAllElement(XmlElement parentElement) {
    AbstractXmlElementGenerator elementGenerator = new SimpleSelectAllElementGenerator();
    initializeAndExecuteGenerator(elementGenerator, parentElement);
  }

  protected void addSelectByPageInfoElement(XmlElement parentElement) {
    if (introspectedTable.getRules().generateBaseResultMap()) {
      AbstractXmlElementGenerator elementGenerator = new SelectByPageInfoElementGenerator(false);
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addCountByPageInfoElement(XmlElement parentElement) {
    if (introspectedTable.getRules().generateBaseResultMap()) {
      AbstractXmlElementGenerator elementGenerator = new CountByPageInfoElementGenerator(false);
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addSelectByWhereElement(XmlElement parentElement) {
    if (introspectedTable.getRules().generateBaseResultMap()) {
      AbstractXmlElementGenerator elementGenerator = new SelectByWhereElementGenerator(false);
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addDeleteByWhereElement(XmlElement parentElement) {
    if (introspectedTable.getRules().generateBaseResultMap()) {
      AbstractXmlElementGenerator elementGenerator = new DeleteByWhereElementGenerator(false);
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addBatchInsertElement(XmlElement parentElement) {
    if (introspectedTable.getRules().generateBaseResultMap()) {
      AbstractXmlElementGenerator elementGenerator = new BatchInsertElementGenerator(false);
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }

  protected void addBatchDeleteElement(XmlElement parentElement) {
    if (introspectedTable.getRules().generateBaseResultMap()) {
      AbstractXmlElementGenerator elementGenerator = new BatchDeleteElementGenerator(false);
      initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
  }


}
