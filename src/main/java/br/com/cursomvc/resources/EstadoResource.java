package br.com.cursomvc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cursomvc.dto.CidadeDTO;
import br.com.cursomvc.dto.EstadoDTO;
import br.com.cursomvc.models.Cidade;
import br.com.cursomvc.models.Estado;
import br.com.cursomvc.services.CidadeService;
import br.com.cursomvc.services.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findall(){
		List<Estado> estados = service.findAll();
		List<EstadoDTO> listDto = estados.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping("/{estado_id}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable("estado_id") Integer estado_id){
		List<Cidade> cidades = cidadeService.findByEstado(estado_id);
		List<CidadeDTO> listDto = cidades.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	
	
	
	
	
}
