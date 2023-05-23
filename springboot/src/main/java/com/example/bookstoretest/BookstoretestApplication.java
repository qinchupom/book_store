package com.example.bookstoretest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.bookstoretest.mapper")
public class BookstoretestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoretestApplication.class, args);
	}

}
