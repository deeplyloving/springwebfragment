package org.test.base.service;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

	public void onApplicationEvent(ApplicationEvent arg0) {
//		PropertyConfigurator.configure("log4j.properties");
		System.out.println("事件 好多啊"+arg0.getSource().getClass().getName());
	}

}
