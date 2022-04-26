package ${bussPackage}.controller;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ${bussPackage}.controller.BasicController;
import ${bussPackage}.domain#if($!entityPackage).${entityPackage}#end.${className};
import ${bussPackage}.service#if($!entityPackage).${entityPackage}#end.I${className}${Cname}Service;

import ${bussPackage}.vo.ResultJson;
/**
 * 
 * <br>
 * <b>功能：</b>${className}${Cname}Controller<br>
 *   <br>
 */ 
@Controller
public class ${className}${Cname}Controller extends BasicController{
	
	@Autowired 
	private I${className}${Cname}Service ${lowerName}${Cname}Service; 
	/**
	 * 
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/${className}${Cname}",method={#set($separtor="")#foreach($method in ${methods})${separtor}RequestMethod.${method}#set($separtor=",")#end}) 
	public ResultJson  ${cname}(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String username	=	request.getParameter("username");
		String password	=	request.getParameter("password");
		String mobile	=	request.getParameter("mobile");
		String identify	=	request.getParameter("identify");
		if(StringUtils.isBlank(username)){
			return rs("200101", "username is empty");
		}
		if(StringUtils.isBlank(password)){
			return rs("200101", "password");
		}
		if(!PatternUtil.patternCheck(mobile, PatternUtil.MOBILE_PATTERN)){
			return rs("200101", "mobile");
		}
		if(!PatternUtil.patternCheck(identify, PatternUtil.IDENTIFY_SIMPLE_PATTERN)){
			return rs("200101", "identify");
		}
		Map<String,Object>	map	=	new HashMap<>();
		map.put("username", username);
		map.put("password", password);
		map.put("mobile", mobile);
		map.put("identify", identify);
		ResultJson	rs	= ${lowerName}${Cname}Service.todo(map);
		return rs; 
	}

}
