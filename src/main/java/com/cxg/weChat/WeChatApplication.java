package com.cxg.weChat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.cxg.weChat.*.mapper")
@SpringBootApplication
public class WeChatApplication  extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(WeChatApplication.class, args);
		System.out.println("__________.___  ________________________________________\n" +
				"\\______   \\   |/  _____/\\______   \\_   _____/\\_   _____/\n" +
				" |    |  _/   /   \\  ___ |    |  _/|    __)_  |    __)_ \n" +
				" |    |   \\   \\    \\_\\  \\|    |   \\|        \\ |        \\\n" +
				" |______  /___|\\______  /|______  /_______  //_______  /\n" +
				"        \\/            \\/        \\/        \\/         \\/ ");
	}
}
