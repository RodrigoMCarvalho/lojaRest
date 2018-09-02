package br.com.cursomvc.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.cursomvc.dto.ClienteDTO;
import br.com.cursomvc.models.Cliente;
import br.com.cursomvc.repositories.ClienteRepository;
import br.com.cursomvc.services.exceptions.DataIntegrityException;
import br.com.cursomvc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public List<Cliente>findAll(){
		return repo.findAll();
	}
	
	public Cliente findById(Integer id)  {
		Optional<Cliente> cliente = repo.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
						"Cliente não encontrada! ID " + id + ", do tipo: " + Cliente.class.getName()));
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage,String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente save(Cliente cliente){
		cliente.setId(null);
		return repo.save(cliente);
	}

	public Cliente update(Cliente cliente) {
		Cliente novoCliente = findById(cliente.getId()); //caso o ID seja inexistente será lançada uma exceção
		updateData(novoCliente, cliente);
		return repo.save(novoCliente);
	}
	
	private void updateData(Cliente novoCliente, Cliente cliente) { //método para evitar null em cpf e tipo
		novoCliente.setNome(cliente.getNome());
		novoCliente.setEmail(cliente.getNome());
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir esse cliente");
		}	
	}

	public Cliente fromDTO(@Valid ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
}
