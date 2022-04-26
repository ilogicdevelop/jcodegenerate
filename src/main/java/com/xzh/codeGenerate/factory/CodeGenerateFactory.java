package com.xzh.codeGenerate.factory;

import com.xzh.codeGenerate.CommonPageParser;
import com.xzh.codeGenerate.CreateBean;
import com.xzh.codeGenerate.def.CodeResourceUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CodeGenerateFactory
{
  private static final Logger logger = LoggerFactory.getLogger(CodeGenerateFactory.class);
  private static String url = CodeResourceUtil.URL;
  private static String username = CodeResourceUtil.USERNAME;
  private static String passWord = CodeResourceUtil.PASSWORD;

  private static String buss_package = CodeResourceUtil.bussiPackage;
  private static String mapper_path = CodeResourceUtil.mapper_path;
  private static String domain_path = CodeResourceUtil.domain_path;
  private static String projectPath = getProjectPath();

  
  public static void codeGenerate(String tableName, String codeName, String controllerEntityPackage, String keyType,String[] ingnoreFields,
		  Map<String,Map<String,String>> configuration){
	  
	  codeGenerate(tableName, codeName,"", controllerEntityPackage, keyType,ingnoreFields,configuration);
  }

  /**
   * 
   * @param tableName
   * @param codeName
   * @param entityPackage
   * @param controllerEntityPackage
   * @param keyType
   * @param ingnoreFields
   */
  public static void codeGenerate(String tableName, String codeName, String entityPackage, String controllerEntityPackage, String keyType,String[] ingnoreFields,
		  Map<String,Map<String,String>> configuration)
  {
    CreateBean createBean = new CreateBean();
    createBean.setMysqlInfo(url, username, passWord);

    String className = createBean.getTablesNameToClassName(tableName);
    String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());
    
    String[] types	=	{"add","update","updateBySelective","delete","queryById"};
    String type	=	configuration.get("parameters").get("type");
    projectPath	=	configuration.get("parameters").get("projectPath");
    String srcPath = projectPath + CodeResourceUtil.source_root_package + File.separator;
    String testPath = projectPath + CodeResourceUtil.source_test_root_package + File.separator+ CodeResourceUtil.bussiPackageUrl + File.separator;
    String pckPath = srcPath + CodeResourceUtil.bussiPackageUrl + File.separator;
    String resourcesPath	=	projectPath	+	CodeResourceUtil.source_resources+File.separator;
    String webPath = projectPath + CodeResourceUtil.web_root_package + "" +File.separator +"view"+ File.separator + CodeResourceUtil.bussiPackageUrl + File.separator;
    String entityPath=(entityPackage==null||"".equals(entityPackage))?"": entityPackage + File.separator;
    String cPath=(controllerEntityPackage==null||"".equals(controllerEntityPackage))?"": controllerEntityPackage + File.separator;
    String cname	=	configuration.get("parameters").get("name");
    String Cname	=	firstToUpp(cname);
    //路径配置
    String beanPath = "domain" +File.separator + entityPath + className + ".java";
    
    String servicePath = "service" +File.separator + entityPath + className + Cname+"Service.java";
    String serviceImplPath = "service" +File.separator +"impl" + File.separator + entityPath + className + Cname +"ServiceImpl.java";
    String controllerPath = "controller" +File.separator + className + Cname +"Controller.java";
    String 	sqlMapperPath = "mapper" +File.separator + entityPath + className + "Mapper.xml";
    String 	junitTestPath = "servicetest" +File.separator  + entityPath + className + Cname +"ServiceTest.java";
//    String	mapperPath = "dao" +File.separator +"I" + entityPath + className + "Mapper.java";
    String	mapperPath = "dao" +File.separator + entityPath + className + "Mapper.java";//mybatis-plus不需要前缀：I
    String  daoImplPath = "dao" +File.separator +"impl" +File.separator + entityPath + className + "MapperImpl.java";
    webPath = webPath + entityPath;
    
    VelocityContext context = new VelocityContext();
    context.put("className", className);
    context.put("lowerName", lowerName);
    context.put("codeName", codeName);
    context.put("tableName", tableName);
    context.put("bussPackage", buss_package);
    context.put("mapperPath", mapper_path);
    context.put("domainPath", domain_path);
    context.put("entityPackage", entityPackage==""?null:entityPackage);
    context.put("controllerEntityPackage", controllerEntityPackage==""?null:controllerEntityPackage);
    context.put("config", configuration);
    context.put("cname", cname);
    context.put("Cname", Cname);
    
    Map<String,String> daoMethods	=	configuration.get("daoMethods");
    String methods	=	configuration.get("parameters").get("method");
    List<String> methodlist	=	new ArrayList<String>();
    
    if(StringUtils.isNotBlank(methods)){
    	methodlist	=	Arrays.asList(methods.split(","));
    }
    context.put("methods",methodlist);
    context.put("daoMethods",daoMethods);
    context.put("keyType", keyType);
    try
    {
      context.put("feilds", createBean.getBeanFeilds(tableName,ingnoreFields));
    } catch (Exception e) {
      e.printStackTrace();
    }

    try
    {
      Map<?, ?> sqlMap = createBean.getAutoCreateSql(tableName,ingnoreFields);
      context.put("columnDatas", createBean.getColumnDatas(tableName,ingnoreFields));
      context.put("SQL", sqlMap);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

//    CommonPageParser.WriterPage(context, "EntityTemplate.ftl", pckPath, beanPath);
//    CommonPageParser.WriterPage(context, "DaoTemplate.ftl", pckPath, mapperPath);
//    CommonPageParser.WriterPage(context, "DaoImplTemplate.ftl", pckPath, daoImplPath);//mp版本不需要实现类，只需要接口
//    CommonPageParser.WriterPage(context, "MapperTemplate.xml", resourcesPath, sqlMapperPath); //原始版本的模板


    //Mp版本
    CommonPageParser.WriterPage(context, "EntityTemplate.ftl", pckPath, beanPath);
    CommonPageParser.WriterPage(context, "MpDaoTemplate.ftl", pckPath, mapperPath);
    CommonPageParser.WriterPage(context, "MpMapperTemplate.xml", resourcesPath, sqlMapperPath);//根据mybatisplus进行修改版本-仅保留自定义方法，其他继承自basemapper

//    CommonPageParser.WriterPage(context, "/junit/ServiceJunitTemplate.ftl", testPath, junitTestPath);
//    CommonPageParser.WriterPage(context, "ServiceTemplate.ftl", pckPath, servicePath);
//    CommonPageParser.WriterPage(context, "ServiceImplTemplate.ftl", pckPath, serviceImplPath);
//    CommonPageParser.WriterPage(context, "ControllerTemplate.ftl", pckPath, controllerPath);

   // CommonPageParser.WriterPage(context, "jspTemplate.ftl", webPath, jspPath);

    logger.info("----------------------------\u4EE3\u7801\u751F\u6210\u5B8C\u6BD5---------------------------");
  }

  public static String getProjectPath()
  {
    String path = System.getProperty("user.dir").replace("\\", "/") + "/";
    logger.info("获取默认工程目录 {}",path);
    return path;
  }
  public static String getContextProjectPath()
  {
    try{
//      String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
      String path = CodeGenerateFactory.class.getResource("").getPath();
      path = path.substring(5);//linux下注意最先面的/不要删掉
      path = path.substring(0,path.lastIndexOf("!"));
      path = path.substring(0, path.lastIndexOf("/")+1);
//      String path1 = CodeGenerateFactory.class.getClassLoader().getResource("").toString();//静态类调用报错
      logger.info("get project path: {}",path);

      return path;
    }catch (Exception e){
      logger.error("get project path error", e);
    }
    return "";
  }
  public static String firstToUpp(String str){
	  if(str!=null){
		  char[] array	=	str.toCharArray();
		  array[0]-=32;
		  return String.valueOf(array);
	  }
	  return "";
  }
  public static void main(String[] args) {
  }
}