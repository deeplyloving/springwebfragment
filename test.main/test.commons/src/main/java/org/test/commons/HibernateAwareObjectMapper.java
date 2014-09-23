package org.test.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;


public class HibernateAwareObjectMapper extends ObjectMapper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5561413319380459540L;

	public HibernateAwareObjectMapper(){
		this.registerModule(new Hibernate4Module());
	}
}
