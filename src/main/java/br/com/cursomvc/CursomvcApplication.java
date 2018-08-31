package br.com.cursomvc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.cursomvc.models.Categoria;
import br.com.cursomvc.models.Cidade;
import br.com.cursomvc.models.Cliente;
import br.com.cursomvc.models.Endereco;
import br.com.cursomvc.models.Estado;
import br.com.cursomvc.models.ItemPedido;
import br.com.cursomvc.models.Pagamento;
import br.com.cursomvc.models.PagamentoComBoleto;
import br.com.cursomvc.models.PagamentoComCartao;
import br.com.cursomvc.models.Pedido;
import br.com.cursomvc.models.Produto;
import br.com.cursomvc.models.enums.EstadoPagamento;
import br.com.cursomvc.models.enums.TipoCliente;
import br.com.cursomvc.repositories.CategoriaRepository;
import br.com.cursomvc.repositories.CidadeRepository;
import br.com.cursomvc.repositories.ClienteRepository;
import br.com.cursomvc.repositories.EnderecoRepository;
import br.com.cursomvc.repositories.EstadoRepository;
import br.com.cursomvc.repositories.ItemPedidoRepository;
import br.com.cursomvc.repositories.PagamentoRepository;
import br.com.cursomvc.repositories.PedidoRepository;
import br.com.cursomvc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomvcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository	enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository ItemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat4 = new Categoria(null, "Decoração");
		Categoria cat5 = new Categoria(null, "Perfumaria");
		Categoria cat6 = new Categoria(null, "Jardinagem");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 50.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Rio de janeiro");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Rio das Ostras", est1);
		Cidade c2 = new Cidade(null, "Saquarema", est1);
		Cidade c3 = new Cidade(null, "Santos", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1, c2));
		est2.getCidades().addAll(Arrays.asList(c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Rodrigo", "rodrigo@mail.com", "121212121", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("9999-9999", "8888-8888"));
		
		Endereco e1 = new Endereco(null, "Rua ABC", "422", "bloco 5", "Abolição", "20751-200", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua XYZ", "150", "bloco 1", "Centro", "52001-200", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("20/07/2018 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("12/08/2018 21:15"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, 
				ped2, sdf.parse("20/08/2018 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.setPedidos(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		ItemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
	
	
	
	
	
	
	
}
