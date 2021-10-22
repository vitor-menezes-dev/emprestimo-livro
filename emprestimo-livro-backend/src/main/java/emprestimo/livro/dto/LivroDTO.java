package emprestimo.livro.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import emprestimo.livro.domain.Livro;

public class LivroDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String isbn;
	private String titulo;
	private Set<String> autores = new HashSet<>();
	private Set<ExemplarDTO> exemplares = new HashSet<>();
	
	public LivroDTO(Livro livro) {
		super();
		this.id = livro.getId();
		this.isbn = livro.getIsbn();
		this.titulo = livro.getTitulo();
		this.autores = livro.getAutores();
		livro.getExemplares().forEach(obj -> this.addExemplares(new ExemplarDTO(obj)));
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
	public Set<ExemplarDTO> getExemplares() {
		return exemplares;
	}
	public void setExemplares(Set<ExemplarDTO> exemplares) {
		this.exemplares = exemplares;
	}
	
	public void addExemplares(ExemplarDTO exemplar) {
		this.exemplares.add(exemplar);
	}


}
