package org.test.base.service;

import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class ServiceImpl implements IService{
	
	public Log log = new Log(this.getClass());
	
	public class Log{
		private Class<?> clazz;
		public Log(Class<?> clazz){
			this.clazz = clazz;
		}
		public Logger logger = null;
		
		private Logger getLogger(){
			if(logger == null){
				this.logger = Logger.getLogger(clazz.getName());
			}
			return this.logger;
		}
		
		public void info(String msg){
			getLogger().log(Level.INFO, msg);
		}
		
		public void warn(String msg){
			getLogger().log(Level.WARNING, msg);
		}
		
		public void error(String msg,Throwable e){
			getLogger().log(Level.SEVERE, msg,e);
		}
		
		public void debug(String msg){
			getLogger().log(Level.FINEST, msg);
		}
	}
	
	
	
}
