package br.com.cursomvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cursomvc.models.Pedido;
import br.com.cursomvc.repositories.PedidoRepository;
import br.com.cursomvc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public List<Pedido> findAll(){
		List<Pedido> pedidos = repo.findAll();
		return pedidos;
	}
	
	public Pedido findById(Integer id)  {
		Optional<Pedido> pedido = repo.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
						"Pedido não encontrado! ID " + id + ", do tipo: " + Pedido.class.getName()));
	}
}
