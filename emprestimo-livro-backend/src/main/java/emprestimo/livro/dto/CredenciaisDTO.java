package emprestimo.livro.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import emprestimo.livro.services.validation.PessoaUpdate;

@PessoaUpdate
public class CredenciaisDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento Obrigatório")
	private String login;
	
	@NotEmpty(message = "Preenchimento Obrigatório")
	private String senha;
	
	public CredenciaisDTO() {
	}

	public CredenciaisDTO(
			@NotEmpty(message = "Preenchimento Obrigatório") String login,
			@NotEmpty(message = "Preenchimento Obrigatório") String senha) {
		super();
		this.login = login;
		this.senha = senha;
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
	
}
