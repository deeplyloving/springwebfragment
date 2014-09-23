package org.test.base.service;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService, ApplicationContextAware{
	private ApplicationContext context;
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.context = arg0;
		
	}
	public void test() {
		Map<String, MenuExpoint> a  = this.context.getBeansOfType(MenuExpoint.class, true, true);
		Map<String, MenuExpoint> b  = this.context.getBeansOfType(MenuExpoint.class, false, false);
		Map<String, MenuExpoint> c  = this.context.getBeansOfType(MenuExpoint.class, true, false);
		Map<String, MenuExpoint> d = this.context.getBeansOfType(MenuExpoint.class, false, true);
		Map<String, Object> e  = this.context.getBeansWithAnnotation(Controller.class);
//		Object obj = this.context.getBean("test");
		System.out.println("");
	}
	

}
