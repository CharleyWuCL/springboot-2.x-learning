package com.springboot.chapter14.pojo;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.springboot.chapter14.enumeration.SexEnum;

public class User implements Serializable {
	private static final long serialVersionUID = 3923229573077975377L;
	@Id
	private Long id;
	// 性别
	private SexEnum sex;
	// 在MongoDB中使用user_name保存属性
	@Field("user_name")
	private String userName;
	private String note;

	/**** setter and getter ****/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SexEnum getSex() {
		return sex;
	}

	public void setSex(SexEnum sex) {
		this.sex = sex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
