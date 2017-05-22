package com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper;


import com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper.elements.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.SimpleSelectAllElementGenerator;

public class XMLMapperGenerator extends org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator {

    @Override
    protected XmlElement getSqlMapElement() {

        XmlElement answer = super.getSqlMapElement();

        // 增加扩展的方法
        addSelectAllElement(answer);
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
