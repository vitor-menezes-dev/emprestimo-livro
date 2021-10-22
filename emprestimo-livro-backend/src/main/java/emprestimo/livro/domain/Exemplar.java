package emprestimo.livro.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Exemplar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "livro_id")
	private Livro livro;
	
	@JsonIgnore
	@OneToMany(mappedBy = "exemplar")
	private Set<Emprestimo> emprestimos = new HashSet<>();
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date adquirido;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dispensado;
	
	public Exemplar() {}
	
	public Exemplar(Integer id, Livro livro, Date adquirido) {
		super();
		this.id = id;
		this.livro = livro;
		this.adquirido = adquirido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Date getAdquirido() {
		return adquirido;
	}

	public void setAdquirido(Date adquirido) {
		this.adquirido = adquirido;
	}

	public Date getDispensado() {
		return dispensado;
	}

	public void setDispensado(Date dispensado) {
		this.dispensado = dispensado;
	}

	public Set<Emprestimo> getEmprestimos() {
		return emprestimos;
	}

	public void setEmprestimos(Set<Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}
	
	public void addEmprestimos(Emprestimo emprestimo) {
		this.emprestimos.add(emprestimo);
	}
	
}
