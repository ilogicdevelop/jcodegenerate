package ${bussPackage}.dao#if($!entityPackage).${entityPackage}#end;

import java.util.List;
import java.util.Map;

import com.xzh.domain.${className};
/**
 * 
 * <br>
 * <b>功能：</b>${className}Dao<br>
 */
public interface I${className}Mapper {
    public  void add(${className} ${lowerName});

    public  void update(${className} ${lowerName});

    public  void updateBySelective(${className} ${lowerName}); 

    public void delete(Object id);

    public  ${className} queryById(Object id);
    #foreach($param in ${daoMethods.keySet()})
    
    public  List<Map<String,Object>>  $param(Object object);
    
    public  Integer  count$param(Object object);
	
	#end
}
