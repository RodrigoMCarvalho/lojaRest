package br.com.cursomvc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.cursomvc.dto.ClienteDTO;
import br.com.cursomvc.models.Cliente;
import br.com.cursomvc.repositories.ClienteRepository;
import br.com.cursomvc.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO>{
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private HttpServletRequest request;
	
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDTO, ConstraintValidatorContext context) {
		List<FieldMessage> listError = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		Integer uriId= Integer.parseInt(map.get("id"));
		
		Cliente clienteEmail = repo.findByEmail(objDTO.getEmail());
		if(clienteEmail != null && !clienteEmail.getId().equals(uriId)) {
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
