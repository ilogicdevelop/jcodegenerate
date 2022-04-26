package com.xzh.codeGenerate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzh.codeGenerate.def.FtlDef;
import com.xzh.codeGenerate.factory.CodeGenerateFactory;
import com.xzh.codeGenerate.utils.JaxbUtil;
import com.xzh.codeGenerate.xmlbean.CustomMethod;
import com.xzh.codeGenerate.xmlbean.Data;


public class Generate {

	private static final Logger logger	=	LoggerFactory.getLogger(Generate.class);
//	@Test
	private static void config(Data data) {
		/** 此处修改成你的 表名 和 中文注释***/
		 String codeName ="table";//中文注释  当然你用英文也是可以的 
		 String keyType = FtlDef.KEY_TYPE_02;//主键生成方式 01:UUID  02:自增
		 String[] tables	=	data.getTables().split(",");
		 String[] ingnoreFields	=	{"au_id1","auditor1","audit_time1","au_id2","auditor2",
				 "audit_time2","au_id3","auditor3","audit_time3","url_1","dynurl_1","style_1","creator","mu_id","mender","fname_prefix","published","audit_flag","reject_reason1","reject_reason2","reject_reason3","workflow"};
		 //以下参数都由参数接收
		 //HTTP协议
		 String	postmethod	=	data.getHttpmethods();
		 //contorller名称
		 String	cname	=	data.getControllername();
//		 项目路径
		 String projectPath	=	data.getPath();
		 //额外方法
		 //自定义sql（需要开发人员控制好格式）
		 List<CustomMethod>	customlist	=	data.getCustomMethodlist();
		 
		 Map<String,String>	daoMethods	=	new HashMap<String, String>();
		 logger.debug("list size is {}",customlist.size());
		 if(customlist!=null && customlist.size()>0){
				 for (CustomMethod customMethod : customlist) {
					 daoMethods.put(customMethod.getName(), customMethod.getSql());
				 }
		 }
		 
		 
		 Map<String,Map<String, String>> configuration	=	new HashMap<>();
		 Map<String,String>	cparameters	=	new HashMap<>();
		 Map<String,String>	parameters	=	new HashMap<String, String>();
		 cparameters.put("String", VALIDENUM.MOBILE.value);
		 
		 parameters.put("method", postmethod.toUpperCase());
		 parameters.put("name", cname.toLowerCase());
		 parameters.put("projectPath", projectPath);
		 
		 configuration.put("cparameters", cparameters);
		 configuration.put("parameters", parameters);
		 configuration.put("daoMethods", daoMethods);
		 
		 StringBuilder sb	=	new StringBuilder();
		 for (String string : tables) {
			 if(StringUtils.isNotBlank(string))
				 CodeGenerateFactory.codeGenerate(string, codeName,sb.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).toString(),keyType,ingnoreFields,configuration);
			 sb.delete(0, sb.length());	
		 }

	}
	
	public static enum VALIDENUM{
		MOBILE(0,"mobile");
		private int key;
		private String value;
		private VALIDENUM(int index,String value){
			this.key	=	index;
			this.value	=	value;
		}
		public static	VALIDENUM	get(int index){
			VALIDENUM[] validEnums	=	VALIDENUM.values();
			for (VALIDENUM validenum : validEnums) {
				if(validenum.key ==index){
					return validenum;
				}
			}
			return null;
		}
	}
	public static String getXml(String sql){
//		d_id where a.insuranceprice=#{price} and b.paystatus=#{status}";
//		String parseSql	=	"\\wwhere\\s+[\\w+\\d*][.]?(.*?\\s+)+.*";
//		String parseSql	=	"^(\\w+\\s+where)(\\s+([\\w\\d]*?)\\s+)*(.*)$";
		String parseSql	=	"where\\s+(\\w+?\\s*[!=|=|<|>|like]{1}\\s*[']?[\\w|\\d]+[']?).*$";
		Pattern pattern	=	Pattern.compile(parseSql);
		Matcher	matcher	=	pattern.matcher(sql);
		int j	=	0;
		while(matcher.find()){
			System.out.println(matcher.groupCount());
			for(int i=0;i<matcher.groupCount();i++)
				System.out.println(matcher.group(i));
			j++;
		}
		return "";
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String xmlPath	=	StringUtils.EMPTY;
		if(args.length>0)
			xmlPath	=	args[0];
//		xmlPath	=	"e:\\config.xml";
		Data	data	=	JaxbUtil.converyToJavaBean(JaxbUtil.getXmlFromFile(xmlPath), Data.class);
		config(data);

}
}
