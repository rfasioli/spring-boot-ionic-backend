package br.com.rfasioli.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEMailService extends AbstractEmailService {

	private static final Logger logger = LoggerFactory.getLogger(MockEMailService.class);
	
	@Override
	public void sendEMail(SimpleMailMessage msg) {
		logger.info("Simulando envio de email...");
		logger.info(msg.toString());
		logger.info("Email enviado!");
	}

}
