package ${bussPackage}.service#if($!entityPackage).${entityPackage}#end;

import java.util.List;

import com.xzh.vo.ResultJson;
import com.xzh.domain.${className};
/**
 * 
 * <br>
 * <b>功能：</b>${className}Service<br>
 */
public interface I${className}${Cname}Service {

	public  ResultJson todo(Object object);
}
