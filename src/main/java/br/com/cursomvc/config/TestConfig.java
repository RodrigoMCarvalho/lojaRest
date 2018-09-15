package br.com.cursomvc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.cursomvc.services.DBService;
import br.com.cursomvc.services.EmailService;
import br.com.cursomvc.services.MockEmailService;

@Configuration
@Profile("test") //application-test.properties
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean  //instanciar o BD no profile de test
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
