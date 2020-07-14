package com.springboot.chapter17.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**** imports ****/
@Component
@Qualifier
public class MyZuulFilter extends ZuulFilter {

	// 注入StringRedisTemplate
	@Autowired
	private StringRedisTemplate residTemplate = null;

	// 是否过滤
	@Override
	public boolean shouldFilter() {
		// 请求上下文
		RequestContext ctx = RequestContext.getCurrentContext();
		// 获取HttpServletRequest对象
		HttpServletRequest req = ctx.getRequest();
		// 取出表单序列号
		String serialNumber = req.getParameter("serialNumber");
		// 如果存在验证码返回为true，启用过滤器
		return !StringUtils.isEmpty(serialNumber);
	}

	// 过滤器逻辑方法
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest req = ctx.getRequest();
		// 取出表单序列号和请求验证码
		String serialNumber = req.getParameter("serialNumber");
		String reqCode = req.getParameter("verificationCode");
		// 从Redis中取出验证码
		String verifCode = residTemplate.opsForValue().get(serialNumber);
		// Redis验证码为空或者与请求不一致，拦截请求报出错误
		if (verifCode == null || !verifCode.equals(reqCode)) {
			// 不再转发请求
			ctx.setSendZuulResponse(false);
			// 设置HTTP响应码为401（未授权）
			ctx.setResponseStatusCode(401);
			// 设置响应类型为JSON数据集
			ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8.getType());
			// 设置响应体
			ctx.setResponseBody("{'success': false, " + "'message':'Verification Code Error'}");
		}
		// 一致放过
		return null;
	}

	// 过滤器类型为请求前
	@Override
	public String filterType() {
		return "pre";
	}

	// 过滤器排序，数字越小优先级越高
	@Override
	public int filterOrder() {
		return 0;
	}
}