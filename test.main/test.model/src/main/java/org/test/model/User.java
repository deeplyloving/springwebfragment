package org.test.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Hello world!
 *
 */
@Entity
@Table(name = "test_user")
public class User extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6304923126391719629L;

	private String code;
	
	private String name;
	
	private Integer state;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
