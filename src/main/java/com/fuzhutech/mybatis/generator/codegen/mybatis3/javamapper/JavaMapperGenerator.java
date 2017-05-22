package com.fuzhutech.mybatis.generator.codegen.mybatis3.javamapper;

import com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

//未使用Plugin形式扩展,是因为Plugin形式无法完成rootInterface中的方法将不再重新生成的需求
public class JavaMapperGenerator extends org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator{

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
                introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type =
                new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);

        String rootInterface =
                introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (!stringHasValue(rootInterface)) {
            rootInterface = context.getJavaClientGeneratorConfiguration()
                    .getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }

        if (stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
            interfaze.addSuperInterface(fqjt);
            interfaze.addImportedType(fqjt);
        }

        //添加泛型rootInterface
        String rootInterfaceGeneric =
                introspectedTable.getTableConfigurationProperty("rootInterfaceGeneric");
        if (!stringHasValue(rootInterfaceGeneric)) {
            rootInterfaceGeneric = context.getJavaClientGeneratorConfiguration()
                    .getProperty("rootInterfaceGeneric");
        }
        if (stringHasValue(rootInterfaceGeneric)) {
            //import 实体类
            FullyQualifiedJavaType entityType =  new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
            interfaze.addImportedType(entityType);

            //import 接口
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterfaceGeneric);
            interfaze.addImportedType(fqjt);

            interfaze.addSuperInterface(
                    new FullyQualifiedJavaType(rootInterfaceGeneric + "<" + entityType.getShortName() + ">"));
        }

        // rootInterface中的方法将不再重新生成,增加自定义的方法--目前自定义方法均包含在父接口中
        addCountByExampleMethod(interfaze);
        addDeleteByExampleMethod(interfaze);
        // addDeleteByPrimaryKeyMethod(interfaze);
        // addInsertMethod(interfaze);
        // addInsertSelectiveMethod(interfaze);
        addSelectByExampleWithBLOBsMethod(interfaze);
        addSelectByExampleWithoutBLOBsMethod(interfaze);
        // addSelectByPrimaryKeyMethod(interfaze);
        addUpdateByExampleSelectiveMethod(interfaze);
        addUpdateByExampleWithBLOBsMethod(interfaze);
        addUpdateByExampleWithoutBLOBsMethod(interfaze);
        // addUpdateByPrimaryKeySelectiveMethod(interfaze);
        addUpdateByPrimaryKeyWithBLOBsMethod(interfaze);
        // addUpdateByPrimaryKeyWithoutBLOBsMethod(interfaze);

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().clientGenerated(interfaze, null, introspectedTable)) {
            answer.add(interfaze);
        }

        List<CompilationUnit> extraCompilationUnits = getExtraCompilationUnits();
        if (extraCompilationUnits != null) {
            answer.addAll(extraCompilationUnits);
        }

        return answer;
    }

    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        return new XMLMapperGenerator();
    }
}
