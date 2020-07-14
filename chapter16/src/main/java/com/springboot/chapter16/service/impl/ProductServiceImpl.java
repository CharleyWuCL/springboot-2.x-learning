package com.springboot.chapter16.service.impl;

import org.springframework.stereotype.Service;

import com.springboot.chapter16.pojo.Product;
import com.springboot.chapter16.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Override
	public Product getProduct(Long id) {
		throw new RuntimeException("未能支持该方法");
	}
	
	

}
