package br.com.rfasioli.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rfasioli.cursomc.domain.ItemPedido;
import br.com.rfasioli.cursomc.domain.PagamentoComBoleto;
import br.com.rfasioli.cursomc.domain.Pedido;
import br.com.rfasioli.cursomc.domain.enums.EstadoPagamento;
import br.com.rfasioli.cursomc.repositories.ItemPedidoRepository;
import br.com.rfasioli.cursomc.repositories.PagamentoRepository;
import br.com.rfasioli.cursomc.repositories.PedidoRepository;
import br.com.rfasioli.cursomc.services.email.EmailService;
import br.com.rfasioli.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmailService emailService;

	public Pedido find(Integer id) throws ObjectNotFoundException {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
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
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
	
		itemPedidoRepository.saveAll(obj.getItens());
		
//		emailService.sendOrderConfirmationEmail(obj);
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
}
