package br.com.cursomvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cursomvc.models.Categoria;
import br.com.cursomvc.repositories.CategoriaRepository;
import br.com.cursomvc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public List<Categoria> buscarTodos(){
		List<Categoria> categorias = repo.findAll();
		return categorias;
	}
	
	public Categoria buscarPorId(Integer id)  {
		Optional<Categoria> categoria = repo.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
						"Categoria não encontrada! ID " + id + ", do tipo: " + Categoria.class.getName()));
	}
}
