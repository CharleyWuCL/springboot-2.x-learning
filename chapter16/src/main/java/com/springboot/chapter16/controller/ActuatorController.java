package com.springboot.chapter16.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.chapter16.endpoint.DataBaseConnectionEndpoint;

@Endpoint(id="dbcheck2")
@Controller
public class ActuatorController {
	
	@Autowired
	private DataBaseConnectionEndpoint dbEnpoint = null;
	
	@GetMapping("/test2")
	public Map<String, Object> checkDb() {
		return dbEnpoint.test();
	}
	
	
}
