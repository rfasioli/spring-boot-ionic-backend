package br.com.rfasioli.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.rfasioli.cursomc.domain.Cliente;
import br.com.rfasioli.cursomc.dto.ClienteDTO;
import br.com.rfasioli.cursomc.repositories.ClienteRepository;
import br.com.rfasioli.cursomc.services.exception.DataIntegrityException;
import br.com.rfasioli.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;

	public Cliente find (Integer id) throws ObjectNotFoundException {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repo.save(obj);
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
	
	private Cliente updateData (Cliente current, Cliente obj) {
		if (obj.getNome() != null) current.setNome(obj.getNome());
		if (obj.getEmail() != null) current.setEmail(obj.getEmail());
		return current;
	}
	
}

