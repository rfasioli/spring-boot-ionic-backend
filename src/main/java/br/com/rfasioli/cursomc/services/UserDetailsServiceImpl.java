package br.com.rfasioli.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.rfasioli.cursomc.domain.Cliente;
import br.com.rfasioli.cursomc.security.UserSS;
import br.com.rfasioli.cursomc.services.exception.ObjectNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired ClienteService clienteService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = null;
		try {
			cliente = clienteService.findByEmail(email);
		} catch (ObjectNotFoundException ex) {
			throw new UsernameNotFoundException(email, ex);
		}
		
		return new UserSS(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
	}

}
