package br.com.rfasioli.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.rfasioli.cursomc.domain.enums.TipoCliente;
import br.com.rfasioli.cursomc.dto.ClienteNewDTO;
import br.com.rfasioli.cursomc.resources.exception.FieldMessage;
import br.com.rfasioli.cursomc.services.validation.utils.DocumentUtilBR;

public class ClientInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>{

	@Override
	public void initialize(ClienteInsert ann) {
		
	}
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !DocumentUtilBR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido"));
		}
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !DocumentUtilBR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido"));
		}
		
		for (FieldMessage fieldMessage : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessage()).
				addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
		}
		
		return list.isEmpty();
	}

}
