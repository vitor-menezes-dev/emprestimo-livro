package emprestimo.livro.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import emprestimo.livro.domain.Pessoa;
import emprestimo.livro.services.validation.PessoaUpdate;

@PessoaUpdate
public class PessoaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Preenchimento Obrigatório")
	@Length(min = 5, max = 120, message = "O Tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

	@NotEmpty(message = "Preenchimento Obrigatório")
	private String login;

	private Date desativado;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date nascimento;
	
	private String senha;

	public PessoaDTO() {
	}

	public PessoaDTO(Pessoa obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.login = obj.getLogin();
		this.desativado = obj.getDesativado();
		this.nascimento = obj.getNascimento();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public void setDesativado(boolean isDesativado) {
		if (isDesativado)
			this.desativado = new Date();
		else
			this.desativado = null;
	}

	public boolean isDesativado() {
		return this.desativado != null;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonInclude(Include.NON_NULL)
	public Date getDesativadoDate() {
		return this.desativado ;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PessoaDTO [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (nome != null) {
			builder.append("nome=");
			builder.append(nome);
			builder.append(", ");
		}
		if (login != null) {
			builder.append("login=");
			builder.append(login);
			builder.append(", ");
		}
		if (desativado != null) {
			builder.append("desativado=");
			builder.append(desativado);
			builder.append(", ");
		}
		if (nascimento != null) {
			builder.append("nascimento=");
			builder.append(nascimento);
			builder.append(", ");
		}
		if (senha != null) {
			builder.append("senha=");
			builder.append(senha);
		}
		builder.append("]");
		return builder.toString();
	}

	
	
	
}
