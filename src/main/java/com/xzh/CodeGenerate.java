package com.xzh;

import com.xzh.codeGenerate.def.FtlDef;
import com.xzh.codeGenerate.factory.CodeGenerateFactory;
import com.xzh.codeGenerate.utils.JaxbUtil;
import com.xzh.codeGenerate.xmlbean.CustomMethod;
import com.xzh.codeGenerate.xmlbean.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerate {

	private static final Logger logger	=	LoggerFactory.getLogger(CodeGenerate.class);
	private static void config(Data data) {
		/** 此处修改成你的 表名 和 中文注释***/
		 String codeName ="table";//中文注释  当然你用英文也是可以的 
		 String keyType = FtlDef.KEY_TYPE_02;//主键生成方式 01:UUID  02:自增
		 String[] tables	=	data.getTables().split(",");
		 String[] ingnoreFields	=	data.getIngnorefields().split(",");
		 //以下参数都由参数接收
		 //HTTP协议
		 String	postmethod	=	data.getHttpmethods();
		 //contorller名称
		 String	cname	=	data.getControllername();
//		 项目路径：生成文件的基路径
		 String projectPath	=	data.getPath();
		 //额外方法
		 //自定义sql（需要开发人员控制好格式）
		 List<CustomMethod>	customlist	=	data.getCustomMethodlist();
		 
		 Map<String,String>	daoMethods	=	new HashMap<String, String>();
		 if(customlist!=null && customlist.size()>0){
			 logger.debug("list size is {}",customlist.size());
			 for (CustomMethod customMethod : customlist) {
				 if(StringUtils.isNotEmpty(customMethod.getName())){
					 daoMethods.put(customMethod.getName(), customMethod.getSql());
				 }
			 }
		 }
		 
		 Map<String,Map<String, String>> configuration	=	new HashMap<>();
		 Map<String,String>	cparameters	=	new HashMap<>();
		 Map<String,String>	parameters	=	new HashMap<String, String>();
		 
		 parameters.put("method", postmethod.toUpperCase());
		 parameters.put("name", cname.toLowerCase());
		 parameters.put("projectPath", projectPath);
		 
		 configuration.put("cparameters", cparameters);
		 configuration.put("parameters", parameters);
		 configuration.put("daoMethods", daoMethods);
		 
		 StringBuilder sb	=	new StringBuilder();
		 for (String string : tables) {
			 string	=	StringUtils.trim(string);
			 if(StringUtils.isNotBlank(string))
				 CodeGenerateFactory.codeGenerate(string, codeName,sb.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).toString(),keyType,ingnoreFields,configuration);
			 sb.delete(0, sb.length());	
		 }
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String xmlPath;
//		Boolean debug = true;
		Boolean debug = false;
		if (debug) {
//		xmlPath	=	"e:\\config\\config-notifyinfo.xml";
			args = new String[]{"e:\\config.xml"};
		}
		if(args.length>0){
			xmlPath	=	args[0];
			logger.info("args 0 is ----->{}",args[0]);
		}else{
			logger.info("args is empty………………………………");
			return;
		}
		Data	data	=	JaxbUtil.converyToJavaBean(JaxbUtil.getXmlFromFile(xmlPath), Data.class);
		config(data);
	}
}
