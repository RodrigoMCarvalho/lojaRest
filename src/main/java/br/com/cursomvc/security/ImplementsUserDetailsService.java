package br.com.cursomvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.cursomvc.models.Cliente;
import br.com.cursomvc.repositories.ClienteRepository;

public class ImplementsUserDetailsService implements UserDetailsService{
	
	@Autowired
	private ClienteRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = repo.findByEmail(email);
		if (cliente == null) {

			throw new UsernameNotFoundException("Usuario não encontrado!");

		}

		return new Usuario(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
	}

}
