package br.com.cursomvc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.cursomvc.models.Cliente;
import br.com.cursomvc.repositories.ClienteRepository;
import br.com.cursomvc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPass;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("Email não localizado");
		}
		String novaSenha = newPassword();
		cliente.setSenha(bCryptPass.encode(novaSenha));
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, novaSenha);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for(int i=0; i<10; i++) {
			vet[i]= randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		if(opt == 0) {  //gera um dígito
			return (char) (random.nextInt(10) + 48); //48 unicode 0, 10 possíveis (0 - 9)
		}
		else if (opt == 1) { //gera uma letra maiúscula
			return (char) (random.nextInt(26) + 65); //48 unicode A, 26 possíveis (A - Z)
		}
		else { //gera uma letra minúscula
			return (char) (random.nextInt(26) + 97); //48 unicode a, 26 possíveis (a - z)
		}
	}
	
	
	
	
	
	
	
	
	
}
