package com.fuzhutech.mybatis.generator.codegen.mybatis3.javamapper;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.SelectAllMethodGenerator;
import com.fuzhutech.mybatis.generator.codegen.mybatis3.javamapper.elements.ExtendsRootInterfaceGenerator;
import com.fuzhutech.mybatis.generator.codegen.mybatis3.xmlmapper.XmlGenerator;
import org.mybatis.generator.config.PropertyRegistry;

public class JavaClientGenerator
    extends org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator {

  public JavaClientGenerator() {
    super(true);
  }

  public JavaClientGenerator(boolean requiresMatchedXMLGenerator) {
    super(requiresMatchedXMLGenerator);
  }

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
    addExtendsRootInterface(interfaze);

    // rootInterface中的方法将不再重新生成
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

    // 将SelectAll打开
    // addSelectAllMethod(interfaze);

    // 增加自定义的方法--目前自定义方法均包含在父接口中

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

  protected void addExtendsRootInterface(Interface interfaze) {
    AbstractJavaMapperMethodGenerator methodGenerator = new ExtendsRootInterfaceGenerator(false);
    initializeAndExecuteGenerator(methodGenerator, interfaze);
  }
  
  protected void addSelectAllMethod(Interface interfaze) {
    AbstractJavaMapperMethodGenerator methodGenerator = new SelectAllMethodGenerator();
    initializeAndExecuteGenerator(methodGenerator, interfaze);
  }

  @Override
  public AbstractXmlGenerator getMatchedXMLGenerator() {
    return new XmlGenerator();
  }


}
