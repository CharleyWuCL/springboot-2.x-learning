package com.springboot.chapter10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.chapter10.pojo.User;
import com.springboot.chapter10.service.UserService;

/****imports****/
@RequestMapping("/data")
@Controller
public class DataModelController {
    // 注入用户服务类
    @Autowired
    private UserService userService = null;
    
    // 测试Model接口
    @GetMapping("/model")
    public String useModel(Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        // 这里返回字符串，在Spring MVC中，会自动创建ModelAndView且绑定名称
        return "data/user";
    }
    
    // 测试modelMap类
    @GetMapping("/modelMap")
    public ModelAndView useModelMap(Long id, ModelMap modelMap) {
        User user = userService.getUser(id);
        ModelAndView mv = new ModelAndView();
        // 设置视图名称
        mv.setViewName("data/user");
        // 设置数据模型，此处modelMap并没有和mv绑定，这步系统会自动处理
        modelMap.put("user", user);
        return mv;
    }
    
    // 测试ModelAndView
    @GetMapping("/mav")
    public ModelAndView useModelAndView(Long id, ModelAndView mv) {
        User user = userService.getUser(id);
        // 设置数据模型
        mv.addObject("user", user);
        // 设置视图名称
        mv.setViewName("data/user");
        return mv;
    }
}