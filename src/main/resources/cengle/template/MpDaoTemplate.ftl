package ${mapperPath}#if($!entityPackage).${entityPackage}#end;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;
import ${domainPath}.${className};

public interface ${className}Mapper extends BaseMapper<${className}>{

    #foreach($param in ${daoMethods.keySet()})
    
    public  List<Map<String,Object>>  $param(Object object);
    
    public  Integer  count$param(Object object);
	
	#end
}
