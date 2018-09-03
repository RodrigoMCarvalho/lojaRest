package br.com.cursomvc.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cursomvc.dto.CategoriaDTO;
import br.com.cursomvc.models.Categoria;
import br.com.cursomvc.repositories.CategoriaRepository;
import br.com.cursomvc.services.exceptions.DataIntegrityException;
import br.com.cursomvc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public List<Categoria>findAll(){
		return repo.findAll();
	}
	
	public Categoria findById(Integer id)  {
		Optional<Categoria> categoria = repo.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
						"Categoria não encontrada! ID " + id + ", do tipo: " + Categoria.class.getName()));
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage,String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		return repo.findAll(pageRequest);
	}
	
	@Transactional
	public Categoria save(Categoria categoria){
		categoria.setId(null);
		return repo.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		Categoria novaCategoria = findById(categoria.getId()); //caso o ID seja inexistente será lançada uma exceção
		updateData(novaCategoria, categoria);
		return repo.save(novaCategoria);
	}
	
	private void updateData(Categoria novaCategoria, Categoria categoria) { 
		novaCategoria.setNome(categoria.getNome());
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}	
	}
	
	//converter categoriaDTO para categoria
	public Categoria fromDTO(@Valid CategoriaDTO categoriaDto) {
		return new Categoria(categoriaDto.getId(), categoriaDto.getNome());
	}

}
