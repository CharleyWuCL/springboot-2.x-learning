package com.springboot.chapter13.pojo;

import java.io.Serializable;

/**** imports ****/
public class User implements Serializable {
	private static final long serialVersionUID = 8081849731640304905L;
	private Long id;
	private String userName = null;
	private String note = null;

	public User(Long id, String userName, String note) {
		this.id = id;
		this.userName = userName;
		this.note = note;
	}

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