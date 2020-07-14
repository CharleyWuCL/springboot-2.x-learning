package com.springboot.chapter15.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.chapter15.service.PurchaseService;

/**** imports ****/
// REST风格控制器
@RestController
public class PurchaseController {
	@Autowired
	PurchaseService purchaseService = null;

	// 定义JSP视图
	@GetMapping("/test")
	public ModelAndView testPage() {
		ModelAndView mv = new ModelAndView("test");
		return mv;
	}

	@PostMapping("/purchase")
	public Result purchase(Long userId, Long productId, Integer quantity) {
		boolean success = purchaseService.purchaseRedis(userId, productId, quantity);
		String message = success ? "抢购成功" : "抢购失败";
		Result result = new Result(success, message);
		return result;
	}

	// 响应结果
	class Result {
		private boolean success = false;
		private String message = null;

		public Result() {
		}

		public Result(boolean success, String message) {
			this.success = success;
			this.message = message;
		}

		/**** setter and getter ****/

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}
}