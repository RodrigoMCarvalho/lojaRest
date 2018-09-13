package br.com.cursomvc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.cursomvc.services.DBService;

@Configuration
@Profile("dev") //application-test.properties
public class DevConfig {
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Autowired
	private DBService dbService;
	
	@Bean  //instanciar o BD no profile de test
	public boolean instantiateDatabase() throws ParseException {
		
		if(!"create".equals(strategy)) {  //para não instanciar caso não seja configurado com "create"
			return false;
		}
		dbService.instantiateTestDatabase();
		return true;
	}
}