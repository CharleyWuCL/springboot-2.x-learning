package com.springboot.chapter4.aspect.validator;

import com.springboot.chapter4.pojo.User;

public interface UserValidator {
	
	public boolean validate(User user);
}
