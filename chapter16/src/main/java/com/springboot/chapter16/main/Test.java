package com.springboot.chapter16.main;

import java.net.InetAddress;

public class Test {

	public static void main(String[] args) throws Exception {
		String urlStr = "www.qq.com";
		ping(urlStr);
	}
	
	public static boolean ping(String ipAddress) throws Exception {
        int  timeOut =  3000 ;  //超时应该在3钞以上        
        boolean status = InetAddress.getByName(ipAddress).isReachable(timeOut);     // 当返回值是true时，说明host是可用的，false则不可。
        return status;
    }

}
