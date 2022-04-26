package com.xzh.codeGenerate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzh.codeGenerate.factory.CodeGenerateFactory;

public class CommonPageParser
{
  private static VelocityEngine ve;
  private static final String CONTENT_ENCODING = "UTF-8";
  private static final Logger logger = LoggerFactory.getLogger(CommonPageParser.class);

  private static boolean isReplace = true;

  static
  {
    try
    {
      Properties properties = new Properties();
      /*String templateBasePath = CodeGenerateFactory.getProjectPath() + "/src/main/resources/cengle/template";
      logger.info("velocity template file path is   {}",templateBasePath);
      properties.setProperty("resource.loader", "file");
      properties.setProperty("file.resource.loader.path", templateBasePath);
      properties.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
      properties.setProperty("file.resource.loader.cache", "true");
      properties.setProperty("file.resource.loader.modificationCheckInterval", "30");*/
//      ***************************
      properties.setProperty("resource.loader", "class");
      properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
//      ***************************
//      properties.setProperty("resource.loader", "jar");
//      properties.setProperty("jar.resource.loader.class", "org.apache.velocity.runtime.resource.loader.JarResourceLoader");
//      properties.setProperty("jar.resource.loader.path", "jar:file:WebRoot/WEB-INF/lib/vm.jar");
//      ***************************
      properties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.Log4JLogChute");
      properties.setProperty("runtime.log.logsystem.log4j.logger", "org.apache.velocity");
      properties.setProperty("directive.set.null.allowed", "true");
      VelocityEngine velocityEngine = new VelocityEngine();
      velocityEngine.init(properties);
      ve = velocityEngine;
    } catch (Exception e) {
    	logger.error("pageparse error {}",e);
    }
  }

  public static void WriterPage(VelocityContext context, String templateName, String fileDirPath, String targetFile)
  {
    try
    {
      File file = new File(fileDirPath + targetFile);
      if (!file.exists()) {
        new File(file.getParent()).mkdirs();
      }else if(templateName.indexOf("Service")>-1 && templateName.indexOf("Junit")== -1){
    	  logger.info("Service已经存在，跳过生成");
    	  return;
      }else if (isReplace) {
        logger.info("\u66FF\u6362\u6587\u4EF6:" + file.getAbsolutePath());
      }

      
      Template template = ve.getTemplate("cengle/template/"+templateName, CONTENT_ENCODING);
      FileOutputStream fos = new FileOutputStream(file);
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, CONTENT_ENCODING));
      template.merge(context, writer);
      writer.flush();
      writer.close();
      fos.close();
      logger.info("\u751F\u6210\u6587\u4EF6\uFF1A" + file.getAbsolutePath());
    } catch (Exception e) {
    	logger.error("write page error {}",e);
    }
  }
}