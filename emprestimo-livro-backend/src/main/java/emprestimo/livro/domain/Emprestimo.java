package emprestimo.livro.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Emprestimo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne()
	@JoinColumn(name = "exemplar_id")
	private Exemplar exemplar;

	@ManyToOne()
	@JoinColumn(name = "pessoa_id")
	private Pessoa pessoa;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date retirada;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date devolvido;

	public Emprestimo() {
	}

	public Emprestimo(Integer id, Exemplar exemplar, Pessoa pessoa, Date retirada) {
		super();
		this.id = id;
		this.exemplar = exemplar;
		this.pessoa = pessoa;
		this.retirada = retirada;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Exemplar getExemplar() {
		return exemplar;
	}

	public void setExemplar(Exemplar exemplar) {
		this.exemplar = exemplar;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Date getRetirada() {
		return retirada;
	}

	public void setRetirada(Date retirada) {
		this.retirada = retirada;
	}

	public Date getDevolvido() {
		return devolvido;
	}

	public void setDevolvido(Date devolvido) {
		this.devolvido = devolvido;
	}

}
