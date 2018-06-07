package br.com.rfasioli.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rfasioli.cursomc.domain.ItemPedido;
import br.com.rfasioli.cursomc.domain.PagamentoComBoleto;
import br.com.rfasioli.cursomc.domain.Pedido;
import br.com.rfasioli.cursomc.domain.enums.EstadoPagamento;
import br.com.rfasioli.cursomc.repositories.ItemPedidoRepository;
import br.com.rfasioli.cursomc.repositories.PagamentoRepository;
import br.com.rfasioli.cursomc.repositories.PedidoRepository;
import br.com.rfasioli.cursomc.repositories.ProdutoRepository;
import br.com.rfasioli.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;

	public Pedido find(Integer id) throws ObjectNotFoundException {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = pedidoRepository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoRepository.getOne(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
	
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
	
}
