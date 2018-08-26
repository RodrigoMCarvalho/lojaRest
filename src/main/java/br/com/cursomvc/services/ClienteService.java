package br.com.cursomvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cursomvc.models.Cliente;
import br.com.cursomvc.repositories.ClienteRepository;
import br.com.cursomvc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public List<Cliente> buscarTodos(){
		List<Cliente> categorias = repo.findAll();
		return categorias;
	}
	
	public Cliente buscarPorId(Integer id)  {
		Optional<Cliente> categoria = repo.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
						"Cliente não encontrado! ID " + id + ", do tipo: " + Cliente.class.getName()));
	}
}
