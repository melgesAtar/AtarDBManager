package br.com.atardigital.AtarDBManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.atardigital.AtarDBManager.controller"})
@ComponentScan(basePackages = {"br.com.atardigital.AtarDBManager.DAO"})
@ComponentScan(basePackages = {"br.com.atardigital.AtarDBManager.model"})
public class AtarDbManagerApplication {

	public static void main(String[] args) {

		SpringApplication.run(AtarDbManagerApplication.class, args);
	}

}
