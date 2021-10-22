package emprestimo.livro.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import emprestimo.livro.domain.Pessoa;
import emprestimo.livro.repositories.PessoaRepository;
import emprestimo.livro.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	PessoaRepository pessoaRepository;

	@Autowired
	private BCryptPasswordEncoder pe;


	private Random rand = new Random();

	public void sendNewPassword(String login) {

		Pessoa cliente = pessoaRepository.findByLogin(login);

		if (cliente == null) {
			throw new ObjectNotFoundException("Login não encontrado");
		}

		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));

		pessoaRepository.save(cliente);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < vet.length; i++) {
			vet[i] = ramdomChar();
		}
		return new String(vet); 
	}

	private char ramdomChar() {
		int opt = rand.nextInt(3);

		switch (opt) {
		case 0: // gera digito
			return (char) (rand.nextInt(10) + 48);
		case 1:// gera maiuscula
			return (char) (rand.nextInt(26) + 65);
		default: // gera minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}

}
