package br.com.rfasioli.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.rfasioli.cursomc.domain.Cliente;
import br.com.rfasioli.cursomc.repositories.ClienteRepository;
import br.com.rfasioli.cursomc.services.email.EmailService;
import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder bpe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	
	public void sendNewPassword(String email) throws ObjectNotFoundException {
		Cliente cliente = clienteRepository.findByEmail(email)
				.orElseThrow(() -> new ObjectNotFoundException("Email não encontrado."));
		
		String newPass = newPassword();
		cliente.setSenha(bpe.encode(newPass));
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		switch (opt) {
		case 0:
			return (char) (rand.nextInt(10) + 48);
		case 1:
			return (char) (rand.nextInt(26) + 65);
		case 2:
			return (char) (rand.nextInt(26) + 97);
		}
		return 0;
	}
}
