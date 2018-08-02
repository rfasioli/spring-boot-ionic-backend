package br.com.rfasioli.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.rfasioli.cursomc.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			return (UserSS)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception ex) {
			return null;
		}
	}
}
