package org.test.commons.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Log{
	
	private Logger logger = null;
	
	private Class<?> clazz;
	
	public Log(Class<?> clazz){
		this.clazz = clazz;
		logger = getLogger();
	}
	
	private Logger getLogger(){
		if(logger == null){
			this.logger = LoggerFactory.getLogger(clazz);
		}
		return this.logger;
	}
	
	public void info(String msg){
		getLogger().info(msg);
	}
	
	public void warn(String msg){
		getLogger().warn(msg);
	}
	
	public void warn(String msg,Throwable e){
		getLogger().warn(msg,e);
	}
	
	public void error(String msg){
		getLogger().error(msg);
	}
	
	public void error(String msg,Throwable e){
		getLogger().error(msg,e);
	}
	
	public void debug(String msg){
		getLogger().debug(msg);
	}
}