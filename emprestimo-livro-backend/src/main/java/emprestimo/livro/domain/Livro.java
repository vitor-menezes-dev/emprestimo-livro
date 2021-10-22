package emprestimo.livro.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Livro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String isbn;
	private String titulo;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "AUTORES")
	private Set<String> autores = new HashSet<>();

	@JsonIgnore
	@OneToMany(mappedBy = "livro")
	private Set<Exemplar> exemplares = new HashSet<>();

	public Livro() {
	}

	public Livro(Integer id, String isbn, String titulo) {
		super();
		this.id = id;
		this.isbn = isbn;
		this.titulo = titulo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Set<String> getAutores() {
		return autores;
	}

	public void setAutores(Set<String> autores) {
		this.autores = autores;
	}

	public void addAutores(String autor) {
		this.autores.add(autor);
	}

	public Set<Exemplar> getExemplares() {
		return exemplares;
	}

	public void setExemplares(Set<Exemplar> exemplares) {
		this.exemplares = exemplares;
	}

	public void addExemplares(Exemplar exemplar) {
		this.exemplares.add(exemplar);
	}
}
