package com.springboot.chapter7.pojo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/*** imports ***/
@Alias("user")
public class User implements Serializable {
	private static final long serialVersionUID = 7760614561073458247L;
	private Long id;
	private String userName;
	private String note;

	/** setter and getter **/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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