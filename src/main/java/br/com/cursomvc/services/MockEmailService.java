package br.com.cursomvc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import br.com.cursomvc.models.Pedido;

public class MockEmailService extends AbstractEmailService{
	
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		// TODO Auto-generated method stub
		super.sendOrderConfirmationEmail(pedido);
	}

	@Override
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		// TODO Auto-generated method stub
		return super.prepareSimpleMailMessageFromPedido(pedido);
	}

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email....");
		LOG.info(msg.toString());
		LOG.info("Email enviado!");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Simulando envio de email....");
		LOG.info(msg.toString());
		LOG.info("Email enviado!");
	}

	
	
	
	
}
