package ${bussPackage}.service.Impl#if($!entityPackage).${entityPackage}#end;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${bussPackage}.vo.ResultJson;
import ${domainPath}.${className};
import ${bussPackage}.dao#if($!entityPackage).${entityPackage}#end.I${className}Mapper;
import ${bussPackage}.service#if($!entityPackage).${entityPackage}#end.I${className}${Cname}Service;

/**
 * 
 * <br>
 * <b>功能：</b>${className}Service<br>
 */
@Service("$!{lowerName}${Cname}Service")
public class  ${className}${Cname}ServiceImpl  implements I${className}${Cname}Service {
  private final static Logger logger= LoggerFactory.getLogger(${className}${Cname}ServiceImpl.class);

	@Autowired
    private I${className}Mapper dao;
    
    public  ResultJson todo(Object object){
    	ResultJson	rs=	new ResultJson();
    	Map<String,Object> map	=	(HashMap<String,Object>)object;
    	rs.setErr_code("100100");
		//rs.setContent(this.dao.${cname}(map));
    	return rs;
    }
}
