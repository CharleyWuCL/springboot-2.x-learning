package com.springboot.chapter10.validator;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springboot.chapter10.pojo.User;

/**** imports ****/
public class UserValidator implements Validator {
	
	// 该验证器只是支持User类验证
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(User.class);
	}

	// 验证逻辑
	@Override
	public void validate(Object target, Errors errors) {
		// 对象为空
		if (target == null) {
			// 直接在参数处报错，这样就不能进入控制器的方法了
			errors.rejectValue("", null, "用户不能为空");
			return;
		}
		// 强制转换
		User user = (User) target;
		// 用户名非空串
		if (StringUtils.isEmpty(user.getUserName())) {
			// 增加错误，可以进入控制器方法
			errors.rejectValue("userName", null, "用户名不能为空");
		}
	}
}