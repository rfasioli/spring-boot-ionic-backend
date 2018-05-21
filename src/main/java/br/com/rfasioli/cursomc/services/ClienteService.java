package br.com.rfasioli.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.rfasioli.cursomc.domain.Cidade;
import br.com.rfasioli.cursomc.domain.Cliente;
import br.com.rfasioli.cursomc.domain.Endereco;
import br.com.rfasioli.cursomc.domain.enums.TipoCliente;
import br.com.rfasioli.cursomc.dto.ClienteDTO;
import br.com.rfasioli.cursomc.dto.ClienteNewDTO;
import br.com.rfasioli.cursomc.repositories.ClienteRepository;
import br.com.rfasioli.cursomc.repositories.EnderecoRepository;
import br.com.rfasioli.cursomc.services.exception.DataIntegrityException;
import br.com.rfasioli.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired 
	private EnderecoRepository enderecoRepository;

	public Cliente find (Integer id) throws ObjectNotFoundException {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente current = this.find(obj.getId());
		updateData(current, obj);
		return repo.save(current);
	}
	
	public void delete(Integer id) {
		this.find(id);
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um Cliente que possui pedidos.");
		}
	}

	public List<Cliente> findAll()  {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDto(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);		
	}

	public Cliente fromDto(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidade(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) cli.getTelefones().add(objDto.getTelefone2());
		if (objDto.getTelefone3() != null) cli.getTelefones().add(objDto.getTelefone3());
		return cli;
	}

	private Cliente updateData (Cliente current, Cliente obj) {
		if (obj.getNome() != null) current.setNome(obj.getNome());
		if (obj.getEmail() != null) current.setEmail(obj.getEmail());
		return current;
	}
	
}

