package br.com.rfasioli.cursomc.services.email;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {

	private static final Logger logger = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendEMail(SimpleMailMessage msg) {
		logger.info("Enviando email...");
		mailSender.send(msg);
		logger.info("Email enviado!");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		logger.info("Enviando email...");
		javaMailSender.send(msg);
		logger.info("Email enviado!");
	}

}
