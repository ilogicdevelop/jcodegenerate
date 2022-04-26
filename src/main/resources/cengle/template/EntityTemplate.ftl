package ${domainPath}#if($!entityPackage).${entityPackage}#end;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * <br>
 * <b>功能：</b>${className}Entity<br>
 */
public class ${className}  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	${feilds}
}

