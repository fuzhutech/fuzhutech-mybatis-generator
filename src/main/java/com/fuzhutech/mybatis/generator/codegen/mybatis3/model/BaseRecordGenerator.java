package com.fuzhutech.mybatis.generator.codegen.mybatis3.model;


import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class BaseRecordGenerator extends org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator{

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> answer = super.getCompilationUnits();

        if(!answer.isEmpty()){
            TopLevelClass topLevelClass = (TopLevelClass) answer.get(0);

            String rootInterface = context.getJavaModelGeneratorConfiguration()
                    .getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
            if (stringHasValue(rootInterface)) {
                FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
                topLevelClass.addSuperInterface(fqjt);
                topLevelClass.addImportedType(fqjt);
            }
        }

        return answer;
    }
}
