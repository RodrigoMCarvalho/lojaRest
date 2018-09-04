package br.com.cursomvc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.cursomvc.dto.ClienteNovoDTO;
import br.com.cursomvc.models.enums.TipoCliente;
import br.com.cursomvc.resources.exceptions.FieldMessage;
import br.com.cursomvc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNovoDTO>{
	
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

		for (FieldMessage e : listError) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(e.getMessage())
		.addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return listError.isEmpty(); //se a lista de erros estiver vazia, retorna true
	}

}
