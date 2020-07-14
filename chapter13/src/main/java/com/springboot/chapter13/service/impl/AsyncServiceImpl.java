package com.springboot.chapter13.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.springboot.chapter13.service.AsyncService;

@Service
public class AsyncServiceImpl implements AsyncService {

	@Override
    @Async // 声明使用异步调用
    public void generateReport() {
        // 打印异步线程名称
        System.out.println("报表线程名称："
            + "【" + Thread.currentThread().getName() +"】");
    }

}
