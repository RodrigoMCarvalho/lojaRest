package br.com.cursomvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.cursomvc.services.S3Service;

@SpringBootApplication
public class CursomvcApplication implements CommandLineRunner {
	
	@Autowired
	private S3Service s3Service;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomvcApplication.class, args);
	}

	@Override  //para iniciar dados num BD em memória
	public void run(String... args) throws Exception {
		s3Service.uploadFile("C:\\Users\\rodri_000\\Desktop\\Desenvolvimento Web\\ws-ionic\\CursoSpringIonic\\src\\assets\\imgs\\cp1.jpg");
	}
	
	
	
	
	
	
	
}
