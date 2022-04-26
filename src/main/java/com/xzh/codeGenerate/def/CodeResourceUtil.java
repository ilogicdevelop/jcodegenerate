package com.xzh.codeGenerate.def;

import com.xzh.codeGenerate.factory.CodeGenerateFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class CodeResourceUtil {
    private static final Logger logger	=	LoggerFactory.getLogger(CodeResourceUtil.class);
//    private static final ResourceBundle bundle = ResourceBundle.getBundle("cengle/cengle_database");
//    private static final ResourceBundle bundlePath = ResourceBundle.getBundle("cengle/cengle_config");
    private static final ResourceBundle bundlePath = getBundleFromDisk("cengle_config");
    //偷个懒，合并配置文件后，对应的变量使用暂不调整
    private static final ResourceBundle bundle = bundlePath;

    public static String DIVER_NAME = "com.mysql.jdbc.Driver";

    public static String URL = "jdbc:mysql://localhost:3306/febs_cloud_base?useUnicode=true&characterEncoding=UTF-8";

    public static String USERNAME = "root";

    public static String PASSWORD = "root";

    public static String DATABASE_NAME = "febs_cloud_base";

    public static String DATABASE_TYPE = "mysql";
    public static String DATABASE_TYPE_MYSQL = "mysql";
    public static String DATABASE_TYPE_ORACLE = "oracle";

    public static String CENGLE_UI_FIELD_REQUIRED_NUM = "4";

    public static String CENGLE_UI_FIELD_SEARCH_NUM = "3";

    public static String web_root_package = "";

    public static String source_root_package = "src/main/java";

    public static String source_test_root_package = "src/test/java";

    public static String bussiPackage = "sun";
    public static String bussiPackageUrl = "sun";

    public static String entity_package = "entity";
    public static String mapper_path = "";
    public static String domain_path = "";

    // public static String page_package = "page";

    public static String ENTITY_URL = source_root_package + "/" + bussiPackageUrl + "/" + entity_package + "/";

    //public static String PAGE_URL = source_root_package + "/" + bussiPackageUrl + "/" + page_package + "/";

    public static String ENTITY_URL_INX = bussiPackage + "." + entity_package + ".";

    //public static String PAGE_URL_INX = bussiPackage + "." + page_package + ".";
    public static String TEMPLATEPATH;
    public static String CODEPATH = source_root_package + "/" + bussiPackageUrl + "/";

    public static String JSPPATH = web_root_package + "/" + bussiPackageUrl + "/";
    public static String CENGLE_GENERATE_TABLE_ID;
    public static String CENGLE_GENERATE_UI_FILTER_FIELDS;
    public static String SYSTEM_ENCODING;
    public static String source_resources;


    static {
        DIVER_NAME = getDIVER_NAME();
        URL = getURL();
        USERNAME = getUSERNAME();
        PASSWORD = getPASSWORD();
        DATABASE_NAME = getDATABASE_NAME();

        SYSTEM_ENCODING = getSYSTEM_ENCODING();
        TEMPLATEPATH = getTEMPLATEPATH();
        source_root_package = getSourceRootPackage();
        source_test_root_package = getSource_test_root_package();
        source_resources = getSource_resources();
        web_root_package = getWebRootPackage();
        web_root_package.replace(".", "/");
        bussiPackage = getBussiPackage();
        mapper_path = getMapperPath();
        domain_path = getDomainPath();
        bussiPackageUrl = bussiPackage.replace(".", "/");

        CENGLE_GENERATE_TABLE_ID = getCengle_generate_table_id();

        CENGLE_UI_FIELD_SEARCH_NUM = getCengle_ui_search_filed_num();

        if ((URL.indexOf("mysql") >= 0) || (URL.indexOf("MYSQL") >= 0))
            DATABASE_TYPE = DATABASE_TYPE_MYSQL;
        else if ((URL.indexOf("oracle") >= 0) || (URL.indexOf("ORACLE") >= 0)) {
            DATABASE_TYPE = DATABASE_TYPE_ORACLE;
        }

        source_root_package = source_root_package.replace(".", "/");
    }

    private void ResourceUtil() {
    }

    public static final String getDIVER_NAME() {
        return bundle.getString("diver_name");
    }

    public static final String getURL() {
        return bundle.getString("url");
    }

    public static final String getUSERNAME() {
        return bundle.getString("username");
    }

    public static final String getPASSWORD() {
        return bundle.getString("password");
    }

    public static final String getDATABASE_NAME() {
        return bundle.getString("database_name");
    }

    private static String getBussiPackage() {
        return bundlePath.getString("bussi_package");
    }

    private static String getMapperPath() {
        return bundlePath.getString("mapper_path");
    }
    private static String getDomainPath() {
        return bundlePath.getString("domain_path");
    }

    public static final String getEntityPackage() {
        return bundlePath.getString("entity_package");
    }

    public static final String getPagePackage() {
        return bundlePath.getString("page_package");
    }

    public static final String getTEMPLATEPATH() {
        return bundlePath.getString("templatepath");
    }

    public static final String getSourceRootPackage() {
        return bundlePath.getString("source_root_package");
    }

    public static final String getSource_resources() {
        return bundlePath.getString("source_resources");
    }

    public static final String getWebRootPackage() {
        return bundlePath.getString("webroot_package");
    }

    public static final String getSYSTEM_ENCODING() {
        return bundlePath.getString("system_encoding");
    }

    public static final String getCengle_generate_table_id() {
        return bundlePath.getString("cengle_generate_table_id");
    }

    public static final String getCengle_generate_ui_filter_fields() {
        return bundlePath.getString("cengle_generate_ui_filter_fields");
    }

    public static final String getCengle_ui_search_filed_num() {
        return bundlePath.getString("cengle_ui_search_filed_num");
    }

    public static final String getCengle_ui_field_required_num() {
        return bundlePath.getString("cengle_ui_field_required_num");
    }

    public static String getSource_test_root_package() {
        return source_test_root_package;
    }

    public static void setSource_test_root_package(String source_test_root_package) {
        CodeResourceUtil.source_test_root_package = source_test_root_package;
    }
    /**
     * //获取配置文件，jar包同级目录，未找到读取class目录下文件
     * @Date 10:14 2021-06-17
     * @param fileName filename只传文件名称，不要后缀，默认添加：.properties
     * @return java.util.ResourceBundle
     **/
    public static ResourceBundle getBundleFromDisk(String fileName){
        if (StringUtils.isEmpty(fileName)) {
            fileName = "cengle/cengle_config.properties";
        }
        String filePath = CodeGenerateFactory.getContextProjectPath()+fileName+".properties";
        logger.info("获取配置文件路径 :{}", filePath);
        try (InputStream inputStream = new FileInputStream(filePath)) {
            ResourceBundle resourceBundle = new PropertyResourceBundle(inputStream);
            return resourceBundle !=null ? resourceBundle :ResourceBundle.getBundle("cengle/"+fileName);
        } catch (IOException e ) {
            logger.error("error info", e);
            return ResourceBundle.getBundle("cengle/"+fileName);
        }
    }
}