package br.com.cursomvc.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.cursomvc.models.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
