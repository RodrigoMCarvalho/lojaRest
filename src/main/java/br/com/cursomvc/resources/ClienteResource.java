package br.com.cursomvc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cursomvc.models.Cliente;
import br.com.cursomvc.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@GetMapping
	public ResponseEntity<?> listar() {
		List<Cliente> categorias = service.buscarTodos();
		return ResponseEntity.ok(categorias);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") Integer id) {
		Cliente categoria = service.buscarPorId(id);
		return ResponseEntity.ok(categoria);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
