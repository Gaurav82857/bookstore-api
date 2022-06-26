package com.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bookstore.data.SampleDiscountsData;
import com.bookstore.data.SampleStoreData;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@SpringBootApplication
@EnableSwagger2
public class BookStoreApplication implements CommandLineRunner {

	
	@Value("${assisted.mode}")
	private String shouldLoadData;

	private final SampleStoreData storeData;
	private final SampleDiscountsData discountsData;

	@Autowired
	public BookStoreApplication(SampleStoreData storeData, SampleDiscountsData discountsData) {
		this.storeData = storeData;
		this.discountsData = discountsData;
	}

	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}

	@Override
	public void run(String... args) {
		if(Boolean.valueOf(shouldLoadData)){
			storeData.loadStore();
			log.info("assisted.mode is enabled.");
		} else{
			log.info("assisted.mode is disabled.");
		}
		discountsData.loadDiscounts();
	}
}
