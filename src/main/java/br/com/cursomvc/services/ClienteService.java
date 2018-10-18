package br.com.cursomvc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.cursomvc.dto.ClienteDTO;
import br.com.cursomvc.dto.ClienteNovoDTO;
import br.com.cursomvc.models.Cidade;
import br.com.cursomvc.models.Cliente;
import br.com.cursomvc.models.Endereco;
import br.com.cursomvc.models.enums.Perfil;
import br.com.cursomvc.models.enums.TipoCliente;
import br.com.cursomvc.repositories.ClienteRepository;
import br.com.cursomvc.repositories.EnderecoRepository;
import br.com.cursomvc.security.Usuario;
import br.com.cursomvc.services.exceptions.AuthorizationException;
import br.com.cursomvc.services.exceptions.DataIntegrityException;
import br.com.cursomvc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;

	public List<Cliente>findAll(){
		return repo.findAll();
	}
	
	public Cliente findById(Integer id)  {
		
		Usuario usuario = UsuarioService.authenticated();
		System.out.println(usuario);
		if(usuario == null || !usuario.hasRole(Perfil.ADMIN) && !id.equals(usuario.getId())) { //se o usuario for nulo ou não for ADMIN e o ID nao for igual do usuario logado
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> cliente = repo.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
						"Cliente não encontrada! ID " + id + ", do tipo: " + Cliente.class.getName()));
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage,String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		return repo.findAll(pageRequest);
	}
	
	@Transactional
	public Cliente save(Cliente cliente){
		cliente.setId(null);
		cliente = repo.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}

	public Cliente update(Cliente cliente) {
		Cliente novoCliente = findById(cliente.getId()); //caso o ID seja inexistente será lançada uma exceção
		updateData(novoCliente, cliente);
		return repo.save(novoCliente);
	}
	
	private void updateData(Cliente novoCliente, Cliente cliente) { //método para evitar null em cpf e tipo
		novoCliente.setNome(cliente.getNome());
		novoCliente.setEmail(cliente.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		Usuario usuario = UsuarioService.authenticated();
		if(usuario == null) {
			throw new AuthorizationException("Acesso negado");
		}
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		String fileName = prefix + usuario.getId() + ".jpg"; //Ex: cp1.jpg - prefix=cp, id=1
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos relacionados	.");
		}	
	}

	public Cliente fromDTO(@Valid ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(@Valid ClienteNovoDTO objtDTO) {
		Cliente cli =  new Cliente(null , objtDTO.getNome(), 
				objtDTO.getEmail(), objtDTO.getCpfOuCnpj(), 
				TipoCliente.toEnum(objtDTO.getTipo()),
				bCryptPasswordEncoder.encode(objtDTO.getSenha()));
		
		Cidade cidade = new Cidade(objtDTO.getCidadeId(), null, null);
			
		Endereco end = new Endereco(null, objtDTO.getLogradouro(), 
				objtDTO.getNumero(), objtDTO.getComplemento(), 
				objtDTO.getBairro(), objtDTO.getCep(), cli, cidade);
		cli.getEnderecos().add(end);
		
		cli.getTelefones().add(objtDTO.getTelefone1());    //apenas telefone 1 é obrigatório
		if(objtDTO.getTelefone2() != null) {
			cli.getTelefones().add(objtDTO.getTelefone2());
		}
		if(objtDTO.getTelefone3() != null) {
			cli.getTelefones().add(objtDTO.getTelefone3());
		}
		return cli;
	}
	
}
