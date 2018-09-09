package br.com.cursomvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.cursomvc.models.Categoria;
import br.com.cursomvc.models.Produto;
import br.com.cursomvc.repositories.CategoriaRepository;
import br.com.cursomvc.repositories.ProdutoRepository;
import br.com.cursomvc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Produto> findAll(){
		List<Produto> produtos = repo.findAll();
		return produtos;
	}
	
	public Produto findById(Integer id)  {
		Optional<Produto> produto = repo.findById(id);
		return produto.orElseThrow(() -> new ObjectNotFoundException(
						"Produto não encontrado! ID " + id + ", do tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> seach(String nome, List<Integer> ids, Integer page, Integer linesPerPage,String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids); //busca os Ids passados na lista
		return repo.findDistinctByNomeIgnoreCaseContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
