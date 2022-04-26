package ${bussPackage}.dao.impl#if($!entityPackage).${entityPackage}#end;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xzh.domain.${className};
import com.xzh.dao.I${className}Mapper;
/**
 * 
 * <br>
 * <b>功能：</b>${className}DaoImpl<br>
 */
@Repository
public class ${className}MapperImpl extends SqlSessionDaoSupport implements
		I${className}Mapper{

   @Autowired  
   public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){  
       super.setSqlSessionFactory(sqlSessionFactory);  
   }  
   private String ns	=	"${className}Mapper"; //命名空间
	
	public  void add(${className} ${lowerName}) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(ns+".add", ${lowerName});
	}
	
	public  void update(${className} ${lowerName}) {
		// TODO Auto-generated method stub
		this.getSqlSession().update(ns+".update", ${lowerName});
	}
	
	public  void updateBySelective(${className} ${lowerName}) {
		// TODO Auto-generated method stub
		this.getSqlSession().update(ns+".updateBySelective", ${lowerName});
	}
	
	public void delete(Object id) {
		// TODO Auto-generated method stub
		this.getSqlSession().delete(ns+".delete", id);
	}
	
	public  ${className} queryById(Object id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(ns+".queryById", id);
	}
	
	#foreach($param in ${daoMethods.keySet()})
    public  List<Map<String,Object>> $param(Object object) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(ns+".$param", object);
	}
    public Integer count$param(Object object) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(ns+".count$param", object);
	}
	#end
}
