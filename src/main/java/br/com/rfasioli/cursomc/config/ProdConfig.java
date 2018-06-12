package br.com.rfasioli.cursomc.config;

import java.text.ParseException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.rfasioli.cursomc.services.EmailService;
import br.com.rfasioli.cursomc.services.MockEMailService;

@Configuration
@Profile("prod")
public class ProdConfig {
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new MockEMailService();
	}

}
