package com.springboot.chapter10.controller;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.springboot.chapter10.pojo.User;
import com.springboot.chapter10.service.PdfExportService;
import com.springboot.chapter10.service.UserService;
import com.springboot.chapter10.validator.UserValidator;
import com.springboot.chapter10.view.PdfView;

/**** imports ****/
@Controller
@RequestMapping("/user")
public class UserController {
	// 注入用户服务类
	@Autowired
	private UserService userService = null;

	/**
	 * 打开请求页面
	 * 
	 * @return 字符串，指向页面
	 */
	@GetMapping("/add")
	public String add() {
		return "/user/add";
	}

	/**
	 * 新增用户
	 * 
	 * @param user
	 *            通过@RequestBody注解得到JSON参数
	 * @return 回填id后的用户信息
	 */
	@PostMapping("/insert")
	@ResponseBody
	public User insert(@RequestBody User user) {
		userService.insertUser(user);
		return user;
	}

	// {...}代表占位符，还可以配置参数名称
	@GetMapping("/{id}")
	// 响应为JSON数据集
	@ResponseBody
	// @PathVariable通过名称获取参数
	public User get(@PathVariable("id") Long id) {
		return userService.getUser(id);
	}

	@GetMapping("/converter")
	@ResponseBody
	public User getUserByConverter(User user) {
		return user;
	}

	@GetMapping("/list")
	@ResponseBody
	public List<User> list(List<User> userList) {
		return userList;
	}

	/**
	 * 调用控制器前先执行这个方法
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 绑定验证器
		binder.setValidator(new UserValidator());
		// 定义日期参数格式，参数不再需注解@DateTimeFormat，boolean参数表示是否允许为空
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
	}

	/**
	 * 
	 * @param user
	 *            -- 用户对象用StringToUserConverter转换
	 * @param Errors
	 *            --验证器返回的错误
	 * @param date
	 *            -- 因为WebDataBinder已经绑定了格式，所以不再需要注解
	 * @return 各类数据
	 */
	@GetMapping("/validator")
	@ResponseBody
	public Map<String, Object> validator(@Valid User user, Errors Errors, Date date) {
		Map<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("date", date);
		// 判断是否存在错误
		if (Errors.hasErrors()) {
			// 获取全部错误
			List<ObjectError> oes = Errors.getAllErrors();
			for (ObjectError oe : oes) {
				// 判定是否字段错误
				if (oe instanceof FieldError) {
					// 字段错误
					FieldError fe = (FieldError) oe;
					map.put(fe.getField(), fe.getDefaultMessage());
				} else {
					// 对象错误
					map.put(oe.getObjectName(), oe.getDefaultMessage());
				}
			}
		}
		return map;
	}

	// 导出接口
	@GetMapping("/export/pdf")
	public ModelAndView exportPdf(String userName, String note) {
		// 查询用户信息列表
		List<User> userList = userService.findUsers(userName, note);
		// 定义PDF视图
		View view = new PdfView(exportService());
		ModelAndView mv = new ModelAndView();
		// 设置视图
		mv.setView(view);
		// 加入数据模型
		mv.addObject("userList", userList);
		return mv;
	}

	// 导出PDF自定义
	@SuppressWarnings("unchecked")
	private PdfExportService exportService() {
		// 使用Lambda表达式定义自定义导出
		return (model, document, writer, request, response) -> {
			try {
				// A4纸张
				document.setPageSize(PageSize.A4);
				// 标题
				document.addTitle("用户信息");
				// 换行
				document.add(new Chunk("\n"));
				// 表格，3列
				PdfPTable table = new PdfPTable(3);
				// 单元格
				PdfPCell cell = null;
				// 字体，定义为蓝色加粗
				Font f8 = new Font();
				f8.setColor(Color.BLUE);
				f8.setStyle(Font.BOLD);
				// 标题
				cell = new PdfPCell(new Paragraph("id", f8));
				// 居中对齐
				cell.setHorizontalAlignment(1);
				// 将单元格加入表格
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("user_name", f8));
				// 居中对齐
				cell.setHorizontalAlignment(1);
				table.addCell(cell);
				cell = new PdfPCell(new Paragraph("note", f8));
				cell.setHorizontalAlignment(1);
				table.addCell(cell);
				// 获取数据模型中的用户列表
				List<User> userList = (List<User>) model.get("userList");
				for (User user : userList) {
					document.add(new Chunk("\n"));
					cell = new PdfPCell(new Paragraph(user.getId() + ""));
					table.addCell(cell);
					cell = new PdfPCell(new Paragraph(user.getUserName()));
					table.addCell(cell);
					String note = user.getNote() == null ? "" : user.getNote();
					cell = new PdfPCell(new Paragraph(note));
					table.addCell(cell);
				}
				// 在文档中加入表格
				document.add(table);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		};
	}
	
//	// 显示用户
//	@GetMapping("/show")
//	public String showUser(Long id, Model model) {
//	    User user = userService.getUser(id);
//	    model.addAttribute("user", user);
//	    return "data/user";
//	}
//
//	// 使用字符串指定跳转
//	@GetMapping("/redirect1")
//	public String redirect1(String userName, String note) {
//	    User user = new User();
//	    user.setNote(note);
//	    user.setUserName(userName);
//	    // 插入数据库后，回填user的id
//	    userService.insertUser(user);
//	    return "redirect:/user/show?id=" + user.getId();
//	}
//
//	// 使用模型和视图指定跳转
//	@GetMapping("/redirect2")
//	public ModelAndView redirect2(String userName, String note) {
//	    User user = new User();
//	    user.setNote(note);
//	    user.setUserName(userName);
//	    userService.insertUser(user);
//	    ModelAndView mv = new ModelAndView();
//	    mv.setViewName("redirect:/user/show?id=" + user.getId());
//	    return mv;
//	}
	
	// 显示用户
	// 参数user直接从数据模型RedirectAttributes对象中取出
	@RequestMapping("/showUser")
	public String showUser(User user, Model model) {
	    System.out.println(user.getId());
	    return "data/user";
	}

	// 使用字符串指定跳转
	@RequestMapping("/redirect1")
	public String redirect1(String userName, String note, RedirectAttributes ra) {
	    User user = new User();
	    user.setNote(note);
	    user.setUserName(userName);
	    userService.insertUser(user);
	    // 保存需要传递给重定向的对象
	    ra.addFlashAttribute("user", user);
	    return "redirect:/user/showUser";
	}

	// 使用模型和视图指定跳转
	@RequestMapping("/redirect2")
	public ModelAndView redirect2(String userName, String note,
	        RedirectAttributes ra) {
	    User user = new User();
	    user.setNote(note);
	    user.setUserName(userName);
	    userService.insertUser(user);
	    // 保存需要传递给重定向的对象
	    ra.addFlashAttribute("user", user);
	    ModelAndView mv = new ModelAndView();
	    mv.setViewName("redirect:/user/showUser");
	    return mv;
	}
	
	@GetMapping("/header/page")
	public String headerPage() {
	    return "header";
	}

	@PostMapping("/header/user")
	@ResponseBody
	// 通过@RequestHeader接收请求头参数
	public User headerUser(@RequestHeader("id") Long id) {
	    User user = userService.getUser(id);
	    return user;
	}
}