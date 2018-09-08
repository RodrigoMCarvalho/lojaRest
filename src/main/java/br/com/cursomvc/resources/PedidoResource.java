package br.com.cursomvc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cursomvc.models.Pedido;
import br.com.cursomvc.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	@GetMapping
	public ResponseEntity<?> findAll() {
		List<Pedido> pedidos = service.findAll();
		return ResponseEntity.ok(pedidos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
		Pedido pedido = service.findById(id);
		return ResponseEntity.ok(pedido);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
