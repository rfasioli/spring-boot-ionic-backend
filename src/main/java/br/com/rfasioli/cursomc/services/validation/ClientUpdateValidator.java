package br.com.rfasioli.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.rfasioli.cursomc.dto.ClienteDTO;
import br.com.rfasioli.cursomc.repositories.ClienteRepository;
import br.com.rfasioli.cursomc.resources.exception.FieldMessage;

public class ClientUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO>{

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteUpdate ann) {
		
	}
	
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.valueOf(map.get("id"));

		List<FieldMessage> list = new ArrayList<>();
				
		if (clienteRepository.countByIdNotAndEmail(uriId, objDto.getEmail()) > 0) {
			list.add(new FieldMessage("Email", "Email já cadastrado."));			
		}

		for (FieldMessage fieldMessage : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessage()).
				addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
		}
		
		return list.isEmpty();
	}

}
