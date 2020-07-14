package com.springboot.chapter17.user.pojo;

import java.io.Serializable;

public class UserPo implements Serializable {
	private static final long serialVersionUID = -2535737897308758054L;
	private Long id;
	private String userName;
	// 1-银级会员，2-黄金会员。3-钻石会员
	private int level;
	private String note;

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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}