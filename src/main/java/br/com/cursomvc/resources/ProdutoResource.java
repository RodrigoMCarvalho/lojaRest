package br.com.cursomvc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cursomvc.dto.CategoriaDTO;
import br.com.cursomvc.models.Produto;
import br.com.cursomvc.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
		Produto pedido = service.findById(id);
		return ResponseEntity.ok(pedido);
	}
	
	@GetMapping
	public ResponseEntity<Page<Produto>> findPage(
									@RequestParam(value ="nome", defaultValue="") Integer nome, 
									@RequestParam(value ="categorias", defaultValue="") Integer categorias, 
									@RequestParam(value ="page", defaultValue="0") Integer page, 
									@RequestParam(value ="linesPerPage", defaultValue="24") Integer linesPerPage,
									@RequestParam(value ="orderBy", defaultValue="nome") String orderBy, 
									@RequestParam(value ="direction", defaultValue="ASC") String direction) {
		
		Page<Produto> produtos = service.seach(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> categoriasDto = categorias.map(cat -> new CategoriaDTO(cat));
		return ResponseEntity.ok().body(categoriasDto);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
