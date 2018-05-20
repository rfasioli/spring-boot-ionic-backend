package br.com.rfasioli.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rfasioli.cursomc.domain.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}
