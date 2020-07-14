package com.springboot.chapter4.aspect.service.impl;

import org.springframework.stereotype.Service;

import com.springboot.chapter4.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public void sayHello(String name) {
		if (name == null || name.trim() == "") {
			throw new RuntimeException ("parameter is null!!");
		}
		System.out.println("hello " + name);
	}

}
