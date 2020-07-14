package com.springboot.chapter3.pojo;
import org.springframework.stereotype.Component;
import com.springboot.chapter3.pojo.definition.Animal;
@Component
public class Cat implements Animal {
	@Override
	public void use() {
		System.out.println("猫【" + Cat.class.getSimpleName()+"】是抓老鼠。");
	}
}