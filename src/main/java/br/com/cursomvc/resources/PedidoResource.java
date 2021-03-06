package br.com.cursomvc.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.cursomvc.models.Pedido;
import br.com.cursomvc.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	@GetMapping("/todos")
	public ResponseEntity<?> findAll() {
		List<Pedido> pedidos = service.findAll();
		return ResponseEntity.ok(pedidos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
		Pedido pedido = service.findById(id);
		return ResponseEntity.ok(pedido);
	}
	
	@PostMapping
	public ResponseEntity<Void> save(@Valid @RequestBody Pedido pedido){
			pedido = service.save(pedido);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()   //obtém a URI do novo recurso que foi inserido
					.path("/{id}").buildAndExpand(pedido.getId()).toUri();
			return ResponseEntity.created(uri).build();
	}
	
	@GetMapping
	public ResponseEntity<Page<Pedido>> findPage(
									@RequestParam(value ="page", defaultValue="0") Integer page, 
									@RequestParam(value ="linesPerPage", defaultValue="24") Integer linesPerPage,
									@RequestParam(value ="orderBy", defaultValue="instante") String orderBy, 
									@RequestParam(value ="direction", defaultValue="DESC") String direction) {
		
		Page<Pedido> listPedidos = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(listPedidos);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
