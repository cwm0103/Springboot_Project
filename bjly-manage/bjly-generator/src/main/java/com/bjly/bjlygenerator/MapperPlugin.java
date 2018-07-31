package com.bjly.bjlygenerator;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.rules.Rules;
import org.mybatis.generator.internal.util.StringUtility;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class MapperPlugin extends PluginAdapter {

    private static final String DEFAULT_DAO_SUPER_CLASS = "com.bjly.bjlymybatis.config.BaseMapper";
    private static final String DEFAULT_EXPAND_DAO_SUPER_CLASS = "";
    private String bussDomain = "";
    private String targetDir;
    private String targetPackage;

    private String daoSuperClass;

    private   final char UNDERLINE='_';

    private boolean caseSensitive = false;
    //强制生成注解
    private boolean forceAnnotation = true;

    //开始的分隔符，例如mysql为`，sqlserver为[
    private String beginningDelimiter = "";
    //结束的分隔符，例如mysql为`，sqlserver为]
    private String endingDelimiter = "";
    //数据库模式
    private String schema;

    private TopLevelClass topLevelClass;
    // 扩展
    private String expandDaoTargetPackage;
    private String expandDaoSuperClass;
    private ShellCallback shellCallback = null;


    /**
     * 构造函数
     */
    public MapperPlugin()
    {
        shellCallback=new DefaultShellCallback(false);
    }

    public  String underlineToCamel(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (c==UNDERLINE){
                if (++i<len){
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            }else{
                sb.append(c);
            }
        }
        return upperCase(sb.toString());
    }
    public  String camelToUnderline(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
    public String upperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    public String LowerCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }


    /**
     * 生成XML
     * @param introspectedTable
     */
    @Override
    public void initialized(IntrospectedTable introspectedTable) {

        String modelName = introspectedTable.getTableConfigurationProperty("modelName");
        String model = introspectedTable.getTableConfigurationProperty("model");
        String primarykey = introspectedTable.getPrimaryKeyType();
        String xmlPackage = introspectedTable.getMyBatis3XmlMapperPackage();
        String daoName = modelName + "mapper";
        xmlPackage = xmlPackage + "." + model;

        String fullyQualifiedTableNameAtRuntime = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        //是不是这里
        String s = underlineToCamel(fullyQualifiedTableNameAtRuntime);

        String namespace = targetPackage + "." + daoName + "." + model+"."+s+"Mapper";
        introspectedTable.setMyBatis3XmlMapperPackage(xmlPackage);
        introspectedTable.setMyBatis3FallbackSqlMapNamespace(namespace);

        String modeltype = targetPackage + "." + "webentity"+"."+model+"."+s;

        introspectedTable.setBaseRecordType(modeltype);
        Rules rules = introspectedTable.getRules();
        //introspectedTable.getr;


    }

    /**
     * 处理实体类的包和@Table注解
     *
     * @param topLevelClass
     * @param introspectedTable
     */
    private void processEntityClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //引入JPA注解
        topLevelClass.addImportedType("javax.persistence.*");
        topLevelClass.addImportedType("lombok.*");
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
        GeneratedKey generatedKey = tableConfiguration.getGeneratedKey();
        String idkey = generatedKey.getColumn();
        String runtimeSqlStatement = generatedKey.getRuntimeSqlStatement();
        boolean a= runtimeSqlStatement.equalsIgnoreCase("MySql");
        List<Field> fields = topLevelClass.getFields();
        for (Field d : fields) {

            boolean b = d.getName().equalsIgnoreCase(idkey);

            if (b) {
                d.addAnnotation("@Id");
                String GenerationType = introspectedTable.getTableConfigurationProperty("GenerationType");
                if(GenerationType.equalsIgnoreCase("IDENTITY")) {
                    if(runtimeSqlStatement.equalsIgnoreCase("PostgreSQL")){
                        d.addAnnotation("@GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = \"menuSeq\")");
                        d.addAnnotation("@SequenceGenerator(name = \"menuSeq\", initialValue = 1, allocationSize = 1, sequenceName = \"'"+tableName+"_"+camelToUnderline(idkey)+"_seq'\")");
                    }else if(runtimeSqlStatement.equalsIgnoreCase("MySql")){
                        d.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
                    }
                }else {
                    d.addAnnotation("@GeneratedValue(generator = \"UUID\")");
                }

            }
        }


        //如果包含空格，或者需要分隔符，需要完善
        if (StringUtility.stringContainsSpace(tableName)) {
            tableName = context.getBeginningDelimiter()
                    + tableName
                    + context.getEndingDelimiter();
        }
        //是否忽略大小写，对于区分大小写的数据库，会有用
        if (caseSensitive && !topLevelClass.getType().getShortName().equals(tableName)) {
            topLevelClass.addAnnotation("@Table(name = \"" + getDelimiterName(tableName) + "\")");
        } else if (!topLevelClass.getType().getShortName().equalsIgnoreCase(tableName)) {
            topLevelClass.addAnnotation("@Table(name = \"" + getDelimiterName(tableName) + "\")");
        } else if (StringUtility.stringHasValue(schema)
                || StringUtility.stringHasValue(beginningDelimiter)
                || StringUtility.stringHasValue(endingDelimiter)) {
            topLevelClass.addAnnotation("@Table(name = \"" + getDelimiterName(tableName) + "\")");
        } else if (forceAnnotation) {
            topLevelClass.addAnnotation("@Table(name = \"" + getDelimiterName(tableName) + "\")");
        }
        topLevelClass.addAnnotation("@Data");

        List<org.mybatis.generator.api.dom.java.Method> methods = topLevelClass.getMethods();
        methods.removeAll(methods);


    }



    public String getDelimiterName(String name) {
        StringBuilder nameBuilder = new StringBuilder();
        if (StringUtility.stringHasValue(schema)) {
            nameBuilder.append(schema);
            nameBuilder.append(".");
        }
        nameBuilder.append(beginningDelimiter);
        nameBuilder.append(name);
        nameBuilder.append(endingDelimiter);
        return nameBuilder.toString();
    }

    /**
     * 生成实体
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.topLevelClass =topLevelClass;
        // addSerialVersionUID(topLevelClass, introspectedTable);
        // return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
        processEntityClass(topLevelClass, introspectedTable);
        return true;
    }

    /**
     * 继承父类
     * @param warnings
     * @return
     */
    @Override
    public boolean validate(List<String> warnings) {
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
        String domain = introspectedTable.getTableConfigurationProperty("model");
        //String namespace=targetPackage  + "." + domain + ".dao."+shortName;
        XmlElement root = document.getRootElement();
        List<Attribute> rootAttributes = root.getAttributes();
        introspectedTable.setMyBatis3FallbackSqlMapNamespace("");
        return true;
        //return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        //return false;
        return super.sqlMapGenerated(sqlMap,introspectedTable);
    }

    /**
     * 生成mapper
     * @param introspectedTable
     * @return
     */
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        JavaFormatter javaFormatter = context.getJavaFormatter();
        String modelName = introspectedTable.getTableConfigurationProperty("modelName");
        String model = introspectedTable.getTableConfigurationProperty("model");
        boolean isModelProject = Boolean.valueOf(introspectedTable.getTableConfigurationProperty("isModelProject"));
        String path = introspectedTable.getTableConfigurationProperty("path");


        List<GeneratedJavaFile> mapperJavaFiles = new ArrayList<GeneratedJavaFile>();
        for (GeneratedJavaFile javaFile : introspectedTable.getGeneratedJavaFiles()) {
            CompilationUnit unit = javaFile.getCompilationUnit();
            FullyQualifiedJavaType baseModelJavaType = unit.getType();
            javaFile.getTargetPackage();
            String shortName = baseModelJavaType.getShortName();

            GeneratedJavaFile mapperJavafile = null;

            String daoPackage = targetPackage + ".webmapper"+"."+model;
            Interface mapperInterface = new Interface(daoPackage + "." + shortName + "Mapper");
            mapperInterface.setVisibility(JavaVisibility.PUBLIC);
            mapperInterface.addJavaDocLine("/**");
            mapperInterface.addJavaDocLine(" * 由MyBatis Generator工具自动生成");
            mapperInterface.addJavaDocLine(" */");

            FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType(daoSuperClass);
            FullyQualifiedJavaType springRepository = new FullyQualifiedJavaType("org.springframework.stereotype.Repository");
            // 添加泛型支持
            daoSuperType.addTypeArgument(baseModelJavaType);
            //   mapperInterface.addAnnotation("@Repository");
            mapperInterface.addImportedType(springRepository);
            mapperInterface.addImportedType(baseModelJavaType);
            mapperInterface.addImportedType(daoSuperType);
            mapperInterface.addSuperInterface(daoSuperType);

            String packge="";
            if(isModelProject) {
                targetDir=path + "/bjly-"+modelName+"/" + modelName + "-mapper/src/main/java";
                mapperJavafile = new GeneratedJavaFile(mapperInterface, targetDir, javaFormatter);
            }else{
                targetDir=path + "/bjly-"+modelName+"/src/main/java";
                mapperJavafile = new GeneratedJavaFile(mapperInterface, targetDir, javaFormatter);
            }
            if (!IsFileExites(daoPackage, mapperJavafile)) {
                mapperJavaFiles.add(mapperJavafile);
            }
//                            GeneratedJavaFile serviceInterfaceJavaFile = GeneratedServiceInterface(modelName, shortName, baseModelJavaType, javaFormatter);
//                            if (serviceInterfaceJavaFile != null) {
//                                mapperJavaFiles.add(serviceInterfaceJavaFile);
//                            }
            GeneratedJavaFile serviceJavaFile = GeneratedServiceImp(modelName, shortName, baseModelJavaType, javaFormatter,introspectedTable);
            if (serviceJavaFile != null) {
                mapperJavaFiles.add(serviceJavaFile);
            }
//            GeneratedJavaFile pojoFile = GeneratedPojo(modelName, shortName, introspectedTable, javaFormatter);
//            if (pojoFile != null) {
//                mapperJavaFiles.add(pojoFile);
//            }
        }
        return mapperJavaFiles;
    }


    /**
     *生成实体
     * @param domain
     * @param shortName
     * @param introspectedTable
     * @param javaFormatter
     * @return
     */
    private GeneratedJavaFile GeneratedPojo(String domain, String shortName, IntrospectedTable introspectedTable, JavaFormatter javaFormatter) {
        //String serviceTargetPackage = targetPackage + "." + domain + ".pojo" + "";
        //String serviceTargetPackage = targetPackage + ".webentity"  +"."+domain;
        GeneratedJavaFile javaFile = null;
        String path = introspectedTable.getTableConfigurationProperty("path");
        javaFile = new GeneratedJavaFile(topLevelClass, path+"/bjly-web/"+domain+"-entity/src/main/", javaFormatter);
//        if (IsFileExites(serviceTargetPackage, javaFile)) {
//            return null;
//        }
        return javaFile;
    }

    /**
     * 生成ServiceImp 类
     * @param domain
     * @param shortName
     * @param baseModelJavaType
     * @param javaFormatter
     * @param introspectedTable
     * @return
     */
    private GeneratedJavaFile GeneratedServiceImp(String domain, String shortName, FullyQualifiedJavaType baseModelJavaType, JavaFormatter javaFormatter, IntrospectedTable introspectedTable) {

        String serviceSuperClass = "BaseService";
        GeneratedJavaFile javaFile = null;
        String path = introspectedTable.getTableConfigurationProperty("path");
        String model = introspectedTable.getTableConfigurationProperty("model");
        String serviceTargetPackage = targetPackage + ".webservice"  +"."+model;
        TopLevelClass serviceClass = new TopLevelClass(serviceTargetPackage +"." + shortName + "Service");
        boolean isModelProject = Boolean.valueOf(introspectedTable.getTableConfigurationProperty("isModelProject"));
        serviceClass.setVisibility(JavaVisibility.PUBLIC);
        serviceClass.addJavaDocLine("/**");
        serviceClass.addJavaDocLine(" * 由MyBatis Generator工具自动生成");
        serviceClass.addJavaDocLine(" */");
        serviceClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");
        serviceClass.addImportedType("org.springframework.stereotype.Service");
        serviceClass.addImportedType("tk.mybatis.mapper.entity.Example");
        serviceClass.addImportedType("com.github.pagehelper.*");
        serviceClass.addImportedType("java.util.List");
//        serviceClass.addImportedType(targetPackage + ".BaseService");
//        serviceClass.addImportedType(targetPackage + "." + domain + ".service.I" + shortName + "Service");
        serviceClass.addAnnotation("@Service");
        serviceClass.addImportedType(baseModelJavaType);
        FullyQualifiedJavaType serviceSuperType = new FullyQualifiedJavaType("I" + shortName + "Service");
        // serviceClass.addSuperInterface(serviceSuperType);
        // serviceClass.addImportedType(serviceSuperType);

        // FullyQualifiedJavaType serviceSuper = new FullyQualifiedJavaType(serviceSuperClass);
        // 添加泛型支持
        //serviceSuper.addTypeArgument(baseModelJavaType);
        // serviceClass.setSuperClass(serviceSuper);
        FullyQualifiedJavaType serviceType = new FullyQualifiedJavaType(targetPackage + ".webmapper." + model + "." + shortName + "Mapper");
        FullyQualifiedJavaType pojoType = new FullyQualifiedJavaType(targetPackage + ".webentity."+model + "." + shortName + "");

        Field field = new Field(LowerCase(shortName) + "Mapper", serviceType);
        field.addAnnotation("@Autowired");
        field.setVisibility(JavaVisibility.PRIVATE);
        serviceClass.addField(field);
        //添加增删改查方法
        //增加
        org.mybatis.generator.api.dom.java.Method insertMethod = new org.mybatis.generator.api.dom.java.Method();
        insertMethod.setVisibility(JavaVisibility.PUBLIC);

        insertMethod.setReturnType(FullyQualifiedJavaType.getIntInstance());
        insertMethod.setName("insert");
        Parameter parameter = new Parameter(pojoType,LowerCase(shortName));

        insertMethod.addParameter(parameter);
        context.getCommentGenerator().addGeneralMethodComment(insertMethod,
                introspectedTable);

        insertMethod.addBodyLine("return "+LowerCase(shortName) + "Mapper.insert("+LowerCase(shortName)+");");
        insertMethod.addJavaDocLine("/**");
        insertMethod.addJavaDocLine(" * 新增");
        insertMethod.addJavaDocLine(" */");
        serviceClass.addMethod(insertMethod);


        //修改主键
        org.mybatis.generator.api.dom.java.Method updateMethod = new org.mybatis.generator.api.dom.java.Method();
        updateMethod.setVisibility(JavaVisibility.PUBLIC);

        updateMethod.setReturnType(FullyQualifiedJavaType.getIntInstance());
        updateMethod.setName("updateByPrimaryKey");

        updateMethod.addParameter(parameter);
        context.getCommentGenerator().addGeneralMethodComment(updateMethod,
                introspectedTable);

        updateMethod.addBodyLine("return "+LowerCase(shortName) + "Mapper.updateByPrimaryKey("+LowerCase(shortName)+");");
        updateMethod.addJavaDocLine("/**");
        updateMethod.addJavaDocLine(" * 修改");
        updateMethod.addJavaDocLine(" */");
        serviceClass.addMethod(updateMethod);
        //删除
        org.mybatis.generator.api.dom.java.Method deleteMethod = new org.mybatis.generator.api.dom.java.Method();
        deleteMethod.setVisibility(JavaVisibility.PUBLIC);


        Parameter deparameter = new Parameter(FullyQualifiedJavaType.getObjectInstance(),"id");
        deleteMethod.setReturnType(FullyQualifiedJavaType.getIntInstance());
        deleteMethod.setName("deleteByPrimaryKey");

        deleteMethod.addParameter(deparameter);
        context.getCommentGenerator().addGeneralMethodComment(deleteMethod,
                introspectedTable);

        deleteMethod.addBodyLine("return "+LowerCase(shortName) + "Mapper.deleteByPrimaryKey("+"id"+");");
        deleteMethod.addJavaDocLine("/**");
        deleteMethod.addJavaDocLine(" * 删除");
        deleteMethod.addJavaDocLine(" */");
        serviceClass.addMethod(deleteMethod);

        //查询单条
        org.mybatis.generator.api.dom.java.Method selectMethod = new org.mybatis.generator.api.dom.java.Method();
        selectMethod.setVisibility(JavaVisibility.PUBLIC);



        selectMethod.setReturnType(pojoType);
        selectMethod.setName("selectByPrimaryKey");

        selectMethod.addParameter(deparameter);
        context.getCommentGenerator().addGeneralMethodComment(selectMethod,
                introspectedTable);

        selectMethod.addBodyLine("return "+LowerCase(shortName) + "Mapper.selectByPrimaryKey("+"id"+");");
        selectMethod.addJavaDocLine("/**");
        selectMethod.addJavaDocLine(" * 通过主键查询");
        selectMethod.addJavaDocLine(" */");
        serviceClass.addMethod(selectMethod);
        //查询所有
        org.mybatis.generator.api.dom.java.Method selectAllMethod = new org.mybatis.generator.api.dom.java.Method();
        selectAllMethod.setVisibility(JavaVisibility.PUBLIC);



        FullyQualifiedJavaType newListInstance = FullyQualifiedJavaType.getNewListInstance();
        newListInstance.addTypeArgument(pojoType);
        selectAllMethod.setReturnType(newListInstance);
        selectAllMethod.setName("selectAll");

        // selectAllMethod.addParameter(deparameter);
        context.getCommentGenerator().addGeneralMethodComment(selectAllMethod,
                introspectedTable);
        selectAllMethod.addBodyLine("Example example=new Example("+shortName+".class);");
        selectAllMethod.addBodyLine("return "+LowerCase(shortName) + "Mapper.selectByExample("+"example"+");");
        selectAllMethod.addJavaDocLine("/**");
        selectAllMethod.addJavaDocLine(" * 查询所有");
        selectAllMethod.addJavaDocLine(" */");
        serviceClass.addMethod(selectAllMethod);

        //分页查询

        org.mybatis.generator.api.dom.java.Method pageMethod = new org.mybatis.generator.api.dom.java.Method();
        pageMethod.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType pageInfo = new FullyQualifiedJavaType( "com.github.pagehelper.PageInfo");
        pageInfo.addTypeArgument(pojoType);
        // FullyQualifiedJavaType newListInstance = FullyQualifiedJavaType.getNewListInstance();
        //  newListInstance.addTypeArgument(pojoType);
        pageMethod.setReturnType(pageInfo);
        pageMethod.setName("select");

        //第一个参数
        Parameter pagep=new Parameter(FullyQualifiedJavaType.getIntInstance(),"pageNum");
        Parameter pages=new Parameter(FullyQualifiedJavaType.getIntInstance(),"pageSize");


        pageMethod.addParameter(0,parameter);
        pageMethod.addParameter(1,pagep);
        pageMethod.addParameter(2,pages);
        context.getCommentGenerator().addGeneralMethodComment(pageMethod,
                introspectedTable);
        pageMethod.addBodyLine("Example example=new Example("+shortName+".class);");
        pageMethod.addBodyLine(" PageHelper .startPage(pageNum,pageSize);");
        pageMethod.addBodyLine("List<"+shortName+"> pages = "+LowerCase(shortName) + "Mapper.selectByExample(example);");
        pageMethod.addBodyLine(" PageInfo<"+shortName+"> list=new PageInfo<>(pages);");
        pageMethod.addBodyLine("return list;");
        pageMethod.addJavaDocLine("/**");
        pageMethod.addJavaDocLine(" * 分页查询");
        pageMethod.addJavaDocLine(" */");
        serviceClass.addMethod(pageMethod);



        if(isModelProject) {
            targetDir=path + "/bjly-web/" +domain + "-service/src/main/java";
            javaFile = new GeneratedJavaFile(serviceClass, targetDir, javaFormatter);
        }else {
            targetDir=path + "/bjly-"+domain+"src/main/java";
            javaFile = new GeneratedJavaFile(serviceClass, targetDir, javaFormatter);
        }
        if (IsFileExites(serviceTargetPackage, javaFile)) {
            return null;
        }
        return javaFile;
    }


    /**
     * 判断文件是否存在
     * @param tpackage
     * @param javafile
     * @return
     */
    private boolean IsFileExites(String tpackage, GeneratedJavaFile javafile) {
        try {
            File mapperDir = shellCallback.getDirectory(targetDir, tpackage);
            File mapperFile = new File(mapperDir, javafile.getFileName());
            return mapperFile.exists();
        } catch (ShellException e) {
            e.printStackTrace();
        }
        return false;
    }


    //region  下面所有return false的方法都不生成。这些都是基础的CRUD方法，使用通用Mapper实现

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(org.mybatis.generator.api.dom.java.Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(org.mybatis.generator.api.dom.java.Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(org.mybatis.generator.api.dom.java.Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(org.mybatis.generator.api.dom.java.Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(org.mybatis.generator.api.dom.java.Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(org.mybatis.generator.api.dom.java.Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(org.mybatis.generator.api.dom.java.Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(org.mybatis.generator.api.dom.java.Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(org.mybatis.generator.api.dom.java.Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(org.mybatis.generator.api.dom.java.Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectAllMethodGenerated(org.mybatis.generator.api.dom.java.Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectAllMethodGenerated(org.mybatis.generator.api.dom.java.Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(org.mybatis.generator.api.dom.java.Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(org.mybatis.generator.api.dom.java.Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(org.mybatis.generator.api.dom.java.Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(org.mybatis.generator.api.dom.java.Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectAllElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerApplyWhereMethodGenerated(org.mybatis.generator.api.dom.java.Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerInsertSelectiveMethodGenerated(org.mybatis.generator.api.dom.java.Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerUpdateByPrimaryKeySelectiveMethodGenerated(org.mybatis.generator.api.dom.java.Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }
    //endregion

}
