package com.amaris.TransactionPayPal.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configurazioni generali 
 * 
 * it.silicondev.mago.config ApplicationConfig 
 * @author valeria
 *
 */
@Configuration
public class ApplicationConfig {
	
	@Bean
	public ModelMapper modelMapper(){
	    return new ModelMapper();
	}

}
