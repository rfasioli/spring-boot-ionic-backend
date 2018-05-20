package br.com.rfasioli.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rfasioli.cursomc.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
