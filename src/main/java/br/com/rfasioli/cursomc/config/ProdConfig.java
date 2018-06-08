package br.com.rfasioli.cursomc.config;

import java.text.ParseException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfig {
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		return true;
	}
}
