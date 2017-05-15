package com.fuzhutech.mybatis.generator.codegen.mybatis3;

import java.util.ArrayList;
import java.util.List;

import com.fuzhutech.mybatis.generator.codegen.mybatis3.javamapper.JavaClientGenerator;
import com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper.XmlGenerator;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
import org.mybatis.generator.codegen.mybatis3.javamapper.AnnotatedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.MixedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator;
import org.mybatis.generator.codegen.mybatis3.model.ExampleGenerator;
import org.mybatis.generator.codegen.mybatis3.model.PrimaryKeyGenerator;
import org.mybatis.generator.codegen.mybatis3.model.RecordWithBLOBsGenerator;

import org.mybatis.generator.internal.ObjectFactory;

public class IntrospectedTableImpl extends IntrospectedTableMyBatis3Impl {

  public IntrospectedTableImpl() {
    super();
  }

  //XMLMapperGenerator更换为自定义实现
  @Override
  protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator,
      List<String> warnings, ProgressCallback progressCallback) {
    if (javaClientGenerator == null) {
      if (context.getSqlMapGeneratorConfiguration() != null) {
        xmlMapperGenerator = new XmlGenerator();
      }
    } else {
      xmlMapperGenerator = javaClientGenerator.getMatchedXMLGenerator();
    }

    initializeAbstractGenerator(xmlMapperGenerator, warnings, progressCallback);
  }

  //JavaMapperGenerator更换为自定义实现
  @Override
  protected AbstractJavaClientGenerator createJavaClientGenerator() {
    if (context.getJavaClientGeneratorConfiguration() == null) {
      return null;
    }

    String type = context.getJavaClientGeneratorConfiguration().getConfigurationType();

    AbstractJavaClientGenerator javaGenerator;
    if ("XMLMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
      javaGenerator = new JavaClientGenerator();
    } else if ("MIXEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
      javaGenerator = new MixedClientGenerator();
    } else if ("ANNOTATEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
      javaGenerator = new AnnotatedClientGenerator();
    } else if ("MAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
      javaGenerator = new JavaClientGenerator();
    } else {
      javaGenerator = (AbstractJavaClientGenerator) ObjectFactory.createInternalObject(type);
    }

    return javaGenerator;
  }

  @Override
  protected void calculateJavaModelGenerators(List<String> warnings,
      ProgressCallback progressCallback) {
    if (getRules().generateExampleClass()) {
      AbstractJavaGenerator javaGenerator = new ExampleGenerator();
      initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
      javaModelGenerators.add(javaGenerator);
    }

    if (getRules().generatePrimaryKeyClass()) {
      AbstractJavaGenerator javaGenerator = new PrimaryKeyGenerator();
      initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
      javaModelGenerators.add(javaGenerator);
    }

    if (getRules().generateBaseRecordClass()) {
      AbstractJavaGenerator javaGenerator = new BaseRecordGenerator();
      initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
      javaModelGenerators.add(javaGenerator);
    }

    if (getRules().generateRecordWithBLOBsClass()) {
      AbstractJavaGenerator javaGenerator = new RecordWithBLOBsGenerator();
      initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
      javaModelGenerators.add(javaGenerator);
    }
  }

  //自定义根据参数是否覆盖XML文件
  @Override
  public List<GeneratedXmlFile> getGeneratedXmlFiles() {
    List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>();

    if (xmlMapperGenerator != null) {
      Document document = xmlMapperGenerator.getDocument();
      // 倒数第二个参数即是 boolean isMergeable；原来在这里固定了传入：true ，导致了前端的overwrite无效；

      /**
       * 原来中的 GeneratedXmlFile 保留；将其中构造函数中的true 修改为 ： false; 设置 isMergeable = false； 在生成
       * xml文件的时候，将不是合并，而是直接覆盖；
       */
      /*
       * GeneratedXmlFile gxf = new GeneratedXmlFile(document, getMyBatis3XmlMapperFileName(),
       * getMyBatis3XmlMapperPackage(),
       * context.getSqlMapGeneratorConfiguration().getTargetProject(), true,
       * context.getXmlFormatter());
       */
      String tmp = context.getProperty("mergeable");
      boolean mergeable = true;
      if ("false".equalsIgnoreCase(tmp)) {
        mergeable = false;
      }

      GeneratedXmlFile gxf = new GeneratedXmlFile(document, getMyBatis3XmlMapperFileName(),
          getMyBatis3XmlMapperPackage(),
          context.getSqlMapGeneratorConfiguration().getTargetProject(), mergeable,
          context.getXmlFormatter());
      if (context.getPlugins().sqlMapGenerated(gxf, this)) {
        answer.add(gxf);
      }
    }

    return answer;
  }
}
