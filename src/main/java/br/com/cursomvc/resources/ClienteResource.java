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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.cursomvc.dto.ClienteDTO;
import br.com.cursomvc.dto.ClienteNovoDTO;
import br.com.cursomvc.models.Cliente;
import br.com.cursomvc.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@GetMapping("/todos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<Cliente>> findAll() {
		List<Cliente> categorias = service.findAll();

		return ResponseEntity.ok().body(categorias);
	}
	
	@GetMapping("/page")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Page<ClienteDTO>> findPage(
									@RequestParam(value ="page", defaultValue="0") Integer page, 
									@RequestParam(value ="linesPerPage", defaultValue="24") Integer linesPerPage,
									@RequestParam(value ="orderBy", defaultValue="nome") String orderBy, 
									@RequestParam(value ="direction", defaultValue="ASC") String direction) {
		
		Page<Cliente> categorias = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> categoriasDto = categorias.map(cat -> new ClienteDTO(cat));
		return ResponseEntity.ok().body(categoriasDto);
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAllDTO() {
		List<Cliente> clientes = service.findAll();
		List<ClienteDTO> clientesDto = clientes.stream().map(cli 
				-> new ClienteDTO(cli)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(clientesDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable("id") Integer id) {
		Cliente categoria = service.findById(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@GetMapping("/email")
	public ResponseEntity<Cliente> findByEmail(@RequestParam("value") String email){
		Cliente cliente = service.findByEmail(email);
		return ResponseEntity.ok().body(cliente);
	}
	
	@PostMapping
	public ResponseEntity<Void> save(@Valid @RequestBody ClienteNovoDTO clienteNovoDTO){
			Cliente cliente = service.fromDTO(clienteNovoDTO);
			cliente = service.save(cliente);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()   //obtém a URI do novo recurso que foi inserido
					.path("/{id}").buildAndExpand(cliente.getId()).toUri();
			return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO categoriaDto, @PathVariable("id") Integer id) {
		Cliente categoria = service.fromDTO(categoriaDto);
		categoria.setId(id);
		categoria = service.update(categoria);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Cliente> delete(@PathVariable("id") Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/picture")
	public ResponseEntity<Void> uploadProfilePicture (@RequestParam(name="file") MultipartFile multipartFile){
		URI uri = service.uploadProfilePicture(multipartFile); //upload de uma imagem
		
		return ResponseEntity.created(uri).build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
