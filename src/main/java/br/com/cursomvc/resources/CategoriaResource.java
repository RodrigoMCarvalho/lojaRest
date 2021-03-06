package br.com.cursomvc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.cursomvc.dto.CategoriaDTO;
import br.com.cursomvc.models.Categoria;
import br.com.cursomvc.services.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@GetMapping("/todos")
	public ResponseEntity<List<Categoria>> findAll() {
		List<Categoria> categorias = service.findAll();

		return ResponseEntity.ok().body(categorias);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
									@RequestParam(value ="page", defaultValue="0") Integer page, 
									@RequestParam(value ="linesPerPage", defaultValue="24") Integer linesPerPage,
									@RequestParam(value ="orderBy", defaultValue="nome") String orderBy, 
									@RequestParam(value ="direction", defaultValue="ASC") String direction) {
		
		Page<Categoria> categorias = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> categoriasDto = categorias.map(cat -> new CategoriaDTO(cat));
		return ResponseEntity.ok().body(categoriasDto);
	}
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAllDTO() {
		List<Categoria> categorias = service.findAll();
		List<CategoriaDTO> categoriasDto = categorias.stream().map(cat 
				-> new CategoriaDTO(cat)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(categoriasDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable("id") Integer id) {
		Categoria categoria = service.findById(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> save(@Valid @RequestBody CategoriaDTO categoriaDto){
			Categoria categoria = service.fromDTO(categoriaDto);
			categoria = service.save(categoria);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()   //obtém a URI do novo recurso que foi inserido
					.path("/{id}").buildAndExpand(categoriaDto.getId()).toUri();
			return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaDto, @PathVariable("id") Integer id) {
		Categoria categoria = service.fromDTO(categoriaDto);
		categoria.setId(id);
		categoria = service.update(categoria);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Categoria> delete(@PathVariable("id") Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
