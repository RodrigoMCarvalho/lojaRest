package br.com.cursomvc.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cursomvc.models.Cliente;
import br.com.cursomvc.models.ItemPedido;
import br.com.cursomvc.models.PagamentoComBoleto;
import br.com.cursomvc.models.Pedido;
import br.com.cursomvc.models.enums.EstadoPagamento;
import br.com.cursomvc.repositories.ItemPedidoRepository;
import br.com.cursomvc.repositories.PagamentoRepository;
import br.com.cursomvc.repositories.PedidoRepository;
import br.com.cursomvc.security.Usuario;
import br.com.cursomvc.services.exceptions.AuthorizationException;
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
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
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
		pedido.setCliente(clienteService.findById(pedido.getCliente().getId())); //busca o cliente para mostrar no toString
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE); //pedido que acabou de ser criado ainda está com pagamento pendente
		pedido.getPagamento().setPedido(pedido);
		
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {  //Compara o tipo de uma variável a uma classe (se lê "é um")
				PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
				boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
		}
		repo.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for (ItemPedido itemPedido : pedido.getItens()) {
			itemPedido.setDesconto(0.0);
			itemPedido.setProduto(produtoService.findById(itemPedido.getProduto().getId())); //seta o produto em itemPedido
			itemPedido.setPreco(itemPedido.getProduto().getPreco());  //busca o preço do produto
			itemPedido.setPedido(pedido);
		}
		itemPedidoRepository.saveAll(pedido.getItens());
		//emailService.sendOrderConfirmationEmail(pedido);
		emailService.sendOrderConfirmationHtmlEmail(pedido);
		return pedido;
	}
	
	//realiza busca paginada
	public Page<Pedido> findPage(Integer page, Integer linesPerPage,String orderBy, String direction){
		
		Usuario usuario = UsuarioService.authenticated();
		if(usuario == null ) {
			throw new AuthorizationException("Acesso negado");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.findById(usuario.getId());
		
		return repo.findByCliente(cliente, pageRequest);
	}
}
