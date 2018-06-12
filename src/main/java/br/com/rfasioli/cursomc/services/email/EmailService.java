package br.com.rfasioli.cursomc.services.email;

import org.springframework.mail.SimpleMailMessage;

import br.com.rfasioli.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEMail(SimpleMailMessage msg);
	
}
