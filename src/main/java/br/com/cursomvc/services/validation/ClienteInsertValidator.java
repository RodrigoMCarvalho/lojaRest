package br.com.cursomvc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.cursomvc.dto.ClienteNovoDTO;
import br.com.cursomvc.models.Cliente;
import br.com.cursomvc.models.enums.TipoCliente;
import br.com.cursomvc.repositories.ClienteRepository;
import br.com.cursomvc.resources.exceptions.FieldMessage;
import br.com.cursomvc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNovoDTO>{
	
	@Autowired
	private ClienteRepository repo;
	
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNovoDTO objDTO, ConstraintValidatorContext context) {
		List<FieldMessage> listError = new ArrayList<>();
		
		if(objDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !BR.isValidCPF(objDTO.getCpfOuCnpj())) {
			listError.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if(objDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !BR.isValidCNPJ(objDTO.getCpfOuCnpj())) {
			listError.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente clienteEmail = repo.findByEmail(objDTO.getEmail());
		if(clienteEmail != null) {
			listError.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : listError) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(e.getMessage())
		.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return listError.isEmpty(); //se a lista de erros estiver vazia, retorna true
	}

}
