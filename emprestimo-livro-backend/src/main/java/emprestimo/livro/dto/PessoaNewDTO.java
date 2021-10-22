package emprestimo.livro.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import emprestimo.livro.services.validation.PessoaInsert;

@PessoaInsert
public class PessoaNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento Obrigatório")
	@Length(min = 5, max = 200, message = "O Tamanho deve ser entre 5 e 200 caracteres")
	private String nome;

	@NotEmpty(message = "Preenchimento Obrigatório")
	private String login;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	private String senha;

	private Date nascimento;


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

}
