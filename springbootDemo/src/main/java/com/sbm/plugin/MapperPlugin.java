package com.sbm.plugin;

import java.io.File;
import java.util.ArrayList;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.rules.Rules;

import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * Created by chenwangming on 2017/12/21.
 */
public class MapperPlugin extends PluginAdapter {
    private static final String DEFAULT_DAO_SUPER_CLASS = "com.sbm.domain.Mapper";
    private static final String DEFAULT_EXPAND_DAO_SUPER_CLASS = "";
    private static final String DEFAULT_DOMAIN = "com.sbm.domain";
    private String bussDomain="";
    private String targetDir;
    private String targetPackage;

    private String daoSuperClass;

    // 扩展
    private String expandDaoTargetPackage;
    private String expandDaoSuperClass;
    private ShellCallback shellCallback = null;

    public MapperPlugin() {
        shellCallback = new DefaultShellCallback(false);
    }
    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        bussDomain = introspectedTable.getTableConfigurationProperty("Domain");
        String primarykey= introspectedTable.getPrimaryKeyType();
        String xmlPackage= introspectedTable.getMyBatis3XmlMapperPackage();
        String modelName=introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        String daoName= modelName+"Mapper";
        xmlPackage=xmlPackage+"."+bussDomain;
        String namespace=targetPackage  + "." + bussDomain + ".dao."+daoName;
        introspectedTable.setMyBatis3XmlMapperPackage(xmlPackage);
        introspectedTable.setMyBatis3FallbackSqlMapNamespace(namespace);
        String  modeltype=targetPackage  + "." + bussDomain + ".model."+modelName;
        introspectedTable.setBaseRecordType(modeltype);
        Rules rules=introspectedTable.getRules();
        //introspectedTable.getr;


    }
    /**
     * 生成实体
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // addSerialVersionUID(topLevelClass, introspectedTable);

        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }
    @Override
    public boolean validate(List<String> list) {
        targetDir = properties.getProperty("targetProject");
        boolean valid = stringHasValue(targetDir);

        targetPackage = properties.getProperty("targetPackage");
        boolean valid2 = stringHasValue(targetPackage);

        daoSuperClass = properties.getProperty("daoSuperClass");
        if (!stringHasValue(daoSuperClass)) {
            daoSuperClass = DEFAULT_DAO_SUPER_CLASS;
        }

        expandDaoTargetPackage = properties.getProperty("expandTargetPackage");
        expandDaoSuperClass = properties.getProperty("expandDaoSuperClass");
        if (!stringHasValue(expandDaoSuperClass)) {
            expandDaoSuperClass = DEFAULT_EXPAND_DAO_SUPER_CLASS;
        }
        return valid && valid2;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        String domain = introspectedTable.getTableConfigurationProperty("Domain");
        //String namespace=targetPackage  + "." + domain + ".dao."+shortName;
        XmlElement root=  document.getRootElement();
        List<Attribute> rootAttributes= root.getAttributes();
        introspectedTable.setMyBatis3FallbackSqlMapNamespace("");

        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        return super.sqlMapGenerated(sqlMap,introspectedTable);
    }



    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        JavaFormatter javaFormatter = context.getJavaFormatter();
        String domain = introspectedTable.getTableConfigurationProperty("Domain");
        List<GeneratedJavaFile> mapperJavaFiles = new ArrayList<GeneratedJavaFile>();
        for (GeneratedJavaFile javaFile : introspectedTable.getGeneratedJavaFiles()) {
            CompilationUnit unit = javaFile.getCompilationUnit();
            FullyQualifiedJavaType baseModelJavaType = unit.getType();
            javaFile.getTargetPackage();
            String shortName = baseModelJavaType.getShortName();

            GeneratedJavaFile mapperJavafile = null;

            if (shortName.endsWith("Mapper")) { // 扩展Mapper
                if (stringHasValue(expandDaoTargetPackage)) {
                    Interface mapperInterface = new Interface(
                            expandDaoTargetPackage + "." + shortName.replace("Mapper", "ExpandMapper"));
                    mapperInterface.setVisibility(JavaVisibility.PUBLIC);
                    mapperInterface.addJavaDocLine("/**");
                    mapperInterface.addJavaDocLine(" * " + shortName + "扩展");
                    mapperInterface.addJavaDocLine(" */");

                    FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType(expandDaoSuperClass);
                    mapperInterface.addImportedType(daoSuperType);
                    mapperInterface.addSuperInterface(daoSuperType);

                    mapperJavafile = new GeneratedJavaFile(mapperInterface, targetDir, javaFormatter);
                    try {
                        File mapperDir = shellCallback.getDirectory(targetDir, targetPackage);
                        File mapperFile = new File(mapperDir, mapperJavafile.getFileName());
                        // 文件不存在
                        if (!mapperFile.exists()) {
                            mapperJavaFiles.add(mapperJavafile);
                        }
                    } catch (ShellException e) {
                        e.printStackTrace();
                    }
                }
            } else if (!shortName.endsWith("Example")) { // CRUD Mapper
                String daoPackage= targetPackage + "." + domain + ".dao";
                Interface mapperInterface = new Interface(daoPackage + "." + shortName + "Mapper");
                mapperInterface.setVisibility(JavaVisibility.PUBLIC);
                mapperInterface.addJavaDocLine("/**");
                mapperInterface.addJavaDocLine(" * 由MyBatis Generator工具自动生成");
                mapperInterface.addJavaDocLine(" */");

                FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType(daoSuperClass);
                FullyQualifiedJavaType springRepository= new FullyQualifiedJavaType("org.springframework.stereotype.Repository");
                // 添加泛型支持
                daoSuperType.addTypeArgument(baseModelJavaType);
                mapperInterface.addAnnotation("@Repository");
                mapperInterface.addImportedType(springRepository);
                mapperInterface.addImportedType(baseModelJavaType);
                mapperInterface.addImportedType(daoSuperType);
                mapperInterface.addSuperInterface(daoSuperType);
                mapperJavafile = new GeneratedJavaFile(mapperInterface, targetDir, javaFormatter);
                if (!IsFileExites(daoPackage,mapperJavafile))
                {
                    mapperJavaFiles.add(mapperJavafile);
                }

                GeneratedJavaFile serviceInterfaceJavaFile = GeneratedServiceInterface(domain, shortName, baseModelJavaType, javaFormatter);
                if (serviceInterfaceJavaFile != null) {
                    mapperJavaFiles.add(serviceInterfaceJavaFile);
                }
                GeneratedJavaFile serviceJavaFile = GeneratedServiceImp(domain, shortName, baseModelJavaType, javaFormatter);
                if (serviceJavaFile != null) {
                    mapperJavaFiles.add(serviceJavaFile);
                }

            }
        }
        return mapperJavaFiles;
    }

    /**
     * 生成ServiceImp 类
     *
     * @return
     */
    private GeneratedJavaFile GeneratedServiceImp(String domain, String shortName, FullyQualifiedJavaType baseModelJavaType, JavaFormatter javaFormatter) {
        String serviceTargetPackage = targetPackage + "." + domain + ".service" + ".imp";
        String serviceSuperClass = "BaseService";
        GeneratedJavaFile javaFile = null;

        TopLevelClass serviceClass = new TopLevelClass(serviceTargetPackage + "." + shortName + "ServiceImp");

        serviceClass.setVisibility(JavaVisibility.PUBLIC);
        serviceClass.addJavaDocLine("/**");
        serviceClass.addJavaDocLine(" * 由MyBatis Generator工具自动生成");
        serviceClass.addJavaDocLine(" */");
        serviceClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");
        serviceClass.addImportedType("org.springframework.stereotype.Service");
        serviceClass.addImportedType("java.util.List");
        serviceClass.addImportedType(targetPackage +".BaseService");
        serviceClass.addImportedType(targetPackage + "." + domain + ".service.I" + shortName + "Service" );
        serviceClass.addAnnotation("@Service");
        serviceClass.addImportedType(baseModelJavaType);
        FullyQualifiedJavaType serviceSuperType = new FullyQualifiedJavaType("I" + shortName + "Service");
        serviceClass.addSuperInterface(serviceSuperType);
        serviceClass.addImportedType(serviceSuperType);

        FullyQualifiedJavaType serviceSuper = new FullyQualifiedJavaType(serviceSuperClass);
        // 添加泛型支持
        serviceSuper.addTypeArgument(baseModelJavaType);
        serviceClass.setSuperClass(serviceSuper);
        FullyQualifiedJavaType serviceType=new FullyQualifiedJavaType(targetPackage +"." + domain + ".dao." + shortName + "Mapper");
        Field field=new Field(  shortName+"Mapper",serviceType);
        field.addAnnotation("@Autowired");
        field.setVisibility(JavaVisibility.PRIVATE);
        serviceClass.addField(field);
        javaFile = new GeneratedJavaFile(serviceClass, targetDir, javaFormatter);
        if (IsFileExites(serviceTargetPackage,javaFile)){
            return  null;
        }
        return javaFile;
    }

    /**
     * 生成ServerInterface
     *
     * @return
     */
    private GeneratedJavaFile GeneratedServiceInterface(String domain, String shortName, FullyQualifiedJavaType baseModelJavaType, JavaFormatter javaFormatter) {
        String serviceInterfaceTargetPackage = targetPackage + "." + domain + ".service";
        GeneratedJavaFile javaFile = null;
        String serviceSuperClass=targetPackage +".IService";
        Interface serviceInterface = new Interface(serviceInterfaceTargetPackage + ".I" + shortName + "Service");

        serviceInterface.setVisibility(JavaVisibility.PUBLIC);
        serviceInterface.addJavaDocLine("/**");
        serviceInterface.addJavaDocLine(" * 由MyBatis Generator工具自动生成");
        serviceInterface.addJavaDocLine(" */");
        serviceInterface.addImportedType(baseModelJavaType);

        FullyQualifiedJavaType serviceSuperType = new FullyQualifiedJavaType(serviceSuperClass);
        serviceSuperType.isExplicitlyImported();

        // 添加泛型支持
        serviceSuperType.addTypeArgument(baseModelJavaType);
        serviceInterface.addSuperInterface(serviceSuperType);
        serviceInterface.addImportedType(serviceSuperType);
        javaFile = new GeneratedJavaFile(serviceInterface, targetDir, javaFormatter);
        if (IsFileExites(serviceInterfaceTargetPackage,javaFile)){
            return  null;
        }
        return javaFile;
    }

    private  boolean IsFileExites(String tpackage,GeneratedJavaFile javafile)
    {
        try {
            File mapperDir = shellCallback.getDirectory(targetDir, tpackage);
            File mapperFile = new File(mapperDir, javafile.getFileName());
            return  mapperFile.exists();
        }catch (ShellException e) {
            e.printStackTrace();
        }
        return  false;
    }
}
