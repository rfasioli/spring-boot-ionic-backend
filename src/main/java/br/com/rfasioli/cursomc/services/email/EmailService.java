package br.com.rfasioli.cursomc.services.email;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.rfasioli.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEMail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj); 
	
	void sendHtmlEmail(MimeMessage msg); 
	 	
}
