package com.fuzhutech.mybatis.generator.codegen.mybatis3;

import com.fuzhutech.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import com.fuzhutech.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator;
import com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.AnnotatedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.MixedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.model.ExampleGenerator;
import org.mybatis.generator.codegen.mybatis3.model.PrimaryKeyGenerator;
import org.mybatis.generator.codegen.mybatis3.model.RecordWithBLOBsGenerator;
import org.mybatis.generator.internal.ObjectFactory;

import java.util.ArrayList;
import java.util.List;

//扩展原有的IntrospectedTableMyBatis3Impl
public class IntrospectedTableMyBatis3Impl extends org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl {

    //47 XMLMapperGenerator更换为自定义实现
    @Override
    protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator,
                                               List<String> warnings, ProgressCallback progressCallback) {
        if (javaClientGenerator == null) {
            if (context.getSqlMapGeneratorConfiguration() != null) {
                xmlMapperGenerator = new XMLMapperGenerator();
            }
        } else {
            xmlMapperGenerator = javaClientGenerator.getMatchedXMLGenerator();
        }

        initializeAbstractGenerator(xmlMapperGenerator, warnings, progressCallback);
    }

    //74 JavaMapperGenerator更换为自定义实现
    @Override
    protected AbstractJavaClientGenerator createJavaClientGenerator() {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return null;
        }

        String type = context.getJavaClientGeneratorConfiguration().getConfigurationType();

        AbstractJavaClientGenerator javaGenerator;
        if ("XMLMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new JavaMapperGenerator();
        } else if ("MIXEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new MixedClientGenerator();
        } else if ("ANNOTATEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new AnnotatedClientGenerator();
        } else if ("MAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new JavaMapperGenerator();
        } else {
            javaGenerator = (AbstractJavaClientGenerator) ObjectFactory.createInternalObject(type);
        }

        return javaGenerator;
    }

    //96 BaseRecordGenerator变更为自定义实现
    protected void calculateJavaModelGenerators(List<String> warnings, ProgressCallback progressCallback) {
        if(this.getRules().generateExampleClass()) {
            AbstractJavaGenerator javaGenerator = new ExampleGenerator();
            this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            this.javaModelGenerators.add(javaGenerator);
        }

        if(this.getRules().generatePrimaryKeyClass()) {
            AbstractJavaGenerator javaGenerator = new PrimaryKeyGenerator();
            this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            this.javaModelGenerators.add(javaGenerator);
        }

        if(this.getRules().generateBaseRecordClass()) {
            AbstractJavaGenerator javaGenerator = new BaseRecordGenerator();
            this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            this.javaModelGenerators.add(javaGenerator);
        }

        if(this.getRules().generateRecordWithBLOBsClass()) {
            AbstractJavaGenerator javaGenerator = new RecordWithBLOBsGenerator();
            this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
            this.javaModelGenerators.add(javaGenerator);
        }

    }

    //170 自定义根据参数是否覆盖XML文件
    @Override
    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>();

        if (xmlMapperGenerator != null) {
            Document document = xmlMapperGenerator.getDocument();
            // 原有函数倒数第二个参数即是 boolean isMergeable；原来在这里固定了传入：true ，导致了前端的overwrite无效；
            //设置 isMergeable = false； 在生成xml文件的时候，将不是合并，而是直接覆盖；

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
