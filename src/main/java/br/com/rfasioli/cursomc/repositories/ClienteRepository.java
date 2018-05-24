package br.com.rfasioli.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.rfasioli.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	@Transactional(readOnly=true)
	long countByEmail(String email);
	
	@Transactional(readOnly=true)
	long countByIdNotAndEmail(Integer id, String email);
	
}
