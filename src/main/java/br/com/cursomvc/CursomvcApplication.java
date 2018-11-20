package br.com.cursomvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomvcApplication implements CommandLineRunner {
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomvcApplication.class, args);
	}

	@Override  //para iniciar dados num BD em memória
	public void run(String... args) throws Exception {
	}
	
	
	
	
	
	
	
}
