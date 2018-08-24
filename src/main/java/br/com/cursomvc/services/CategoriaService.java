package br.com.cursomvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cursomvc.model.Categoria;
import br.com.cursomvc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public List<Categoria> buscarTodos(){
		List<Categoria> categorias = repo.findAll();
		return categorias;
	}
	
	public Optional<Categoria> buscarPorId(Integer id) {
		Optional<Categoria> categoria = repo.findById(id);
		return categoria;
	}
}
