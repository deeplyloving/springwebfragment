package org.test.web.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.test.commons.cache.Cache;

/**
 * Hello world!
 *
 */
public class BaseCtrl implements ApplicationContextAware
{
	@Autowired
	private RequestMappingHandlerMapping mapping;
	
	private ApplicationContext context;
	
	protected HttpServletRequest request;  
    protected HttpServletResponse response;  
    public Model model;
    
    public List<Map<String,String>> getMenus(){
    	List<Map<String,String>> menus = new ArrayList<Map<String,String>>();
    	Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
    	for(Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            System.out.println(entry.getKey());
            HandlerMethod  handlerMethod = entry.getValue();
            System.out.println(handlerMethod.getBean().getClass().getName());
            System.out.println(handlerMethod.getBeanType());
//            if(handlerMethod.getBean().getClass().getInterfaces()[0] == MenuExpoint.class){
//            	((MenuExpoint)handlerMethod.getBean()).registerMenu(menus);
//            }
//            if(handlerMethod.getBeanType().getInterfaces()[0] == MenuExpoint.class){
//            	((MenuExpoint)handlerMethod.getBean()).registerMenu(menus);
//            }
    	}
    	Map<String, Object> s = this.context.getBeansWithAnnotation(Controller.class);
    	String[]  a = this.context.getBeanDefinitionNames();
//    	Map<String, MenuExpoint> ss = this.context.getBeansOfType(MenuExpoint.class);
    	return menus;
    }
    
	@ModelAttribute
	public void init(Model model,HttpServletRequest request, HttpServletResponse response,RedirectAttributes attributes){
		this.request = request;
		this.response = response;
		this.model = model;
		if(StringUtils.isNotBlank(getCookie("keep_params"))){
			String keep_params = getCookie("keep_params");
			Object value = Cache.get(keep_params);
			String key = Cache.get(keep_params+"_key")+"";
			Cache.delete(keep_params);
			Cache.delete(keep_params+"_key");
			removeCookie("keep_params");
			model.addAttribute(key,value);
		}
		if(StringUtils.isNotBlank(getCookie("keep_errors"))){
			String keep_params = getCookie("keep_errors");
			Object value = Cache.get(keep_params+"_errors");
			Cache.delete(keep_params+"_errors");
			removeCookie("keep_errors");
		}
//		User user = null;
//		try {
//			user = AppContextHolder.getCurrentUser(false);
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
//		if(user!=null)model.addAttribute("currentUser", user);
	}
	
	
	public void setCookie(String key,String value,int maxAge ){
		Cookie cookie = new Cookie(key,value);
	    cookie.setPath("/");
	    //cookie.setMaxAge(60*60*24*7);//保留7天  
	    if(maxAge>0)cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	}
	
	public void removeCookie(String key){
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if(key.equals(cookie.getName())){
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
					break;
				}
			}
		}
	}
	
	public String getCookie(String key){
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if(key.equals(cookie.getName())){
					return cookie.getValue();
				}
			}
		}
		return "";
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.context = arg0;
	}
}
