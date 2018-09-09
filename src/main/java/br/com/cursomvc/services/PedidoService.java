package br.com.cursomvc.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cursomvc.models.ItemPedido;
import br.com.cursomvc.models.PagamentoComBoleto;
import br.com.cursomvc.models.Pedido;
import br.com.cursomvc.models.enums.EstadoPagamento;
import br.com.cursomvc.repositories.ItemPedidoRepository;
import br.com.cursomvc.repositories.PagamentoRepository;
import br.com.cursomvc.repositories.PedidoRepository;
import br.com.cursomvc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	public List<Pedido> findAll(){
		List<Pedido> pedidos = repo.findAll();
		return pedidos;
	}
	
	public Pedido findById(Integer id)  {
		Optional<Pedido> pedido = repo.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
						"Pedido não encontrado! ID " + id + ", do tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido save(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstance(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE); //pedido que acabou de ser criado ainda está com pagamento pendente
		pedido.getPagamento().setPedido(pedido);
		
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {  //Compara o tipo de uma variável a uma classe (se lê "é um")
				PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
				boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstance());
		}
		repo.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for (ItemPedido itemPedido : pedido.getItens()) {
			itemPedido.setDesconto(0.0);
			itemPedido.setPreco(produtoService.findById(itemPedido.getProduto().getId()).getPreco());
			itemPedido.setPedido(pedido);
			itemPedidoRepository.save(itemPedido);
		}
		return pedido;
	}
}
